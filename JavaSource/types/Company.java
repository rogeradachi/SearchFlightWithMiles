package types;

public enum Company {
	TAM("tam"), GOL("gol");
	
	private String value;
	
	private Company(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
