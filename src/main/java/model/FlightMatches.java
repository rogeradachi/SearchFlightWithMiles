package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import navigation.FaresManager;
import enums.NationalAirports;

public class FlightMatches {
	private FaresManager fr_m;

	public NationalAirports from;
	public NationalAirports to;

	private ArrayList<FlightDetails> outBoundFLights;
	private ArrayList<FlightDetails> inBoundFlights;
	private ArrayList<FlightDetails> bestFares;

	private Calendar earliestDepartureTime;
	private Calendar latestReturnTime;

	private boolean oneWay;

	public FlightMatches(NationalAirports from, NationalAirports to) {
		this.from = from;
		this.to = to;
	}

	public FlightMatches(FaresManager fr_m, Calendar earliestDepartureTime, Calendar latestReturnTime) {
		this.earliestDepartureTime = earliestDepartureTime;
		this.latestReturnTime = latestReturnTime;
		this.oneWay = false;
	}

	public FlightMatches(FaresManager fr_m, Calendar earliestDepartureTime) {
		this.earliestDepartureTime = earliestDepartureTime;
		this.oneWay = true;
	}

	public ArrayList<FlightDetails> bestFares() {
		ArrayList<FlightDetails> bestFares = new ArrayList<FlightDetails>();
		this.excludesFlightsNotInTheInterval();

		if (oneWay) {
			ArrayList<FlightDetails> exclude = new ArrayList<FlightDetails>();
			for (FlightDetails departure : outBoundFLights) {
				if (!fr_m.belowLimit(departure.getAmount())) {
					exclude.add(departure);
				}
				if( earliestDepartureTime.before(departure.getFlightTime())){
					exclude.add(departure);
				}
			}
			outBoundFLights.removeAll(exclude);
			
			return outBoundFLights;
		} else {
			for (FlightDetails departure : outBoundFLights) {
				for (FlightDetails returning : inBoundFlights) {
					if (fr_m.belowLimit(departure.getAmount(), returning.getAmount())) {
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

		merged.setAmount(departureFlight.getAmount() + returnFlight.getAmount()).setFlightCode(departureFlight.getFlightCode() + "/" + returnFlight.getFlightCode()).setArriveTime(returnFlight.getArriveTime())
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
			if (flightDetails != null && flightDetails.getFlightTime().before(earliestDepartureTime)) {
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

	public FaresManager getFr_m() {
		return fr_m;
	}

	public void setFr_m(FaresManager fr_m) {
		this.fr_m = fr_m;
	}
}
