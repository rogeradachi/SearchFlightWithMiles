package util;

import model.FlightDetails;

public class ParserFlight {
	// Voo - G3-1260
	//
	// Sai
	// 06h55
	// (GRU)
	// Chega
	// 08h13
	// (FLN)
	// 0 PARADAS / DURAÇÃO: 01H18

	// 10.000
	public static FlightDetails parseTo(String flightCode,
			String departureTime, String arriveTime,
			String timeUntilDestination, String amountLine, String from,
			String to) {
		FlightDetails details = new FlightDetails();

		details.setFlightCode(flightCode).setFlightTime(getHour(departureTime))
				.setArriveTime(getHour(arriveTime))
				.setFlightDuration(getHour(timeUntilDestination)).setAmount(getAmount(amountLine));

		return details;
	}

	private static String getHour(String value) {
		int index = value.indexOf("h");
		if (index > 0) {
			return value.substring(index - 2, index + 3);
		}
		
		int index2 = value.indexOf("H");
		if (index > 0) {
			return value.substring(index - 2, index + 3);
		}
		
		return "NoTime";
	}
	
	private static int getAmount(String value){
		value = value.replaceAll(".", "").replaceAll(",", "");
		return Integer.parseInt(value);
	}
}
