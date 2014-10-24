package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import model.FlightDetails;
import model.SearchFilter;
import navigation.DateManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import bycompany.Ids;
import enums.NationalAirports;

public class ParserFlightTam {
	private static final String NAO_EXISTE = "-----";
	
	public static FlightDetails parseTo(String flightCode, Calendar departureTime, Calendar arriveTime, String timeUntilDestination, String amountLine,
			NationalAirports from, NationalAirports to) {
		FlightDetails details = new FlightDetails();

		int amount = getTamAmount(amountLine);
		if (amount > 0) {
			details.setFlightCode(flightCode).setFlightTime(departureTime).setArriveTime(arriveTime).setFlightDuration(timeUntilDestination).setAmount(amount);
			return details;
		} else {
			return null;
		}
	}

	private static int getTamAmount(String value) {
		value = value.trim();
		value = value.replaceAll("\\.", "").replaceAll(",", "");
		value = value.replaceAll("(\\r|\\n)", "");
		
		return Integer.parseInt(value);
	}
	
	public static String[] extractAirportAndFlightTimeMultiPlus(String value) {
		String[] extraction = new String[3];

		extraction[0] = value.substring(0, 5);// partida
		extraction[1] = value.substring(6, 9);// aeroporto
		if (value.length() > 9) {
			extraction[2] = "+1";// chega no dia seguinte
		}

		return extraction;
	}
	
	public static String extractFaresMultiPlus(String value) {
		String extraction;

		extraction = value.split("(\\r|\\n)")[0];

		return extraction;
	}
	
	public static String cheapestFare(String milesPromo, String milesClassico, String milesIrrestrito, int limit) {
		Integer temp = limit + 1;
		String amount = temp.toString();// set above the limit

		boolean keepSearching = true;

		if (!milesPromo.contains("-")) {
			try {
				amount = milesPromo;
				keepSearching = false;
			} catch (Exception ex) {
				keepSearching = true;
			}
		}
		if (keepSearching && !milesClassico.contains("-")) {
			try {
				amount = milesClassico;
				keepSearching = false;
			} catch (Exception ex) {
				keepSearching = true;
			}
		}
		if (keepSearching && !milesIrrestrito.contains("-")) {
			try {
				amount = milesIrrestrito;
			} catch (Exception ex) {

			}
		}

		return amount;
	}
	
	public static Calendar convertCalendar(String timeMultiplus, int day, int month, int year) {
		Calendar calendar = GregorianCalendar.getInstance(Locale.US);
		String[] time = timeMultiplus.split(":");
		calendar.set(year, month, day, Integer.parseInt(time[0]), Integer.parseInt(time[1]));

		return calendar;
	}
	
	
}
