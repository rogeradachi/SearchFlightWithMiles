package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import enums.NationalAirports;

public class FlightDetails implements Comparable<FlightDetails> {
	private String company;
	private String flightCode;
	private Integer amount;
	private String flightDuration;
	private Integer stopOvers;
	private NationalAirports outbound;
	private NationalAirports inbound;
	private Calendar arriveTime;
	private Calendar flightTime;

	public String getFlightCode() {
		return flightCode;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public Integer getStopOvers() {
		return stopOvers;
	}

	public FlightDetails setStopOvers(Integer stopOvers) {
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

	@Override
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat("dd/MM HH'h'mm");
		
		return flightCode + ";" + company + ";" + amount + ";" + flightDuration + ";" + stopOvers + ";" + f.format(flightTime.getTime()) + ";"
				+ f.format(arriveTime.getTime());
	}

	@Override
	public int compareTo(FlightDetails o) {
		return this.getAmount() - o.getAmount();
	}

	public NationalAirports getOutbound() {
		return outbound;
	}

	public FlightDetails setOutbound(NationalAirports outbound) {
		this.outbound = outbound;
		return this;
	}

	public NationalAirports getInbound() {
		return inbound;
	}

	public FlightDetails setInbound(NationalAirports inbound) {
		this.inbound = inbound;
		return this;
	}
	
}
