package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.ESchedule;
import entity.StaffDNScuhdule;
import serviceimpl.EmployeeSchedule;
import utils.Connector;

public class EmployeeScheduleServiceImpl implements EmployeeSchedule {

	@Override
	public void insert(ESchedule employeeSchedule, String employeeType) {
		String sql = "";
		if (employeeType.equals("Staff")) {
			sql = "INSERT INTO `staff_schedule`(`s_id`, `staff_id`) VALUES (?,?)";
			try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
				stmt.setInt(1, employeeSchedule.getS_id());
				stmt.setInt(2, employeeSchedule.getE_id());
				stmt.executeUpdate();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			sql = "INSERT INTO `dn_schedule`(`s_id`, `dn_id`) VALUES (?,?)";
			try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
				stmt.setInt(1, employeeSchedule.getS_id());
				stmt.setInt(2, employeeSchedule.getE_id());
				stmt.executeUpdate();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Added");
	}

	@Override
	public List<StaffDNScuhdule> getAll(String type) {
		List<StaffDNScuhdule> list = new ArrayList<StaffDNScuhdule>();
		String sql = "";
		if (type.equals("Staff")) {
			sql = "SELECT day_of_week,start_time,end_time,s.name,s.id_code FROM schedule se,staff s,staff_schedule ss WHERE ss.staff_id=s.staff_id AND se.s_id=ss.s_id";

		} else {
			sql = "SELECT day_of_week,start_time,end_time,dn.name,dn.id_code FROM schedule se,doctors_nurses dn,dn_schedule ds WHERE dn.dn_id=ds.dn_id AND se.s_id=ds.s_id AND (dn.position='DOCTOR' OR dn.position='Professor')";
		}
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				String dayofWeek = rs.getString(1);
				String startTime = rs.getString(2);
				String endTime = rs.getString(3);
				String name = rs.getString(4);
				String id_code = rs.getString(5);
				StaffDNScuhdule schedule = new StaffDNScuhdule(id_code, name, dayofWeek, startTime, endTime);
				list.add(schedule);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
//		new EmployeeScheduleServiceImpl().findAll("Staff", sss).forEach(e -> {
//			System.out.println(e);
//		});
	}

	public StaffDNScuhdule getObject(ResultSet rs) throws SQLException {
		String dayofWeek = rs.getString(1);
		String startTime = rs.getString(2);
		String endTime = rs.getString(3);
		String name1 = rs.getString(4);
		String id_code = rs.getString(5);
		StaffDNScuhdule schedule = new StaffDNScuhdule(id_code, name1, dayofWeek, startTime, endTime);
		return schedule;
	}

	@Override
	public List<StaffDNScuhdule> findAll(String type, StaffDNScuhdule staffDNScuhdule) {
		List<StaffDNScuhdule> list = new ArrayList<StaffDNScuhdule>();
		String sql = "";
		if (type.equals("Staff")) {
			sql = "SELECT day_of_week,start_time,end_time,s.name,s.id_code FROM schedule se,staff s,staff_schedule ss WHERE ss.staff_id=s.staff_id AND se.s_id=ss.s_id";
		} else {
			sql = "SELECT day_of_week,start_time,end_time,s.name,s.id_code FROM schedule se,doctors_nurses s,dn_schedule ss WHERE ss.dn_id=s.dn_id AND se.s_id=ss.s_id AND (s.position='DOCTOR' OR s.position='Professor')";
		}
		if (!staffDNScuhdule.getIdcode().isEmpty()) {
			sql += " AND s.id_code LIKE '%" + staffDNScuhdule.getIdcode() + "%'";
		}
		if (!staffDNScuhdule.getName().isEmpty()) {
			sql += " AND s.name LIKE '%" + staffDNScuhdule.getName() + "%'";
		}
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				list.add(getObject(rs));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public int getScheduleID(int sordID, String type) {
		int id = 0;
		String sql = "";
		if (type.equals("Staff")) {
			sql = "SELECT s_id FROM `staff_schedule` WHERE staff_id=?";
		} else {
			sql = "SELECT s_id FROM `dn_schedule` WHERE dn_id=?";
		}
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, sordID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return id;
	}

}
