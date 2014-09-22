package util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import enums.NationalAirports;
import model.FlightDetails;

public class ParserFlight {

	public static FlightDetails parseTo(String flightCode, Calendar departureTime, Calendar arriveTime, String timeUntilDestination, String amountLine, NationalAirports from, NationalAirports to) {
		FlightDetails details = new FlightDetails();

		int amount = getAmount(amountLine);

		if (amount > 0) {
			details.setFlightCode(extractCode(flightCode)).setFlightTime(departureTime).setArriveTime(arriveTime).setFlightDuration(timeUntilDestination).setAmount(amount).setFrom(from).setTo(to);
			return details;
		} else {
			return null;
		}
	}
	
	private static String extractCode(String flightCode){
		return flightCode.replaceAll("Voo - ", "");
	}

	public static Calendar returnCalendar(String time, int day, int month, int year) {
		Calendar calendar = GregorianCalendar.getInstance(Locale.US);

		int[] hourMinute = getHour(time);

		calendar.set(year, month, day, hourMinute[0], hourMinute[1]);

		return calendar;
	}

	private static int[] getHour(String value) {
		value = extractTime(value);
		int index = value.indexOf("h");
		int index2 = value.indexOf("H");

		int[] hourMinute = new int[2];
		if (index > 0) {
			hourMinute[0] = Integer.parseInt(value.substring(index - 2, index));
			hourMinute[1] = Integer.parseInt(value.substring(index + 1, index + 3));
		} else if (index2 > 0) {
			hourMinute[0] = Integer.parseInt(value.substring(index2 - 2, index2));
			hourMinute[1] = Integer.parseInt(value.substring(index2 + 1, index2 + 3));
		}

		return hourMinute;
	}

	private static String extractTime(String time) {
		time = time.replaceAll("(\\r|\\n)", "");

		int ind = time.lastIndexOf('(');

		time = time.substring(0, ind);

		int ind2 = time.lastIndexOf('h');
		if (ind2 < 1) {
			ind2 = time.lastIndexOf('H');
		}

		return time.substring(ind2 - 2, ind2 + 3);
	}

	private static int getAmount(String value) {
		value = value.trim();
		if(value.contains("MILHAS REDUZIDAS")){
			value = value.split("\\r?\\n")[1];	
		}
		else{
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
