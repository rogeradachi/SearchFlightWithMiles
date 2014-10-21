package main;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import model.FlightMatches;
import model.SearchFilter;
import model.Trip;
import navigation.DateManager;
import navigation.TripManager;
import util.FileReadService;
import bycompany.NavigateGolSmiles;
import bycompany.NavigateTamMultiplus;
import enums.Company;

public class SearchFares {
	protected @Inject NavigateGolSmiles smiles;
	protected @Inject NavigateTamMultiplus multiplus;

	protected @Inject DateManager dt_m;
	protected @Inject TripManager trip_m;
	protected @Inject SearchFilter flt;
	protected boolean oneWay;
	protected HashMap<String, String> urls;
	private ArrayList<FlightMatches> matches;

	public SearchFares() {
		dt_m = FileReadService.readDates();
		flt = FileReadService.readSearchType();
		urls = FileReadService.readUrls();
		
		matches = new ArrayList<FlightMatches>();
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
		firstLoopSmiles();
		
		FlightMatches match;
		
		Trip trip = trip_m.next();
		while(trip != null){
			match = new FlightMatches(flt, trip.fromObj(), trip.toObj());
			match.getListResults().addAll(this.smiles.loopSearchFlights(trip, dt_m, flt));
			
			this.matches.add(match);
			
			this.resetCalendar();
			trip = trip_m.next();
		}
		this.getBestFares();
		
		this.smiles.closeDriver();
	}
	
	private void getBestFares(){
		for (FlightMatches flightMatches : matches) {
			flightMatches.bestFares();
		}
	}
	
	private void firstLoopSmiles(){
		this.smiles = new NavigateGolSmiles(urls);
		this.smiles.loginUserSpace();
		Trip trip = trip_m.next();
		FlightMatches match = new FlightMatches(flt, trip.fromObj(), trip.toObj());
		match.getListResults().add(this.smiles.searchFlightsFirstLoop(trip, dt_m, flt));
		match.getListResults().addAll(this.smiles.loopSearchFlights(trip, dt_m, flt));
		this.matches.add(match);
	}

	private void doSearchMultiplus() {
		trip_m = new TripManager(Company.TAM);
		//this.multiplus = new NavigateTamMultiplus(urls);
	}

	public static void main(String[] args) {
		SearchFares src = new SearchFares();
		src.doSearchSmiles();		
	}
}
