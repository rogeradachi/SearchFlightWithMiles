package bycompany;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import model.FlightMatches;
import model.FlightSingleResult;
import model.SearchFilter;
import model.Trip;
import navigation.DateManager;
import navigation.TripManager;


public class NavigateTamMultiplus extends SearchToolInstance{
	
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
		// TODO Auto-generated method stub
		return null;
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
