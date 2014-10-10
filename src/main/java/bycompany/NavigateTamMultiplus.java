package bycompany;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import navigation.DateManager;
import navigation.FaresManager;
import navigation.TripManager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import conditional.WaitCondition;
import enums.NationalAirports;

public class NavigateTamMultiplus extends SearchToolInstance{
	
	public NavigateTamMultiplus(HashMap<String,String> urls){
		this.url = urls.get("multiplus");
	}

	@Override
	public void searchFlights(TripManager trip, DateManager dt_m, FaresManager fare_m) {
		WaitCondition.waitPageLoaded(driver);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loopSearchFlights(TripManager trip, DateManager dt_m, FaresManager fare_m) {
		WaitCondition.waitPageLoaded(driver);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extractFlightDetails(List<WebElement> details, Calendar flightTime, NationalAirports to) {
		WaitCondition.waitPageLoaded(driver);
		// TODO Auto-generated method stub
		
	}

}
