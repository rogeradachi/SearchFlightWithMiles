package navigation;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import model.Trip;
import util.FileReadService;
import bycompany.RestrictionFactory;
import enums.Company;
import enums.NationalAirports;

public class TripManager {
	private RestrictionFactory restriction;
	private @Inject ArrayList<NationalAirports> from;
	private @Inject ArrayList<NationalAirports> to;
	private int indexTrips;
	private HashMap<String, ArrayList<NationalAirports>> fromTo;
	private ArrayList<Trip> trips;

	public TripManager(Company flightCo) {
		restriction = new RestrictionFactory(flightCo);
		indexTrips = 0;
		fromTo = FileReadService.fromTo();
		from = fromTo.get("from");
		to = fromTo.get("to");
		trips = new ArrayList<Trip>();
		initializeTrips();
	}

	public TripManager() {
		indexTrips = 0;
		trips = new ArrayList<Trip>();
	}

	/**
	 * Reset the indexes from loop and set new flight company
	 * 
	 * @param flightCo
	 */
	public void reset(Company flightCo) {
		if (flightCo != null)
			restriction = new RestrictionFactory(flightCo);
		else
			restriction = null;
		indexTrips = 0;
	}

	/**
	 * Reset the indexes from loop and set new flight company
	 * 
	 * @param flightCo
	 */
	public void reset() {
		restriction = null;
		indexTrips = 0;
	}

	/**
	 * Get the next pair origin->destination
	 * 
	 * @return encapsuled object with next trip
	 */
	public Trip next() {	
		if(indexTrips >= trips.size()){
			return null;
		}
		
		while(!possibleTrip(trips.get(indexTrips).fromObj(), trips.get(indexTrips).toObj())){
			++indexTrips;
			if(indexTrips >= trips.size()){
				return null;
			}
		}
		Trip nextTrip = trips.get(indexTrips);
		indexTrips++;
		
		return nextTrip;
	}

	/**
	 * Verify if the 'from' and 'to' arguments are served by given company
	 * 
	 * @param from
	 *            departing airport
	 * @param to
	 *            destination airport
	 * @return TRUE if possible, FALSE otherwise
	 */
	private boolean possibleTrip(NationalAirports from, NationalAirports to) {
		if (this.restriction == null) {
			return true;
		} else {
			return (restriction.attends(from) && restriction.attends(to));
		}
	}
	
	private void initializeTrips(){
		for (NationalAirports origins : from) {
			for (NationalAirports destiny : to) {
				this.trips.add(new Trip(origins, destiny));
			}
		}
	}

	public void addOrigin(NationalAirports from) {
		this.from.add(from);
	}

	public void addDestiny(NationalAirports to) {
		this.to.add(to);
	}

	public void addAllOrigin(ArrayList<NationalAirports> from) {
		this.from.addAll(from);
	}

	public void addAllDestination(ArrayList<NationalAirports> to) {
		this.to.addAll(to);
	}

	public NationalAirports From() {
		if(indexTrips < trips.size()){
			return trips.get(indexTrips).fromObj();
		}
		return null;
	}

	public void setFrom(ArrayList<NationalAirports> from) {
		this.from = from;
	}

	public NationalAirports To() {
		if(indexTrips < trips.size()){
			return trips.get(indexTrips).toObj();
		}
		return null;
	}

	public void setTo(ArrayList<NationalAirports> to) {
		this.to = to;
	}
}
