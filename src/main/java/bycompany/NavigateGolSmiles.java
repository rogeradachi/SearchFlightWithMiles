package bycompany;

import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.WebElement;

import enums.NationalAirports;

public class NavigateGolSmiles extends SearchToolInstance {
	private final String DATEPICKER_INPUT_VOLTA = "id('datepickerInputVolta')";
	private final String DATEPICKER_INPUT_IDA = "id('datepickerInputIda')";
	private final String golInputLoginxPath = "id('s_1_1_9_0')";
	private final String golInputPswdxPath = "id('s_1_1_10_0')";
	private final String golSubmitLoginxPath = "id('s_1_1_12_0')";
	private final String golGoToTicketsId = "s_4_1_4_0";
	private final String gol = "https://clientes.smiles.com.br/eloyalty_ptb/start.swe?SWECmd=GotoView&SWEView=Login%20View";
	@Override
	public void includeSearchFilters() {
		
	}
	@Override
	public void loginUserSpace() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void searchFlights() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void loopSearchFlights() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void extractFlightDetails(List<WebElement> details, Calendar flightTime, NationalAirports to) {
		// TODO Auto-generated method stub
		
	}
}
