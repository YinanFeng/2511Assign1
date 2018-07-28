package ass1;

import java.util.ArrayList;

public class BookingManager {
	private ArrayList<Booking> bookingList = new ArrayList<Booking>();
	
	public void addNewBooking(String[] bookingInfo) {
		Booking newBooking = new Booking(bookingInfo);
		bookingList.add(newBooking);
	    
	}
	
}
