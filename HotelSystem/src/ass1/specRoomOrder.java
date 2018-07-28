package ass1;

public class specRoomOrder {
	private String roomType;
	private int nor;
	
	public specRoomOrder(String roomType, int nor) {
		this.roomType = roomType;
		this.nor = nor;
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
