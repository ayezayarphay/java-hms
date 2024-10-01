package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Room;
import entity.RoomType;
import serviceimpl.RoomService;
import utils.Connector;

public class RoomServiceImpl implements RoomService {

	@Override
	public void insert(Room room) {
		String sql = "INSERT INTO `room`(`type`, `floor`, `price`, `status`) VALUES (?,?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, room);
			stmt.executeUpdate();
			System.out.println("Add One");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void update(Room room) {
		String sql = "UPDATE `room` SET `type`=?,`floor`=?,`price`=?,`status`=?WHERE r_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, room);
			stmt.setInt(5, room.getId());
			stmt.executeUpdate();
			System.out.println("Update One");
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	@Override
	public List<Room> getAllData() {
		List<Room> list = new ArrayList<Room>();
		String sql = "SELECT * FROM room";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				Room room = new Room(rs.getInt(1), RoomType.valueOf(rs.getString(2)), rs.getString(3), rs.getDouble(4),
						rs.getInt(5));
				list.add(room);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return list;
	}

	public void addObject(PreparedStatement stmt, Room room) throws SQLException {
		stmt.setString(1, room.getRoomtype().toString());
		stmt.setString(2, room.getFloor());
		stmt.setDouble(3, room.getPrice());
		stmt.setInt(4, room.getStatus());

	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM `room` WHERE r_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("Delete one");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
//		Random r = new Random();
		for (int i = 1; i <= 10; i++) {
			// boolean a = r.nextBoolean();
			Room room = new Room(RoomType.Special, "Floor 13", 80000, 0);
			new RoomServiceImpl().insert(room);
		}
	}

	@Override
	public int getStatus(int id) {
		String sql = "SELECT status FROM room WHERE r_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

	@Override
	public Room getRoom(int id) {
		String sql = "SELECT * FROM room where r_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Room room = new Room();
				room.setId(rs.getInt(1));
				room.setRoomtype(RoomType.valueOf(rs.getString(2)));
				room.setFloor(rs.getString(3));
				room.setPrice(rs.getDouble(4));
				room.setStatus(rs.getInt(5));
				return room;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void updateRoomStatus(int id, int status) {
		String sql = "UPDATE `room` SET `status`=? WHERE r_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, status);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			System.out.println("Status Update");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public double getPrice(int id) {
		String sql = "SELECT price FROM room WHERE r_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	@Override
	public List<String> getUnavailableList(int id) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT r.r_id,r.floor,p.p_code,p.name,t.pcase,t.date,t.leave_date FROM room r,patient p,treatment t WHERE t.p_id=p.p_id AND t.r_id=r.r_id AND t.cureType='Hospital_Patient' AND r.r_id=? AND r.status=1";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				list.add(rs.getString(1));
				list.add(rs.getString(2));
				list.add(rs.getString(3));
				list.add(rs.getString(4));
				list.add(rs.getString(5));
				list.add(rs.getString(6));
				list.add(rs.getString(7));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<String> getAppointmentList(int id) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT r.r_id,r.floor,p.p_code,p.name,a.date,a.status FROM room r,patient p,appointment a,appointment_room ar WHERE ar.r_id=r.r_id AND a.app_id=ar.app_id AND a.p_id=p.p_id AND r.r_id=? AND r.status=2";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				list.add(rs.getString(1));
				list.add(rs.getString(2));
				list.add(rs.getString(3));
				list.add(rs.getString(4));
				list.add(rs.getString(5));
				list.add(rs.getString(6));
				list.add(String.valueOf(rs.getBoolean(6)));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public void updatePrice(int from, int to, double price) {
		String sql = "UPDATE room SET price=? WHERE r_id BETWEEN ? AND ?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, from);
			stmt.setInt(2, to);
			stmt.setDouble(3, price);
			stmt.executeUpdate();
			System.out.println("Updated Price");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String getOrigionalPrice(int from, int to) {
		String text = "";
		String sql = "SELECT price FROM room WHERE r_id BETWEEN ? AND ? GROUP BY price";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, from);
			stmt.setInt(2, to);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				text += rs.getString(1) + " ";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return text;
	}

}
