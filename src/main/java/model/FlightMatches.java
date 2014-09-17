package model;

import java.util.ArrayList;
import java.util.Calendar;

public class FlightMatches {
	private ArrayList<FlightDetails> departureFlights;
	private ArrayList<FlightDetails> returnFlights;
	
	private Calendar earliestDepartureTime;
	private Calendar latestReturnTime;
	
	public ArrayList<FlightDetails> bestFares(){
		ArrayList<FlightDetails> bestFares = new ArrayList<FlightDetails>();
		
		//TODO: Filtrar as saída e retornos
		
		return bestFares;
	}
	
	public ArrayList<FlightDetails> getDepartureFlights() {
		return departureFlights;
	}
	public void setDepartureFlights(ArrayList<FlightDetails> departureFlights) {
		this.departureFlights = departureFlights;
	}
	public ArrayList<FlightDetails> getReturnFlights() {
		return returnFlights;
	}
	public void setReturnFlights(ArrayList<FlightDetails> returnFlights) {
		this.returnFlights = returnFlights;
	}
	
	
}
