package bycompany;
import enums.NationalAirports;
import types.Company;
import bycompany.AttendedAirports;
import bycompany.GolNationalAttendedAirports;
import bycompany.TamNationalAttendedAirports;

public class RestrictionFactory {
	private AttendedAirports attended;

	public RestrictionFactory(Company company) {
		if (company.getValue() == Company.GOL.getValue()) {
			this.attended = new GolNationalAttendedAirports();
		} else {
			this.attended = new TamNationalAttendedAirports();
		}
	}

	public boolean attends(NationalAirports airport) {
		return attended.attendsAirport(airport);
	}
}
