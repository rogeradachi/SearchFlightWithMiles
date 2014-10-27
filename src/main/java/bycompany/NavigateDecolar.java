package bycompany;

import java.util.ArrayList;
import java.util.HashMap;

import enums.FareType;
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
