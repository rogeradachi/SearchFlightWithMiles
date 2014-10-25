package model;

import java.util.Calendar;

import enums.NationalAirports;

public class MultiplusExtract {
	private Calendar time;
	private NationalAirports airport;
	
	public Calendar getTime() {
		return time;
	}
	public MultiplusExtract setTime(Calendar time) {
		this.time = time;
		return this;
	}
	public NationalAirports getAirport() {
		return airport;
	}
	public MultiplusExtract setAirport(NationalAirports airport) {
		this.airport = airport;
		return this;
	}
}
