package enums;

public enum FareType {
	MILES(1), CASH(2);
	
	private int value;

	public int getValue() {
		return value;
	}

	private FareType(int value) {
		this.value = value;
	}
}
