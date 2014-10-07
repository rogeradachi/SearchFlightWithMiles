package bycompany;

import enums.Company;
import util.FileReadService;

public class TamNationalServedAirports extends ServedAirports {
	public TamNationalServedAirports(){
		this.setAirports(FileReadService.readAirports(Company.TAM));
	}
}
