package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import model.FlightDetails;
import enums.FileName;
import enums.NationalAirports;

public class FileStream {
	public static void outputResults(ArrayList<FlightDetails> flightList, NationalAirports from, NationalAirports to, String company) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer;
		try {
			String filename = String.format(FileName.resultFile.getValue(), from.code(), to.code(), company);
			writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, false)));
			
			writeHeader(writer);
			for (FlightDetails flight: flightList){
				writer.println(flight.toString());	
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeHeader(PrintWriter writer){
		StringBuilder header = new StringBuilder();
		header.append("Código IDA/VOLTA").append(";")
		.append("Quanto?").append(";")
		.append("De").append(";")
		.append("PARA").append(";")
		.append("Paradas").append(";")
		.append("Horário IDA").append(";")
		.append("Horário VOLTA").append(";");
		writer.println();
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
