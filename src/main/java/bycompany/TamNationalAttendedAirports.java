package bycompany;

import java.util.ArrayList;

import enums.NationalAirports;

public class TamNationalAttendedAirports {
	public static ArrayList<String> airports = new ArrayList<String>() {
		{
			add("AJU");
			add("BEL");
			add("BHZ");
			add("BPS");
			add("BSB");
			add("BVB");
			add("CGB");
			add("CGH");
			add("CGR");
			add("CNF");
			add("CWB");
			add("FLN");
			add("FOR");
			add("GIG");
			add("GRU");
			add("GYN");
			add("IGU");
			add("IMP");
			add("IOS");
			add("JOI");
			add("JPA");
			add("LDB");
			add("MAB");
			add("MAO");
			add("MCP");
			add("MCZ");
			add("NAT");
			add("NVT");
			add("PMW");
			add("POA");
			add("PVH");
			add("RAO");
			add("RBR");
			add("REC");
			add("RIO");
			add("SAO");
			add("SDU");
			add("SJP");
			add("SLZ");
			add("SSA");
			add("STM");
			add("THE");
			add("UDI");
			add("VCP");
			add("VIX");
		}
	};
	
	public static boolean hasTamAirport(NationalAirports ap){
		return airports.contains(ap.toString());
	}
}
