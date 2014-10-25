package bycompany;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import model.FlightDetails;
import model.FlightMatches;
import model.FlightSingleResult;
import model.MultiplusExtract;
import model.SearchFilter;
import model.SearchToolInstance;
import model.Trip;
import navigation.DateManager;
import navigation.TripManager;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import util.Help;
import util.ParserFlightTam;
import conditional.WaitCondition;
import enums.Company;
import enums.Ids;

public class NavigateTamMultiplus extends SearchToolInstance {

	public NavigateTamMultiplus(HashMap<String, String> urls, SearchFilter flt, TripManager trip_m, DateManager dt_m) {
		this.url = urls.get("multiplus");
		this.flt = flt;
		this.trip_m = trip_m;
		this.dt_m = dt_m;
	}

	private void navigateThroughInternalFramesTam() {
		// WaitCondition.waitFrameLoaded(driver, Ids.multiPlusFrameLojinha);
		WaitCondition.waitPageLoaded(driver);
		driver.switchTo().frame("lojinha");// to the frame lojinha
	}

	public FlightMatches firstLoopMultiplus() throws FileNotFoundException, UnsupportedEncodingException {
		this.initializeDriver();
		navigateThroughInternalFramesTam();

		Trip trip = trip_m.next();

		FlightMatches match = new FlightMatches(flt, trip.fromObj(), trip.toObj(), Company.TAM.toString());
		match.addListResults(this.searchFlightsFirstLoop(trip));
		match.addAllListResults(this.loopSearchFlights(trip));

		return match;
	}

	public ArrayList<FlightMatches> searchMultiplus() throws FileNotFoundException, UnsupportedEncodingException {
		ArrayList<FlightMatches> matches = new ArrayList<FlightMatches>();
		trip_m = new TripManager(Company.TAM);
		matches.add(firstLoopMultiplus());

		FlightMatches match;

		Trip trip = trip_m.next();
		while (trip != null) {
			match = new FlightMatches(flt, trip.fromObj(), trip.toObj(), Company.TAM.toString());
			match.getListResults().addAll(this.loopSearchFlights(trip));
			matches.add(match);

			dt_m.resetFlightDates();
			trip = trip_m.next();
		}

		this.closeDriver();

		return matches;
	}

	@Override
	public FlightSingleResult searchFlightsFirstLoop(Trip trip) {
		WaitCondition.waitElementClicable(Ids.multiPlusMilesxPath, driver);
		actionClickElement(Ids.multiPlusMilesxPath);

		if (flt.getOneWay()) {
			actionClickElement(Ids.multiPlusOneWayXpath);
		} else {
			chooseDateMultiplus(Ids.multiPlusFlightDatexPath, Ids.multiPlusReturnDatexPath, dt_m, flt.getOneWay());
		}
		chooseAirport(Ids.multiPlusOriginxPath, Ids.multiPlusDestinationxPath, trip);

		actionClickElement(Ids.multiPlusSubmitFirstxPath);

		return this.extractFlightDetails();
	}

	@Override
	public ArrayList<FlightSingleResult> loopSearchFlights(Trip trip) {
		WaitCondition.waitElementClicable(Ids.multiPlusOriginxPath, driver);

		inputAirportMultiplus(Ids.multiPlusOriginxPath, Ids.multiPlusDestinationxPath, trip);
		ArrayList<FlightSingleResult> results = this.loopDates(trip, dt_m, flt);

		return results;
	}

	private void inputAirportMultiplus(String xpathFrom, String xpathTo, Trip trip) {
		WebElement departure = driver.findElement(By.xpath(xpathFrom));
		departure.click();
		departure.sendKeys(trip.from());

		WebElement return_ = driver.findElement(By.xpath(xpathTo));
		return_.click();
		return_.sendKeys(trip.to());
	}

