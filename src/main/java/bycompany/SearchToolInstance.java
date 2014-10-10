package bycompany;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import model.FlightDetails;
import navigation.DateManager;
import navigation.FaresManager;
import navigation.TripManager;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import conditional.WaitCondition;
import enums.NationalAirports;

public abstract class SearchToolInstance {
	private final String DD_MM_YYYY = "dd/MM/yyyy";
	
	public String url;
	public @Inject ArrayList<FlightDetails> matches;
	public WebDriver driver;
	public WaitCondition wait;
	
	
	
	public abstract void searchFlights(TripManager trip, DateManager dt_m, FaresManager fare_m);
	public abstract void loopSearchFlights(TripManager trip, DateManager dt_m, FaresManager fare_m);
	public abstract void extractFlightDetails(List<WebElement> details, Calendar flightTime, NationalAirports to);
	
	protected void chooseDate(String xpath, Calendar date) {
		WebElement dtInput = driver.findElement(By.xpath(xpath));
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		dtInput.sendKeys(del + format.format(date.getTime()));
	}

	protected void chooseAirport(String xpath, NationalAirports destination) {
		WebElement airport = driver.findElement(By.xpath(xpath));
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		airport.sendKeys(del + destination.code());
	}
	
	protected void actionClickElement(String xpath){
		driver.findElement(By.xpath(xpath)).click();
	}

	protected void initializeDriver(){
		if(url != null){
			driver = new FirefoxDriver();
			driver.get(url);
		}
	}
	
}
