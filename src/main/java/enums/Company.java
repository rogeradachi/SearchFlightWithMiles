package enums;

public enum Company {
	TAM("Tam"), GOL("Gol"), DECOLAR("Decolar");
	
	private String value;
	
	private Company(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
