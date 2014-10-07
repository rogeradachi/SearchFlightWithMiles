package enums;

public enum FileName {
	loginFile("login.txt"), BrazilianAirports("BrazilianAirports.txt"), TamAirports("TamAirports"), GolAirports("GolAirports"), resultFile("result_%s_%s.ods"), datesFile("dates"), searchTypeFile("searchType");
	
	private String value;

	public String getValue() {
		return value;
	}

	private FileName(String value) {
		this.value = value;
	}
}
