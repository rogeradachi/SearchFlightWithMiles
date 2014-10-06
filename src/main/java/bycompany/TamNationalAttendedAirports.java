package bycompany;

import types.Company;
import util.FileStream;

public class TamNationalAttendedAirports extends AttendedAirports {
	public TamNationalAttendedAirports(){
		this.setAirports(FileStream.readAirports(Company.TAM));
	}
}
