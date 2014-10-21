package util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.FlightDetails;
import model.SmilesExtract;
import enums.Company;
import enums.NationalAirports;

public class ParserFlightGol {

	public static FlightDetails parseTo(String leave, String arrive, Calendar flightTime, String flightCode, String timeUntilDestination, String amountLine) {
		FlightDetails details = new FlightDetails();

		SmilesExtract leaveFlight = extractFlight(leave, flightTime.get(Calendar.DATE), flightTime.get(Calendar.MONTH), flightTime.get(Calendar.YEAR));

		SmilesExtract arriveFlight = extractFlight(arrive, flightTime.get(Calendar.DATE), flightTime.get(Calendar.MONTH), flightTime.get(Calendar.YEAR));

		int amount = getGolAmount(amountLine);

		if (amount > 0) {
			details.setFlightCode(extractGolCode(flightCode)).setFlightTime(leaveFlight.getExtractedDate()).setArriveTime(arriveFlight.getExtractedDate())
					.setFlightDuration(timeUntilDestination).setAmount(amount);
			return details;
		} else {
			return null;
		}
	}

	private static String extractGolCode(String flightCode) {
		return flightCode.replaceAll("Voo - ", "");
	}

	public static SmilesExtract extractFlight(String time, int day, int month, int year) {
		SmilesExtract extracted = new SmilesExtract();
		String[] split = time.split("(\\r|\\n)");

		int[] hour = extractGolFlightHour(split[1]);
		NationalAirports apt = extractGolAirport(split[2]);

		Calendar calendar = GregorianCalendar.getInstance(Locale.US);
		calendar.set(year, month, day, hour[0], hour[1]);

		extracted.setAirport(apt).setHour(hour).setExtractedDate(calendar);

		return extracted;
	}

	private static int[] extractGolFlightHour(String time) {
		String[] hour;
		int[] hourInt = new int[2];

		hour = time.split("h");
		hourInt[0] = Integer.parseInt(hour[0]);
		hourInt[1] = Integer.parseInt(hour[1]);

		return hourInt;
	}

	private static NationalAirports extractGolAirport(String time) {
		Pattern p = Pattern.compile("\\((.*?)\\)");
		Matcher matcher = p.matcher(time);
		while (matcher.find()) {
			if (matcher.group(1) != null && matcher.group(1).length() > 0) {
				return NationalAirports.valueOf(matcher.group(1));
			}
		}
		return null;
	}

	private static int getGolAmount(String value) {
		value = value.trim();
		if (value.contains("MILHAS REDUZIDAS")) {
			value = value.split("\\r?\\n")[1];
		} else {
			value = value.split("\\r?\\n")[0];
		}
		value = value.replaceAll("\\.", "").replaceAll(",", "");
		value = value.replaceAll("(\\r|\\n)", "");
		if (value.equalsIgnoreCase("ESGOTADO")) {
			return -1;
		} else {
			return Integer.parseInt(value);
		}
	}
}
