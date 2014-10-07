package bycompany;

import enums.Company;
import util.FileStream;

public class TamNationalServedAirports extends ServedAirports {
	public TamNationalServedAirports(){
		this.setAirports(FileStream.readAirports(Company.TAM));
	}
}
