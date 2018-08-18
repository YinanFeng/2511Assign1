package ass1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author YinanFeng
 *
 */
public class Hotel {
	private String name;
	private int roomNumber;
	private ArrayList<Room> singleRoomList;
	private ArrayList<Room> doubleRoomList;
	private ArrayList<Room> tripleRoomList;
	private ArrayList<Room> allRoomList;
	
	/**
	 * @param name
	 */
	//set the basic value for Hotel
	public Hotel(String name) {
		this.name = name;
		this.roomNumber = 0;
		this.singleRoomList = new ArrayList<Room>();
		this.doubleRoomList = new ArrayList<Room>();
		this.tripleRoomList = new ArrayList<Room>();
		this.allRoomList = new ArrayList<Room>();
	}

	public String getName() {
		return name;
	}

	public int getRoomNumber() {
		return roomNumber;
	}
	
	
	public ArrayList<Room> getAllRoomList() {
		return allRoomList;
	}

	/**
	 * @param roomInfo
	 */
	//add the room to specific type of list
	public void addNewRoom(String[] roomInfo) {
		Room newRoom = new Room(roomInfo);

		if(newRoom.getCapacity() == 1) {
			this.singleRoomList.add(newRoom);
		
		}
		if(newRoom.getCapacity() == 2) {
			this.doubleRoomList.add(newRoom);
		}
		if(newRoom.getCapacity() == 3) {
			this.tripleRoomList.add(newRoom);
		}
		this.allRoomList.add(newRoom);
		
	}
	
	/**
	 * @param specDate
	 * @param duration
	 * @param type
	 * @return num
	 */
	//check how many room available for specific type
	public int availableSpecTypeRoom(Calendar specDate,int duration,int type) {
		int num=0;
		ArrayList<Room> typeRoomList = null;
		//check which type it in
		if(type == 1) {
			typeRoomList = this.singleRoomList;
		}
		if(type == 2) {
			typeRoomList = this.doubleRoomList;
		}
		if(type == 3) {
			typeRoomList = this.tripleRoomList;
		}
		//loop the room to find whether it is available
		for(Room room:typeRoomList) {
			int isavailable = 1;
			for(int j=1;j<=duration;j++) {
				if(room.getCanBook((specDate.get(Calendar.MONTH)+1),specDate.get(Calendar.DAY_OF_MONTH)) == true) {
				}else {
					isavailable = 0;
				}
				specDate.add(Calendar.DATE,1);
			}
			specDate.add(Calendar.DATE,(-1)*duration);
			//add 1 if it is available
			if(isavailable == 1) {
				num++;
			}	
		}

		return num;
	}
	
	
	/**
	 * @param roomList
	 * @param specDate
	 * @param duration
	 * @param type
	 * @param num
	 * @return roomBookedList
	 */
	//book the room in the hotel by getting time
	public ArrayList<Room> bookRoom(ArrayList<Integer> roomList,Calendar specDate,int duration,int type, int num) {
		ArrayList<Room> typeRoomList = null;
		ArrayList<Room> roomBookedList = new ArrayList<Room>();
		//get the right room list by type
		if(type == 1) {
			typeRoomList = this.singleRoomList;
		}
		if(type == 2) {
			typeRoomList = this.doubleRoomList;
		}
		if(type == 3) {
			typeRoomList = this.tripleRoomList;
		}
		int total = 0;
		//loop the room to find
		for(Room room:typeRoomList) {
			int isavailable = 1;
			for(int j=1;j<=duration;j++) {
				if(room.getCanBook((specDate.get(Calendar.MONTH)+1),specDate.get(Calendar.DAY_OF_MONTH)) == true) {
				}else {
					isavailable = 0;
				}
				specDate.add(Calendar.DATE,1);
			}
			specDate.add(Calendar.DATE,(-1)*duration);
			//if the room is available, book it for the duration and store it in list 
			if(isavailable == 1) {
				roomBookedList.add(room);
				for(int j=1;j<=duration;j++) {
					
					room.bookTheRoom((specDate.get(Calendar.MONTH)+1),specDate.get(Calendar.DAY_OF_MONTH));
					specDate.add(Calendar.DATE,1);
				}
				specDate.add(Calendar.DATE,(-1)*duration);
				roomList.add(room.getRoomNumber());
				total++;
				room.addBookingInfo(specDate,duration);
			}
			
			if(total == num) {
				break;
			}
		}
		//return all available room in a list for next step 
		return roomBookedList;
	}
	
