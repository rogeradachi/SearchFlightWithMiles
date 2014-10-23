package main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import model.FlightDetails;
import model.FlightMatches;
import model.SearchFilter;
import model.Trip;
import navigation.DateManager;
import navigation.TripManager;
import util.FileReadService;
import util.FileStream;
import bycompany.NavigateGolSmiles;
import bycompany.NavigateTamMultiplus;
import enums.Company;

public class SearchFares {
	
	protected @Inject NavigateTamMultiplus multiplus;

	protected @Inject DateManager dt_m;
	protected @Inject TripManager trip_m;
	protected @Inject SearchFilter flt;
	protected boolean oneWay;
	protected HashMap<String, String> urls;
	private ArrayList<FlightMatches> matches;
	private ArrayList<FlightDetails> bestFares;
	private HashMap<String, FlightMatches> resultMatches;
	private NavigateGolSmiles smiles;

	public SearchFares() {
		dt_m = FileReadService.readDates();
		flt = FileReadService.readSearchType();
		urls = FileReadService.readUrls();
		
		matches = new ArrayList<FlightMatches>();
		resultMatches = new HashMap<String, FlightMatches>();
	}

	/**
	 * Simply reset the flying dates and prepare for another iteraction
	 */
	private void resetCalendar() {
		dt_m.resetFlightDates();
	}
	
	private void endOrganizeResults(){
		this.getBestFares();
		//FileStream.outputResults(flightList, from, to, company, fare);
	}
	
	private void getBestFares(){
		for (FlightMatches flightMatches : matches) {
			flightMatches.bestFares();			
		}
	}

	private void doSearchMultiplus() {
		trip_m = new TripManager(Company.TAM);
		
		NavigateTamMultiplus multiplus = new NavigateTamMultiplus(urls, flt, trip_m, dt_m);
	}
	
	private void addMatches() {
		for (FlightMatches searchMatches : matches) {
			if (searchMatches != null) {
				searchMatches.sortMatches();
				if (resultMatches.containsKey(searchMatches.getKey())) {
					FlightMatches matches = resultMatches.remove(searchMatches.getKey());
					matches.getBestFares().addAll(searchMatches.getBestFares());
					resultMatches.put(searchMatches.getKey(), matches);
				} else {
					resultMatches.put(searchMatches.getKey(), searchMatches);
				}
			}	
		}
	}
	
	public void doSearchSmiles() throws FileNotFoundException, UnsupportedEncodingException{
		trip_m = new TripManager(Company.GOL);
		
		NavigateGolSmiles smiles = new NavigateGolSmiles(urls,flt, trip_m, dt_m);
		matches.add(smiles.firstLoopSmiles());
		matches.addAll(smiles.doSearchSmiles());
		addMatches();
	}

	public static void main(String[] args) {
		SearchFares src = new SearchFares();
			try {
				src.doSearchSmiles();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
