package bycompany;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import model.FlightDetails;
import model.FlightMatches;
import model.SearchFilter;
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
	protected SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);

	public abstract void searchFlights(TripManager trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt);

	public abstract void loopSearchFlights(TripManager trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt);

	public abstract void extractFlightDetails(List<WebElement> details, Calendar flightTime, NationalAirports to);

	protected void chooseDate(String xpathFrom, String xPathTo, DateManager dt_m, boolean oneWay) {
		WebElement dtInputFrom = driver.findElement(By.xpath(xpathFrom));
		String delFrom = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		dtInputFrom.sendKeys(delFrom + format.format(dt_m.getEarliestDeparture().getTime()));

		if (!oneWay) {
			WebElement dtInputTo = driver.findElement(By.xpath(xpathFrom));
			String delTo = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
			dtInputTo.sendKeys(delTo + format.format(dt_m.getEarliestDeparture().getTime()));
		}
	}

	protected void chooseAirport(String xpathFrom, String xPathTo, TripManager trip) {
		WebElement airportFrom = driver.findElement(By.xpath(xpathFrom));
		String delFrom = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		airportFrom.sendKeys(delFrom + trip.From().code());

		WebElement airportTo = driver.findElement(By.xpath(xPathTo));
		String delTo = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		airportTo.sendKeys(delTo + trip.To().code());
	}

	protected void radioOneWayTrip(boolean oneway, String xpath) {
		if (oneway) {
			WaitCondition.waitElementVisible(xpath, driver);
			WaitCondition.waitElementClicable(xpath, driver);
			this.actionClickElement(xpath);
		}
	}
	
	protected FlightMatches initFlightMatches(DateManager dt_m, FaresManager fare_m, SearchFilter flt) {
		if (flt.getOneWay()) {
			return new FlightMatches(fare_m, dt_m.getEarliestDeparture());
		} else {
			return new FlightMatches(fare_m, dt_m.getEarliestDeparture(), dt_m.getLatestReturn());
		}
	}

	protected void actionClickElement(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	protected void initializeDriver() {
		if (url != null) {
			driver = new FirefoxDriver();
			driver.get(url);
		}
	}

}
