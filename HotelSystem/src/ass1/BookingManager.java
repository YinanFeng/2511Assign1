package ass1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class BookingManager {
	private ArrayList<Hotel> hotelList;
	private ArrayList<Booking> bookingList;

	
	public BookingManager() {
		this.hotelList = new ArrayList<Hotel>();
		this.bookingList = new ArrayList<Booking>();
	}
	
	public void addNewRoom(String[] roomInfo) {
		Hotel findHotel = null;
		if(hotelList.size() == 0) {
			findHotel = new Hotel(roomInfo[1]);
			this.hotelList.add(findHotel);
		}else{
			boolean same=false;
			for(Hotel hotel:this.hotelList) {
				if(hotel.getName().equals(roomInfo[1])){
					same = true;
					findHotel = hotel;
					break;
				}
			}
			//it is a new hotel
			if(same == false) {
				findHotel = new Hotel(roomInfo[1]);
				this.hotelList.add(findHotel);
			}
		}
		
		findHotel.addNewRoom(roomInfo);
	}

	//return -1 if the booking is not successful, return 0 if it is success.
	public boolean addNewBooking(String[] bookingInfo) {
		infoProcessor newPro = new infoProcessor();
		Calendar startDate = newPro.convertStartDate(bookingInfo);
		int duration = Integer.parseInt(bookingInfo[4]);
		String[] orders = Arrays.copyOfRange(bookingInfo, 5, bookingInfo.length-1);
		Booking newBooking = new Booking(bookingInfo[0],bookingInfo[1],startDate,duration,orders);
		BookingManager bm = new BookingManager();
		Hotel availableHotel = bm.checkRoomAvailable(newBooking);
		//reject the booking if there is no available room.
 		if(availableHotel == null) {
 			newBooking.setBookingStatus("Booking rejected");
			bookingList.add(newBooking);
 			return false;
		}
 		//set hotel name for order.
 		newBooking.setHotelName(availableHotel.getName());
 		//booking is success, them booking the room and record the room number booked.
 		bm.bookAllRoom(availableHotel,newBooking);
		bookingList.add(newBooking);
		return true;  
	}
	
	public Hotel checkRoomAvailable(Booking newBooking) {
		Hotel availableHotel = null;
		for(Hotel hotel:this.hotelList) {
			int isAvailableHotel = 1;
			for(int i=0;i<newBooking.totalOrderNumber();i++) {
				int availableNum =0;
				availableNum = hotel.availableSpecTypeRoom(newBooking.getStartDate(),newBooking.getDuration(),newBooking.getOrderList().get(i).getRoomType());
				if(availableNum < newBooking.getOrderList().get(i).getNor()) {
					isAvailableHotel =0;
				}
			}
			if(isAvailableHotel == 1) {
				availableHotel = hotel;
				break;
			}
		}
		return availableHotel;	
	}
	
	public void bookAllRoom(Hotel hotel,Booking booking) {
		for(int i=0;i<booking.totalOrderNumber();i++) {
			int roomType = booking.getOrderList().get(i).getRoomType();
			int numRequired = booking.getOrderList().get(i).getNor();
			hotel.bookRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration(),roomType,numRequired);
		}
	}
	
	public boolean cancelBooking(String[] cancelInfo) {
		Booking booking= findBookingByBooker(cancelInfo[1]);
		if(booking == null) {
			return false;
		}
		Hotel hotel = findHotelByName(booking.getHotelName());
		//set the status of room, change it to available.
		hotel.cancelRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration());
		booking.setBookingStatus("Cancel");
		return true;
	}
	
	public Booking findBookingByBooker(String bookerName) {
		for(Booking booking:bookingList) {
			if(booking.getName() == bookerName && (booking.getBookingStatus().equals("Booking")||booking.getBookingStatus().equals("Change"))) {
				return booking;
			}
		}
		return null;
	}
	
	public Hotel findHotelByName(String hotelName) {
		for(Hotel hotel:this.hotelList) {
			if(hotel.getName() == hotelName) {
				return hotel;
			}
		}
		return null;
	}

	public boolean changeBooking(String[] changeInfo) {
		BookingManager bm = new BookingManager();
		Booking booking = findBookingByBooker(changeInfo[1]);
		Hotel hotel = findHotelByName(booking.getHotelName());
		hotel.cancelRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration());
		booking.setBookingStatus("CancelByChange");
		//try to reorder to see whether success
		boolean changeSuccess = bm.addNewBooking(changeInfo);
		if(changeSuccess == true) {
			return true;
		}else {
			//remain the original booking if change rejected.
			booking.setBookingStatus("Booking");
			hotel.rebookRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration());
			return false;
		}
	}
}


