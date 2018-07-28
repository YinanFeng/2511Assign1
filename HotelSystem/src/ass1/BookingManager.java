package ass1;

import java.util.ArrayList;

public class BookingManager {
	private ArrayList<Booking> bookingList = new ArrayList<Booking>();
	
	
	public boolean checkRoomAvailable(String[] bookingInfo) {
		Booking newBooking = new Booking(bookingInfo);
		
		for(int i=0;i<newBooking.getOrderList().size();i++) {
			if(newBooking.getOrderList().get(i).getRoomType().equals("single")) {
				
				
				
			}
			if(newBooking.getOrderList().get(i).getRoomType().equals("double")) {
				
				
				
			}
			
			
			
		}
		
		

		
	}
	
	
	
	public void addNewBooking(String[] bookingInfo) {
		Booking newBooking = new Booking(bookingInfo);
		bookingList.add(newBooking);
		
		//change room(room will be occupid after it)
	    
	}
	
	public void cancelBooking(String[] cancelInfo) {
		
	}
	
}
