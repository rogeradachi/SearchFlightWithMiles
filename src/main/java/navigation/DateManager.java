package navigation;

import java.util.Calendar;

import javax.inject.Inject;

public class DateManager {
	private static final int _59 = 59;
	private static final int _23 = 23;
	private static final int _0 = 0;
	@Inject
	private Calendar earliestDeparture;
	@Inject
	private Calendar latestReturn;
	@Inject
	private Calendar startWindowDate;
	@Inject
	private Calendar endWindowDate;
	private Calendar initialReturnDate;
	private String travelDayofWeek;
	private String returnDayofWeek;
	@Inject
	private int jumpDays;
	
	public DateManager(){
		
	}

	public void resetFlightDates() {
		earliestDeparture = (Calendar) startWindowDate.clone();
		latestReturn = (Calendar) initialReturnDate.clone();
	}

	public void setJumpDays(int jumpDaysForward) {
		this.jumpDays = jumpDaysForward;
	}

	/*
	 * Method to jump 'n' days forward to next search iteration
	 */
	public void forwardPeriod() {
		earliestDeparture.add(Calendar.DATE, this.jumpDays);
		latestReturn.add(Calendar.DATE, this.jumpDays);
	}

	public void setEarliestDeparture(int day, int month, int year, int hour, int minute) {
		this.earliestDeparture.set(year, month, day, hour, minute);
	}

	public void setEarliestDeparture(int day, int month, int year) {
		this.setEarliestDeparture(day, month, year, _0, _0);
	}

	public void setLatestReturn(int day, int month, int year, int hour, int minute) {		
		this.latestReturn.set(year, month, day, hour, minute);
		
		if (initialReturnDate == null) {//set only once, the first return date.
			initialReturnDate = (Calendar) this.latestReturn.clone();
		}
	}

	public void setLatestReturn(int day, int month, int year) {
		this.setLatestReturn(day, month, year, _23, _59);
	}

	public void setStartWindowDate(int day, int month, int year) {
		if(startWindowDate == null)
			startWindowDate = Calendar.getInstance();
		startWindowDate.set(year, month, day, _0, _0);
	}

	public void setEndWindowDate(int day, int month, int year) {
		if(endWindowDate == null)
			endWindowDate = Calendar.getInstance();
		endWindowDate.set(year, month, day, _23, _59);
	}

	public Calendar getEarliestDeparture() {
		return earliestDeparture;
	}

	public Calendar getLatestReturn() {
		return latestReturn;
	}

	public Calendar getStartWindowDate() {
		return startWindowDate;
	}

	public Calendar getEndWindowDate() {
		return endWindowDate;
	}

	public String getTravelDayofWeek() {
		return travelDayofWeek;
	}

	public String getReturnDayofWeek() {
		return returnDayofWeek;
	}

	public void setTravelDayofWeek(String departureDayofWeek) {
		this.travelDayofWeek = departureDayofWeek;
	}

	public void setReturnDayofWeek(String returnDayofWeek) {
		this.returnDayofWeek = returnDayofWeek;
	}

	public void setEarliestDeparture(Calendar earliestDeparture) {
		this.earliestDeparture = earliestDeparture;
	}

	public void setLatestReturn(Calendar latestReturn) {
		this.latestReturn = latestReturn;
		if (initialReturnDate == null) {//set only once, the first return date.
			initialReturnDate = (Calendar) this.latestReturn.clone();
		}
	}

	public void setStartWindowDate(Calendar startWindowDate) {
		this.startWindowDate = startWindowDate;
	}

	public void setEndWindowDate(Calendar endWindowDate) {
		this.endWindowDate = endWindowDate;
	}
}
