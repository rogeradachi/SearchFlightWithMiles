package util;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import bycompany.GolNationalServedAirports;
import bycompany.TamNationalServedAirports;
import enums.NationalAirports;

public class Help {
	public static boolean exists(WebDriver driver, String xpath) {
		boolean exists = false;
		try {
			exists = driver.findElements(By.xpath(xpath)).size() > 0;
		} catch (Exception ex) {
			exists = false;
		}
		return exists;
	}

	public static ArrayList<NationalAirports> attendedByTam(ArrayList<NationalAirports> airports) {
		ArrayList<NationalAirports> attended = new ArrayList<NationalAirports>();
		for (int i = 0; i < airports.size(); i++) {
			NationalAirports nationalAirports = airports.get(i);
//			if (TamNationalAttendedAirports(nationalAirports)) {
//				attended.add(nationalAirports);
//			}
		}
		
		return attended;
	}
	
	public static ArrayList<NationalAirports> attendedByGol(ArrayList<NationalAirports> airports) {
		ArrayList<NationalAirports> attended = new ArrayList<NationalAirports>();
		for (int i = 0; i < airports.size(); i++) {
			NationalAirports nationalAirports = airports.get(i);
//			if (GolNationalAttendedAirports.attendsAirport(nationalAirports)) {
//				attended.add(nationalAirports);
//			}
		}
		
		return attended;
	}
}
