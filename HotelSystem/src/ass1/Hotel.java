package ass1;

import java.util.ArrayList;
import java.util.Calendar;

public class Hotel {
	private String name;
	private int roomNumber;
	private ArrayList<Room> singleRoomList;
	private ArrayList<Room> doubleRoomList;
	private ArrayList<Room> tripleRoomList;
	
	public Hotel(String name) {
		this.name = name;
		this.roomNumber = 0;
		this.singleRoomList = new ArrayList<Room>();
		this.doubleRoomList = new ArrayList<Room>();
		this.tripleRoomList = new ArrayList<Room>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
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
	}
	
	public int availableSpecTypeRoom(Calendar specDate,int duration,int type) {
		int num=0;
		ArrayList<Room> typeRoomList = null;
		if(type == 1) {
			typeRoomList = this.singleRoomList;
		}
		if(type == 2) {
			typeRoomList = this.doubleRoomList;
		}
		if(type == 3) {
			typeRoomList = this.tripleRoomList;
		}
		
		for(Room room:typeRoomList) {
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
				num++;
			}	
		}

		return num;
	}
	
	public void bookRoom(ArrayList<Integer> roomList,Calendar specDate,int duration,int type, int num) {
		ArrayList<Room> typeRoomList = null;
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
		for(Room room:typeRoomList) {
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
				for(int j=1;j<=duration;j++) {
					room.bookTheRoom(specDate.MONTH,specDate.DAY_OF_MONTH);
					specDate.add(Calendar.DATE,1);
				}
				specDate.add(Calendar.DATE,(-1)*duration);
				roomList.add(room.getRoomNumber());
				total++;
			}
			if(total == num) {
				break;
			}
		}
	}
	
	public void cancelRoom(ArrayList<Integer> roomList,Calendar specDate,int duration) {
		for(int i=0;i<roomList.size();i++) {
			Room room = findRoomByNumber(roomList.get(i));
			for(int j=1;j<=duration;j++) {
				room.cancelBooking(specDate.MONTH,specDate.DAY_OF_MONTH);
				specDate.add(Calendar.DATE,1);
			}
			specDate.add(Calendar.DATE,(-1)*duration);
		}
	}
	
	public void rebookRoom(ArrayList<Integer> roomList,Calendar specDate,int duration) {
		for(int i=0;i<roomList.size();i++) {
			Room room = findRoomByNumber(roomList.get(i));
			for(int j=1;j<=duration;j++) {
				room.bookTheRoom(specDate.MONTH,specDate.DAY_OF_MONTH);
				specDate.add(Calendar.DATE,1);
			}
			specDate.add(Calendar.DATE,(-1)*duration);
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
	

}
