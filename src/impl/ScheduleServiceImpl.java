package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.StaffDNScuhdule;
import serviceimpl.ScheduleService;
import utils.Connector;

public class ScheduleServiceImpl implements ScheduleService {
	public void addObject(PreparedStatement stmt, StaffDNScuhdule schedule) throws SQLException {
		stmt.setString(1, schedule.getDayOfWeek());
		stmt.setString(2, schedule.getStartTime());
		stmt.setString(3, schedule.getEndTime());

	}

	@Override
	public int insert(StaffDNScuhdule schedule) {
		int id = 0;
		String sql = "INSERT INTO `schedule`( `day_of_week`, `start_time`, `end_time`) VALUES (?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, schedule);
			stmt.executeUpdate();
			System.out.println("Added.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql1 = "SELECT s_id FROM schedule ORDER BY s_id DESC LIMIT 1";
		try (ResultSet rs = Connector.getResultSet(sql1)) {
			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return id;
	}

	@Override
	public void update(StaffDNScuhdule schedule) {
		String sql = "UPDATE `schedule` SET `day_of_week`=?,`start_time`=?,`end_time`=? WHERE `s_id`=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, schedule);
			stmt.setInt(4, schedule.getId());
			stmt.executeUpdate();
			System.out.println("Updated");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public StaffDNScuhdule getStartAndEndTime(String id_code) {
		String sql = "SELECT s.start_time,s.end_time FROM schedule s,dn_schedule ds,doctors_nurses dn WHERE s.s_id=ds.s_id AND ds.dn_id=dn.dn_id AND dn.id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, id_code);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				StaffDNScuhdule dnScuhdule = new StaffDNScuhdule();
				dnScuhdule.setStartTime(rs.getString(1));
				dnScuhdule.setEndTime(rs.getString(2));
				return dnScuhdule;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public String getDayOfWeek(String idcode) {
		String date = "";
		String result = "";
		String sql = "SELECT s.day_of_week FROM schedule s,dn_schedule ds,doctors_nurses dn WHERE ds.s_id=s.s_id AND ds.dn_id=dn.dn_id AND dn.id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, idcode);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				date = rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String array[] = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };
		for (String string : array) {
			if (!date.contains(string)) {
				result += string + " ";
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(new ScheduleServiceImpl().getDayOfWeek("DN-1004"));
	}
}
