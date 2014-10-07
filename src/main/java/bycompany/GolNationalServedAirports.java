package bycompany;

import enums.Company;
import util.FileStream;

public class GolNationalServedAirports extends ServedAirports {
	public GolNationalServedAirports(){
		this.setAirports(FileStream.readAirports(Company.GOL));
	}
}
