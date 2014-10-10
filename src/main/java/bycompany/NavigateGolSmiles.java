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
import navigation.DateManager;
import navigation.FaresManager;
import navigation.TripManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import util.FileReadService;
import util.ParserFlightGol;
import conditional.WaitCondition;
import enums.NationalAirports;

public class NavigateGolSmiles extends SearchToolInstance {
	private final String golResultsDeparture = "id('site')/div[6]/div[3]/div";
	private final String golResultsReturn = "id('site')/div[7]/div[3]/div";
	private final String golSubmitFirstSearch = "id('toCategory')/a";
	private final String golInputReturn = "id('datepickerInputVolta')";
	private final String golInputTripDay = "id('datepickerInputIda')";
	private final String golInputLoginxPath = "id('s_3_1_8_0')";
	private final String golInputPswdxPath = "id('s_3_1_9_0')";
	private final String golSubmitLoginxPath = "id('s_3_1_11_0')";
	private final String golGoToTicketsId = "id('s_4_1_4_0')";
	private final String golOneWayxPath = ".//*[@id='tripRadio']/li[2]/input";
	private final String golFromxPath = "id('fs_container_origins[0]')/a";
	private final String golToxPath = "id('fs_container_destinations[0]')/a";
	protected @Inject Login smilesLogin;

	public NavigateGolSmiles(HashMap<String, String> urls) {
		this.url = urls.get("smiles");
		smilesLogin = FileReadService.readPersonalDetailsFromFile();
	}

	public void loginUserSpace() {
		this.initializeDriver();

		WaitCondition.waitPageLoaded(driver);

		navigateThroughInternalFramesGol();

		inputLogin(smilesLogin.getLogin(), golInputLoginxPath);
		inputPassWord(smilesLogin.getPassword(), golInputPswdxPath);
		actionClickElement(golSubmitLoginxPath);

		gotoSmilesSearchPage();
	}

	private void gotoSmilesSearchPage() {
		WaitCondition.waitPageLoaded(driver);

		navigateThroughInternalFramesGol();
		WaitCondition.waitElementVisible(golGoToTicketsId, driver);
		WaitCondition.waitElementClicable(golGoToTicketsId, driver);

		actionClickElement(golGoToTicketsId);
	}

	@Override
	public void searchFlights(TripManager trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		WaitCondition.waitPageLoaded(driver);

		this.radioOneWayTrip(flt.getOneWay(), this.golOneWayxPath);

		this.chooseAirport(golFromxPath, golToxPath, trip);
		this.chooseDate(golInputTripDay, golInputReturn, dt_m, flt.getOneWay());

		this.actionClickElement(golSubmitFirstSearch);
	}

	@Override
	public void loopSearchFlights(TripManager trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		WaitCondition.waitPageLoaded(driver);
		// TODO Auto-generated method stub

	}
	
	public ArrayList<FlightDetails> extractFlightDetails(NationalAirports dep, NationalAirports des, boolean oneWay) {
		WaitCondition.waitElementVisible(golResultsDeparture, driver);

		FlightMatches searchMatches = initFlightMatches(oneWay);

		// departure flights
		List<WebElement> departures = driver.findElements(By.xpath(golResultsDeparture));
		if (departures != null && departures.size() > 0) {
			departures.remove(0);// remove header
		}
		searchMatches.setOutBoundFlights(extractListDetails(departures, golResultsReturn, dep, des));

		if (!oneWay) {
			// return flights
			List<WebElement> arrives = driver.findElements(By.xpath("id('site')/div[7]/div[3]/div"));
			arrives.remove(0);// remove header

			searchMatches.setInBoundFlights(extractListDetails(arrives, latestReturnDate, dep, des));
		}

		return searchMatches.bestMilesFares();
	}

	@Override
	public void extractFlightDetails(List<WebElement> details, Calendar flightTime, NationalAirports to) {
		WaitCondition.waitPageLoaded(driver);
		
		ArrayList<FlightDetails> listaFlights = new ArrayList<FlightDetails>();

		for (WebElement webElement : details) {
			WebElement flightDetails = webElement.findElement(By.cssSelector(".contentFlight"));
			WebElement flightPrice = webElement.findElement(By.cssSelector(".contentTarifas"));

			String code = flightDetails.findElement(By.cssSelector(".voo-titulo")).getText();
			String leave = flightDetails.findElement(By.cssSelector(".saida-voo")).getText();
			String arrive = flightDetails.findElement(By.cssSelector(".chegada-voo")).getText();
			String duration = flightDetails.findElement(By.cssSelector(".duracao-voo")).getText();

			// TODO: verificar o conflitos de datas fixas do voo de partida e
			// chegada (podem ocorrer em dias diferentes e consequentemnte em
			// anos diferentes)
			Calendar departureDate = ParserFlightGol.returnGolPatternCalendar(leave, flightTime.get(Calendar.DATE), flightTime.get(Calendar.MONTH),
					flightTime.get(Calendar.YEAR));
			Calendar returnDate = ParserFlightGol.returnGolPatternCalendar(arrive, flightTime.get(Calendar.DATE), flightTime.get(Calendar.MONTH),
					flightTime.get(Calendar.YEAR));

			FlightDetails flight = ParserFlightGol.parseTo(code, departureDate, returnDate, duration, flightPrice.getText(), dep, des);
			if (flight != null) {
				listaFlights.add(flight);
			}
		}
		//return listaFlights;

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
		driver = driver.switchTo().frame(0);// to the first frame
		driver = driver.switchTo().frame(1); // to the swecontent frame
		driver = driver.switchTo().frame("_sweview"); // to the main view frame
	}
}
