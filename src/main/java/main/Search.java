package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import conditional.WaitPageLoad;

public class Search {
	final String golInputLoginId = "s_1_1_9_0";
	final String golInputPswdId = "s_1_1_10_0";
	final String golSubmitLoginId = "s_1_1_12_0";
	final String golGoToTicketsId = "s_4_1_4_0";
	final String gol = "https://clientes.smiles.com.br/eloyalty_ptb/start.swe?SWECmd=GotoView&SWEView=Login%20View";
	final String tam = "http://www.tam.com.br";
	final String loginNameGol = "";
	final String loginNameTam = "";
	final String pswdNameGol = "";
	final String psgdNameTam = "";

	public Search() {
	}

	public void SearchGol() {
		WebDriver driver = new FirefoxDriver();
		loginPageGol(driver);
		smilesPage(driver);
		smilesSearchPage(driver);
	}

	public void SearchTam() {
		WebDriver driver = new FirefoxDriver();
		loginPageTam(driver);
	}

	private void loginPageGol(WebDriver driver) {
		driver.get(gol);

		waitPageLoaded(driver);
		
		navigateThroughInternalFramesGol(driver);
		inputLogin(driver, loginNameGol, golInputLoginId);
		inputPassWord(driver, pswdNameGol, golInputPswdId);
		driver.findElement(By.id(golSubmitLoginId)).click();
	}

	private void smilesPage(WebDriver driver) {
		waitPageLoaded(driver);
		
		navigateThroughInternalFramesSmiles(driver);
		driver.findElement(By.id(golGoToTicketsId)).click();
	}
	
	private void smilesSearchPage(WebDriver driver){		
		waitPageLoaded(driver);
		
		navigateThroughInternalFramesSearch(driver);
		//TODO:Do the search here
		
		//fs_popUp_origins[0]
		//coloca a classe 'highlights'
		//<div id="fs_popUp_origins[0]" class="fs_popUpClass airports" style="width: 206px; display: none;"><ul class="fs_innerPopUpClass"><li class="fs_elementLine highlight" value="CCS">Caracas (CCS)</li></ul></div>
		//<div id="fs_popUp_destinations[0]" class="fs_popUpClass airports" style="width: 206px; display: none;"><ul class="fs_innerPopUpClass"><li class="fs_elementLine" value="SLZ">São Luis (SLZ)</li><li class="fs_elementLine" value="GRU">São Paulo - Guarulhos (GRU)</li><li class="fs_elementLine highlight" value="SAO">São Paulo - Todos os Aeroportos (SAO)</li></ul></div>
	}

	private void loginPageTam(WebDriver driver) {
		driver.get(tam);
		
		waitPageLoaded(driver);

		driver = navigateThroughInternalFramesTam(driver);
	}
	
	private void waitPageLoaded(WebDriver driver){
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(new WaitPageLoad()));
	}

	private WebDriver navigateThroughInternalFramesGol(WebDriver driver) {
		driver = driver.switchTo().frame(0);// to the first frame
		driver = driver.switchTo().frame(1); // to the swecontent frame
		driver = driver.switchTo().frame("_sweview"); // to the main view frame
		return driver;
	}

	private WebDriver navigateThroughInternalFramesSmiles(WebDriver driver) {
		driver = driver.switchTo().frame(0);
		driver = driver.switchTo().frame(1);
		driver = driver.switchTo().frame("_sweview");

		return driver;
	}
	
	private WebDriver navigateThroughInternalFramesSearch(WebDriver driver) {
		driver = driver.switchTo().frame(0);

		return driver;
	}

	private WebDriver navigateThroughInternalFramesTam(WebDriver driver) {
		driver = driver.switchTo().frame("lojinha");// to the frame lojinha
		print(driver);
		return driver;
	}

	private void inputLogin(WebDriver driver, String loginName, String id) {
		WebElement input = driver.findElement(By.id(id));
		input.sendKeys(loginName);
	}

	private void inputPassWord(WebDriver driver, String loginName, String id) {
		WebElement input = driver.findElement(By.id(id));
		input.sendKeys(loginName);
	}

	private void print(WebDriver driver) {
		System.out.println(driver.getPageSource());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Search execute = new Search();
		execute.SearchGol();
		// SplitCityNameCode nameSplit = new SplitCityNameCode();
		// nameSplit.splitExtractListCities();

	}
}
