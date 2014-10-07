package main;

import javax.inject.Inject;

import model.Login;
import navigation.DateManager;
import navigation.FaresManager;
import navigation.TripManager;
import util.FileReadService;
import bycompany.NavigateGolSmiles;
import bycompany.NavigateTamMultiplus;

public class SearchFares {
	protected @Inject NavigateGolSmiles smiles;
	protected @Inject NavigateTamMultiplus multiplus;	

	protected @Inject DateManager dt_m;
	protected @Inject FaresManager fare_m;
	protected @Inject TripManager trip_m;
	protected @Inject Login smilesLogin;
	protected boolean oneWay;
	
	public SearchFares(){
		dt_m = FileReadService.readDates();
		smilesLogin = FileReadService.readPersonalDetailsFromFile();
		FileReadService.readSearchType();
	}
	
	private void setOneWay(boolean oneWay){
		this.oneWay = oneWay;
	}
	
	private void setEarliestDeparture(int day, int month, int year, int hour, int minute){
		dt_m.setEarliestOutbound(day, month, year, hour, minute);
	}
}
