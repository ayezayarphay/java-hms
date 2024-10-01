package entity;

public class Room {
	private int id;
	private RoomType roomtype;
	private String floor;
	private double price;
	private int status;

	public Room(int id, RoomType roomtype, String floor, double price, int status) {
		super();
		this.id = id;
		this.roomtype = roomtype;
		this.floor = floor;
		this.price = price;
		this.status = status;
	}

	public Room(RoomType roomtype, String floor, double price, int status) {
		super();
		this.roomtype = roomtype;
		this.floor = floor;
		this.price = price;
		this.status = status;
	}

	public Room() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public RoomType getRoomtype() {
		return roomtype;
	}

	public String getFloor() {
		return floor;
	}

	public double getPrice() {
		return price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRoomtype(RoomType roomtype) {
		this.roomtype = roomtype;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", roomtype=" + roomtype + ", floor=" + floor + ", price=" + price + ", status="
				+ status + "]";
	}

}
