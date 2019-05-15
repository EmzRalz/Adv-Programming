import java.time.LocalDate;
import java.util.ArrayList;

public class HolidayYear implements Observable {
	
	private ArrayList <Observer> observer = new ArrayList<Observer>();
	/**
	*This class will calculate a holiday year based on preset values to make
	*it easier for the user. It has been preset to the 1/04/ year determined by the current
	*month. Max holiday allowance has been set to 28 based on the statutory amount
	**/

	//local date counts months starting from 1
	private final int startMonth = 4;
	private final int startDate = 1;
	private LocalDate startOfYear;
	private final int maxHolidayAllowance = 28;
	private int weeksLeft;
	private double holidaysAccrued;
	private int shifts;
	private String weeks;
	
	public HolidayYear(){
		
		//initialises holiday year to begin on preset figures
		this.startOfYear = LocalDate.of(getStartOfFinancialYear(),startMonth,startDate);
		
		//getStartOfFinancialYear calculates which year we should be looking at based on the current month
		
	}
	
	
	private int getStartOfFinancialYear() {
		//thisYear is the current year
		LocalDate thisYear = LocalDate.now();
		//compare passage of time in current year to determine if the financial
		//year start date is upcoming or passed
		int year = thisYear.getYear();
		LocalDate proposedFinYear = LocalDate.of(year, startMonth, startDate);
		if(thisYear.getDayOfYear() >= proposedFinYear.getDayOfYear()) {
			return year;
			//if more days, or the same days have passed than this years financial year beginning then
			//pass current year
		}
		//if current day is less, return year before
		return year - 1;
		
	}
	//getWeeksPassed calculates average hours over weeks period and returns that data
	public String getWeeksPassed(LocalDate endDate, int hours, int totalHours, int shifts){
		//determine weeks passed in the current financial year as reference point
		//for the user
		this.shifts = shifts;
		int weeksPassed = calculateWeeks(startOfYear, endDate);
		//weeksLeft is used to calculate how many holidays will be accrued on new contract
		weeksLeft = 52 - weeksPassed;
		
		int avgHours = totalHours/weeksPassed;
		//average weekly hours worked over the number of weeks calculated
		int dailyHours = hours/shifts;
		//calculates a daily contracted amount to then calculate what fulltime is
		int fullTime = dailyHours * 5;
		
		double percentageOfFullTime = (double)avgHours/(double)fullTime;
		//use percentage, eg 80% to determine base number of holidays gained at new contract rates
		this.holidaysAccrued =(((double)maxHolidayAllowance/52) * weeksPassed) * percentageOfFullTime;
		String ha = String.format("%.2f", holidaysAccrued);
		
		String weeks = "They have worked over the " + weeksPassed + " weeks, on average: " + avgHours + " hours per week. \nThey have accrued "
				+ ha + " days (at " + dailyHours +" hours a day in holiday pay)";
		return weeks;
		
	}
	
	public String getCurrentWeekofYear() {
		//returns string to display what week period of the financial year we are at
		int weeksPassed = calculateWeeks(startOfYear, LocalDate.now());
		this.weeks = "Current financial week is: " + weeksPassed;
		return weeks;
	}
	
	//a summary of the total entitlement based on getWeeksPassed input
	public String yearTotalAccrual() {
		//calculate projected accrual for rest of holiday year
		double addAC = ((((double)28/52 )* weeksLeft)/5)*shifts;
		//add earned accrual onto this
		double tA = holidaysAccrued + addAC;
		//String format this double value into something smaller
		String total = String.format("%.2f", tA);
		//return sum total as a string
		String totalEntitlement = "For the year their entitlement will be: " + total;
		return totalEntitlement;
	}
 //method to calculate weeks passed between any two dates	
	private int calculateWeeks(LocalDate baseDate, LocalDate laterDate) {

		int y = baseDate.getDayOfYear();
		//calculate weeks by number of days difference, however if the later
		//date is on a different year period then an additional 52 weeks 
		//must be added to reflect the passage of time
		if(laterDate.getYear() > baseDate.getYear()) {
			int x = laterDate.getDayOfYear() + 365 ;
			int weeksPassed = (x - y)/7;
			return weeksPassed;
			//this isn't wholly accurate due to leap years
		}
		int x = laterDate.getDayOfYear(); 
		int weeksPassed = (x - y)/7;
		return weeksPassed;
	}
	
	//option to change financial year if requested by user
	public void setFinancialYear(LocalDate nDate) {
		this.startOfYear = nDate;
		this.getCurrentWeekofYear();
		notifyObserver();
	}
	
	//observer pattern implemented to update display if Financial Year is changed
	public void addObserver(Observer obs) {
		observer.add(obs);
	}
	
	public void notifyObserver() {
		for(Observer x: observer) {
			x.update(weeks);
		}
	}


	
}
