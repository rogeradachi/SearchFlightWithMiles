package enums;

public enum FileName {
	loginFile("login.txt"), resultFile("result.txt");
	
	private String value;

	public String getValue() {
		return value;
	}

	private FileName(String value) {
		this.value = value;
	}
}
