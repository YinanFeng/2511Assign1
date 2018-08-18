package ass1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author YinanFeng
 *
 */
public class Room {
	private String hotelName;
	private int roomNumber;
	private int capacity;
	private boolean[][] canBook;
	private ArrayList<bookingInfo> bookingInfoList;
	
	private int theIndex;
	private static int countRoom = 1;
	
	/**
	 * @param roomInfo
	 */
	//set original value of Room
	public Room(String[] roomInfo) {
		this.hotelName = roomInfo[1];
		this.roomNumber = Integer.parseInt(roomInfo[2]);
		this.capacity = Integer.parseInt(roomInfo[3]);
		this.canBook = new boolean[13][32];
		for(int i=1;i<=12;i++) {
			for(int j=1;j<=31;j++) {
				this.canBook[i][j] = true;
			}
		}
		this.bookingInfoList = new ArrayList<bookingInfo>();
		this.theIndex = countRoom;
		countRoom++;
	}
	
	public int getTheIndex() {
		return theIndex;
	}
	
	/**
	 * @param startDate
	 * @param nor
	 */
	//when cancel a order, delete the information stored in the room
	public void deleteBookingInfo(Calendar startDate,int nor) {
		for(bookingInfo bi:this.bookingInfoList) {
			if(bi.getStartdate().equals(startDate) && bi.getNumRoom()==nor) {
				this.bookingInfoList.remove(bi);
				break;
			}
		}
	}
	
	/**
	 * @param startDate
	 * @param duration
	 * 
	 */
	//when have a order, store the information in the room, sort it every time and return that list 
	public ArrayList<bookingInfo> addBookingInfo(Calendar startDate,int duration) {
		bookingInfo bi= new bookingInfo(startDate,duration);
		this.bookingInfoList.add(bi);
		//sort the list by startDate.
		Collections.sort(this.bookingInfoList, new Comparator<bookingInfo>(){
		     public int compare(bookingInfo o1, bookingInfo o2){
		         return o1.getStartdate().compareTo(o2.getStartdate());
		     }
		});
		
		return bookingInfoList;
	}
	
	public String getHotelName() {
		return this.hotelName;
	}

	public int getRoomNumber() {
		return this.roomNumber;
	}

	public int getCapacity() {
		return this.capacity;
	}
	
	public boolean getCanBook(int Month,int Day) {
		return this.canBook[Month][Day];
	}

	public void cancelBooking(int Month,int Day) {
		this.canBook[Month][Day] = true;
	}
	
	public void bookTheRoom(int Month,int Day) {
		this.canBook[Month][Day] = false;
	}
	
	

	/**
	 * @return res
	 */
	// return a string contains all bookinginfo related to the room
	public String allBookingInfo() {
		StringBuilder sb = new StringBuilder();
		for(bookingInfo bi:this.bookingInfoList) {
			Calendar cl = bi.getStartdate();
			int month = cl.get((Calendar.MONTH));
			month = month+1;
			String monthFormat = switchMonth(month);
			int day = cl.get(Calendar.DAY_OF_MONTH);
			int nor = bi.getNumRoom();
			sb.append(" ");
			sb.append(monthFormat).append(" ");
			sb.append(day).append(" ");
			sb.append(nor);
		}
		sb.append("\n");
		String res = new String(sb);
        return res;
	}
	
	private String switchMonth(int monthS) {
		String monthN = "";
		switch(monthS) {
			case 1:
				monthN = "Jan";break;
			case 2:
				monthN = "Feb";break;
			case 3:
				monthN = "Mar";break;
			case 4:
				monthN = "Apr";break;
			case 5:
				monthN = "May";break;
			case 6:
				monthN = "Jun";break;
			case 7:
				monthN = "Jul";break;
			case 8:
				monthN = "Aug";break;
			case 9:
				monthN = "Sep";break;
			case 10:
				monthN = "Oct";break;
			case 11:
				monthN = "Nov";break;
			case 12:
				monthN = "Dec";break;
		}
		return monthN;
	}
	
	
}
