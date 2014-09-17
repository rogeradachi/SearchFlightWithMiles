package navigation;

public enum Xpaths {
	DATEPICKER_INPUT_VOLTA("id('datepickerInputVolta')");
	
	private String xpath;
	
	private Xpaths(String value){
		this.xpath = value;
	}
	
	private String xPath(){
		return xpath;
	}
}
