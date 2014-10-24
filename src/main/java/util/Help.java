package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
}
