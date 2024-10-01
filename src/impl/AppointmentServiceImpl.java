package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXCheckBox;

import entity.Appointment;
import entity.PatientAppointment;
import entity.RoomAppointment;
import javafx.scene.paint.Color;
import serviceimpl.AppointmentService;
import utils.Connector;

public class AppointmentServiceImpl implements AppointmentService {

	@Override
	public int getToken(LocalDate date, String idcode) {
		int token = 0;
		String sql = "SELECT ap.token FROM doctors_nurses dn,appointment a,appointment_patient ap WHERE a.dn_id=dn.dn_id AND a.app_id=ap.app_id AND date=? AND dn.id_code=? ORDER by ap.app_id DESC LIMIT 1";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, date.toString());
			stmt.setString(2, idcode);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				token = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return token;
	}

	public RoomAppointment getObject2(ResultSet rs) throws Exception {
		RoomAppointment roomAppointment = new RoomAppointment();
		roomAppointment.setP_id(rs.getString(1));
		roomAppointment.setP_name(rs.getString(2));
		roomAppointment.setDn_id(rs.getString(3));
		roomAppointment.setDn_name(rs.getString(4));
		roomAppointment.setDate(LocalDate.parse(rs.getString(5)));
		roomAppointment.setRoomNum(rs.getInt(6));
		roomAppointment.setStatus(rs.getInt(7));
		return roomAppointment;
	}

	public PatientAppointment getObject(ResultSet rs) throws SQLException {
		PatientAppointment patientAppointment = new PatientAppointment();
		patientAppointment.setP_id(rs.getString(1));
		patientAppointment.setPatientName(rs.getString(2));
		patientAppointment.setDn_id(rs.getString(3));
		patientAppointment.setDoctorName(rs.getString(4));
		patientAppointment.setToken(rs.getInt(5));
		patientAppointment.setStatus(rs.getInt(6));
		patientAppointment.setDate(LocalDate.parse(rs.getString(7)));
		return patientAppointment;
	}

	@Override
	public List<PatientAppointment> getPatient(String cond) {
		List<PatientAppointment> list = new ArrayList<PatientAppointment>();
		String sql = "SELECT p.p_code,p.name,dn.id_code,dn.name,ap.token,a.status,a.date FROM patient p,doctors_nurses dn, appointment a,appointment_patient ap WHERE ap.app_id=a.app_id AND a.p_id=p.p_id AND a.dn_id=dn.dn_id AND a.status=0 ORDER BY a.date";

		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				JFXCheckBox box = new JFXCheckBox("Available");
				box.setSelected(rs.getBoolean(6));
				box.setCheckedColor(Color.rgb(64, 89, 169));
				if (cond.equals("JFX")) {
					PatientAppointment appointment = new PatientAppointment(rs.getInt(5),
							LocalDate.parse(rs.getString(7)), rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getString(4), box);
					list.add(appointment);
				} else {
					PatientAppointment appointment = new PatientAppointment(rs.getInt(5),
							LocalDate.parse(rs.getString(7)), rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getInt(6));
					list.add(appointment);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}

	public void addObjectForAppointment(PreparedStatement stmt, Appointment appointment) throws SQLException {
		stmt.setInt(1, appointment.getStatus());
		stmt.setInt(2, appointment.getCash());
		stmt.setString(3, appointment.getDate().toString());
		stmt.setInt(4, appointment.getP_id());
		stmt.setInt(5, appointment.getStaff_id());
		stmt.setInt(6, appointment.getDn_id());
	}

	@Override
	public void insert(Appointment appointment, RoomAppointment roomAppointment, PatientAppointment patientAppointment,
			String type) {
		String sql1 = "INSERT INTO `appointment`( `status`, `cancel`, `date`, `p_id`, `staff_id`, `dn_id`) VALUES (?,?,?,?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql1)) {
			addObjectForAppointment(stmt, appointment);
			stmt.executeUpdate();
			System.out.println("Added");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		int id = 0;
		String sql2 = "SELECT app_id FROM appointment ORDER BY app_id DESC LIMIT 1";
		try (ResultSet rs = Connector.getResultSet(sql2)) {
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (type.equals("Patient")) {

			insertPatient(patientAppointment, id);
		} else {
			insertRoom(roomAppointment, id);
		}

	}

	public void insertRoom(RoomAppointment roomAppointment, int id) {
		String sql = "INSERT INTO `appointment_room`(`app_id`, `r_id`) VALUES (?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.setInt(2, roomAppointment.getRoomNum());
			stmt.executeUpdate();
			System.out.println("Added");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void insertPatient(PatientAppointment patientAppointment, int id) {
		String sql = "INSERT INTO `appointment_patient`(`app_id`, `token`) VALUES (?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.setInt(2, patientAppointment.getToken());
			stmt.executeUpdate();
			System.out.println("Added");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void update(Appointment appointment, RoomAppointment roomAppointment, PatientAppointment patientAppointment,
			String type) {
		int app_id = 0;
		String sql1 = "SELECT ap.app_id FROM appointment a,appointment_patient ap WHERE ap.app_id=a.app_id AND ap.token=? AND a.date=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql1)) {
			stmt.setInt(1, patientAppointment.getToken());
			stmt.setString(2, appointment.getDate().toString());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				app_id = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (type.equals("Patient")) {
			updatePatiendAppointment(patientAppointment);
		} else {

		}
		String sql2 = "UPDATE `appointment` SET `status`=?,`date`=?,`p_id`=?,`dn_id`=? WHERE app_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql2)) {
			stmt.setInt(1, appointment.getStatus());
			stmt.setString(2, appointment.getDate().toString());
			stmt.setInt(3, appointment.getP_id());
			stmt.setInt(4, appointment.getDn_id());
			stmt.setInt(5, app_id);
			stmt.executeUpdate();
			System.out.println("Updated");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void updatePatiendAppointment(PatientAppointment appointment) {
		String sql = "UPDATE `appointment_patient` SET `token`=? WHERE app_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, appointment.getToken());
			stmt.setInt(2, appointment.getApp_id());
			stmt.executeUpdate();
			System.out.println("Update");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void main(String[] args) {

	}

	@Override
	public boolean isBoken(String id_code, LocalDate date, String dn_id) {
		String sql = "SELECT a.app_id FROM appointment a,patient p ,doctors_nurses dn WHERE a.p_id=p.p_id AND dn.dn_id=a.dn_id AND p.p_code=? AND a.date=? AND dn.id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, id_code);
			stmt.setString(2, date.toString());
			stmt.setString(3, dn_id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public void updateStatus(String p_id, LocalDate date, int select, String d_id) {
		String sql = "UPDATE appointment a,patient p,doctors_nurses dn SET status=? WHERE a.p_id=p.p_id AND date=? AND p.p_code=? AND a.dn_id=dn.dn_id AND dn.id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, select);
			stmt.setString(2, date.toString());
			stmt.setString(3, p_id);
			stmt.setString(4, d_id);
			stmt.executeUpdate();
			System.out.println("Update status");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public List<RoomAppointment> searchAppointmentRoomList(RoomAppointment roomAppointment, boolean select) {
		List<RoomAppointment> list = new ArrayList<RoomAppointment>();
		String sql = "SELECT p.p_code,p.name,dn.id_code,dn.name,a.date,ar.r_id,a.status,a.cancel FROM patient p,doctors_nurses dn,appointment a, appointment_room ar WHERE p.p_id=a.p_id AND dn.dn_id=a.dn_id AND a.app_id=ar.app_id";
		if (!roomAppointment.getP_name().isEmpty()) {
			sql += " AND p.name LIKE '%" + roomAppointment.getP_name() + "%'";
		}
		if (!roomAppointment.getDn_name().isEmpty()) {
			sql += " AND dn.name LIKE '%" + roomAppointment.getDn_name() + "%'";
		}
		if (roomAppointment.getDate() != null) {
			sql += " AND a.date='" + roomAppointment.getDate() + "'";
		}
		sql += " ORDER BY a.app_id";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				String pCode = rs.getString(1);
				String pName = rs.getString(2);
				String dnCode = rs.getString(3);
				String dnName = rs.getString(4);
				LocalDate date = LocalDate.parse(rs.getString(5));
				int rID = rs.getInt(6);
				int status = rs.getInt(7);
				JFXCheckBox check = new JFXCheckBox("available");
				check.setCheckedColor(Color.rgb(64, 89, 169));
				check.setSelected(status == 1 ? true : false);
				RoomAppointment room = new RoomAppointment(pCode, pName, dnCode, dnName, date, rID, status, check);
				list.add(room);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public boolean isAppointment(String id_code) {
		int id = 0;
		String sql1 = "SELECT ar.app_id FROM appointment a,appointment_room ar,patient p WHERE a.app_id=ar.app_id AND a.p_id=p.p_id AND  p.p_code=? AND a.cancel=0 ORDER BY ar.app_id DESC LIMIT 1";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql1)) {
			stmt.setString(1, id_code);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql2 = "SELECT date FROM appointment WHERE app_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql2)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				LocalDate date = LocalDate.parse(rs.getString(1));
				if (date.isAfter(LocalDate.now())) {
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public List<Integer> cancelAutoAppointment() {
		List<Integer> list = new ArrayList<Integer>();
		String sql = "SELECT r.r_id FROM room r,appointment a,appointment_room ar WHERE ar.app_id=a.app_id AND ar.r_id=r.r_id AND a.date=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, LocalDate.now().toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				updateCancel(LocalDate.now());
				list.add(rs.getInt(1));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return list;
	}

	public void updateCancel(LocalDate date) {
		String sql = "UPDATE appointment a,appointment_room ar,room r SET a.cancel=1  WHERE a.app_id=ar.app_id AND r.r_id=ar.r_id AND r.status=2 AND a.date=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, date.toString());
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void cancelAppointment(String id_code, LocalDate date) {
		String sql = "SELECT a.date FROM appointment a,patient p WHERE date=? AND p.p_id=a.p_id AND p.p_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, date.toString());
			stmt.setString(2, id_code);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String sql2 = "UPDATE appointment a,patient p SET cancel=? WHERE a.p_id=p.p_id AND a.date=? AND p.p_code='"
						+ id_code + "'";

				try (PreparedStatement stmt2 = Connector.getPrepareStatement(sql2)) {
					stmt2.setInt(1, 1);
					stmt2.setString(2, rs.getString(1));
					stmt2.executeUpdate();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public List<RoomAppointment> getRoomAppointment() {
		List<RoomAppointment> list = new ArrayList<RoomAppointment>();
		String sql = "SELECT p.p_code,p.name,dn.id_code,dn.name,a.date,ar.r_id,a.status FROM patient p,doctors_nurses dn,appointment a, appointment_room ar WHERE p.p_id=a.p_id AND dn.dn_id=a.dn_id AND a.app_id=ar.app_id AND a.cancel=0 AND a.status=0";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				String pCode = rs.getString(1);
				String pName = rs.getString(2);
				String dnCode = rs.getString(3);
				String dnName = rs.getString(4);
				LocalDate date = LocalDate.parse(rs.getString(5));
				int rID = rs.getInt(6);
				int status = rs.getInt(7);
				JFXCheckBox check = new JFXCheckBox("available");
				check.setCheckedColor(Color.rgb(64, 89, 169));
				check.setSelected(status == 1 ? true : false);

				RoomAppointment roomAppointment = new RoomAppointment(pCode, pName, dnCode, dnName, date, rID, status,
						check);
				list.add(roomAppointment);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public List<PatientAppointment> findgetPatient(PatientAppointment patientAppointment, String cond) {
		List<PatientAppointment> list = new ArrayList<PatientAppointment>();
		String sql = "SELECT p.p_code,p.name,dn.id_code,dn.name,ap.token,a.status,a.date FROM patient p,doctors_nurses dn, appointment a,appointment_patient ap WHERE ap.app_id=a.app_id AND a.p_id=p.p_id AND a.dn_id=dn.dn_id AND a.status=0 ";
		if (!patientAppointment.getPatientName().isEmpty()) {
			sql += " AND p.name LIKE'%" + patientAppointment.getPatientName() + "%'";
		}
		if (!patientAppointment.getDoctorName().isEmpty()) {
			sql += " AND dn.name LIKE'%" + patientAppointment.getDoctorName() + "%'";
		}
		if (patientAppointment.getDate() != null) {
			sql += " AND a.date='" + patientAppointment.getDate() + "'";
		}
		sql += " ORDER BY a.date";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				JFXCheckBox box = new JFXCheckBox("Available");
				box.setSelected(rs.getBoolean(6));
				box.setCheckedColor(Color.rgb(64, 89, 169));
				if (cond.equals("JFX")) {
					PatientAppointment appointment = new PatientAppointment(rs.getInt(5),
							LocalDate.parse(rs.getString(7)), rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getString(4), box);
					list.add(appointment);
				} else {
					PatientAppointment appointment = new PatientAppointment(rs.getInt(5),
							LocalDate.parse(rs.getString(7)), rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getInt(6));
					list.add(appointment);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}

	@Override
	public List<RoomAppointment> getRoomAppointment2() {
		List<RoomAppointment> list = new ArrayList<RoomAppointment>();
		String sql = "SELECT p.p_code,p.name,dn.id_code,dn.name,a.date,ar.r_id,a.status,a.cancel FROM patient p,doctors_nurses dn,appointment a, appointment_room ar WHERE p.p_id=a.p_id AND dn.dn_id=a.dn_id AND a.app_id=ar.app_id ORDER BY a.app_id";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				String pCode = rs.getString(1);
				String pName = rs.getString(2);
				String dnCode = rs.getString(3);
				String dnName = rs.getString(4);
				LocalDate date = LocalDate.parse(rs.getString(5));
				int rID = rs.getInt(6);
				int status = rs.getInt(7);
				int cancel = rs.getInt(8);
				JFXCheckBox check = new JFXCheckBox("available");
				check.setCheckedColor(Color.rgb(64, 89, 169));
				check.setSelected(status == 1 ? true : false);

				RoomAppointment roomAppointment = new RoomAppointment(pCode, pName, dnCode, dnName, date, rID, status,
						cancel);
				list.add(roomAppointment);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

}
