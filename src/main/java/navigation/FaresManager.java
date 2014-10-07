package navigation;

import enums.PaymentType;

public class FaresManager {
	private int faresLimit;
	private PaymentType paymentType;
	
	public FaresManager(PaymentType paymentType){
		this.paymentType = paymentType;
	}
	
	/**
	 * Constructor. If the payment is not set, set MILES to default.
	 */
	public FaresManager(){
		this.paymentType = PaymentType.miles;
	}
	
	/**
	 * For return tickets
	 * @param fareOutgoing outgoing fare
	 * @param fareReturn return fare
	 * @return TRUE if the sum is below the limit, FALSE otherwise
	 */
	public boolean belowLimit(int fareOutgoing, int fareReturn){
		if(fareOutgoing + fareReturn <= faresLimit){
			return true;
		}
		return false;
	}
	
	/**
	 * For one way ticket
	 * @param fare Oneway fare
	 * @return TRUE if the fare is below the limit, FALSE otherwise
	 */
	public boolean belowLimit(int fare){
		if(fare <= faresLimit){
			return true;
		}
		return false;
	}

	public int getFaresLimit() {
		return faresLimit;
	}

	public void setFaresLimit(int faresLimit) {
		this.faresLimit = faresLimit;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}	
}
