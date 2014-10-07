package bycompany;
import enums.Company;
import enums.NationalAirports;
import bycompany.ServedAirports;
import bycompany.GolNationalServedAirports;
import bycompany.TamNationalServedAirports;

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
