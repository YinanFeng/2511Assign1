package ass1;

import java.util.ArrayList;
import java.util.Calendar;

public class Booking {
	private String bookingStatus;  //Booking Cancel Change
	private String name;
	private Calendar startDate;
	private int duration;
	private ArrayList<specRoomOrder> orderList = new ArrayList<specRoomOrder>();
	
	
	public Booking(String status,String bookerName,Calendar startDate,int duration,String[] orders) {
		this.bookingStatus = status;
		this.name = bookerName;
		this.startDate = startDate;
	    this.duration = duration;
	    
	    int orderNum = orders.length/2;
	    int index = 0;
	    for(int i=1;i<=orderNum;i++) {
	    	int nor = Integer.parseInt(orders[index+1]);
	    	specRoomOrder OneOrder = new specRoomOrder(orders[index],nor);
	    	index = index+2;
	    	this.orderList.add(OneOrder);
	    }
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


	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}


	public int getDuration() {
		return this.duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}


	public ArrayList<specRoomOrder> getOrderList() {
		return this.orderList;
	}


	public void setOrderList(ArrayList<specRoomOrder> orderList) {
		this.orderList = orderList;
	}
	
	public int totalOrderNumber() {
		return this.orderList.size();
	}
	
	
}
