package types;

public enum Company {
	TAM("Tam"), GOL("Gol");
	
	private String value;
	
	private Company(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
