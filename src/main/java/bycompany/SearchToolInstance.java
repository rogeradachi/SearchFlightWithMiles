package bycompany;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import model.FlightDetails;
import model.FlightMatches;
import model.FlightSingleResult;
import model.SearchFilter;
import model.Trip;
import navigation.DateManager;
import navigation.FaresManager;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import conditional.WaitCondition;

public abstract class SearchToolInstance {
	private final String DD_MM_YYYY = "dd/MM/yyyy";

	public String url;
	public @Inject ArrayList<FlightDetails> matches;
	public WebDriver driver;
	public WaitCondition wait;
	protected SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);

	public abstract FlightSingleResult searchFlightsFirstLoop(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt);

	public abstract ArrayList<FlightSingleResult> loopSearchFlights(Trip trip, DateManager dt_m, FaresManager fare_m, SearchFilter flt);

	public abstract FlightSingleResult extractFlightDetails(DateManager dt_m, FaresManager fare_m, SearchFilter flt);

	protected void chooseDate(String xpathFrom, String xPathTo, DateManager dt_m, boolean oneWay) {
		WebElement dtInputFrom = driver.findElement(By.xpath(xpathFrom));
		String delFrom = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		dtInputFrom.sendKeys(delFrom + format.format(dt_m.getEarliestDeparture().getTime()));

		if (!oneWay) {
			WebElement dtInputTo = driver.findElement(By.xpath(xPathTo));
			String delTo = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
			dtInputTo.sendKeys(delTo + format.format(dt_m.getLatestReturn().getTime()));
		}
	}

	protected void chooseAirport(String xpathFrom, String xPathTo, Trip trip) {
		WebElement airportFrom = driver.findElement(By.xpath(xpathFrom));
		String delFrom = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		airportFrom.sendKeys(delFrom + trip.from());

		WebElement airportTo = driver.findElement(By.xpath(xPathTo));
		String delTo = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		airportTo.sendKeys(delTo + trip.to());
	}

	protected void radioOneWayTrip(boolean oneway, String xpath) {
		if (oneway) {
			WaitCondition.waitElementVisible(xpath, driver);
			WaitCondition.waitElementClicable(xpath, driver);
			this.actionClickElement(xpath);
		}
	}	

	protected void actionClickElement(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	protected void initializeDriver() {
		if (url != null) {
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.get(url);
		}
	}

}
