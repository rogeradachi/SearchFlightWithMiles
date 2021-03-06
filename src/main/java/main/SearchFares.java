package main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import model.FlightDetails;
import model.FlightMatches;
import model.SearchFilter;
import navigation.DateManager;
import navigation.TripManager;
import util.FileReadService;
import util.FileStream;
import bycompany.NavigateGolSmiles;
import bycompany.NavigateTamMultiplus;
import enums.Company;

public class SearchFares {

	protected @Inject DateManager dt_m;
	protected @Inject TripManager trip_m;
	protected @Inject SearchFilter flt;
	protected boolean oneWay;
	protected HashMap<String, String> urls;
	private ArrayList<FlightMatches> matches;
	private ArrayList<FlightDetails> bestFares;
	private HashMap<String, FlightMatches> resultMatches;
	private NavigateGolSmiles smiles;
	protected @Inject NavigateTamMultiplus multiplus;

	public SearchFares() {
		dt_m = FileReadService.readDates();
		flt = FileReadService.readSearchType();
		urls = FileReadService.readUrls();

		matches = new ArrayList<FlightMatches>();
		resultMatches = new HashMap<String, FlightMatches>();
	}

	private void outputResults() throws FileNotFoundException, UnsupportedEncodingException {
		for (String from_to : this.resultMatches.keySet()) {
			FlightMatches matches = resultMatches.get(from_to);
			FileStream.outputResults(matches.bestFares(), matches.from, matches.to, flt.getFareType());
		}
	}

	private void endOrganizeResults() {
		this.getBestFares();
		// FileStream.outputResults(flightList, from, to, company, fare);
	}

	private void getBestFares() {
		for (FlightMatches flightMatches : matches) {
			flightMatches.bestFares();
		}
	}

	private void doSearchMultiplus() throws FileNotFoundException, UnsupportedEncodingException {
		trip_m = new TripManager(Company.TAM);

		NavigateTamMultiplus multiplus = new NavigateTamMultiplus(urls, flt, trip_m, dt_m);
		addMatches(multiplus.searchMultiplus());
	}

	private void addMatches(ArrayList<FlightMatches> partialMatches) {
		if (partialMatches != null && partialMatches.size() > 0) {
			for (FlightMatches flightMatches : partialMatches) {
				if (resultMatches.containsKey(flightMatches.getKey())) {
					FlightMatches temp = resultMatches.remove(flightMatches.getKey());
					temp.addAllListResults(flightMatches.getListResults());
					resultMatches.put(flightMatches.getKey(), temp);
				} else {
					resultMatches.put(flightMatches.getKey(), flightMatches);
				}	
			}
		}
	}

	public void doSearchSmiles() throws FileNotFoundException, UnsupportedEncodingException {
		trip_m = new TripManager(Company.GOL);

		NavigateGolSmiles smiles = new NavigateGolSmiles(urls, flt, trip_m, dt_m);
		ArrayList<FlightMatches> golMatches = new ArrayList<FlightMatches>();
		golMatches.addAll(smiles.searchSmiles());
		addMatches(golMatches);
	}

	public static void main(String[] args) {
		SearchFares src = new SearchFares();
		try {
			src.doSearchSmiles();
			//src.doSearchMultiplus();
			src.outputResults();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
