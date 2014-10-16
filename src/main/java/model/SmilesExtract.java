package model;

import java.util.Calendar;

import enums.NationalAirports;

public class SmilesExtract {
	private int [] hour;
	private NationalAirports airport;
	private Calendar extractedDate;
	
	public int[] getHour() {
		return hour;
	}
	public SmilesExtract setHour(int[] hour) {
		this.hour = hour;
		return this;
	}
	public NationalAirports getAirport() {
		return airport;
	}
	public SmilesExtract setAirport(NationalAirports airport) {
		this.airport = airport;
		return this;
	}
	public Calendar getExtractedDate() {
		return extractedDate;
	}
	public SmilesExtract setExtractedDate(Calendar extractedDate) {
		this.extractedDate = extractedDate;
		return this;
	}
}
