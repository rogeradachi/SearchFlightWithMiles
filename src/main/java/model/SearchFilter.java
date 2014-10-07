package model;

import enums.FareType;

public class SearchFilter {
	private FareType fareType;
	private Boolean oneWay;
	
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
}
