package bycompany;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.FlightDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import util.FileStream;
import enums.NationalAirports;

public abstract class SearchToolInstance {
	private final int one_week = 7;
	private final String DD_MM_YYYY = "dd/MM/yyyy";
	
	public String url;
	private String userName;
	private String passwd;	

	private Calendar earliestDepartureHour;
	private Calendar latestReturnDate;
	private Calendar lastAvailableTravellingDate;
	private int departureYear;
	private int departureMonth;
	private int departureDay;
	private int departureHour;
	private int departureMinute;

	private int returnYear;
	private int returnMonth;
	private int returnDay;
	private int returnHour;
	private int returnMinute;

	private String departureDayofWeek;
	private String returnDayofWeek;
	private int maximumMilesLimit;
	private int maximumAmountLimit;

	private ArrayList<FlightDetails> matches;
	
	private WebDriver driver;

	private NationalAirports from;
	private ArrayList<NationalAirports> to;
	
	private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");	
	
	public abstract void includeSearchFilters();
	public abstract void loginUserSpace();
	public abstract void searchFlights();
	public abstract void loopSearchFlights();
	public abstract void extractFlightDetails(List<WebElement> details, Calendar flightTime, NationalAirports to);
	
	private void forwardPeriod() {
		earliestDepartureHour.add(Calendar.DATE, one_week);
		earliestDepartureHour.set(Calendar.HOUR_OF_DAY, departureHour);
		earliestDepartureHour.set(Calendar.MINUTE, departureMinute);

		latestReturnDate.add(Calendar.DATE, one_week);
		latestReturnDate.set(Calendar.HOUR_OF_DAY, returnHour);
		latestReturnDate.set(Calendar.MINUTE, returnMinute);
	}

	private void forwardPeriod(int forwardDays) {
		earliestDepartureHour.add(Calendar.DATE, forwardDays);
		earliestDepartureHour.set(Calendar.HOUR_OF_DAY, departureHour);
		earliestDepartureHour.set(Calendar.MINUTE, departureMinute);

		latestReturnDate.add(Calendar.DATE, forwardDays);
		latestReturnDate.add(Calendar.HOUR_OF_DAY, returnHour);
		latestReturnDate.add(Calendar.MINUTE, returnMinute);
	}
	
	private void chooseReturnDate(String xpath) {
		WebElement returndt = driver.findElement(By.xpath(xpath));
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		returndt.sendKeys(del + format.format(latestReturnDate.getTime()));
	}

	private void chooseFromItemList(List<WebElement> listItem, NationalAirports destination) {
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(destination.toString()).append(")");
		for (WebElement webElement : listItem) {
			if (webElement.getText().contains(builder.toString())) {
				webElement.click();
			}
		}
	}

	private void writeOutFileResults(ArrayList<FlightDetails> matches, NationalAirports from, NationalAirports to) {
		try {
			FileStream.outputResults(matches, from, to);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
