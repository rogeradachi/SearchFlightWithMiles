package bycompany;
import model.GolNationalServedAirports;
import model.ServedAirports;
import model.TamNationalServedAirports;
import enums.Company;
import enums.NationalAirports;

public class RestrictionFactory {
	private ServedAirports served;

	public RestrictionFactory(Company company) {
		if (company.getValue() == Company.GOL.getValue()) {
			this.served = new GolNationalServedAirports();
		} else {
			this.served = new TamNationalServedAirports();
		}
	}

	public boolean attends(NationalAirports airport) {
		return served.attendsAirport(airport);
	}
}
