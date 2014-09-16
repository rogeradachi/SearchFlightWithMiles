package model;

public class FlightDetails {
	private String flightCode;
	private String amount;
	private String from;
	private String to;
	private String flightDuration;
	private String stopOvers;
	private String arriveTime;
	private String flightTime;
	
	public String getFlightCode() {
		return flightCode;
	}
	public FlightDetails setFlightCode(String flightCode) {
		this.flightCode = flightCode;
		return this;
	}
	public String getAmount() {
		return amount;
	}
	public FlightDetails setAmount(String amount) {
		this.amount = amount;
		return this;
	}
	public String getFrom() {
		return from;
	}
	public FlightDetails setFrom(String from) {
		this.from = from;
		return this;
	}
	public String getTo() {
		return to;
	}
	public FlightDetails setTo(String to) {
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
	public String getStopOvers() {
		return stopOvers;
	}
	public FlightDetails setStopOvers(String stopOvers) {
		this.stopOvers = stopOvers;
		return this;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public FlightDetails setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
		return this;
	}
	public String getFlightTime() {
		return flightTime;
	}
	public FlightDetails setFlightTime(String flightTime) {
		this.flightTime = flightTime;
		return this;
	}
	@Override
	public String toString() {
		return "Codigo=" + flightCode + ", valor=" + amount
				+ ", Saida=" + from + ", Chegada=" + to + ", Duracao="
				+ flightDuration + ", Paradas=" + stopOvers + ", Partida=" + flightTime + ", Chegada="
				+ arriveTime;
	}
	
	
}
