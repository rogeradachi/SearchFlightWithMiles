package bycompany;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import model.FlightDetails;
import model.FlightMatches;
import model.Login;
import model.SearchFilter;
import model.SmilesExtract;
import model.Trip;
import navigation.DateManager;
import navigation.FaresManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import util.FileReadService;
import util.ParserFlightGol;
import conditional.WaitCondition;

public class NavigateGolSmiles extends SearchToolInstance {
	private static String golSubmitLoopSearch = "id('search')/div[3]/a";
	private static String FlyingTime_CSS = ".duracao-voo";
	private static String Flight_Code_CSS = ".voo-titulo";
	private static String Fligh_Inbound_CSS = ".chegada-voo";
	private static String Flight_Outbound_CSS = ".saida-voo";
	private static String CONTENT_TARIFAS_CSS = ".contentTarifas";
	private static String CONTENT_FLIGHT_CSS = ".contentFlight";
	private static String golDropDownListDestination = "id('fs_popUp_destinations[0]')/ul/li";
	private static String golDropDownListOrigin = "id('fs_popUp_origins[0]')/ul/li";
	private final String golResultsDeparture = "id('site')/div[6]/div[3]/div";
	private final String golResultsReturn = "id('site')/div[7]/div[3]/div";
	private final String golSubmitFirstSearch = "id('toCategory')/a";
	private final String golInputReturnDay = "id('datepickerInputVolta')";
	private final String golInputTripDay = "id('datepickerInputIda')";
	private final String golInputLoginxPath = "id('s_3_1_8_0')";
	private final String golInputPswdxPath = "id('s_3_1_9_0')";
	private final String golSubmitLoginxPath = "id('s_3_1_11_0')";
	private final String golGoToTicketsId = ".//*[@id='s_4_1_4_0']";
	private final String golOneWayxPath = ".//*[@id='tripRadio']/li[2]/input";
	private final String golFromxPath = "id('fs_container_origins[0]')/a";
	private final String golToxPath = "id('fs_container_destinations[0]')/a";
	private final String golInternalFramexPath = ".//*[@id='symbUrlIFrame2']";
	
	protected @Inject Login smilesLogin;

	public NavigateGolSmiles(HashMap<String, String> urls) {
		this.url = urls.get("smiles");
		smilesLogin = FileReadService.readPersonalDetailsFromFile();
	}

	public void loginUserSpace() {
		this.initializeDriver();

		navigateThroughInternalFramesGol();

		inputLogin(smilesLogin.getLogin(), golInputLoginxPath);
		inputPassWord(smilesLogin.getPassword(), golInputPswdxPath);
		actionClickElement(golSubmitLoginxPath);

		gotoSmilesSearchPage();
	}

	private void gotoSmilesSearchPage() {
		navigateThroughInternalFramesGol();
		WaitCondition.waitElementVisible(golGoToTicketsId, driver);
		WaitCondition.waitElementClicable(golGoToTicketsId, driver);

		actionClickElement(golGoToTicketsId);
		WaitCondition.waitFrameLoadedxPath(driver, this.golInternalFramexPath);
	}

	@Override
	public void searchFlights(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		WaitCondition.waitElementClicable(this.golOneWayxPath, driver);
		this.radioOneWayTrip(flt.getOneWay(), this.golOneWayxPath);

		this.chooseAirport(this.golFromxPath, golToxPath, trip);
		this.chooseDate(golInputTripDay, golInputReturnDay, dt_m, flt.getOneWay());

		this.actionClickElement(golSubmitFirstSearch);

		this.extractFlightDetails(trip, dt_m, fare_m, flt);
	}

	public void loopDates(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		WaitCondition.waitPageLoaded(driver);

		while (dt_m.forwardPeriod()) {
			this.chooseDate(golInputTripDay, golInputReturnDay, dt_m, flt.getOneWay());

			this.actionClickElement(golSubmitLoopSearch);
			this.extractFlightDetails(trip, dt_m, fare_m, flt);
		}
	}

