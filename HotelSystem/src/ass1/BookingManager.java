package ass1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class BookingManager {
	private ArrayList<Booking> bookingList;
	private ArrayList<Room> singleRoomList;
	private ArrayList<Room> doubleRoomList;
	
	public BookingManager() {
		this.bookingList = new ArrayList<Booking>();
		this.singleRoomList = new ArrayList<Room>();
		this.doubleRoomList = new ArrayList<Room>();	
	}
	
	public ArrayList<Room> checkRoomAvailable(Booking newBooking) {
		ArrayList<Room> orderRoomList = new ArrayList<Room>();
		
		for(int i=0;i<newBooking.getOrderList().size();i++) {
			ArrayList<Room> allAvailable = availableBookingNum(newBooking.getOrderList().get(i).getRoomType(),newBooking.getStartDate(),newBooking.getDuration());
			if(allAvailable.size() >= newBooking.getOrderList().get(i).getNor()) {
				for(int j=0;j<newBooking.getOrderList().get(i).getNor();j++) {
					orderRoomList.add(allAvailable.get(j));
				}
			}else {
				return null;
			}
		}
		
		return orderRoomList;	
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
	
	
	//return -1 if the booking is not successful, return 0 if it is success.
	public boolean addNewBooking(String[] bookingInfo) {
		infoProcessor newPro = new infoProcessor();
		Calendar startDate = newPro.convertStartDate(bookingInfo);
		int duration = Integer.parseInt(bookingInfo[4]);
		String[] orders = Arrays.copyOfRange(bookingInfo, 5, bookingInfo.length-1);
		Booking newBooking = new Booking(bookingInfo[0],bookingInfo[1],startDate,duration,orders);
		
		BookingManager bm = new BookingManager();
		
		ArrayList<Room> orderRoomList = new ArrayList<Room>();
		orderRoomList = bm.checkRoomAvailable(newBooking);
		//check if it is a valid booking.
		if(orderRoomList == null) {
			//booking rejected.
			newBooking.setBookingStatus("Booking rejected");
			bookingList.add(newBooking);
			return false;
		}else{
			bookingList.add(newBooking);
			bm.bookSpecRoom(orderRoomList,newBooking);
		}
		
		return true;
	    
	}
	
	public void cancelBooking(String[] cancelInfo) {
		Booking booking= findBookingByBooker(cancelInfo[1]);
		BookingManager bm = new BookingManager();
		bm.cancelSpecRoom(booking);
		booking.setBookingStatus("Cancel");
		
	}
	
	public void changeBooking(String[] changeInfo) {
		BookingManager bm = new BookingManager();
		Booking booking = findBookingByBooker(changeInfo[1]);
		
		bm.cancelSpecRoom(booking);
		booking.setBookingStatus("CancelByChange");
		
		boolean changeSuccess = bm.addNewBooking(changeInfo);
		
		if(changeSuccess == true) {
			
		}else {
			this.bookingList.remove(bm.findBookingByBooker(changeInfo[1]));
			
			bm.reorderSpecRoom(booking);
			booking.setBookingStatus("Booking");
		}

	}

	public ArrayList<Room> availableBookingNum(String type, Calendar specDate,int duration) {
		ArrayList<Room> allAvailable = new ArrayList<Room>();
		if(type.equals("single")) {
			for(Room room:singleRoomList) {
				int isavailable = 1;
				for(int j=1;j<=duration;j++) {
					if(room.getCanBook(specDate.MONTH,specDate.DAY_OF_MONTH) == true) {
					}else {
						isavailable = 0;
					}
					specDate.add(Calendar.DATE,1);
				}
				specDate.add(Calendar.DATE,(-1)*duration);
				if(isavailable == 1) {
					allAvailable.add(room);
				}
			}

		}else{
			for(Room room:doubleRoomList) {
				int isavailable = 1;
				for(int j=1;j<=duration;j++) {
					if(room.getCanBook(specDate.MONTH,specDate.DAY_OF_MONTH) == true){
					}else {
						isavailable = 0;
					}
					specDate.add(Calendar.DATE,1);
				}
				specDate.add(Calendar.DATE,(-1)*duration);
				if(isavailable == 1) {
					allAvailable.add(room);
				}
			}
		}
		return allAvailable;
	}

	
	
	public void bookSpecRoom(ArrayList<Room> roomList,Booking newBooking) {
		Calendar specDate = newBooking.getStartDate();
		int duration = newBooking.getDuration();
		
		int k = 0;
		int m = 0;
		
		for(Room room:roomList) {
			if(k == newBooking.getOrderList().get(m).getNor()) {
				m++;
				k=0;
			}
			
			for(int j=1;j<=duration;j++) {
				room.bookTheRoom(specDate.MONTH,specDate.DAY_OF_MONTH);
				specDate.add(Calendar.DATE,1);
			}
			newBooking.getOrderList().get(m).setHotelName(room.getHotelName(),k);
			newBooking.getOrderList().get(m).setRoomNumber(room.getRoomNumber(),k);
			k++;
			specDate.add(Calendar.DATE,(-1)*duration);
		}	
	}
	
	public Room findRoomByHotelAndNumber(String hotelName,int num,String roomType) {
		if(roomType.equals("single")) {
			for(Room room : this.singleRoomList) {
				if(room.getHotelName().equals(hotelName) && (room.getRoomNumber() == num)) {
					return room;
				}
			}	
		}
		if(roomType.equals("double")) {
			for(Room room : this.doubleRoomList) {
				if(room.getHotelName().equals(hotelName) && (room.getRoomNumber() == num)) {
					return room;
				}
			}	
		}
		
		return null;
		
	}
	
	
	public void cancelSpecRoom(Booking newBooking) {
		Calendar specDate = newBooking.getStartDate();
		int duration = newBooking.getDuration();
		
		for(int i=0;i<newBooking.totalOrderNumber();i++) {
			String[] hotelName = newBooking.getOrderList().get(i).getHotelName();
			int[] roomNumber = newBooking.getOrderList().get(i).getRoomNumber();
			for(int j=0;j<newBooking.getOrderList().get(i).getNor();j++) {
				Room room = findRoomByHotelAndNumber(hotelName[j],roomNumber[j],newBooking.getOrderList().get(i).getRoomType());
				for(int m=1;m<=duration;m++) {
					room.cancelBooking(specDate.MONTH,specDate.DAY_OF_MONTH);
					specDate.add(Calendar.DATE,1);
				}
				specDate.add(Calendar.DATE,(-1)*duration);
			}
		}
	}
	
	public void reorderSpecRoom(Booking newBooking) {
		Calendar specDate = newBooking.getStartDate();
		int duration = newBooking.getDuration();
		
		for(int i=0;i<newBooking.totalOrderNumber();i++) {
			String[] hotelName = newBooking.getOrderList().get(i).getHotelName();
			int[] roomNumber = newBooking.getOrderList().get(i).getRoomNumber();
			for(int j=0;j<newBooking.getOrderList().get(i).getNor();j++) {
				Room room = findRoomByHotelAndNumber(hotelName[j],roomNumber[j],newBooking.getOrderList().get(i).getRoomType());
				for(int m=1;m<=duration;m++) {
					room.bookTheRoom(specDate.MONTH,specDate.DAY_OF_MONTH);
					specDate.add(Calendar.DATE,1);
				}
				specDate.add(Calendar.DATE,(-1)*duration);
			}
		}		
	}
	

	public Booking findBookingByBooker(String bookerName) {
		for(Booking booking:bookingList) {
			if(booking.getName() == bookerName && (booking.getBookingStatus().equals("Booking")||booking.getBookingStatus().equals("Change"))) {
				return booking;
			}
		}
		return null;
	}
}


