package try111;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class try11111 {

	public static void main(String[] args) {
		Calendar g = new GregorianCalendar(2018,0,27);
		Calendar end = (Calendar) g.clone();
		end.add(GregorianCalendar.DAY_OF_MONTH, 5);
		System.out.println(end.getTime());
	}

}
