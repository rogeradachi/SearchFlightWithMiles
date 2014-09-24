package bycompany;

import java.util.ArrayList;

import enums.NationalAirports;

public class GolNationalAttendedAirports {
	public static ArrayList<String> airports = new ArrayList<String>() {
		{
			add("ATM");
			add("AJU");
			add("JTC");
			add("BEL");
			add("CNF");
			add("BHZ");
			add("PLU");
			add("BVB");
			add("BSB");
			add("CLV");
			add("CPV");
			add("CGR");
			add("CKS");
			add("CXJ");
			add("XAP");
			add("CZS");
			add("CGB");
			add("CWB");
			add("FEN");
			add("FLN");
			add("FOR");
			add("IGU");
			add("GYN");
			add("IOS");
			add("IMP");
			add("JPA");
			add("JOI");
			add("JDO");
			add("LDB");
			add("MCP");
			add("MCZ");
			add("MAO");
			add("MAB");
			add("MGF");
			add("MOC");
			add("NAT");
			add("NVT");
			add("PMW");
			add("PNZ");
			add("POA");
			add("BPS");
			add("PVH");
			add("PPB");
			add("REC");
			add("RBR");
			add("GIG");
			add("RIO");
			add("SDU");
			add("SSA");
			add("STM");
			add("SLZ");
			add("VCP");
			add("CGH");
			add("GRU");
			add("SAO");
			add("THE");
			add("UDI");
			add("VIX");
		}
	};

	public static boolean attendsAirport(NationalAirports ap) {
		return airports.contains(ap.toString());
	}
}
