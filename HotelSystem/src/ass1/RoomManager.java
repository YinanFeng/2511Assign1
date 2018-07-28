package ass1;

import java.util.ArrayList;

public class RoomManager {
	
	private ArrayList<Room> singleRoomList = new ArrayList<Room>();
	private ArrayList<Room> doubleRoomList = new ArrayList<Room>();
	
	
	public void addNewRoom(String[] roomInfo) {
		Room newRoom = new Room(roomInfo);
		if(newRoom.isDouble() == false) {
			this.singleRoomList.add(newRoom);
		}
		
		if(newRoom.isDouble() == true) {
			this.doubleRoomList.add(newRoom);
		}
	
	}
	
//	public boolean bookingRoom( ) {
//		
//	}
	
}
