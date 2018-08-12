package ass1;

import java.util.Calendar;

public class bookingInfo {
	private Calendar startdate;
	private int numRoom;

	public bookingInfo(Calendar startDate, int numRoom) {
		this.startdate = startDate;
		this.numRoom = numRoom;
	}

	public Calendar getStartdate() {
		return startdate;
	}

	public int getNumRoom() {
		return numRoom;
	}
	
	
}