package ass1;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		GregorianCalendar b = new GregorianCalendar(2018,12,1);
		b.add(Calendar.DATE,-2);
		SimpleDateFormat sdf = new SimpleDateFormat("MM dd");
	//	int day = b.get(Calendar.MONTH + 1);;
	//	int month = sdf.format(b.getTime());
		//b.get(Calendar.MONTH)
	//	System.out.println();
	*/
		
		Calendar startDate = new GregorianCalendar(2018,11,1);
		System.out.println(startDate.get(Calendar.MONTH));
	
	}

}
