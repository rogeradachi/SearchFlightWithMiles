package bycompany;

import java.util.HashMap;

import model.SearchFilter;
import model.SearchToolInstance;
import navigation.DateManager;
import navigation.TripManager;

public class NavigateDecolar extends SearchToolInstance {
	public NavigateDecolar(HashMap<String, String> urls, SearchFilter flt, TripManager trip_m, DateManager dt_m) {
		this.url = urls.get("decolar");
		this.flt = flt;
		this.trip_m = trip_m;
		this.dt_m = dt_m;
	}
}
