package conditional;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitCondition {

	private static Wait<WebDriver> initialize(WebDriver driver) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(35, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		return wait;
	}
	
	private static Wait<WebDriver> initialize(WebDriver driver, int timeInSeconds) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		return wait;
	}

	/**
	 * Calls a JS function and check 'document.ready'
	 * 
	 * @param driver
	 *            webDriver
	 */
	public static void waitPageLoaded(WebDriver driver) {
		Wait<WebDriver> wait = initialize(driver);
		wait.until(ExpectedConditions.refreshed(new WaitPageLoad()));
	}

	/**
	 * Wait till frames are visible and possible to swap
	 * 
	 * @param driver
	 *            webDriver
	 */
	public static void waitFrameLoaded(WebDriver driver, String name) {
		Wait<WebDriver> wait = initialize(driver, 30);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By
				.name(name)));
	}
	
	/**
	 * Wait till frames are visible and possible to swap
	 * 
	 * @param driver
	 *            webDriver
	 */
	public static void waitFrameLoadedxPath(WebDriver driver, String xPath) {
		Wait<WebDriver> wait = initialize(driver, 30);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(xPath)));
	}

	/**
	 * Wait until an element is visible
	 * 
	 * @param xpath
	 *            element xpath to check visibility
	 * @param driver
	 *            webDriver
	 */
	public static void waitElementVisible(String xpath, WebDriver driver) {
		Wait<WebDriver> wait = initialize(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(xpath)));
	}

	/**
	 * Wait until an element is clicable
	 * 
	 * @param xpath
	 *            element xpath to check visibility
	 * @param driver
	 *            webDriver
	 */
	public static void waitElementClicable(String xpath, WebDriver driver) {
		Wait<WebDriver> wait = initialize(driver);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	/**
	 * Wait until an element is clicable
	 * 
	 * @param xpath
	 *            element xpath to check visibility
	 * @param driver
	 *            webDriver
	 */
	public static void waitElementClicable(String xpath, WebDriver driver, int timeInSeconds) {
		Wait<WebDriver> wait = initialize(driver, timeInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
}
