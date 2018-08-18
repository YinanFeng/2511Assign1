package ass1;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author YinanFeng
 *
 */
public class Booking {
	private String bookingStatus;  //Booking Cancel Change
	private String name;
	private Calendar startDate;
	private int duration;
	private ArrayList<specRoomOrder> orderList = new ArrayList<specRoomOrder>();
	private String hotelName;
	private ArrayList<Integer> roomOrdered;
	
	
	/**
	 * 
	 * @param status
	 * @param bookerName
	 * @param startDate
	 * @param duration
	 * @param orders
	 */
	//set original value for booking
	public Booking(String status,String bookerName,Calendar startDate,int duration,String[] orders) {
		this.bookingStatus = status;
		this.name = bookerName;
		this.startDate = startDate;
	    this.duration = duration;
	    this.hotelName = "";
	    this.roomOrdered = new ArrayList<Integer>();
	    
	    int orderNum = orders.length/2;

	    int index = 0;
	    for(int i=1;i<=orderNum;i++) {
	    	int nor = Integer.parseInt(orders[index+1]);
	    	int type = whichRoomType(orders[index]);
	    	specRoomOrder OneOrder = new specRoomOrder(type,nor);
	    	index = index+2;
	    	this.orderList.add(OneOrder);
	    	
	    	
	    }
	}

	/**
	 * @param type
	 * @return 1/2/3
	 */
	//use the number to instead the string when represent a room type
	private int whichRoomType(String type) {
		if(type.equals("single")) {
			return 1;
		}
		
		if(type.equals("double")) {
			return 2;
		}
	
		return 3;
	}
	

	public String getHotelName() {
		return hotelName;
	}
	

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}


	public ArrayList<Integer> getRoomOrdered() {
		return roomOrdered;
	}


	public String getBookingStatus() {
		return this.bookingStatus;
	}


	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}


	public String getName() {
		return this.name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Calendar getStartDate() {
		return this.startDate;
	}


	public int getDuration() {
		return this.duration;
	}

	public ArrayList<specRoomOrder> getOrderList() {
		return this.orderList;
	}

	public void setRoomOrdered(ArrayList<Integer> roomOrdered) {
		this.roomOrdered = roomOrdered;
	}
	
	public int totalOrderNumber() {
		return this.orderList.size();
	}
	
	/**
	 * @param i
	 * @return order.getNor()
	 */
	//get the specific number of room required in a room
	public int getSpecOrderNor(int i) {
		specRoomOrder order = this.orderList.get(i);
		return order.getNor();
	}
	
	/**
	 * @param i
	 * @return order.getRoomType()
	 */
	//get the type of room required
	public int getSpecOrderType(int i) {
		specRoomOrder order = this.orderList.get(i);
		return order.getRoomType();
	}
	
}