package enums;

public enum FileName {
	loginFile("login.txt"), resultFile("result_%s_%s_%s.ods");
	
	private String value;

	public String getValue() {
		return value;
	}

	private FileName(String value) {
		this.value = value;
	}
}
