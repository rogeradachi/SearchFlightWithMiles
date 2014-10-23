package bycompany;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import model.FlightDetails;
import model.FlightMatches;
import model.FlightSingleResult;
import model.Login;
import model.SearchFilter;
import model.Trip;
import navigation.DateManager;
import navigation.TripManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import util.FileReadService;
import util.ParserFlightGol;
import conditional.WaitCondition;
import enums.Company;

public class NavigateGolSmiles extends SearchToolInstance {
	private static final String SMILES = "smiles";
	protected @Inject Login smilesLogin;

	public NavigateGolSmiles(HashMap<String, String> urls, SearchFilter flt, TripManager trip_m, DateManager dt_m) {
		this.url = urls.get(SMILES);
		this.flt = flt;
		this.trip_m = trip_m;
		this.dt_m = dt_m;
		smilesLogin = FileReadService.readPersonalDetailsFromFile();
	}
	
	public FlightMatches firstLoopSmiles() throws FileNotFoundException, UnsupportedEncodingException{
		this.loginUserSpace();
		
		Trip trip = trip_m.next();
		
		FlightMatches match = new FlightMatches(flt, trip.fromObj(), trip.toObj());
		match.addListResults(this.searchFlightsFirstLoop(trip));
		match.addListResults(this.loopSearchFlights(trip));
		
		return match;
	}
	
	public ArrayList<FlightMatches> doSearchSmiles() throws FileNotFoundException, UnsupportedEncodingException{
		trip_m = new TripManager(Company.GOL);
		firstLoopSmiles();
		
		ArrayList<FlightMatches> matches = new ArrayList<FlightMatches>();
		FlightMatches match;
		
		Trip trip = trip_m.next();
		while(trip != null){
			match = new FlightMatches(flt, trip.fromObj(), trip.toObj());
			match.getListResults().addAll(this.loopSearchFlights(trip));
			
			matches.add(match);
			
			dt_m.resetFlightDates();
			trip = trip_m.next();
		}
		
		return matches;
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
	public FlightSingleResult searchFlightsFirstLoop(Trip trip) {
		WaitCondition.waitElementClicable(Ids.golOneWayxPath, driver);
		this.radioOneWayTrip(flt.getOneWay(), Ids.golOneWayxPath);

		this.chooseAirport(Ids.golFromxPath, Ids.golToxPath, trip);
		this.chooseDate(Ids.golInputTripDay, Ids.golInputReturnDay, dt_m, flt.getOneWay());

		this.actionClickElement(Ids.golSubmitFirstSearch);

		return this.extractFlightDetails();
	}

	public ArrayList<FlightSingleResult> loopDates(Trip trip, DateManager dt_m, SearchFilter flt) {
		WaitCondition.waitPageLoaded(driver);

		ArrayList<FlightSingleResult> results = new ArrayList<FlightSingleResult>();

		while (dt_m.forwardPeriod()) {
					
			this.chooseDate(Ids.golInputTripDay, Ids.golInputReturnDay, dt_m, flt.getOneWay());

			this.actionClickElement(Ids.golSubmitLoopSearch);
			results.add(this.extractFlightDetails());
		}

		dt_m.resetFlightDates();

		return results;
	}

	@Override
	public ArrayList<FlightSingleResult> loopSearchFlights(Trip trip) {
		WaitCondition.waitElementClicable(Ids.golFromxPath, driver);

		this.chooseAirport(Ids.golFromxPath, Ids.golToxPath, trip);
		ArrayList<FlightSingleResult> results = this.loopDates(trip, dt_m, flt);

		return results;
	}

	@Override
	public FlightSingleResult extractFlightDetails() {
		WaitCondition.waitElementVisible(Ids.golResultsDeparture, driver);

		FlightSingleResult singleResult = new FlightSingleResult(dt_m.getEarliestDeparture(), dt_m.getLatestReturn());

		// departure flights
		List<WebElement> departures = driver.findElements(By.xpath(Ids.golResultsDeparture));
		if (departures != null && departures.size() > 0) {
			departures.remove(0);// remove header
		}
		singleResult.setDepartureFLights(extractListDetails(departures, dt_m.getEarliestDeparture()));

		if (!flt.getOneWay()) {
			// return flights
			List<WebElement> arrives = driver.findElements(By.xpath(Ids.golResultsReturn));
			arrives.remove(0);// remove header

			singleResult.setReturnFlights(extractListDetails(arrives, dt_m.getLatestReturn()));
		}

		return singleResult;
	}

	public void chooseAirport(String xPathDropDownFrom, String xPathDropDownTo, Trip trip) {
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
