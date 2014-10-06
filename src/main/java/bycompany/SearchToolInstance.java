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
	private int maximumMilesLimit;
	private int maximumAmountLimit;

	private ArrayList<FlightDetails> matches;
	
	private WebDriver driver;
	
	private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");	
	
	public abstract void includeSearchFilters();
	public abstract void loginUserSpace();
	public abstract void searchFlights();
	public abstract void loopSearchFlights();
	public abstract void extractFlightDetails(List<WebElement> details, Calendar flightTime, NationalAirports to);
	
	private void chooseDate(String xpath, Calendar date) {
		WebElement dtInput = driver.findElement(By.xpath(xpath));
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		dtInput.sendKeys(del + format.format(date.getTime()));
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
