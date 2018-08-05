package ass1;



public class specRoomOrder {
	private String roomType;
	private int nor;
	private String[] hotelName;
	private int[] roomNumber;
	
	public specRoomOrder(String roomType, int nor) {
		this.roomType = roomType;
		this.nor = nor;
		this.hotelName = new String[nor];
		this.roomNumber =  new int[nor];
	}

	public String[] getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName,int index) {
		this.hotelName[index] = hotelName;
	}

	public int[] getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber,int index) {
		this.roomNumber[index] = roomNumber;
	}

	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getNor() {
		return this.nor;
	}

	public void setNor(int nor) {
		this.nor = nor;
	}
	
}
