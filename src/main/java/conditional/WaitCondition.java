package conditional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitCondition {
	/**
	 * Calls a JS function and check 'document.ready'
	 * @param driver webDriver
	 */
	public static void waitPageLoaded(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		boolean waitOver = false;
		while (waitOver) {
			try {
				wait.until(ExpectedConditions.refreshed(new WaitPageLoad()));
				waitOver = true;
			} catch (Exception ex) {

			}
		}
	}
	
	/**
	 * Wait until an element is visible
	 * @param xpath element xpath to check visibility
	 * @param driver webDriver
	 */
	public static void waitElementVisible(String xpath, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		boolean waitOver = false;
		while (waitOver) {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				waitOver = true;
			} catch (Exception ex) {

			}
		}

	}

	/**
	 * Wait until an element is clicable
	 * @param xpath element xpath to check visibility
	 * @param driver webDriver
	 */
	public static void waitElementClicable(String xpath, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		boolean waitOver = false;
		while (waitOver) {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
				waitOver = true;
			} catch (Exception ex) {

			}
		}

	}
}
