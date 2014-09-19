package main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.FlightDetails;
import model.FlightMatches;

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
import enums.NationalAirports;

public class Search {
	private final String DD_MM_YYYY = "dd/MM/yyyy";
	private final int oneWeek = 7;
	private final String DATEPICKER_INPUT_VOLTA = "id('datepickerInputVolta')";
	private final String DATEPICKER_INPUT_IDA = "id('datepickerInputIda')";
	final String golInputLoginxPath = "id('s_1_1_9_0')";
	final String golInputPswdxPath = "id('s_1_1_10_0')";
	final String golSubmitLoginxPath = "id('s_1_1_12_0')";
	final String golGoToTicketsId = "s_4_1_4_0";
	final String gol = "https://clientes.smiles.com.br/eloyalty_ptb/start.swe?SWECmd=GotoView&SWEView=Login%20View";
	final String tam = "http://www.tam.com.br";
	private String loginNameGol;
	private String loginNameTam;
	private String pswdNameGol;
	private String pswdNameTam;
	private NationalAirports from;
	private NationalAirports to;
	private WebDriver driver;
	
	private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	private Calendar earliestDepartureHour;
	private Calendar latestReturnDate;
	private Calendar lastAvailableTravellingDate;
	private int departureYear;
	private int departureMonth;
	private int departureDay;
	private int departureHour;
	private int departureMinute;

	private int returnYear;
	private int returnMonth;
	private int returnDay;
	private int returnHour;
	private int returnMinute;

	private String departureDayofWeek;
	private String returnDayofWeek;
	private int maximumMilesLimit;
	private int maximumAmountLimit;

	private Map<String, String> login;

	public Search() {
		login = new HashMap<String, String>();
		driver = new FirefoxDriver();
		earliestDepartureHour = GregorianCalendar.getInstance(Locale.US);
		latestReturnDate = GregorianCalendar.getInstance(Locale.US);
		lastAvailableTravellingDate = GregorianCalendar.getInstance(Locale.US);

		departureYear = 2014;
		returnYear = 2014;

		departureMonth = 9;
		returnMonth = 9;

		departureDay = 19;
		returnDay = 21;

		departureHour = 18;
		departureMinute = 10;
		returnHour = 19;
		returnMinute = 10;
				
		earliestDepartureHour.set(departureYear, departureMonth, departureDay, departureHour, departureMinute);
		latestReturnDate.set(returnYear, returnMonth, returnDay, returnHour, returnMinute);		
		lastAvailableTravellingDate.set(2014, 11, 30, returnHour,returnMinute);

		init();
	}

	public void init() {

		HashMap<String, String> mapping = FileStream.readPersonalDetailsFromFile();
		loginNameGol = mapping.get(Login.loginGol.getValue());
		loginNameTam = mapping.get(Login.loginTam.getValue());
		pswdNameGol = mapping.get(Login.passwordGol.getValue());
		pswdNameTam = mapping.get(Login.passwordTam.getValue());

		from = NationalAirports.SP_TODOS;
		to = NationalAirports.SC_Florianopolis;

		maximumMilesLimit = 20000;
		maximumAmountLimit = 500;
	}

	private void forwardPeriod() {
		earliestDepartureHour.add(Calendar.DATE, oneWeek);
		earliestDepartureHour.set(Calendar.HOUR_OF_DAY, departureHour);
		earliestDepartureHour.set(Calendar.MINUTE, departureMinute);
		
		latestReturnDate.add(Calendar.DATE, oneWeek);
		latestReturnDate.add(Calendar.HOUR_OF_DAY, returnHour);
		latestReturnDate.add(Calendar.MINUTE, returnMinute);
	}

	private void forwardPeriod(int forwardDays) {
		earliestDepartureHour.add(Calendar.DATE, forwardDays);
		earliestDepartureHour.set(Calendar.HOUR_OF_DAY, departureHour);
		earliestDepartureHour.set(Calendar.MINUTE, departureMinute);
		
		latestReturnDate.add(Calendar.DATE, forwardDays);
		latestReturnDate.add(Calendar.HOUR_OF_DAY, returnHour);
		latestReturnDate.add(Calendar.MINUTE, returnMinute);
	}

	public void SearchGol() {

		loginPageGol();
		smilesPage();
		smilesSearchPage(from, to);
		extractFlightDetails();

		while (lastAvailableTravellingDate.after(latestReturnDate)) {
			this.forwardPeriod();
			nextSearchPage();
			extractFlightDetails();
		}

		driver.close();
	}

