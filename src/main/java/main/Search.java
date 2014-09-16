package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.FlightDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.FileStream;
import util.ParserFlight;
import conditional.WaitPageLoad;
import enums.Login;

public class Search {
	private final int oneWeek = 7;
	private final String DATEPICKER_INPUT_VOLTA = "datepickerInputVolta";
	private final String DATEPICKER_INPUT_IDA = "datepickerInputIda";
	final String golInputLoginId = "s_1_1_9_0";
	final String golInputPswdId = "s_1_1_10_0";
	final String golSubmitLoginId = "s_1_1_12_0";
	final String golGoToTicketsId = "s_4_1_4_0";
	final String gol = "https://clientes.smiles.com.br/eloyalty_ptb/start.swe?SWECmd=GotoView&SWEView=Login%20View";
	final String tam = "http://www.tam.com.br";
	private String loginNameGol;
	private String loginNameTam;
	private String pswdNameGol;
	private String pswdNameTam;
	private String from = "SAO";
	private String to = "FLN";
	private WebDriver driver;

	private Calendar departureDate;
	private Calendar returnDate;
	private int departureYear;
	private int departureMonth;
	private int departureDay;
	private int returnYear;
	private int returnMonth;
	private int returnDay;
	private String departureDayofWeek;
	private String returnDayofWeek;

	private Map<String, String> login;

	public Search() {
		login = new HashMap<String, String>();
		driver = new FirefoxDriver();
		departureDate = Calendar.getInstance();
		returnDate = Calendar.getInstance();

		departureYear = 2014;
		returnYear = 2014;

		departureMonth = 11;
		returnMonth = 11;

		departureDay = 15;
		returnDay = 18;

		departureDate.set(departureYear, departureMonth, departureDay);
		returnDate.set(returnYear, returnMonth, returnDay);
		
		init();
	}

	public void init() {

		HashMap<String, String> mapping = FileStream
				.readPersonalDetailsFromFile();
		loginNameGol = mapping.get(Login.loginGol.getValue());
		loginNameTam = mapping.get(Login.loginTam.getValue());
		pswdNameGol = mapping.get(Login.passwordGol.getValue());
		pswdNameTam = mapping.get(Login.passwordTam.getValue());
	}

	private void forwardPeriod() {
		departureDate.add(Calendar.DATE, oneWeek);
		returnDate.add(Calendar.DATE, oneWeek);
	}

	private void forwardPeriod(int forwardDays) {
		departureDate.add(Calendar.DATE, forwardDays);
		returnDate.add(Calendar.DATE, forwardDays);
	}

	public void SearchGol() {

		loginPageGol();
		smilesPage();
		smilesSearchPage(from, to);
		extractFlightDetails();

		// int i = 0;
		// while (i < 3) {
		// this.forwardPeriod();
		// nextSearchPage();
		// i++;
		// }

	}

