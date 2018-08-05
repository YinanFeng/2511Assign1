package ass1;

public class Room {
	private String hotelName;
	private int roomNumber;
	private int capacity;
	private String booker;
	private boolean[][] canBook;

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
	}

	public String getHotelName() {
		return this.hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public int getRoomNumber() {
		return this.roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public boolean getCanBook(int Month,int Day) {
		return this.canBook[Month][Day];
	}

	public void cancelBooking(int Month,int Day) {
		this.canBook[Month][Day] = false;
	}
	
	public void bookTheRoom(int Month,int Day) {
		this.canBook[Month][Day] = false;
	}
	
	public boolean isDouble() {
		if(this.capacity == 1) {
			return false;
		}
		return true;
	}
	
	
	
}
