package conditional;

import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WaitExistsId implements ExpectedCondition<Boolean> {

	private String elementId;
	
	public WaitExistsId(String id) {
		this.elementId = id;
	}

	@Override
	public Boolean apply(WebDriver arg0) {
		if(arg0.findElement(ById.id(elementId)) != null){
			return true;
		}
		return false;
	}

}
