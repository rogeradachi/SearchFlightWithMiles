package main;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import enums.NationalAirports;
import model.Login;
import model.SearchFilter;
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
	protected @Inject SearchFilter flt;
	protected boolean oneWay;
	protected HashMap<String, String> urls;
	
	public SearchFares(){
		dt_m = FileReadService.readDates();
		flt = FileReadService.readSearchType();
		urls = FileReadService.readUrls();
		
	}
	
	/**
	 * Simply reset the flying dates and prepare for another iteraction
	 */
	private void resetCalendar(){
		dt_m.resetFlightDates();
	}
	
	public void doSearch(){
	}
	
	private void doSearchSmiles(){
		this.smiles = new NavigateGolSmiles(urls);
		this.smiles.loginUserSpace();
		this.smiles.searchFlights(trip_m, dt_m, fare_m, flt);
	}
	
	private void doSearchMultiplus(){
		this.multiplus = new NavigateTamMultiplus(urls);
	}
	
	public static void main(String[] args) {
		SearchFares src = new SearchFares();
		src.doSearchSmiles();
	}
}
