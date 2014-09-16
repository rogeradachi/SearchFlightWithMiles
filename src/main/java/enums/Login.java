package enums;

public enum Login {
	loginGol("loginNameGol"), loginTam("loginNameTam"), passwordGol("pswdNameGol"), passwordTam("pswdNameTam");
	
	private String value;

	public String getValue() {
		return value;
	}

	private Login(String value) {
		this.value = value;
	}
}