	@Override
	public void loopSearchFlights(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		WaitCondition.waitElementClicable("id('fs_container_origins[0]')/a", driver);

		/* Departure Airport */
		/* dropdown list has to become active */
		driver.findElement(By.xpath("id('fs_container_origins[0]')/a")).click();
		List<WebElement> originLi = driver.findElements(By.xpath("id('fs_popUp_origins[0]')/ul/li"));
		//chooseFromItemList(originLi, origem);

		/* Destination Airport */
		/* dropdown list has to become active */
		driver.findElement(By.xpath("id('fs_container_destinations[0]')/a")).click();
		List<WebElement> destinationLi = driver.findElements(By.xpath("id('fs_popUp_destinations[0]')/ul/li"));
		//chooseFromItemList(destinationLi, destino);

		//chooseDepartureDate(DATEPICKER_INPUT_IDA);
		//if (!oneWay) {
		//	chooseReturnDate(DATEPICKER_INPUT_VOLTA);
		//}

		driver.findElement(By.xpath(golSubmitLoopSearch)).click();
	}

	@Override
	public ArrayList<FlightDetails> extractFlightDetails(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		WaitCondition.waitElementVisible(golResultsDeparture, driver);

		FlightMatches searchMatches = initFlightMatches(dt_m, fare_m, flt);

		// departure flights
		List<WebElement> departures = driver.findElements(By.xpath(golResultsDeparture));
		if (departures != null && departures.size() > 0) {
			departures.remove(0);// remove header
		}
		searchMatches.setOutBoundFlights(extractListDetails(departures, dt_m.getEarliestDeparture()));

		if (!flt.getOneWay()) {
			// return flights
			List<WebElement> arrives = driver.findElements(By.xpath(golResultsReturn));
			arrives.remove(0);// remove header

			searchMatches.setInBoundFlights(extractListDetails(arrives, dt_m.getLatestReturn()));
		}

		return searchMatches.getBestFares();
	}

	private void chooseAirport(String xPathDropDownFrom, String xPathDropDownTo, Trip trip) {
		driver.findElement(By.xpath(xPathDropDownFrom)).click();
		chooseFromItemList(driver.findElements(By.xpath(golDropDownListOrigin)), trip.from());

		driver.findElement(By.xpath(xPathDropDownTo)).click();
		chooseFromItemList(driver.findElements(By.xpath(golDropDownListDestination)), trip.to());
	}

	public ArrayList<FlightDetails> extractListDetails(List<WebElement> departures, Calendar flightTime) {
		WaitCondition.waitPageLoaded(driver);

		ArrayList<FlightDetails> listaFlights = new ArrayList<FlightDetails>();

		for (WebElement webElement : departures) {
			WebElement flightDetails = webElement.findElement(By.cssSelector(CONTENT_FLIGHT_CSS));
			WebElement flightPrice = webElement.findElement(By.cssSelector(CONTENT_TARIFAS_CSS));
			// TODO: verificar o conflitos de datas fixas do voo de partida e
			// chegada (podem ocorrer em dias diferentes e consequentemnte em
			// anos diferentes)
			String leave = flightDetails.findElement(By.cssSelector(Flight_Outbound_CSS)).getText();
			String arrive = flightDetails.findElement(By.cssSelector(Fligh_Inbound_CSS)).getText();
			String code = flightDetails.findElement(By.cssSelector(Flight_Code_CSS)).getText();
			String duration = flightDetails.findElement(By.cssSelector(FlyingTime_CSS)).getText();
			
			FlightDetails flight = ParserFlightGol.parseTo(leave, arrive, flightTime, code, duration, flightPrice.getText());
			if (flight != null) {
				listaFlights.add(flight);
			}
		}
		return listaFlights;
	}

	private void chooseFromItemList(List<WebElement> listItem, String destination) {
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(destination.toString()).append(")");
		for (WebElement webElement : listItem) {
			if (webElement.getText().contains(builder.toString())) {
				webElement.click();
			}
		}
	}

	private void inputLogin(String loginName, String id) {
		WebElement input = driver.findElement(By.xpath(id));
		input.sendKeys(loginName);
	}

	private void inputPassWord(String loginName, String pswd) {
		WebElement input = driver.findElement(By.xpath(pswd));
		input.sendKeys(loginName);
	}

	private void navigateThroughInternalFramesGol() {
		WaitCondition.waitFrameLoaded(driver, "_sweclient");
		// driver = driver.switchTo().frame(0);// to the first frame
		WaitCondition.waitFrameLoaded(driver, "_swecontent");
		// driver = driver.switchTo().frame(1); // to the swecontent frame
		WaitCondition.waitFrameLoaded(driver, "_sweview");
		// driver = driver.switchTo().frame("_sweview"); // to the main view
		// frame
	}
}
