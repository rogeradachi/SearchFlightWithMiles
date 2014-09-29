package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import enums.NationalAirports;

public class FlightMatches {
	private int amountLimit;
	private int milesLimit;

	public NationalAirports from;
	public NationalAirports to;

	private ArrayList<FlightDetails> outBoundFLights;
	private ArrayList<FlightDetails> inBoundFlights;
	private ArrayList<FlightDetails> bestFares;

	private Calendar earliestDepartureTime;
	private Calendar latestReturnTime;

	private boolean oneWay;

	public FlightMatches(NationalAirports from, NationalAirports to) {
		this.amountLimit = 300;
		this.milesLimit = 16000;
		this.from = from;
		this.to = to;
	}

	public FlightMatches(int amountLimit, int milesLimit, Calendar earliestDepartureTime, Calendar latestReturnTime) {
		this.amountLimit = amountLimit;
		this.milesLimit = milesLimit;
		this.earliestDepartureTime = earliestDepartureTime;
		this.latestReturnTime = latestReturnTime;
		this.oneWay = false;
	}

	public FlightMatches(int amountLimit, int milesLimit, Calendar earliestDepartureTime) {
		this.amountLimit = amountLimit;
		this.milesLimit = milesLimit;
		this.earliestDepartureTime = earliestDepartureTime;
		this.oneWay = true;
	}

	public ArrayList<FlightDetails> bestCashFares() {
		ArrayList<FlightDetails> bestFares = new ArrayList<FlightDetails>();
		this.excludesFlightsNotInTheInterval();

		if (oneWay) {
			return outBoundFLights;
		} else {
			for (FlightDetails departure : outBoundFLights) {
				for (FlightDetails returning : inBoundFlights) {
					if (departure.getAmount() + returning.getAmount() <= amountLimit) {
						bestFares.add(mergeFlightDetails(departure, returning));
					}
				}
			}
		}

		return bestFares;
	}

	public ArrayList<FlightDetails> bestMilesFares() {
		ArrayList<FlightDetails> bestFares = new ArrayList<FlightDetails>();
		this.excludesFlightsNotInTheInterval();

		if (oneWay) {
			return outBoundFLights;
		} else {
			for (FlightDetails departure : outBoundFLights) {
				for (FlightDetails returning : inBoundFlights) {
					if (departure.getAmount() + returning.getAmount() <= milesLimit) {
						bestFares.add(mergeFlightDetails(departure, returning));
					}
				}
			}
		}

		return bestFares;
	}

	private void clearList() {
		if (outBoundFLights != null) {
			this.outBoundFLights.clear();
		}
		if (inBoundFlights != null) {
			this.inBoundFlights.clear();
		}
	}

	/**
	 * Merge details from departure and returning flights
	 * 
	 * @param departureFlight
	 *            departure flights details
	 * @param returnFlight
	 *            return flights details
	 * @return merged details
	 */
	public FlightDetails mergeFlightDetails(FlightDetails departureFlight, FlightDetails returnFlight) {
		FlightDetails merged = new FlightDetails();

		merged.setAmount(departureFlight.getAmount() + returnFlight.getAmount())
				.setFlightCode(departureFlight.getFlightCode() + "/" + returnFlight.getFlightCode()).setArriveTime(returnFlight.getArriveTime())
				.setFlightTime(departureFlight.getFlightTime()).setStopOvers(0).setFlightDuration("");
		// TODO: Get stopOvers and flying time

		return merged;
	}

	private void excludesFlightsNotInTheInterval() {
		this.excludesDepartureFlightsNotInTheInterval();

		if (!oneWay) {
			this.excludesReturnFlightsNotInTheInterval();
		}
	}

	private void excludesDepartureFlightsNotInTheInterval() {
		ArrayList<FlightDetails> exclusions = new ArrayList<FlightDetails>();
		for (FlightDetails flightDetails : outBoundFLights) {
			if (flightDetails!=null && flightDetails.getFlightTime().before(earliestDepartureTime)) {
				exclusions.add(flightDetails);
			}
		}
		outBoundFLights.removeAll(exclusions);
	}

	private void excludesReturnFlightsNotInTheInterval() {
		ArrayList<FlightDetails> exclusions = new ArrayList<FlightDetails>();
		for (FlightDetails flightDetails2 : inBoundFlights) {
			if (flightDetails2 != null && flightDetails2.getFlightTime().after(latestReturnTime)) {
				exclusions.add(flightDetails2);
			}
		}
		inBoundFlights.removeAll(exclusions);
	}

	public void addSearchMatches(ArrayList<FlightDetails> flights) {
		if (bestFares == null) {
			bestFares = new ArrayList<FlightDetails>();
		}
		this.bestFares.addAll(flights);
	}

	public void sortMatches() {
		if (bestFares != null) {
			Collections.sort(this.bestFares);
		}
	}

	public ArrayList<FlightDetails> getOutBoundFlights() {
		return outBoundFLights;
	}

	public void setOutBoundFlights(ArrayList<FlightDetails> departureFlights) {
		this.outBoundFLights = departureFlights;
	}

	public ArrayList<FlightDetails> getInBoundFlights() {
		return inBoundFlights;
	}

	public void setInBoundFlights(ArrayList<FlightDetails> returnFlights) {
		this.inBoundFlights = returnFlights;
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

	public ArrayList<FlightDetails> getBestFares() {
		return bestFares;
	}

	public void setBestFares(ArrayList<FlightDetails> bestFares) {
		this.bestFares = bestFares;
	}

	public String getKey() {
		return this.from.code() + "-" + this.to.code();
	}

}
