package bycompany;

import types.Company;
import util.FileStream;

public class GolNationalAttendedAirports extends AttendedAirports {
	public GolNationalAttendedAirports(){
		this.setAirports(FileStream.readAirports(Company.GOL));
	}
}
