package bycompany;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import conditional.WaitCondition;
import enums.Company;
import enums.FareType;
import enums.Ids;
import model.FlightMatches;
import model.FlightSingleResult;
import model.SearchFilter;
import model.SearchToolInstance;
import model.Trip;
import navigation.DateManager;
import navigation.TripManager;

public class NavigateDecolar extends SearchToolInstance {
	public NavigateDecolar(HashMap<String, String> urls, SearchFilter flt, TripManager trip_m, DateManager dt_m) {
		this.url = urls.get("decolar");
		this.flt = flt;
		this.flt.setFareType(FareType.CASH);
		this.trip_m = trip_m;
		this.dt_m = dt_m;
	}
	
	public ArrayList<FlightMatches> searchDecolar() throws FileNotFoundException, UnsupportedEncodingException{
		ArrayList<FlightMatches> matches = new ArrayList<FlightMatches>();
		FlightMatches match;
		
		matches.add(this.firstLoopDecolar());
		
		Trip trip = trip_m.next();
		while(trip != null){
			match = new FlightMatches(flt, trip.fromObj(), trip.toObj(), Company.GOL.toString());
			match.getListResults().addAll(this.loopSearchFlights(trip));
			
			matches.add(match);
			
			dt_m.resetFlightDates();
			trip = trip_m.next();
		}
		
		if(matches.size() == 0){
			return null;
		}
		
		this.closeDriver();
		
		return matches;
	}
	
	private FlightMatches firstLoopDecolar() throws FileNotFoundException, UnsupportedEncodingException{
		this.initializeDriver();
		
		Trip trip = trip_m.next();
		
		FlightMatches match = new FlightMatches(flt, trip.fromObj(), trip.toObj(), Company.DECOLAR.toString());
		match.addListResults(this.searchFlightsFirstLoop(trip));
		match.addAllListResults(this.loopSearchFlights(trip));
		
		return match;
	}

	@Override
	public FlightSingleResult searchFlightsFirstLoop(Trip trip) {
		WaitCondition.waitElementClicable(Ids.decolarOneWayxPath, driver);
		this.radioOneWayTrip(flt.getOneWay(), Ids.decolarOneWayxPath);

		this.chooseAirport(Ids.decolarOrigemXpath, Ids.decolarDestinoXpath, trip);
		this.chooseDate(Ids.decolarFlightDatexPath, Ids.decolarReturnDatexPath, dt_m, flt.getOneWay());

		this.actionClickElement(Ids.decolarSubmitSearchXPath);

		return this.extractFlightDetails();
	}

	@Override
	public ArrayList<FlightSingleResult> loopSearchFlights(Trip trip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlightSingleResult extractFlightDetails() {
		// TODO Auto-generated method stub
		return null;
	}
}
