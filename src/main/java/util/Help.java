package util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class Help {
	public static WebElement checkPresence(final WebElement el, final By locator) {
		try {
			WebElement element = el.findElement(locator);
			return element;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static WebElement checkPresence(final WebDriver driver, final By locator) {
		try {
			WebElement element = driver.findElement(locator);
			return element;
		} catch (Exception ex) {
			return null;
		}
	}

	public static List<WebElement> fluentWaitElements(final WebElement el, final By locator) {
		Wait<WebElement> wait = new FluentWait<WebElement>(el).withTimeout(100, TimeUnit.MILLISECONDS).pollingEvery(50, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);

		try {
			wait.until(new Function<WebElement, List<WebElement>>() {
				@Override
				public List<WebElement> apply(WebElement element) {
					return element.findElements(locator);
				}
			});
		} catch (Exception ex) {
			return null;
		}

		return null;
	};
}
