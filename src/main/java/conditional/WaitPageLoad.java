package conditional;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WaitPageLoad implements ExpectedCondition<Boolean> {

	public WaitPageLoad() {
	}

	@Override
	public Boolean apply(WebDriver driver) {
		return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	}
}
