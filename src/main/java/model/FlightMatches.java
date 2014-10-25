package model;

import java.util.ArrayList;
import java.util.Collections;

import enums.NationalAirports;

public class FlightMatches {
	private SearchFilter flt;

	public NationalAirports from;
	public NationalAirports to;

	private ArrayList<FlightDetails> bestFares;
	private ArrayList<FlightSingleResult> listResults;

	public FlightMatches(SearchFilter fr_m, NationalAirports from, NationalAirports to, String company) {
		listResults = new ArrayList<FlightSingleResult>();
		this.from = from;
		this.to = to;
		this.flt = fr_m;
	}

	public ArrayList<FlightDetails> bestFares() {
		ArrayList<FlightDetails> bestList = new ArrayList<FlightDetails>();
		if (flt.getOneWay()) {
			for (FlightSingleResult flightSingleResult : listResults) {
				flightSingleResult.removeUnwantedResults();
				
				for (int i = flightSingleResult.getDepartureFLights().size() - 1; i >= 0; i--) {					
					if (flightSingleResult.getDepartureFLights().get(i).getAmount() <= flt.getLimit()) {
						bestList.add(flightSingleResult.getDepartureFLights().get(i));
					}
				}
			}
		} else {
			for (FlightSingleResult flightSingleResult : listResults) {
				flightSingleResult.removeUnwantedResults();
				
				for (FlightDetails dep : flightSingleResult.getDepartureFLights()) {
					for (FlightDetails ret : flightSingleResult.getReturnFlights()) {
						if (dep.getAmount() + ret.getAmount() <= flt.getLimit()) {
							bestList.add(this.mergeFlightDetails(dep, ret));
						}
					}
				}
			}
		}
		this.bestFares = this.sortMatches(bestList);

		return bestList;
	}

	public void clearList() {
		if (bestFares != null) {
			this.bestFares.clear();
		}
		if (listResults != null) {
			this.listResults.clear();
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

		merged.setCompany(departureFlight.getCompany()).setAmount(departureFlight.getAmount() + returnFlight.getAmount()).setOriginAirport(departureFlight.getOriginAirport()).setDestinationAirport(departureFlight.getDestinationAirport())
				.setFlightCode(departureFlight.getFlightCode() + " \\ " + returnFlight.getFlightCode()).setArriveTime(returnFlight.getArriveTime())
				.setFlightTime(departureFlight.getFlightTime()).setStopOvers(departureFlight.getStopOvers() + " \\ " + returnFlight.getStopOvers()).setFlightDuration(departureFlight.getFlightDuration() + " \\ " + returnFlight.getFlightDuration());

		return merged;
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
	
	public ArrayList<FlightDetails> sortMatches(ArrayList<FlightDetails> bestFares) {
		if (bestFares != null) {
			Collections.sort(bestFares);
		}
		return bestFares;
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

	public SearchFilter getFlt() {
		return flt;
	}

	public void setFlt(SearchFilter flt) {
		this.flt = flt;
	}

	public ArrayList<FlightSingleResult> getListResults() {
		return listResults;
	}

	public void setListResults(ArrayList<FlightSingleResult> listResults) {
		this.listResults = listResults;
	}
	
	public void addAllListResults(ArrayList<FlightSingleResult> listResults){
		this.listResults.addAll(listResults);
	}
	
	public void addListResults(FlightSingleResult listResults){
		this.listResults.add(listResults);
	}

}
