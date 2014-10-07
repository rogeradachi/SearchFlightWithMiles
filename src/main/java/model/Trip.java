package model;

import enums.NationalAirports;

public class Trip {
	private NationalAirports from;
	private NationalAirports to;
	
	public Trip(NationalAirports from, NationalAirports to){
		this.from = from;
		this.to = to;
	}
	
	public String from(){
		return this.from.code();
	}
	
	public String to(){
		return this.to.code();
	}
}
