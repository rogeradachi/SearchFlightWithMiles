package model;

import java.util.ArrayList;
import java.util.Calendar;

public class FlightSingleResult {
	private Calendar departureDate;
	private Calendar returnDate;

	private ArrayList<FlightDetails> departureFLights;
	private ArrayList<FlightDetails> returnFlights;

	public FlightSingleResult(Calendar departureDate, Calendar returnDate) {
		this.departureDate = (Calendar) departureDate.clone();
		this.returnDate = (Calendar) returnDate.clone();
	}

	public Calendar getDepartureDate() {
		return (Calendar) departureDate.clone();
	}

	public void setDepartureDate(Calendar departureDate) {
		this.departureDate = (Calendar) departureDate.clone();
	}

	public Calendar getReturnDate() {
		return (Calendar) returnDate.clone();
	}

	public void setReturnDate(Calendar returnDate) {
		this.returnDate = (Calendar) returnDate.clone();
	}

	public ArrayList<FlightDetails> getDepartureFLights() {
		return departureFLights;
	}

	public void setDepartureFLights(ArrayList<FlightDetails> departureFLights) {
		this.departureFLights = departureFLights;
	}

	public ArrayList<FlightDetails> getReturnFlights() {
		return returnFlights;
	}

	public void setReturnFlights(ArrayList<FlightDetails> returnFlights) {
		this.returnFlights = returnFlights;
	}

	public void removeUnwantedResults() {
		if (departureFLights != null) {
			for (int i = departureFLights.size() - 1; i >= 0; i--) {
				if (departureFLights.get(i).getFlightTime().before(departureDate)) {
					departureFLights.remove(i);
				}
			}
			this.removeReturnFlights();
		}

	}

	private void removeReturnFlights() {
		if (returnFlights != null) {
			for (int i = returnFlights.size() - 1; i >= 0; i--) {
				if(returnFlights.get(i).getFlightTime().after(returnDate)){
					returnFlights.remove(i);	
				}
			}
		}
	}

}
