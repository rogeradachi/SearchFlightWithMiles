package model;

import java.util.ArrayList;
import java.util.Calendar;

public class FlightMatches {
	private int amountLimit;
	private int milesLimit;

	private ArrayList<FlightDetails> departureFlights;
	private ArrayList<FlightDetails> returnFlights;

	private Calendar earliestDepartureTime;
	private Calendar latestReturnTime;

	public FlightMatches() {
		this.amountLimit = 300;
		this.milesLimit = 16000;
	}

	public FlightMatches(int amountLimit, int milesLimit, Calendar earliestDepartureTime, Calendar latestReturnTime) {
		this.amountLimit = amountLimit;
		this.milesLimit = milesLimit;
		this.earliestDepartureTime = earliestDepartureTime;
		this.latestReturnTime = latestReturnTime;
	}

	public ArrayList<FlightDetails> bestFares() {
		ArrayList<FlightDetails> bestFares = new ArrayList<FlightDetails>();
		this.excludesFlightsNotInTheInterval();

		for (FlightDetails departure : departureFlights) {
			for (FlightDetails returning : returnFlights) {
				if (departure.getAmount() + returning.getAmount() <= amountLimit) {
					bestFares.add(mergeFlightDetails(departure, returning));
				}
			}
		}

		return bestFares;
	}

	public ArrayList<FlightDetails> bestMilesFares() {
		ArrayList<FlightDetails> bestFares = new ArrayList<FlightDetails>();
		this.excludesFlightsNotInTheInterval();

		for (FlightDetails departure : departureFlights) {
			for (FlightDetails returning : returnFlights) {
				if (departure.getAmount() + returning.getAmount() <= milesLimit) {
					bestFares.add(mergeFlightDetails(departure, returning));
				}
			}
		}

		return bestFares;
	}

	/**
	 * Merge details from departure and returning flights
	 * @param departureFlight departure flights details
	 * @param returnFlight return flights details
	 * @return merged details
	 */
	public FlightDetails mergeFlightDetails(FlightDetails departureFlight, FlightDetails returnFlight) {
		FlightDetails merged = new FlightDetails();

		merged.setAmount(departureFlight.getAmount() + returnFlight.getAmount())
				.setFlightCode(departureFlight.getFlightCode() + "/" + returnFlight.getFlightCode()).setArriveTime(returnFlight.getArriveTime())
				.setFlightTime(departureFlight.getFlightTime()).setFrom(departureFlight.getFrom()).setTo(returnFlight.getTo()).setStopOvers(0)
				.setFlightDuration("");

		return merged;
	}

	public void orderFlightsByFares() {

	}

	private void excludesFlightsNotInTheInterval() {
		ArrayList<FlightDetails> exclusions = new ArrayList<FlightDetails>();
		for (FlightDetails flightDetails : departureFlights) {
			if (flightDetails.getFlightTime().before(earliestDepartureTime)) {
				exclusions.add(flightDetails);
			}
		}
		departureFlights.removeAll(exclusions);
		exclusions.clear();

		for (FlightDetails flightDetails2 : returnFlights) {
			if (flightDetails2.getFlightTime().after(latestReturnTime)) {
				exclusions.add(flightDetails2);
			}
		}
		returnFlights.removeAll(exclusions);
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

	public void setAmountLimit(int amountLimit) {
		this.amountLimit = amountLimit;
	}

	public void setMilesLimit(int milesLimit) {
		this.milesLimit = milesLimit;
	}

	public void setEarliestDepartureTime(Calendar earliestDepartureTime) {
		this.earliestDepartureTime = earliestDepartureTime;
	}

	public void setLatestReturnTime(Calendar latestReturnTime) {
		this.latestReturnTime = latestReturnTime;
	}
}
