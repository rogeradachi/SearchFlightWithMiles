package bycompany;

import java.util.ArrayList;

import enums.NationalAirports;

public class ServedAirports {
	private ArrayList<String> airports;
	
	public boolean attendsAirport(NationalAirports ap) {
		return airports.contains(ap.toString());
	}

	public ArrayList<String> getAirports() {
		return airports;
	}

	public void setAirports(ArrayList<String> airports) {
		this.airports = airports;
	}
}
