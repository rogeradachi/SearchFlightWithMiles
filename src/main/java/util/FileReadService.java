package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import model.FlightDetails;
import model.Login;
import model.SearchFilter;
import navigation.DateManager;
import enums.Company;
import enums.FareType;
import enums.FileName;
import enums.NationalAirports;

public class FileReadService {
	public static void outputResults(ArrayList<FlightDetails> flightList, NationalAirports from, NationalAirports to) throws FileNotFoundException,
			UnsupportedEncodingException {
		PrintWriter writer;
		try {
			String filename = String.format("/src/main/results/" + FileName.resultFile.getValue(), from.code(), to.code());
			writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, false)));

			writeHeader(writer);
			for (FlightDetails flight : flightList) {
				writer.println(from.code() + ";" + to.code() + ";" + flight.toString());
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void writeHeader(PrintWriter writer) {
		StringBuilder header = new StringBuilder();
		header.append("Código IDA/VOLTA").append(";").append("Quanto?").append(";").append("De").append(";").append("PARA").append(";").append("Paradas")
				.append(";").append("Horário IDA").append(";").append("Horário VOLTA").append(";");
		writer.println();
	}

	public static Login readPersonalDetailsFromFile() {
		HashMap<String, String> mapping = new HashMap<String, String>();
		Login userLogin = new Login();
		
		BufferedReader br = null;
		try {
			String sCurrentLine;

			br = new BufferedReader(new FileReader("/src/main/resources/" + FileName.loginFile.getValue()));

			while ((sCurrentLine = br.readLine()) != null) {
				String[] dados = sCurrentLine.split("=");
				mapping.put(dados[0], dados[1]);
			}
			
			userLogin.setLogin(mapping.get("loginNameGol"));
			userLogin.setPassword(mapping.get("pswdNameGol"));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return userLogin;
	}

	public static ArrayList<String> readAirports(Company company) {
		ArrayList<String> airports = new ArrayList<String>();

		BufferedReader br = null;
		try {
			String sCurrentLine;

			br = new BufferedReader(new FileReader("/src/main/resources/" + getFileName(company)));

			while ((sCurrentLine = br.readLine()) != null) {
				airports.add(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return airports;
	}

	public static DateManager readDates() {
		DateManager dt_m = new DateManager();
		HashMap<String, String> mapping = new HashMap<String, String>();
		BufferedReader br = null;
		Calendar departure = Calendar.getInstance();
		Calendar return_ = Calendar.getInstance();
		Calendar startWindow = Calendar.getInstance();
		Calendar closeWindow = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt-BR"));
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt-BR"));

		try {
			String sCurrentLine;

			br = new BufferedReader(new FileReader("/src/main/resources/" + FileName.datesFile));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] dados = sCurrentLine.split("=");
				mapping.put(dados[0], dados[1]);
			}

			departure.setTime(sdf.parse(mapping.get("departure")));
			return_.setTime(sdf.parse(mapping.get("return")));
			startWindow.setTime(dt.parse(mapping.get("startWindow")));
			closeWindow.setTime(dt.parse(mapping.get("endWindow")));
			int jumpDays = Integer.parseInt(mapping.get("jumpDays"));
			
			dt_m.setEarliestDeparture(departure);
			dt_m.setLatestReturn(return_);
			dt_m.setStartWindowDate(startWindow);
			dt_m.setEndWindowDate(closeWindow);
			dt_m.setJumpDays(jumpDays);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return dt_m;
	}
	
	public static SearchFilter readSearchType() {
		HashMap<String, String> mapping = new HashMap<String, String>();
		SearchFilter filter = new SearchFilter();
		BufferedReader br = null;

		try {
			String sCurrentLine;

			br = new BufferedReader(new FileReader("/src/main/resources/" + FileName.searchTypeFile));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] dados = sCurrentLine.split("=");
				mapping.put(dados[0], dados[1]);
			}
			
			int fareType = Integer.parseInt(mapping.get("fareType"));
			filter.setFareType(fareType == 1?FareType.miles :FareType.cash);
			
			int oneWay = Integer.parseInt(mapping.get("oneWay"));
			filter.setOneWay( oneWay == 1? true:false );
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return filter;
	}

	private static String getFileName(Company company) {
		switch (company) {
		case TAM:
			return FileName.TamAirports.getValue();
		case GOL:
			return FileName.GolAirports.getValue();
		default:
			return FileName.BrazilianAirports.getValue();
		}
	}
}