	public void extractFlightDetails() {
		waitPageLoaded();

		FlightMatches searchMatches = new FlightMatches(maximumAmountLimit, maximumMilesLimit, earliestDepartureHour, latestReturnDate);
		// departure flights
		List<WebElement> departures = driver.findElements(By.xpath("id('site')/div[6]/div[3]/div"));
		departures.remove(0);// remove header
		searchMatches.setDepartureFlights(extractListDetails(departures, earliestDepartureHour));

		// return flights
		List<WebElement> arrives = driver.findElements(By.xpath("id('site')/div[7]/div[3]/div"));
		arrives.remove(0);// remove header

		searchMatches.setReturnFlights(extractListDetails(arrives, latestReturnDate));

		try {
			FileStream.outputResults(searchMatches.bestMilesFares());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<FlightDetails> extractListDetails(List<WebElement> details, Calendar flightTime) {
		ArrayList<FlightDetails> listaFlights = new ArrayList<FlightDetails>();

		for (WebElement webElement : details) {
			WebElement flightDetails = webElement.findElement(By.cssSelector(".contentFlight"));
			WebElement flightPrice = webElement.findElement(By.cssSelector(".contentTarifas"));

			String code = flightDetails.findElement(By.cssSelector(".voo-titulo")).getText();
			String leave = flightDetails.findElement(By.cssSelector(".saida-voo")).getText();
			String arrive = flightDetails.findElement(By.cssSelector(".chegada-voo")).getText();
			String duration = flightDetails.findElement(By.cssSelector(".duracao-voo")).getText();

			//TODO: verificar o conflitos de datas fixas do voo de partida e chegada (podem ocorrer em dias diferentes e consequentemnte em anos diferentes)
			Calendar departureDate = ParserFlight.returnCalendar(leave,flightTime.get(Calendar.DATE), flightTime.get(Calendar.MONTH), flightTime.get(Calendar.YEAR));
			Calendar returnDate = ParserFlight.returnCalendar(arrive, flightTime.get(Calendar.DATE), flightTime.get(Calendar.MONTH), flightTime.get(Calendar.YEAR));
			
			FlightDetails flight = ParserFlight.parseTo(code, departureDate, returnDate, duration, flightPrice.getText(), from, to);
			if(flight != null){
				listaFlights.add(flight);
			}
		}

		return listaFlights;
	}

	public void SearchTam() {
		loginPageTam();
	}

	private void loginPageGol() {
		driver.get(gol);

		waitPageLoaded();

		navigateThroughInternalFramesGol();
		inputLogin(loginNameGol, golInputLoginxPath);
		inputPassWord(pswdNameGol, golInputPswdxPath);
		driver.findElement(By.xpath(golSubmitLoginxPath)).click();
	}

	private void smilesPage() {
		waitPageLoaded();

		navigateThroughInternalFramesSmiles();
		driver.findElement(By.id(golGoToTicketsId)).click();
	}

	private void smilesSearchPage(NationalAirports origem, NationalAirports destino) {
		waitPageLoaded();

		navigateThroughInternalFramesSearch();

		waitPageLoaded();
		/* Departure Airport */
		/* dropdown list has to become active */
		driver.findElement(By.xpath("id('fs_container_origins[0]')/a")).click();
		List<WebElement> originLi = driver.findElements(By.xpath("id('fs_popUp_origins[0]')/ul/li"));
		chooseFromItemList(originLi, origem);
		/* Destination Airport */
		/* dropdown list has to become active */
		driver.findElement(By.xpath("id('fs_container_destinations[0]')/a")).click();
		List<WebElement> destinationLi = driver.findElements(By.xpath("id('fs_popUp_destinations[0]')/ul/li"));
		chooseFromItemList(destinationLi, destino);

		chooseDepartureDate();
		chooseReturnDate();

		driver.findElement(By.xpath("id('toCategory')/a")).click();
	}

	private void nextSearchPage() {
		waitPageLoaded();

		chooseDepartureDate();
		chooseReturnDate();

		driver.findElement(By.xpath("id('search')/div[3]/a")).click();
	}

	private void chooseDepartureDate() {
		WebElement departure = driver.findElement(By.xpath(DATEPICKER_INPUT_IDA));
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		departure.sendKeys(del + format.format(earliestDepartureHour.getTime()));
	}

	private void chooseReturnDate() {
		WebElement returndt = driver.findElement(By.xpath(DATEPICKER_INPUT_VOLTA));
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		returndt.sendKeys(del + format.format(latestReturnDate.getTime()));
	}

	private void chooseFromItemList(List<WebElement> listItem, NationalAirports destination) {
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(destination.code()).append(")");
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
		WebDriverWait wait = new WebDriverWait(driver, 45);
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
		WebElement input = driver.findElement(By.xpath(id));
		input.sendKeys(loginName);
	}

	private void inputPassWord(String loginName, String pswd) {
		WebElement input = driver.findElement(By.xpath(pswd));
		input.sendKeys(loginName);
	}

	private void print() {
		System.out.println(driver.getPageSource());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Search execute = new Search();
		execute.SearchGol();
		
//		try {
//			Date data = df.parse("10/10/2014 23:12");
//			Date data2 = df.parse("10/10/2014 23:14");
//			Calendar calendar = GregorianCalendar.getInstance(Locale.US);
//			Calendar calendar2 = GregorianCalendar.getInstance(Locale.US);
//			calendar.setTime(data);
//			calendar2.setTime(data2);
//			System.out.println(calendar.getTime());
//			
//			if(calendar2.before(calendar)){
//				System.out.println("2 antes do 1");
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
////		}
		
		// SplitCityNameCode nameSplit = new SplitCityNameCode();
		// nameSplit.splitExtractListCities();

	}
}
