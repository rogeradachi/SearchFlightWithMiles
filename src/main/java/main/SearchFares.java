package main;

import java.util.HashMap;

import javax.inject.Inject;

import model.SearchFilter;
import model.Trip;
import navigation.DateManager;
import navigation.FaresManager;
import navigation.TripManager;
import util.FileReadService;
import bycompany.NavigateGolSmiles;
import bycompany.NavigateTamMultiplus;
import enums.Company;

public class SearchFares {
	protected @Inject NavigateGolSmiles smiles;
	protected @Inject NavigateTamMultiplus multiplus;

	protected @Inject DateManager dt_m;
	protected @Inject FaresManager fare_m;
	protected @Inject TripManager trip_m;
	protected @Inject SearchFilter flt;
	protected boolean oneWay;
	protected HashMap<String, String> urls;

	public SearchFares() {
		dt_m = FileReadService.readDates();
		flt = FileReadService.readSearchType();
		urls = FileReadService.readUrls();
	}

	/**
	 * Simply reset the flying dates and prepare for another iteraction
	 */
	private void resetCalendar() {
		dt_m.resetFlightDates();
	}

	public void doSearch() {
	}

	private void doSearchSmiles(){
		trip_m = new TripManager(Company.GOL);
		
		this.smiles = new NavigateGolSmiles(urls);
		this.smiles.loginUserSpace();
		this.smiles.searchFlightsFirstLoop(trip_m.next(), dt_m, fare_m, flt);
		
		Trip trip = trip_m.next();
		while(trip != null){
			this.smiles.loopSearchFlights(trip, dt_m, fare_m, flt);
			trip = trip_m.next();
			this.resetCalendar();
		}
	}

	private void doSearchMultiplus() {
		trip_m = new TripManager(Company.TAM);
		this.multiplus = new NavigateTamMultiplus(urls);
	}

	public static void main(String[] args) {
		SearchFares src = new SearchFares();
		src.doSearchSmiles();
	}
}
