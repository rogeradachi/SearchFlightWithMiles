package util;

import java.util.Calendar;

import model.FlightDetails;
import enums.Company;
import enums.NationalAirports;

public class ParserFlightTam {
	public static FlightDetails parseTo(String flightCode, Calendar departureTime, Calendar arriveTime, String timeUntilDestination, String amountLine,
			NationalAirports from, NationalAirports to) {
		FlightDetails details = new FlightDetails();

		int amount = getTamAmount(amountLine);
		if (amount > 0) {
			details.setFlightCode(flightCode).setFlightTime(departureTime).setArriveTime(arriveTime).setFlightDuration(timeUntilDestination).setAmount(amount)
					.setCompany(Company.TAM.toString());
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
}
