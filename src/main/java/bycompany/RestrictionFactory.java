package bycompany;

import model.GolNationalServedAirports;
import model.ServedAirports;
import model.TamNationalServedAirports;
import enums.Company;
import enums.NationalAirports;

public class RestrictionFactory {
	private ServedAirports served;

	public RestrictionFactory(Company company) {
		switch (company) {
		case GOL:
			this.served = new GolNationalServedAirports();
			break;
		case TAM:
			this.served = new TamNationalServedAirports();
			break;
		case DECOLAR:
		default:
			this.served = new TamNationalServedAirports();
			break;
		}
	}

	public boolean attends(NationalAirports airport) {
		return served.attendsAirport(airport);
	}
}