	@Override
	public FlightSingleResult extractFlightDetails() {
		WaitCondition.waitElementVisible(Ids.multiPlusResultTableFlightxPath, driver);

		FlightSingleResult singleResult = new FlightSingleResult(dt_m.getEarliestDeparture(), dt_m.getLatestReturn());

		WebElement departureResults;
		try {
			departureResults = driver.findElement(By.xpath(Ids.multiPlusResultTableFlightxPath));
		} catch (Exception ex) {
			return null;
		}

		List<WebElement> outboundsTR = departureResults.findElements(By.cssSelector(Ids.multiPlusFlight_CSS));

		ArrayList<FlightDetails> departure = this.extractFlightsInfo(outboundsTR, dt_m.getEarliestDeparture(), flt);
		singleResult.setDepartureFLights(departure);

		if (!flt.getOneWay()) {
			WebElement returnResults = driver.findElement(By.xpath(Ids.multiPlusResultTableReturnsxPath));
			List<WebElement> inboundsTR = returnResults.findElements(By.cssSelector(Ids.multiPlusFlight_CSS));

			ArrayList<FlightDetails> returning = this.extractFlightsInfo(inboundsTR, dt_m.getLatestReturn(), flt);
			singleResult.setReturnFlights(returning);
		}

		return singleResult;
	}

	public ArrayList<FlightSingleResult> loopDates(Trip trip, DateManager dt_m, SearchFilter flt) {
		WaitCondition.waitPageLoaded(driver);

		ArrayList<FlightSingleResult> results = new ArrayList<FlightSingleResult>();

		while (dt_m.forwardPeriod()) {

			chooseDateMultiplus(Ids.multiPlusFlightDatexPath, Ids.multiPlusReturnDatexPath, dt_m, flt.getOneWay());

			this.actionClickElement(Ids.multiPlusSubmitLoopxpath);

			results.add(this.extractFlightDetails());
		}

		dt_m.resetFlightDates();

		return results;
	}

	private void chooseDateMultiplus(String xpathFrom, String xPathTo, DateManager dt_m, boolean oneWay) {
		WebElement dtInputFrom = driver.findElement(By.xpath(xpathFrom));
		String delFrom = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		dtInputFrom.sendKeys(delFrom + format.format(dt_m.getEarliestDeparture().getTime()));

		if (!oneWay) {
			WebElement dtInputTo = driver.findElement(By.xpath(xPathTo));
			String delTo = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
			dtInputTo.sendKeys(delTo + format.format(dt_m.getLatestReturn().getTime()));
		}
		if (Help.checkPresence(driver, By.xpath(Ids.multiPlusCalendarChoice_OKxpath)) != null) {
			this.actionClickElement(Ids.multiPlusCalendarChoice_OKxpath);
		}
	}

	public ArrayList<FlightDetails> extractFlightsInfo(List<WebElement> infoTR, Calendar date, SearchFilter flt) {
		ArrayList<FlightDetails> infos = new ArrayList<FlightDetails>();

		for (WebElement webElement : infoTR) {
			List<WebElement> outboundsTD = webElement.findElements(By.cssSelector(Ids.multiPlusTD_CSS));

			MultiplusExtract origin = ParserFlightTam.extractAirportAndFlightTimeMultiPlus(outboundsTD.get(0).getText(), date);// saida
			MultiplusExtract destination = ParserFlightTam.extractAirportAndFlightTimeMultiPlus(outboundsTD.get(1).getText(), date);// chegada

			String code = outboundsTD.get(2).getText();// codigo do voo
			String duration = outboundsTD.get(3).getText();// duração
			String fare = ParserFlightTam.parseCheapestFare(outboundsTD, flt.getLimit()); //a melhor categoria de milhas

			FlightDetails flight = ParserFlightTam.parseTo(code, origin.getTime(), destination.getTime(), duration.replace(":", "H"), fare, origin.getAirport(), destination.getAirport(), "0");
			
			if (flight != null) {
				infos.add(flight);
			}
		}

		return infos;
	}
}
