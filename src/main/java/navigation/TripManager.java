package navigation;

import java.util.ArrayList;

import javax.inject.Inject;

import model.Trip;
import types.Company;
import bycompany.RestrictionFactory;
import enums.NationalAirports;

public class TripManager {
	private RestrictionFactory restriction;
	private @Inject ArrayList<NationalAirports> from;
	private @Inject ArrayList<NationalAirports> to;
	private int indexFrom;
	private int indexTo;

	public TripManager(Company flightCo) {
		restriction = new RestrictionFactory(flightCo);
		indexFrom = 0;
		indexTo = 0;
	}

	public TripManager() {
		indexFrom = 0;
		indexTo = 0;
	}

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
