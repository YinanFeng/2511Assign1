package ass1;



public class specRoomOrder {
	private int roomType;
	private int nor;

	
	public specRoomOrder(int roomType, int nor) {
		this.roomType = roomType;
		this.nor = nor;

	}

	public int getRoomType() {
		return this.roomType;
	}

	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}

	public int getNor() {
		return this.nor;
	}

	public void setNor(int nor) {
		this.nor = nor;
	}
	
}