	/**
	 * @param roomList
	 * @param specDate
	 * @param duration
	 */
	//cancel the room, enable it be booked by other customer for this time
	public void cancelRoom(ArrayList<Integer> roomList,Calendar specDate,int duration) {
		for(int i=0;i<roomList.size();i++) {
			Room room = findRoomByNumber(roomList.get(i));
			room.deleteBookingInfo(specDate,duration);
			//loop the duration, cancel the booking
			for(int j=1;j<=duration;j++) {
				room.cancelBooking((specDate.get(Calendar.MONTH)+1),specDate.get(Calendar.DAY_OF_MONTH));
				specDate.add(Calendar.DATE,1);
			}
			specDate.add(Calendar.DATE,(-1)*duration);
		}
	}
	
	/**
	 * @param roomList
	 * @param specDate
	 * @param duration
	 */
	//for change part, rebook if the change rejected
	public void rebookRoom(ArrayList<Integer> roomList,Calendar specDate,int duration) {
		for(int i=0;i<roomList.size();i++) {
			Room room = findRoomByNumber(roomList.get(i));
			for(int j=1;j<=duration;j++) {
				room.bookTheRoom((specDate.get(Calendar.MONTH)+1),specDate.get(Calendar.DAY_OF_MONTH));
				specDate.add(Calendar.DATE,1);
			}
			specDate.add(Calendar.DATE,(-1)*duration);
			room.addBookingInfo(specDate,duration);
		}	
	}
	
	
	public Room findRoomByNumber(int roomNumber) {
		for(Room room:singleRoomList) {
			if(room.getRoomNumber()==roomNumber) {
				return room;
			}
		}
		for(Room room:doubleRoomList) {
			if(room.getRoomNumber()==roomNumber) {
				return room;
			}
		}
		for(Room room:tripleRoomList) {
			if(room.getRoomNumber()==roomNumber) {
				return room;
			}
		}
		return null;
	}
	
	public String allRoomInfoWithBooking(){
		StringBuilder sb = new StringBuilder();
		
		for (Room room:this.allRoomList) {
			sb.append(this.name).append(" ");
        	sb.append(room.getRoomNumber());
        	sb.append(room.allBookingInfo());
        }
		String res = new String(sb);
        return res;
	}
	
	/**
	 * @param booking
	 * @return roomNumList
	 */
	//book all room and sort the list
	public ArrayList<Integer> bookAllRoom(Booking booking) {
		ArrayList<Room> roomList = new ArrayList<Room>();
		//loop and add the room instance to the list
		for(int i=0;i<booking.totalOrderNumber();i++) {	
			int roomType = booking.getSpecOrderType(i);
			int numRequired = booking.getSpecOrderNor(i);
			roomList.addAll(this.bookRoom(booking.getRoomOrdered(),booking.getStartDate(),booking.getDuration(),roomType,numRequired));
		}
		Collections.sort(roomList, new Comparator<Room>(){
		     public int compare(Room o1, Room o2){
		    	 if(o1.getTheIndex() == o2.getTheIndex())
		             return 0;
		         return o1.getTheIndex() < o2.getTheIndex() ? -1 : 1;
		     }
		});	
		ArrayList<Integer> roomNumList = new ArrayList<Integer>();
		//get the number of the room and add it to roomList
		for(Room rm:roomList) {
			roomNumList.add(rm.getRoomNumber());		
		}
		return roomNumList;
	}
	

}
