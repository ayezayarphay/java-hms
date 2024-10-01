package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Gender;
import entity.Patient;
import serviceimpl.PatientService;
import utils.Connector;

public class PatientServiceImpl implements PatientService {

	@Override
	public void insert(Patient patient) {
		String sql = "INSERT INTO `patient`(`p_code`, `name`, `age`, `sex`, `family_phone`, `address`, `other_case`, `allergic`) VALUES (?,?,?,?,?,?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, patient);
			stmt.executeUpdate();
			System.out.println("Add one");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void update(Patient patient) {
		String sql = "UPDATE `patient` SET `p_code`=?,`name`=?,`age`=?,`sex`=?,`family_phone`=?,`address`=?,`other_case`=?,`allergic`=? WHERE p_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, patient);
			stmt.setInt(9, patient.getId());
			stmt.executeUpdate();
			System.out.println("Update One");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Patient> getAllData() {
		List<Patient> list = new ArrayList<Patient>();
		String sql = "SELECT * FROM `patient`";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				list.add(getObject(rs));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public Patient getObject(ResultSet rs) {
		Patient patient = null;
		try {
			patient = new Patient(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
					Gender.valueOf(rs.getString(5)), rs.getString(6), rs.getString(7), rs.getString(8),
					rs.getString(9));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patient;
	}

	public void addObject(PreparedStatement stmt, Patient patient) throws SQLException {
		stmt.setString(1, patient.getP_code());
		stmt.setString(2, patient.getName());
		stmt.setInt(3, patient.getAge());
		stmt.setString(4, patient.getSex().toString());
		stmt.setString(5, patient.getFamily_phone());
		stmt.setString(6, patient.getAddress());
		stmt.setString(7, patient.getOther_case());
		stmt.setString(8, patient.getAllergic());

	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM `patient` WHERE p_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("Delete one");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public int getID(Patient patient) {
		String sql = "SELECT p_id FROM `patient` WHERE p_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, patient.getP_code());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return 0;
	}

	public static void main(String[] args) {
//		Patient patient = new Patient("P1-10", "Hospital Patient", "Kyaw Kyaw", 21, Gender.Male, "0123456",
//				"No.18,Room(3),MyayNiGone,Shangone Street,Yangon", "Can't pee", "None", "pennaciln", "None", "None");
//		// new PatientServiceImpl().insert(patient);
//		int id = new PatientServiceImpl().getID(patient);
//		Patient patient1 = new Patient(id, "P1-10", "Hospital Patient", "Kyaw Kyaw", 20, Gender.Male, "0123456",
//				"No.18,Room(3),MyayNiGone,Shangone Street,Yangon", "Can't pee", "None", "pennaciln", "None", "None");
//		new PatientServiceImpl().update(patient1);
//		List<Patient> list = new PatientServiceImpl().getAllData();
//		for (Patient patient : list) {
//			System.out.println(patient);
//		}
		new PatientServiceImpl().findPatientInfo("M").forEach(s -> System.out.println(s));
	}

	@Override
	public Patient findByID(int id) {
		Patient patient = null;
		String sql = "SELECT * FROM `patient` WHERE `p_id`= ?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				patient = getObject(rs);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return patient;
	}

	@Override
	public Patient findByIdCodeBoolean(String id_code) {
		Patient patient = null;
		String sql = "SELECT * FROM `patient` WHERE `p_code`= ?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, id_code);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				patient = getObject(rs);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return patient;
	}

	public String defindAndORWhere(String sql) {
		if (!sql.contains("WHERE")) {
			sql += " WHERE ";
		} else if (!sql.contains("AND") || sql.contains("WHERE") && sql.contains("AND")) {
			sql += " AND ";
		}
		return sql;
	}

	@Override
	public List<Patient> findAll(Patient patient) {
		List<Patient> list = new ArrayList<Patient>();
		String sql = "SELECT * FROM `patient`";
		if (!patient.getP_code().isEmpty()) {
			sql = defindAndORWhere(sql);
			sql += "p_code LIKE" + "'%" + patient.getP_code() + "%'";
		}
		if (!patient.getName().isEmpty()) {
			sql = defindAndORWhere(sql);
			sql += "name LIKE" + "'%" + patient.getName() + "%'";
		}
		if (!patient.getFamily_phone().isEmpty()) {
			sql = defindAndORWhere(sql);
			sql += "family_phone LIKE" + "'%" + patient.getFamily_phone() + "%'";
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
	public List<String> findPatientInfo(String name) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT p_code,name,family_phone FROM patient WHERE name LIKE" + "'%" + name + "%'"
				+ " AND p_code<>'EP-1001'";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				String format = String.format("%s %s %s", rs.getString(1), rs.getString(2), rs.getString(3));
				list.add(format);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

}
