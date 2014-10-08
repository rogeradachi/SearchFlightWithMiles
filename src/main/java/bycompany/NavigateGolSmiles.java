package bycompany;

import java.util.Calendar;
import java.util.List;

import model.Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import conditional.WaitCondition;
import enums.NationalAirports;

public class NavigateGolSmiles extends SearchToolInstance {
	private final String DATEPICKER_INPUT_VOLTA = "id('datepickerInputVolta')";
	private final String DATEPICKER_INPUT_IDA = "id('datepickerInputIda')";
	private final String golInputLoginxPath = "id('s_1_1_9_0')";
	private final String golInputPswdxPath = "id('s_1_1_10_0')";
	private final String golSubmitLoginxPath = "id('s_1_1_12_0')";
	private final String golGoToTicketsId = "s_4_1_4_0";
	
	public NavigateGolSmiles(String url){
		driver = new FirefoxDriver();
		driver.get(url);		
	}
	
	public void loginUserSpace(Login loginSmiles) {
		WaitCondition.waitPageLoaded(driver);
		
		navigateThroughInternalFramesGol();
		
		inputLogin(loginSmiles.getLogin(), golInputLoginxPath);
		inputPassWord(loginSmiles.getPassword(), golInputPswdxPath);
		actionClickElement(golSubmitLoginxPath);
	}
	@Override
	public void searchFlights() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void loopSearchFlights() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void extractFlightDetails(List<WebElement> details, Calendar flightTime, NationalAirports to) {
		// TODO Auto-generated method stub
		
	}

	private void inputLogin(String loginName, String id) {
		WebElement input = driver.findElement(By.xpath(id));
		input.sendKeys(loginName);
	}

	private void inputPassWord(String loginName, String pswd) {
		WebElement input = driver.findElement(By.xpath(pswd));
		input.sendKeys(loginName);
	}
	
	private WebDriver navigateThroughInternalFramesGol() {
		driver = driver.switchTo().frame(0);// to the first frame
		driver = driver.switchTo().frame(1); // to the swecontent frame
		driver = driver.switchTo().frame("_sweview"); // to the main view frame
		return driver;
	}
}
