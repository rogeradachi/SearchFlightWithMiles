package enums;

public enum FareType {
	miles(1), cash(2);
	
	private int value;

	public int getValue() {
		return value;
	}

	private FareType(int value) {
		this.value = value;
	}
}
