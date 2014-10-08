package enums;

public enum PaymentType {
	miles(1), cash(2);
	
	private int value;

	public int getValue() {
		return value;
	}

	private PaymentType(int value) {
		this.value = value;
	}
}
