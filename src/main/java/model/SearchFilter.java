package model;

import enums.FareType;

public class SearchFilter {
	private FareType fareType;
	private Boolean oneWay;
	private int cashLimit;
	private int milesLimit;
	
	public Boolean getOneWay() {
		return oneWay;
	}
	public void setOneWay(Boolean oneWay) {
		this.oneWay = oneWay;
	}
	public FareType getFareType() {
		return fareType;
	}
	public void setFareType(FareType fareType) {
		this.fareType = fareType;
	}
	public int getCashLimit() {
		return cashLimit;
	}
	public void setCashLimit(int cashLimit) {
		this.cashLimit = cashLimit;
	}
	public int getMilesLimit() {
		return milesLimit;
	}
	public void setMilesLimit(int milesLimit) {
		this.milesLimit = milesLimit;
	}
}
