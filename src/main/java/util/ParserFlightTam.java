package util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import model.FlightDetails;
import model.MultiplusExtract;

import org.openqa.selenium.WebElement;

import enums.Company;
import enums.NationalAirports;

public class ParserFlightTam {
	private static final String NAO_EXISTE = "-----";

	public static FlightDetails parseTo(String flightCode, Calendar departureTime, Calendar arriveTime, String timeUntilDestination, String amountLine,
			NationalAirports from, NationalAirports to, String stops) {
		FlightDetails details = new FlightDetails();

		int amount = getTamAmount(amountLine);
		if (amount > 0) {
			details.setFlightCode(flightCode).setFlightTime(departureTime).setArriveTime(arriveTime).setFlightDuration(timeUntilDestination).setAmount(amount)
					.setOriginAirport(from).setDestinationAirport(to).setStopOvers(stops).setCompany(Company.TAM.toString());
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

	public static MultiplusExtract extractAirportAndFlightTimeMultiPlus(String value, Calendar date) {
		String flyingTime = value.substring(0, 5);// partida;
		String airport = value.substring(6, 9);// aeroporto;
		boolean nextDay = false;

		if (value.length() > 9) {
			nextDay = true;// chega no dia seguinte
		}

		MultiplusExtract extractedInfo = new MultiplusExtract();
		Calendar flightDate = ParserFlightTam.convertCalendar(flyingTime, date.get(Calendar.DATE), date.get(Calendar.MONTH), date.get(Calendar.YEAR));
		
		if(nextDay){
			flightDate.add(Calendar.DATE, 1);
		}
		
		extractedInfo.setTime(flightDate).setAirport(NationalAirports.valueOf(airport));

		return extractedInfo;
	}

	public static String extractFaresMultiPlus(String value) {
		String extraction;

		extraction = value.split("(\\r|\\n)")[0];

		return extraction;
	}

	public static String parseCheapestFare(List<WebElement> outboundsTD, int limit) {
		/* milhas PROMO */
		String milesPromo = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(4).getText());

		String milesClassico = NAO_EXISTE;
		String milesIrrestrito = NAO_EXISTE;
		if (outboundsTD.size() > 5) {
			/* milhas clássico */
			milesClassico = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(5).getText());
			if (outboundsTD.size() > 6) {
				/* milhas Irrestrito */
				milesIrrestrito = ParserFlightTam.extractFaresMultiPlus(outboundsTD.get(6).getText());
			}
		}

		return ParserFlightTam.cheapestFare(milesPromo, milesClassico, milesIrrestrito, limit);
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
