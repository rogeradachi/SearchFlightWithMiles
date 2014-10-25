package model;

import enums.Company;
import util.FileReadService;

public class GolNationalServedAirports extends ServedAirports {
	public GolNationalServedAirports(){
		this.setAirports(FileReadService.readAirports(Company.GOL));
	}
}
