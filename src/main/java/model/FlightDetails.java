package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import enums.NationalAirports;

public class FlightDetails {
	private String flightCode;
	private Integer amount;
	private NationalAirports from;
	private NationalAirports to;
	private String flightDuration;
	private Integer stopOvers;
	private Calendar arriveTime;
	private Calendar flightTime;
	
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
	
	public NationalAirports getFrom() {
		return from;
	}
	public FlightDetails setFrom(NationalAirports from) {
		this.from = from;
		return this;
	}
	public NationalAirports getTo() {
		return to;
	}
	public FlightDetails setTo(NationalAirports to) {
		this.to = to;
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
		return "Codigo=" + flightCode + ", valor=" + amount
				+ ", Saida=" + from.code() + ", Chegada=" + to.code() + ", Duracao="
				+ flightDuration + ", Paradas=" + stopOvers + ", Partida=" + flightTime.getTime() + ", Chegada="+ arriveTime.getTime();
	}
	
	
}
