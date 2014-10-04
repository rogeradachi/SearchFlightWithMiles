package navigation;

import java.util.Calendar;

import javax.inject.Inject;

public class DateManager {
	private static final int _59 = 59;
	private static final int _23 = 23;
	private static final int _0 = 0;
	@Inject
	private Calendar earliestInbound;
	@Inject
	private Calendar latestOutbound;
	@Inject
	private Calendar startWindowDate;
	@Inject
	private Calendar endWindowDate;
	private Calendar initialReturnDate;
	private String travelDayofWeek;
	private String returnDayofWeek;
	private int jumpDays;
	
	public DateManager(){
		
	}

	public void resetFlightDates() {
		earliestInbound = (Calendar) startWindowDate.clone();
		latestOutbound = (Calendar) initialReturnDate.clone();
	}

	public void setJumpDays(int jumpDaysForward) {
		this.jumpDays = jumpDaysForward;
	}

	/*
	 * Method to jump 'n' days forward to next search iteration
	 */
	public void forwardPeriod() {
		earliestInbound.add(Calendar.DATE, this.jumpDays);
		latestOutbound.add(Calendar.DATE, this.jumpDays);
	}

	public void setEarliestInbound(int day, int month, int year, int hour, int minute) {
		this.earliestInbound.set(year, month, day, hour, minute);
	}

	public void setEarliestInbound(int day, int month, int year) {
		this.setEarliestInbound(day, month, year, _0, _0);
	}

	public void setLatestOutbound(int day, int month, int year, int hour, int minute) {		
		this.latestOutbound.set(year, month, day, hour, minute);
		if (initialReturnDate == null) {
			initialReturnDate = (Calendar) this.latestOutbound.clone();
		}
	}

	public void setLatestOutbound(int day, int month, int year) {
		this.setLatestOutbound(day, month, year, _23, _59);
	}

	public void setStartWindowDate(int day, int month, int year) {
	}

	public void setEndWindowDate(int day, int month, int year) {

	}

	public Calendar getEarliestInbound() {
		return earliestInbound;
	}

	public Calendar getLatestOutbound() {
		return latestOutbound;
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

	public void setEarliestInbound(Calendar earliestInbound) {
		this.earliestInbound = earliestInbound;
	}

	public void setLatestOutbound(Calendar latestOutbound) {
		this.latestOutbound = latestOutbound;
	}

	public void setStartWindowDate(Calendar startWindowDate) {
		this.startWindowDate = startWindowDate;
	}

	public void setEndWindowDate(Calendar endWindowDate) {
		this.endWindowDate = endWindowDate;
	}
}
