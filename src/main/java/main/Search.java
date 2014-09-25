package main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import bycompany.GolNationalAttendedAirports;
import bycompany.TamNationalAttendedAirports;
import util.FileStream;
import util.Help;
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
	private ArrayList<NationalAirports> from;
	private ArrayList<NationalAirports> to;
	private WebDriver driver;

	private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	private Calendar earliestDepartureHour;
	private Calendar latestReturnDate;
	private final Calendar firstEarliestDepartureHour;
	private final Calendar firstLatestReturnDate;
	private Calendar lastAvailableTravellingDate;
	private int departureYear;
	private int departureMonth;
	private int departureDay;
	private int departureHour;
	private int departureMinute;
	private boolean oneWay;

	private int returnYear;
	private int returnMonth;
	private int returnDay;
	private int returnHour;
	private int returnMinute;

	private String departureDayofWeek;
	private String returnDayofWeek;
	private int maximumMilesLimit;
	private int maximumAmountLimit;

	private ArrayList<FlightDetails> matches;

	private Map<String, String> login;

	public Search() {
		login = new HashMap<String, String>();
		driver = new FirefoxDriver();
		earliestDepartureHour = GregorianCalendar.getInstance(Locale.US);
		latestReturnDate = GregorianCalendar.getInstance(Locale.US);
		firstEarliestDepartureHour = GregorianCalendar.getInstance(Locale.US);
		firstLatestReturnDate = GregorianCalendar.getInstance(Locale.US);
		lastAvailableTravellingDate = GregorianCalendar.getInstance(Locale.US);

		departureYear = 2014;
		returnYear = 2014;

		departureMonth = Calendar.SEPTEMBER;
		returnMonth = Calendar.SEPTEMBER;

		departureDay = 26;
		returnDay = 28;

		departureHour = 16;
		departureMinute = 10;
		returnHour = 10;
		returnMinute = 10;

		oneWay = false;

		firstEarliestDepartureHour.set(departureYear, departureMonth, departureDay, departureHour, departureMinute);
		firstLatestReturnDate.set(returnYear, returnMonth, returnDay, returnHour, returnMinute);

		earliestDepartureHour = (Calendar) firstEarliestDepartureHour.clone();
		latestReturnDate = (Calendar) firstLatestReturnDate.clone();
		lastAvailableTravellingDate.set(2014, Calendar.OCTOBER, 7, returnHour, returnMinute);

		matches = new ArrayList<FlightDetails>();
		init();
	}

	public void init() {

		HashMap<String, String> mapping = FileStream.readPersonalDetailsFromFile();
		loginNameGol = mapping.get(Login.loginGol.getValue());
		loginNameTam = mapping.get(Login.loginTam.getValue());
		pswdNameGol = mapping.get(Login.passwordGol.getValue());
		pswdNameTam = mapping.get(Login.passwordTam.getValue());

		from = new ArrayList<NationalAirports>();
		to = new ArrayList<NationalAirports>();

		from.add(NationalAirports.CXJ);
		from.add(NationalAirports.FLN);
		from.add(NationalAirports.POA);
		from.add(NationalAirports.NVT);

		to.add(NationalAirports.SAO);

		maximumMilesLimit = 16000;
		maximumAmountLimit = 500;
	}

	private void forwardPeriod() {
		earliestDepartureHour.add(Calendar.DATE, oneWeek);
		earliestDepartureHour.set(Calendar.HOUR_OF_DAY, departureHour);
		earliestDepartureHour.set(Calendar.MINUTE, departureMinute);

		latestReturnDate.add(Calendar.DATE, oneWeek);
		latestReturnDate.set(Calendar.HOUR_OF_DAY, returnHour);
		latestReturnDate.set(Calendar.MINUTE, returnMinute);
	}

	private void forwardPeriod(int forwardDays) {
		earliestDepartureHour.add(Calendar.DATE, forwardDays);
		earliestDepartureHour.set(Calendar.HOUR_OF_DAY, departureHour);
		earliestDepartureHour.set(Calendar.MINUTE, departureMinute);

		latestReturnDate.add(Calendar.DATE, forwardDays);
		latestReturnDate.set(Calendar.HOUR_OF_DAY, returnHour);
		latestReturnDate.set(Calendar.MINUTE, returnMinute);
	}

	public void SearchGol() {

		try {
			loginPageGol();// TODO: Iterate over varios destinations

			smilesPage();
			ArrayList<NationalAirports> attendedFrom = Help.attendedByGol(from);
			ArrayList<NationalAirports> attendedTo = Help.attendedByGol(to);

			boolean nextIteraction = false;
			for (NationalAirports dep : attendedFrom) {
				for (NationalAirports des : attendedTo) {
					if (!nextIteraction) {
						smilesSearchPage(dep, des);
						nextIteraction = true;
					} else {
						smilesSearchPageLoop(dep, des);
					}
					extractFlightDetails(dep, des, oneWay);

					while (lastAvailableTravellingDate.after(latestReturnDate)) {
						this.forwardPeriod();
						nextSearchPage();
						extractFlightDetails(dep, des, oneWay);
					}
					Collections.sort(matches);
					writeOutFileResults(matches, dep, des, "GOL");
				}
				reinitializeDepartureDates();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			driver.close();
		}
	}

	private void writeOutFileResults(ArrayList<FlightDetails> matches, NationalAirports from, NationalAirports to, String company) {
		if (matches != null && matches.size() > 0) {
			try {
				FileStream.outputResults(matches, from, to, company);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void extractFlightDetails(NationalAirports dep, NationalAirports des, boolean oneWay) {
		waitPageLoaded();

		FlightMatches searchMatches = initFlightMatches(oneWay);

		// departure flights
		List<WebElement> departures = driver.findElements(By.xpath("id('site')/div[6]/div[3]/div"));
		departures.remove(0);// remove header
		searchMatches.setDepartureFlights(extractListDetails(departures, earliestDepartureHour, dep, des));

		if (!oneWay) {
			// return flights
			List<WebElement> arrives = driver.findElements(By.xpath("id('site')/div[7]/div[3]/div"));
			arrives.remove(0);// remove header

			searchMatches.setReturnFlights(extractListDetails(arrives, latestReturnDate, dep, des));
		}

		matches.addAll(searchMatches.bestMilesFares());

	}

	private FlightMatches initFlightMatches(boolean oneWay) {
		FlightMatches searchMatches = null;

		if (oneWay) {
			return new FlightMatches(maximumAmountLimit, maximumMilesLimit, earliestDepartureHour);
		} else {
			return new FlightMatches(maximumAmountLimit, maximumMilesLimit, earliestDepartureHour, latestReturnDate);
		}
	}

	private ArrayList<FlightDetails> extractListDetails(List<WebElement> details, Calendar flightTime, NationalAirports dep, NationalAirports des) {
		ArrayList<FlightDetails> listaFlights = new ArrayList<FlightDetails>();

		for (WebElement webElement : details) {
			WebElement flightDetails = webElement.findElement(By.cssSelector(".contentFlight"));
			WebElement flightPrice = webElement.findElement(By.cssSelector(".contentTarifas"));

			String code = flightDetails.findElement(By.cssSelector(".voo-titulo")).getText();
			String leave = flightDetails.findElement(By.cssSelector(".saida-voo")).getText();
			String arrive = flightDetails.findElement(By.cssSelector(".chegada-voo")).getText();
			String duration = flightDetails.findElement(By.cssSelector(".duracao-voo")).getText();

			// TODO: verificar o conflitos de datas fixas do voo de partida e
			// chegada (podem ocorrer em dias diferentes e consequentemnte em
			// anos diferentes)
			Calendar departureDate = ParserFlight.returnCalendar(leave, flightTime.get(Calendar.DATE), flightTime.get(Calendar.MONTH), flightTime.get(Calendar.YEAR));
			Calendar returnDate = ParserFlight.returnCalendar(arrive, flightTime.get(Calendar.DATE), flightTime.get(Calendar.MONTH), flightTime.get(Calendar.YEAR));

			FlightDetails flight = ParserFlight.parseTo(code, departureDate, returnDate, duration, flightPrice.getText(), dep, des);
			if (flight != null) {
				listaFlights.add(flight);
			}
		}

		return listaFlights;
	}

	public void SearchTam() {
		try {
			loginPageTam();
			ArrayList<NationalAirports> attendedFrom = Help.attendedByTam(from);
			ArrayList<NationalAirports> attendedTo = Help.attendedByTam(to);

			boolean homepage = true;
			boolean nextIteraction = false;
			for (NationalAirports dep : attendedFrom) {
				for (NationalAirports des : attendedTo) {
					if (homepage) {
						searchMultiplus(dep, des);
					} else {
						if (!nextIteraction) {
							loopSearchMultiPlus();
						} else {
							loopSearchMultiPlus(dep, des);
							nextIteraction = false;
						}
					}

					waitPageLoaded();
					extractFlightDetailsMultiplus();

					homepage = false;

					while (lastAvailableTravellingDate.after(latestReturnDate)) {
						this.forwardPeriod();
						loopSearchMultiPlus();
						extractFlightDetailsMultiplus();
					}

					Collections.sort(matches);
					writeOutFileResults(matches, dep, des, "TAM");

				}
				nextIteraction = true;
				reinitializeDepartureDates();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			driver.close();
		}

	}

	private void reinitializeDepartureDates() {
		earliestDepartureHour = (Calendar) firstEarliestDepartureHour.clone();
		latestReturnDate = (Calendar) firstLatestReturnDate.clone();
	}

	public void searchMultiplus(NationalAirports from, NationalAirports to) {
		waitElementVisible(".//*[@id='passenger_useMyPoints']");
		driver.findElement(By.xpath(".//*[@id='passenger_useMyPoints']")).click();

		inputOutboundAirport(".//*[@id='search_from']", from);
		inputInboundAirport(".//*[@id='search_to']", to);

		chooseDepartureDate(".//*[@id='search_outbound_date']");
		chooseReturnDate(".//*[@id='search_inbound_date']");

		driver.findElement(By.xpath(".//*[@id='onlineSearchSubmitButton']")).click();
	}

	public void loopSearchMultiPlus() {

		chooseDepartureDate(".//*[@id='search_outbound_date']");
		chooseReturnDate(".//*[@id='search_inbound_date']");

		if (Help.exists(driver, "id('ui-datepicker-div')/x:div[4]/x:button[2]")) {
			driver.findElement(By.xpath("id('ui-datepicker-div')/x:div[4]/x:button[2]")).click();
		}

		if (Help.exists(driver, ".//*[@id='searchPanel']/footer/button")) {
			driver.findElement(By.xpath(".//*[@id='searchPanel']/footer/button")).click();
		}
	}

	public void loopSearchMultiPlus(NationalAirports from, NationalAirports to) {
		inputAirportMultiplus(".//*[@id='search_from']", from);
		inputAirportMultiplus(".//*[@id='search_to']", to);

		chooseDepartureDate(".//*[@id='search_outbound_date']");
		chooseReturnDate(".//*[@id='search_inbound_date']");

		if (Help.exists(driver, "id('ui-datepicker-div')/x:div[4]/x:button[2]")) {
			driver.findElement(By.xpath("id('ui-datepicker-div')/x:div[4]/x:button[2]")).click();
		}

		if (Help.exists(driver, ".//*[@id='searchPanel']/footer/button")) {
			driver.findElement(By.xpath(".//*[@id='searchPanel']/footer/button")).click();
		}
	}

	public void extractFlightDetailsMultiplus() {
		WebElement departureResults = driver.findElement(By.xpath(".//*[@id='outbound_list_flight_direct']/tbody"));
		List<WebElement> outboundsTR = departureResults.findElements(By.cssSelector(".flight"));
		ArrayList<FlightDetails> outboundsDeparture = new ArrayList<FlightDetails>();

		for (WebElement webElement : outboundsTR) {
			List<WebElement> outboundsTD = webElement.findElements(By.cssSelector("td"));

			String[] detailsOutbound = extractAirportAndFlightTimeMultiPlus(outboundsTD.get(0).getText());// saida
			String[] detailsInbound = extractAirportAndFlightTimeMultiPlus(outboundsTD.get(1).getText());// chegada
			String code = outboundsTD.get(2).getText();// codigo do voo
			String duration = outboundsTD.get(3).getText();// duração
			String milesPromo = extractFaresMultiPlus(outboundsTD.get(4).getText());// milhas

			String milesClassico = "-----";
			String milesIrrestrito = "-----";// PROMO
			if (outboundsTD.size() > 5) {
				milesClassico = extractFaresMultiPlus(outboundsTD.get(5).getText());// milhas
																					// classico
				if (outboundsTD.size() > 6) {
					milesIrrestrito = extractFaresMultiPlus(outboundsTD.get(6).getText());// milhas
																							// Irrestrito
				}
			}

			String fare = cheapestFare(milesPromo, milesClassico, milesIrrestrito);
			Calendar outbound = convertCalendar(detailsOutbound[0], earliestDepartureHour.get(Calendar.DATE), earliestDepartureHour.get(Calendar.MONTH), earliestDepartureHour.get(Calendar.YEAR));
			Calendar inbound = convertCalendar(detailsInbound[0], earliestDepartureHour.get(Calendar.DATE), earliestDepartureHour.get(Calendar.MONTH), earliestDepartureHour.get(Calendar.YEAR));

			FlightDetails flight = ParserFlight.parseTo(code, outbound, inbound, duration, fare, NationalAirports.valueOf(detailsOutbound[1]), NationalAirports.valueOf(detailsInbound[1]));
			if (flight != null) {
				outboundsDeparture.add(flight);
			}
		}

		WebElement returnResults = driver.findElement(By.xpath(".//*[@id='inbound_list_flight_direct']/tbody"));
		List<WebElement> outboundsReturnTR = returnResults.findElements(By.cssSelector(".flight"));
		ArrayList<FlightDetails> outboundsReturn = new ArrayList<FlightDetails>();
		for (WebElement webElement : outboundsReturnTR) {
			List<WebElement> outboundsTD = webElement.findElements(By.cssSelector("td"));

			String[] detailsOutbound = extractAirportAndFlightTimeMultiPlus(outboundsTD.get(0).getText());// saida
			String[] detailsInbound = extractAirportAndFlightTimeMultiPlus(outboundsTD.get(1).getText());// chegada
			String code = outboundsTD.get(2).getText();// codigo do voo
			String duration = outboundsTD.get(3).getText();// duração
			String milesPromo = extractFaresMultiPlus(outboundsTD.get(4).getText());// milhas
																					// PROMO
			String milesClassico = "-----";
			String milesIrrestrito = "-----";
			if (outboundsTD.size() > 5) {
				milesClassico = extractFaresMultiPlus(outboundsTD.get(5).getText());// milhas
																					// classico
				if (outboundsTD.size() > 6) {
					milesIrrestrito = extractFaresMultiPlus(outboundsTD.get(6).getText());// milhas
																							// Irrestrito
				}
			}
			String fare = cheapestFare(milesPromo, milesClassico, milesIrrestrito);
			Calendar outbound = convertCalendar(detailsOutbound[0], latestReturnDate.get(Calendar.DATE), latestReturnDate.get(Calendar.MONTH), latestReturnDate.get(Calendar.YEAR));
			Calendar inbound = convertCalendar(detailsInbound[0], latestReturnDate.get(Calendar.DATE), latestReturnDate.get(Calendar.MONTH), latestReturnDate.get(Calendar.YEAR));

			FlightDetails flight = ParserFlight.parseTo(code, outbound, inbound, duration, fare, NationalAirports.valueOf(detailsOutbound[1]), NationalAirports.valueOf(detailsInbound[1]));
			if (flight != null) {
				outboundsReturn.add(flight);
			}
		}

		FlightMatches searchMatches = new FlightMatches(maximumAmountLimit, maximumMilesLimit, earliestDepartureHour, latestReturnDate);
		searchMatches.setDepartureFlights(outboundsDeparture);
		searchMatches.setReturnFlights(outboundsReturn);

		matches.addAll(searchMatches.bestMilesFares());
	}

	private String cheapestFare(String milesPromo, String milesClassico, String milesIrrestrito) {
		Integer temp = this.maximumMilesLimit + 1;
		String amount = temp.toString();// set above the limit

		boolean keepSearching = true;

		if (!milesPromo.contains("-")) {
			try {
				amount = milesPromo;
				keepSearching = false;
			} catch (Exception ex) {
				keepSearching = true;
			}
		}
		if (keepSearching && !milesClassico.contains("-")) {
			try {
				amount = milesClassico;
				keepSearching = false;
			} catch (Exception ex) {
				keepSearching = true;
			}
		}
		if (keepSearching && !milesIrrestrito.contains("-")) {
			try {
				amount = milesIrrestrito;
			} catch (Exception ex) {

			}
		}

		return amount;
	}

	private Calendar convertCalendar(String timeMultiplus, int day, int month, int year) {
		Calendar calendar = GregorianCalendar.getInstance(Locale.US);
		String[] time = timeMultiplus.split(":");
		calendar.set(year, month, day, Integer.parseInt(time[0]), Integer.parseInt(time[1]));

		return calendar;
	}

	private String[] extractAirportAndFlightTimeMultiPlus(String value) {
		String[] extraction = new String[3];

		extraction[0] = value.substring(0, 5);// partida
		extraction[1] = value.substring(6, 9);// aeroporto
		if (value.length() > 9) {
			extraction[2] = "+1";// chega no dia seguinte
		}

		return extraction;
	}

	private String extractFaresMultiPlus(String value) {
		String extraction;

		extraction = value.split("(\\r|\\n)")[0];

		return extraction;
	}

	private void inputAirportMultiplus(String xpath, NationalAirports from) {
		WebElement departure = driver.findElement(By.xpath(xpath));
		departure.click();
		departure.sendKeys(from.toString());
	}

	private void inputOutboundAirport(String xpath, NationalAirports from) {
		WebElement departure = driver.findElement(By.xpath(xpath));
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		departure.sendKeys(from.toString());
	}

	private void inputInboundAirport(String xpath, NationalAirports to) {
		WebElement departure = driver.findElement(By.xpath(xpath));
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		departure.sendKeys(to.toString());
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
		waitElementVisible("id('fs_container_origins[0]')/a");
		driver.findElement(By.xpath("id('fs_container_origins[0]')/a")).click();
		List<WebElement> originLi = driver.findElements(By.xpath("id('fs_popUp_origins[0]')/ul/li"));
		chooseFromItemList(originLi, origem);
		/* Destination Airport */
		/* dropdown list has to become active */
		driver.findElement(By.xpath("id('fs_container_destinations[0]')/a")).click();
		List<WebElement> destinationLi = driver.findElements(By.xpath("id('fs_popUp_destinations[0]')/ul/li"));
		chooseFromItemList(destinationLi, destino);

		chooseDepartureDate(DATEPICKER_INPUT_IDA);
		chooseReturnDate(DATEPICKER_INPUT_VOLTA);

		driver.findElement(By.xpath("id('toCategory')/a")).click();
	}

	private void smilesSearchPageLoop(NationalAirports origem, NationalAirports destino) {
		waitPageLoaded();

		/* Departure Airport */
		/* dropdown list has to become active */
		waitElementVisible("id('fs_container_origins[0]')/a");
		driver.findElement(By.xpath("id('fs_container_origins[0]')/a")).click();
		List<WebElement> originLi = driver.findElements(By.xpath("id('fs_popUp_origins[0]')/ul/li"));
		chooseFromItemList(originLi, origem);
		/* Destination Airport */
		/* dropdown list has to become active */
		driver.findElement(By.xpath("id('fs_container_destinations[0]')/a")).click();
		List<WebElement> destinationLi = driver.findElements(By.xpath("id('fs_popUp_destinations[0]')/ul/li"));
		chooseFromItemList(destinationLi, destino);

		chooseDepartureDate(DATEPICKER_INPUT_IDA);
		chooseReturnDate(DATEPICKER_INPUT_VOLTA);

		driver.findElement(By.xpath("id('search')/div[3]/a")).click();
	}

	private void nextSearchPage() {
		waitPageLoaded();

		chooseDepartureDate(DATEPICKER_INPUT_IDA);
		chooseReturnDate(DATEPICKER_INPUT_VOLTA);

		driver.findElement(By.xpath("id('search')/div[3]/a")).click();
	}

	private void chooseDepartureDate(String xpath) {
		WebElement departure = driver.findElement(By.xpath(xpath));
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		departure.sendKeys(del + format.format(earliestDepartureHour.getTime()));
	}

	private void chooseReturnDate(String xpath) {
		WebElement returndt = driver.findElement(By.xpath(xpath));
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		returndt.sendKeys(del + format.format(latestReturnDate.getTime()));
	}

	private void chooseFromItemList(List<WebElement> listItem, NationalAirports destination) {
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(destination.toString()).append(")");
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
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.refreshed(new WaitPageLoad()));
	}

	private void waitElementVisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
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
		Search execute = new Search();
		// execute.SearchTam();
		execute.SearchGol();
	}
}
