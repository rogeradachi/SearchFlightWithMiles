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
	private int indexFrom;
	private int indexTo;
	private HashMap<String, ArrayList<NationalAirports>> fromTo;

	public TripManager(Company flightCo) {
		restriction = new RestrictionFactory(flightCo);
		indexFrom = 0;
		indexTo = 0;
		fromTo = FileReadService.fromTo();
		from = fromTo.get("from");
		to = fromTo.get("to");
	}

	public TripManager() {
		indexFrom = 0;
		indexTo = 0;
	}
	
	/**
	 * Reset the indexes from loop and set new flight company
	 * @param flightCo
	 */
	public void reset(Company flightCo){
		if( flightCo != null)
			restriction = new RestrictionFactory(flightCo);
		else
			restriction = null;
		indexFrom = 0;
		indexTo = 0;
	}
	
	/**
	 * Reset the indexes from loop and set new flight company
	 * @param flightCo
	 */
	public void reset(){
		restriction = null;
		indexFrom = 0;
		indexTo = 0;
	}

	/**
	 * Get the next pair origin->destination
	 * @return encapsuled object with next trip
	 */
	public Trip next() {
		do {
			if (indexTo >= to.size()) {
				// next iteraction loop
				++indexFrom;// TODO verificar a regra para essa soma
				indexTo = 0;
				if (indexFrom >= from.size()) {
					// list is over
					return null;
				}
			}
			indexTo++;
		} while (!possibleTrip(from.get(indexFrom), to.get(indexTo)));

		return new Trip(from.get(indexFrom), to.get(indexTo));
	}

	/**
	 * Verify if the 'from' and 'to' arguments are served by given company
	 * @param from departing airport
	 * @param to destination airport
	 * @return TRUE if possible, FALSE otherwise
	 */
	private boolean possibleTrip(NationalAirports from, NationalAirports to) {
		if (this.restriction == null) {
			return true;
		}
		else{
			return (restriction.attends(from) && restriction.attends(to));
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

	public ArrayList<NationalAirports> getFrom() {
		return from;
	}

	public void setFrom(ArrayList<NationalAirports> from) {
		this.from = from;
	}

	public ArrayList<NationalAirports> getTo() {
		return to;
	}

	public void setTo(ArrayList<NationalAirports> to) {
		this.to = to;
	}
}
