package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import enums.NationalAirports;

public class FlightDetails implements Comparable<FlightDetails> {
	private SimpleDateFormat f = new SimpleDateFormat("dd/MM HH'h'mm");
	private NationalAirports originAirport;
	private NationalAirports destinationAirport;
	private String flightCode;
	private Integer amount;
	private String flightDuration;
	private String stopOvers;
	private Calendar arriveTime;
	private Calendar flightTime;

	public NationalAirports getOriginAirport() {
		return originAirport;
	}

	public FlightDetails setOriginAirport(NationalAirports originAirport) {
		this.originAirport = originAirport;
		return this;
	}

	public NationalAirports getDestinationAirport() {
		return destinationAirport;
	}

	public FlightDetails setDestinationAirport(NationalAirports destinationAirport) {
		this.destinationAirport = destinationAirport;
		return this;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public FlightDetails setFlightCode(String flightCode) {
		this.flightCode = flightCode;
		return this;
	}

	public Integer getAmount() {
		return amount;
	}

	public FlightDetails setAmount(Integer amount) {
		this.amount = amount;
		return this;
	}

	public String getFlightDuration() {
		return flightDuration;
	}

	public FlightDetails setFlightDuration(String flightDuration) {
		this.flightDuration = flightDuration;
		return this;
	}

	public String getStopOvers() {
		return stopOvers;
	}

	public FlightDetails setStopOvers(String stopOvers) {
		this.stopOvers = stopOvers;
		return this;
	}

	public Calendar getArriveTime() {
		return arriveTime;
	}

	public FlightDetails setArriveTime(Calendar arriveTime) {
		this.arriveTime = arriveTime;
		return this;
	}

	public Calendar getFlightTime() {
		return flightTime;
	}

	public FlightDetails setFlightTime(Calendar flightTime) {
		this.flightTime = flightTime;
		return this;
	}
	
	public String csvString(){
		return flightCode + ";" + this.getOriginAirport().code() + ";" + this.getDestinationAirport().code() + ";" + amount + ";" + flightDuration + ";" + stopOvers + ";" + f.format(flightTime.getTime()) + ";"
				+ f.format(arriveTime.getTime());
	}

	@Override
	public int compareTo(FlightDetails o) {
		return this.getAmount() - o.getAmount();
	}
	
}
