package bycompany;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import model.Login;
import navigation.DateManager;
import navigation.FaresManager;
import navigation.TripManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import util.FileReadService;
import conditional.WaitCondition;
import enums.NationalAirports;

public class NavigateGolSmiles extends SearchToolInstance {
	private final String DATEPICKER_INPUT_VOLTA = "id('datepickerInputVolta')";
	private final String DATEPICKER_INPUT_IDA = "id('datepickerInputIda')";
	private final String golInputLoginxPath = "id('s_3_1_8_0')";
	private final String golInputPswdxPath = "id('s_3_1_9_0')";
	private final String golSubmitLoginxPath = "id('s_3_1_11_0')";
	private final String golGoToTicketsId = "id('s_4_1_4_0')";
	protected @Inject Login smilesLogin;
	
	public NavigateGolSmiles(HashMap<String, String> urls){		
		this.url = urls.get("smiles");
		smilesLogin = FileReadService.readPersonalDetailsFromFile();
	}
	
	public void loginUserSpace() {
		this.initializeDriver();
		
		WaitCondition.waitPageLoaded(driver);
		
		navigateThroughInternalFramesGol();
		
		inputLogin(smilesLogin.getLogin(), golInputLoginxPath);
		inputPassWord(smilesLogin.getPassword(), golInputPswdxPath);
		actionClickElement(golSubmitLoginxPath);
		
		gotoSmilesSearchPage();
	}
	
	private void gotoSmilesSearchPage(){
		WaitCondition.waitPageLoaded(driver);
		
		navigateThroughInternalFramesGol();
		WaitCondition.waitElementVisible(golGoToTicketsId, driver);
		WaitCondition.waitElementClicable(golGoToTicketsId, driver);		
		
		actionClickElement(golGoToTicketsId);
	}
	
	@Override
	public void searchFlights(TripManager trip, DateManager dt_m, FaresManager fare_m) {
		WaitCondition.waitPageLoaded(driver);
		// TODO Auto-generated method stub
		
	}
	@Override
	public void loopSearchFlights(TripManager trip, DateManager dt_m, FaresManager fare_m) {
		WaitCondition.waitPageLoaded(driver);
		// TODO Auto-generated method stub
		
	}
	@Override
	public void extractFlightDetails(List<WebElement> details, Calendar flightTime, NationalAirports to) {
		WaitCondition.waitPageLoaded(driver);
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
	
	private void navigateThroughInternalFramesGol() {
		driver = driver.switchTo().frame(0);// to the first frame
		driver = driver.switchTo().frame(1); // to the swecontent frame
		driver = driver.switchTo().frame("_sweview"); // to the main view frame
	}
}
