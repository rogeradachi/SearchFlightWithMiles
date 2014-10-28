package model;

import util.FileReadService;
import enums.Company;

public class DecolarNationalServedAirports extends ServedAirports {
	public DecolarNationalServedAirports(){
		this.setAirports(FileReadService.readAirports(Company.DECOLAR));
	}

}
