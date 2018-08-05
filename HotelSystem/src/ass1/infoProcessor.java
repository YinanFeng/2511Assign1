package ass1;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class infoProcessor {
	
	public Calendar convertStartDate(String[] bookingInfo) {
		
		int dayN = Integer.parseInt(bookingInfo[3]);
		
		infoProcessor newPro = new infoProcessor();
		int monthN = newPro.switchMonth(bookingInfo[2]);
		
		Calendar startDate = new GregorianCalendar(2018,monthN-1,dayN);
		
		return startDate;
		//Calendar end = (Calendar) g.clone();
		//end.add(GregorianCalendar.DAY_OF_MONTH, 5);
		
	}
	public int switchMonth(String monthS) {
		int monthN = 0;
		switch(monthS) {
			case "Jan":
				monthN = 1;break;
			case "Feb":
				monthN = 2;break;
			case "Mar":
				monthN = 3;break;
			case "Apr":
				monthN = 4;break;
			case "May":
				monthN = 5;break;
			case "Jun":
				monthN = 6;break;
			case "Jul":
				monthN = 7;break;
			case "Aug":
				monthN = 8;break;
			case "Sep":
				monthN = 9;break;
			case "Oct":
				monthN = 10;break;
			case "Nov":
				monthN = 11;break;
			case "Dec":
				monthN = 12;break;
		}
		return monthN;
	}
	
	
}
