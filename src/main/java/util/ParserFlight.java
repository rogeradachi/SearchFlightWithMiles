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

		details.setFlightCode(flightCode).setFlightTime(departureTime)
				.setArriveTime(arriveTime)
				.setFlightDuration(timeUntilDestination).setAmount(amountLine);

		return details;
	}
}
