package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import enums.FileName;
import model.FlightDetails;

public class FileStream {
	public static void outputResults(ArrayList<FlightDetails> flightList) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(FileName.resultFile.getValue(), "UTF-8");
		
		for (FlightDetails flight: flightList){
			writer.println(flight.toString());	
		}
		writer.close();
	}
	
	public static HashMap<String, String> readPersonalDetailsFromFile() {
		HashMap<String, String> mapping = new HashMap<String, String>();
		
		BufferedReader br = null;
		try {
			String sCurrentLine;

			br = new BufferedReader(new FileReader("./" + FileName.loginFile.getValue()));

			while ((sCurrentLine = br.readLine()) != null) {
				String[] dados = sCurrentLine.split("=");
				mapping.put(dados[0], dados[1]);
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
		
		return mapping;
	}
}
