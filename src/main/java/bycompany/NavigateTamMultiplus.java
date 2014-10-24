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
import model.SearchFilter;
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
import enums.NationalAirports;

public class NavigateTamMultiplus extends SearchToolInstance {
	private static final String NAO_EXISTE = "-----";

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

		FlightMatches match = new FlightMatches(flt, trip.fromObj(), trip.toObj());
		match.addListResults(this.searchFlightsFirstLoop(trip));
		match.addListResults(this.loopSearchFlights(trip));

		return match;
	}

	public ArrayList<FlightMatches> searchMultiplus() throws FileNotFoundException, UnsupportedEncodingException {
		ArrayList<FlightMatches> matches = new ArrayList<FlightMatches>();
		trip_m = new TripManager(Company.TAM);
		matches.add(firstLoopMultiplus());

		FlightMatches match;

		Trip trip = trip_m.next();
		while (trip != null) {
			match = new FlightMatches(flt, trip.fromObj(), trip.toObj());
			match.getListResults().addAll(this.loopSearchFlights(trip));

			matches.add(match);

			dt_m.resetFlightDates();
			trip = trip_m.next();
		}

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
		if (Help.exists(driver, Ids.multiPlusCalendarChoice_OKxpath)) {
			this.actionClickElement(Ids.multiPlusCalendarChoice_OKxpath);
		}
	}
	
	public ArrayList<FlightDetails> extractFlightsInfo(List<WebElement> infoTR, Calendar dt_m, SearchFilter flt){
		ArrayList<FlightDetails> infos = new ArrayList<FlightDetails>();
		
		for (WebElement webElement : infoTR) {
			List<WebElement> outboundsTD = webElement.findElements(By.cssSelector(Ids.multiPlusTD_CSS));

			String[] detailsOutbound = ParserFlightTam.extractAirportAndFlightTimeMultiPlus(outboundsTD.get(0).getText());// saida
			String[] detailsInbound = ParserFlightTam.extractAirportAndFlightTimeMultiPlus(outboundsTD.get(1).getText());// chegada
			String code = outboundsTD.get(2).getText();// codigo do voo
			String duration = outboundsTD.get(3).getText();// duração
			/* milhas PROMO*/
			String milesPromo = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(4).getText());

			String milesClassico = NAO_EXISTE;
			String milesIrrestrito = NAO_EXISTE;
			if (outboundsTD.size() > 5) {
				/* milhas clássico */
				milesClassico = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(5).getText());
				if (outboundsTD.size() > 6) {
					/* milhas Irrestrito */
					milesIrrestrito = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(6).getText());
				}
			}

			String fare = ParserFlightTam.cheapestFare(milesPromo, milesClassico, milesIrrestrito, flt.getLimit());
			Calendar outbound = ParserFlightTam.convertCalendar(detailsOutbound[0], dt_m.get(Calendar.DATE), dt_m.get(Calendar.MONTH),
					dt_m.get(Calendar.YEAR));
			Calendar inbound = ParserFlightTam.convertCalendar(detailsInbound[0], dt_m.get(Calendar.DATE), dt_m.get(Calendar.MONTH),
					dt_m.get(Calendar.YEAR));

			FlightDetails flight = ParserFlightTam.parseTo(code, outbound, inbound, duration, fare, NationalAirports.valueOf(detailsOutbound[1]),
					NationalAirports.valueOf(detailsInbound[1]));
			if (flight != null) {
				infos.add(flight);
			}
		}
		
		return infos;
	}
}
