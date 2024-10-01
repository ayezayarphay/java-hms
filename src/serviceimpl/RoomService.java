package serviceimpl;

import java.util.List;

import entity.Room;

public interface RoomService {
	public void insert(Room room);

	public void update(Room room);

	public List<Room> getAllData();

	public void delete(int id);

	public int getStatus(int id);

	public Room getRoom(int id);

	public void updateRoomStatus(int id, int status);

	public double getPrice(int id);

	public List<String> getUnavailableList(int id);

	public List<String> getAppointmentList(int id);

	public void updatePrice(int from, int to, double price);

	public String getOrigionalPrice(int from, int to);

}
