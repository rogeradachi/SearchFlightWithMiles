package bycompany;

import java.util.HashMap;

import model.FlightSingleResult;
import model.SearchFilter;
import model.Trip;
import navigation.DateManager;
import navigation.FaresManager;

public class NavigateTamMultiplus extends SearchToolInstance{
	
	public NavigateTamMultiplus(HashMap<String,String> urls){
		this.url = urls.get("multiplus");
	}

	@Override
	public FlightSingleResult searchFlightsFirstLoop(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlightSingleResult loopSearchFlights(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlightSingleResult extractFlightDetails(DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		// TODO Auto-generated method stub
		return null;
	}


}
