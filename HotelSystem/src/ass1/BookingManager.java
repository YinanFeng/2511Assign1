package ass1;

import java.util.ArrayList;

public class BookingManager {
	private ArrayList<Booking> bookingList = new ArrayList<Booking>();
	private ArrayList<Room> singleRoomList = new ArrayList<Room>();
	private ArrayList<Room> doubleRoomList = new ArrayList<Room>();
	
	
	public boolean checkRoomAvailable(String[] bookingInfo) {
		Booking newBooking = new Booking(bookingInfo);
		
		for(int i=0;i<newBooking.getOrderList().size();i++) {
			if(newBooking.getOrderList().get(i).getRoomType().equals("single")) {
				for(int j=0;j<newBooking.getDuration();j++) {
					
				}
				
				
			}
			if(newBooking.getOrderList().get(i).getRoomType().equals("double")) {
				for(int m=0;m<newBooking.getDuration();m++) {
					
				}
				
				
			}
			
			
			return false;
		}
		
		

		
	}
	
	
	
	public void addNewRoom(String[] roomInfo) {
		Room newRoom = new Room(roomInfo);
		if(newRoom.isDouble() == false) {
			this.singleRoomList.add(newRoom);
		}
		
		if(newRoom.isDouble() == true) {
			this.doubleRoomList.add(newRoom);
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