	public void extractFlightDetails() {
		List<WebElement> resultList = driver.findElements(By
				.cssSelector(".containerSelect"));
		WebElement departure = resultList.get(0);
		WebElement rtn = resultList.get(1);

		List<WebElement> departures = departure.findElements(By
				.cssSelector(".containerVoos"));

		ArrayList<FlightDetails> listaFlights = new ArrayList<FlightDetails>();
		for (WebElement webElement : departures) {
			WebElement flightDetails = webElement.findElement(By
					.cssSelector(".contentFlight"));
			WebElement flightPrice = webElement.findElement(By
					.cssSelector(".contentTarifas"));
			
			String code = flightDetails.findElement(By.cssSelector(".voo-titulo")).getText();
			String leave = flightDetails.findElement(By.cssSelector(".saida-voo")).getText();
			String arrive = flightDetails.findElement(By.cssSelector(".chegada-voo")).getText();
			String duration = flightDetails.findElement(By.cssSelector(".duracao-voo")).getText();
			
			listaFlights.add(ParserFlight.parseTo(code, leave, arrive, duration, flightPrice.getText(), from, to));
		}
		
		try {
			FileStream.outputResults(listaFlights);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void SearchTam() {
		loginPageTam();
	}

	private void loginPageGol() {
		driver.get(gol);

		waitPageLoaded();

		navigateThroughInternalFramesGol();
		inputLogin(loginNameGol, golInputLoginId);
		inputPassWord(pswdNameGol, golInputPswdId);
		driver.findElement(By.id(golSubmitLoginId)).click();
	}

	private void smilesPage() {
		waitPageLoaded();

		navigateThroughInternalFramesSmiles();
		driver.findElement(By.id(golGoToTicketsId)).click();
	}

	private void smilesSearchPage(String origem, String destino) {
		waitPageLoaded();

		navigateThroughInternalFramesSearch();

		WebElement origin = driver
				.findElement(By.id("fs_container_origins[0]"));
		origin.findElement(By.cssSelector("a")).click();
		List<WebElement> originLi = origin
				.findElements(By.cssSelector("ul li"));
		chooseFromItemList(originLi, origem);

		WebElement destinationDiv = driver.findElement(By
				.id("fs_container_destinations[0]"));
		destinationDiv.findElement(By.cssSelector("a")).click();
		List<WebElement> destinationLi = destinationDiv.findElements(By
				.cssSelector("ul li"));
		chooseFromItemList(destinationLi, destino);

		chooseDepartureDate();
		chooseReturnDate();

		WebElement searchButton = driver.findElement(By.id("toCategory"))
				.findElement(By.cssSelector("a"));
		searchButton.click();
	}

	private void nextSearchPage() {
		waitPageLoaded();

		chooseDepartureDate();
		chooseReturnDate();

		driver.findElement(By.id("search"))
				.findElement(
						By.cssSelector("[class='btnContinuar btnewSearchSelect']"))
				.click();
	}

	private void chooseDepartureDate() {
		WebElement departure = driver.findElement(By.id(DATEPICKER_INPUT_IDA));
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		departure.sendKeys(del + format.format(departureDate.getTime()));
	}

	private void chooseReturnDate() {
		WebElement returndt = driver.findElement(By.id(DATEPICKER_INPUT_VOLTA));
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		returndt.sendKeys(del + format.format(returnDate.getTime()));
	}

	private void chooseFromItemList(List<WebElement> listItem,
			String destination) {
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(destination).append(")");
		for (WebElement webElement : listItem) {
			if (webElement.getText().contains(builder.toString())) {
				webElement.click();
			}
		}
	}

	private void loginPageTam() {
		driver.get(tam);

		waitPageLoaded();

		driver = navigateThroughInternalFramesTam();
	}

	private void waitPageLoaded() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(new WaitPageLoad()));
	}

	private WebDriver navigateThroughInternalFramesGol() {
		driver = driver.switchTo().frame(0);// to the first frame
		driver = driver.switchTo().frame(1); // to the swecontent frame
		driver = driver.switchTo().frame("_sweview"); // to the main view frame
		return driver;
	}

	private WebDriver navigateThroughInternalFramesSmiles() {
		driver = driver.switchTo().frame(0);
		driver = driver.switchTo().frame(1);
		driver = driver.switchTo().frame("_sweview");

		return driver;
	}

	private WebDriver navigateThroughInternalFramesSearch() {
		driver = driver.switchTo().frame(0);

		return driver;
	}

	private WebDriver navigateThroughInternalFramesTam() {
		driver = driver.switchTo().frame("lojinha");// to the frame lojinha
		return driver;
	}

	private void inputLogin(String loginName, String id) {
		WebElement input = driver.findElement(By.id(id));
		input.sendKeys(loginName);
	}

	private void inputPassWord(String loginName, String id) {
		WebElement input = driver.findElement(By.id(id));
		input.sendKeys(loginName);
	}

	private void print() {
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
