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
	private static final String SMILES = "smiles";
	protected @Inject Login smilesLogin;

	public NavigateGolSmiles(HashMap<String, String> urls) {
		this.url = urls.get(SMILES);
		smilesLogin = FileReadService.readPersonalDetailsFromFile();
	}

	public void loginUserSpace() {
		this.initializeDriver();

		navigateThroughInternalFramesGol();

		inputLogin(smilesLogin.getLogin(), Ids.golInputLoginxPath);
		inputPassWord(smilesLogin.getPassword(), Ids.golInputPswdxPath);
		actionClickElement(Ids.golSubmitLoginxPath);

		gotoSmilesSearchPage();
	}

	private void gotoSmilesSearchPage() {
		navigateThroughInternalFramesGol();
		WaitCondition.waitElementVisible(Ids.golGoToTicketsId, driver);
		WaitCondition.waitElementClicable(Ids.golGoToTicketsId, driver);

		actionClickElement(Ids.golGoToTicketsId);
		WaitCondition.waitFrameLoadedxPath(driver, Ids.golInternalFramexPath);
	}

	@Override
	public FlightMatches searchFlightsFirstLoop(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		WaitCondition.waitElementClicable(Ids.golOneWayxPath, driver);
		this.radioOneWayTrip(flt.getOneWay(), Ids.golOneWayxPath);

		this.chooseAirport(Ids.golFromxPath, Ids.golToxPath, trip);
		this.chooseDate(Ids.golInputTripDay, Ids.golInputReturnDay, dt_m, flt.getOneWay());

		this.actionClickElement(Ids.golSubmitFirstSearch);

		return this.extractFlightDetails(dt_m, fare_m, flt);
	}

	public ArrayList<FlightMatches> loopDates(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		WaitCondition.waitPageLoaded(driver);
		
		ArrayList<FlightMatches> matches = new ArrayList<FlightMatches>();

		while (dt_m.forwardPeriod()) {
			this.chooseDate(Ids.golInputTripDay, Ids.golInputReturnDay, dt_m, flt.getOneWay());

			this.actionClickElement(Ids.golSubmitLoopSearch);
			matches.add(this.extractFlightDetails(dt_m, fare_m, flt));
		}
		
		dt_m.resetFlightDates();
		
		return matches;
	}

	@Override
	public FlightMatches loopSearchFlights(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
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

		driver.findElement(By.xpath(Ids.golSubmitLoopSearch)).click();
		
		return extractFlightDetails(dt_m,fare_m, flt);
	}

	@Override
	public FlightMatches extractFlightDetails(DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		WaitCondition.waitElementVisible(Ids.golResultsDeparture, driver);

		FlightMatches searchMatches = initFlightMatches(dt_m, fare_m, flt);

		// departure flights
		List<WebElement> departures = driver.findElements(By.xpath(Ids.golResultsDeparture));
		if (departures != null && departures.size() > 0) {
			departures.remove(0);// remove header
		}
		searchMatches.setOutBoundFlights(extractListDetails(departures, dt_m.getEarliestDeparture()));

		if (!flt.getOneWay()) {
			// return flights
			List<WebElement> arrives = driver.findElements(By.xpath(Ids.golResultsReturn));
			arrives.remove(0);// remove header

			searchMatches.setInBoundFlights(extractListDetails(arrives, dt_m.getLatestReturn()));
		}

		return searchMatches;
	}

	private void chooseAirport(String xPathDropDownFrom, String xPathDropDownTo, Trip trip) {
		driver.findElement(By.xpath(xPathDropDownFrom)).click();
		chooseFromItemList(driver.findElements(By.xpath(Ids.golDropDownListOrigin)), trip.from());

		driver.findElement(By.xpath(xPathDropDownTo)).click();
		chooseFromItemList(driver.findElements(By.xpath(Ids.golDropDownListDestination)), trip.to());
	}

	public ArrayList<FlightDetails> extractListDetails(List<WebElement> departures, Calendar flightTime) {
		WaitCondition.waitPageLoaded(driver);

		ArrayList<FlightDetails> listaFlights = new ArrayList<FlightDetails>();

		for (WebElement webElement : departures) {
			WebElement flightDetails = webElement.findElement(By.cssSelector(Ids.CONTENT_FLIGHT_CSS));
			WebElement flightPrice = webElement.findElement(By.cssSelector(Ids.CONTENT_TARIFAS_CSS));
			// TODO: verificar o conflitos de datas fixas do voo de partida e
			// chegada (podem ocorrer em dias diferentes e consequentemnte em
			// anos diferentes)
			String leave = flightDetails.findElement(By.cssSelector(Ids.Flight_Outbound_CSS)).getText();
			String arrive = flightDetails.findElement(By.cssSelector(Ids.Fligh_Inbound_CSS)).getText();
			String code = flightDetails.findElement(By.cssSelector(Ids.Flight_Code_CSS)).getText();
			String duration = flightDetails.findElement(By.cssSelector(Ids.FlyingTime_CSS)).getText();
			
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
		WaitCondition.waitFrameLoaded(driver, Ids.golClientFrame);
		// driver = driver.switchTo().frame(0);// to the first frame
		WaitCondition.waitFrameLoaded(driver, Ids.golContentFrame);
		// driver = driver.switchTo().frame(1); // to the swecontent frame
		WaitCondition.waitFrameLoaded(driver, Ids.golViewFrame);
		// driver = driver.switchTo().frame("_sweview"); // to the main view
		// frame
	}
}
