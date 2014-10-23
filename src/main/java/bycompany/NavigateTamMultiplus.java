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
import org.openqa.selenium.WebElement;

import util.ParserFlightTam;
import conditional.WaitCondition;
import enums.NationalAirports;


public class NavigateTamMultiplus extends SearchToolInstance{
	
	private static final String NAO_EXISTE = "-----";

	public NavigateTamMultiplus(HashMap<String,String> urls,SearchFilter flt, TripManager trip_m, DateManager dt_m){
		this.url = urls.get("multiplus");
		this.flt = flt;
		this.trip_m = trip_m;
		this.dt_m = dt_m;
	}
	
	public FlightMatches firstLoopMultiplus() throws FileNotFoundException, UnsupportedEncodingException{
		this.initializeDriver();
		
		Trip trip = trip_m.next();
		
		FlightMatches match = new FlightMatches(flt, trip.fromObj(), trip.toObj());
		match.addListResults(this.searchFlightsFirstLoop(trip));
		match.addListResults(this.loopSearchFlights(trip));
		
		return match;
	}

	@Override
	public FlightSingleResult searchFlightsFirstLoop(Trip trip) {
		WaitCondition.waitElementClicable(Ids.multiPlusMilesxPath, driver);
		actionClickElement(Ids.multiPlusMilesxPath);

		if (flt.getOneWay()) {
			actionClickElement(Ids.multiPlusOneWayXpath);
		} else {
			chooseDate(Ids.multiPlusFlightDatexPath, Ids.multiPlusReturnDatexPath, dt_m, flt.getOneWay());
		}
		chooseAirport(Ids.multiPlusOriginxPath, Ids.multiPlusDestinationxPath, trip);
		
		actionClickElement(Ids.multiPlusSubmitFirstxPath);
		
		return this.extractFlightDetails();
	}

	@Override
	public ArrayList<FlightSingleResult> loopSearchFlights(Trip trip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlightSingleResult extractFlightDetails() {
		WaitCondition.waitElementVisible(Ids.multiPlusResultTableFlightxPath, driver);

		FlightSingleResult singleResult = new FlightSingleResult(dt_m.getEarliestDeparture(), dt_m.getLatestReturn());

		WebElement departureResults;
		WebElement departureResultsWConnections;
		try {
			departureResults = driver.findElement(By.xpath(Ids.multiPlusResultTableFlightxPath));
		} catch (Exception ex) {
			return null;
		}

		List<WebElement> outboundsTR = departureResults.findElements(By.cssSelector(Ids.multiPlusFlight_CSS));
		ArrayList<FlightDetails> outboundsDeparture = new ArrayList<FlightDetails>();

		for (WebElement webElement : outboundsTR) {
			List<WebElement> outboundsTD = webElement.findElements(By.cssSelector(Ids.multiPlusTD_CSS));

			String[] detailsOutbound = ParserFlightTam.extractAirportAndFlightTimeMultiPlus(outboundsTD.get(0).getText());// saida
			String[] detailsInbound = ParserFlightTam.extractAirportAndFlightTimeMultiPlus(outboundsTD.get(1).getText());// chegada
			String code = outboundsTD.get(2).getText();// codigo do voo
			String duration = outboundsTD.get(3).getText();// duração
			String milesPromo = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(4).getText());// milhas

			String milesClassico = NAO_EXISTE;
			String milesIrrestrito = NAO_EXISTE;// PROMO
			if (outboundsTD.size() > 5) {
				milesClassico = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(5).getText());// milhas
																					// classico
				if (outboundsTD.size() > 6) {
					milesIrrestrito = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(6).getText());// milhas
																							// Irrestrito
				}
			}

			String fare = ParserFlightTam.cheapestFare(milesPromo, milesClassico, milesIrrestrito, flt.getLimit());
			Calendar outbound = ParserFlightTam.convertCalendar(detailsOutbound[0], dt_m.getEarliestDeparture().get(Calendar.DATE), dt_m.getEarliestDeparture().get(Calendar.MONTH),
					dt_m.getEarliestDeparture().get(Calendar.YEAR));
			Calendar inbound = ParserFlightTam.convertCalendar(detailsInbound[0], dt_m.getEarliestDeparture().get(Calendar.DATE), dt_m.getEarliestDeparture().get(Calendar.MONTH),
					dt_m.getEarliestDeparture().get(Calendar.YEAR));

			FlightDetails flight = ParserFlightTam.parseTo(code, outbound, inbound, duration, fare, NationalAirports.valueOf(detailsOutbound[1]),
					NationalAirports.valueOf(detailsInbound[1]));
			if (flight != null) {
				outboundsDeparture.add(flight);
			}
		}

		//searchMatches.setOutBoundFlights(outboundsDeparture);

		if (!flt.getOneWay()) {
			WebElement returnResults = driver.findElement(By.xpath(".//*[@id='inbound_list_flight_direct']/tbody"));
			List<WebElement> outboundsReturnTR = returnResults.findElements(By.cssSelector(Ids.multiPlusFlight_CSS));
			ArrayList<FlightDetails> outboundsReturn = new ArrayList<FlightDetails>();
			for (WebElement webElement : outboundsReturnTR) {
				List<WebElement> outboundsTD = webElement.findElements(By.cssSelector(Ids.multiPlusTD_CSS));

				String[] detailsOutbound = ParserFlightTam.extractAirportAndFlightTimeMultiPlus(outboundsTD.get(0).getText());// saida
				String[] detailsInbound = ParserFlightTam.extractAirportAndFlightTimeMultiPlus(outboundsTD.get(1).getText());// chegada
				String code = outboundsTD.get(2).getText();// codigo do voo
				String duration = outboundsTD.get(3).getText();// duração
				String milesPromo = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(4).getText());// milhas
																						// PROMO
				String milesClassico = NAO_EXISTE;
				String milesIrrestrito = NAO_EXISTE;
				if (outboundsTD.size() > 5) {
					milesClassico = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(5).getText());// milhas
																						// classico
					if (outboundsTD.size() > 6) {
						milesIrrestrito = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(6).getText());// milhas
																								// Irrestrito
					}
				}
				String fare = ParserFlightTam.cheapestFare(milesPromo, milesClassico, milesIrrestrito, flt.getLimit());
				Calendar outbound = ParserFlightTam.convertCalendar(detailsOutbound[0], dt_m.getLatestReturn().get(Calendar.DATE), dt_m.getLatestReturn().get(Calendar.MONTH),
						dt_m.getLatestReturn().get(Calendar.YEAR));
				Calendar inbound = ParserFlightTam.convertCalendar(detailsInbound[0], dt_m.getLatestReturn().get(Calendar.DATE), dt_m.getLatestReturn().get(Calendar.MONTH),
						dt_m.getLatestReturn().get(Calendar.YEAR));

				FlightDetails flight = ParserFlightTam.parseTo(code, outbound, inbound, duration, fare, NationalAirports.valueOf(detailsOutbound[1]),
						NationalAirports.valueOf(detailsInbound[1]));
				if (flight != null) {
					outboundsReturn.add(flight);
				}
			}
			//searchMatches.setInBoundFlights(outboundsReturn);
		}

		//return searchMatches.bestMilesFares();
		return singleResult;
	}
}
