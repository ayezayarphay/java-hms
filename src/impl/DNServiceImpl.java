package impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.DN;
import entity.DNPosition;
import entity.Department;
import entity.Gender;
import serviceimpl.DNService;
import utils.Connector;

public class DNServiceImpl implements DNService {

	@Override
	public void insert(DN dns) {
		String sql = "INSERT INTO `doctors_nurses`(`id_code`, `name`, `image`, `department`, `position`, `father_name`, `mother_name`, `sex`, `dob`, `nrc`, `degree`, `address`, `personal_skill`, ` start_work_date`, `previous_hospital`, `salary`, `is_resign`, `resign_date`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, dns);
			stmt.executeUpdate();
			System.out.println("Add one");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void update(DN dns) {
		String sql = "UPDATE `doctors_nurses` SET `id_code`=?,`name`=?,`image`=?,`department`=?,`position`=?,`father_name`=?,`mother_name`=?,`sex`=?,`dob`=?,`nrc`=?,`degree`=?,`address`=?,`personal_skill`=?,` start_work_date`=?,`previous_hospital`=?,`salary`=?,`is_resign`=?,`resign_date`=? WHERE `dn_id`=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, dns);
			stmt.setInt(19, dns.getId());
			stmt.executeUpdate();
			System.out.println("Update one");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<DN> getAllData() {
		List<DN> list = new ArrayList<DN>();
		String sql = "SELECT * FROM `doctors_nurses`";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {

				list.add(getObject(rs));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public DN getObject(ResultSet rs) throws SQLException {
		int id = rs.getInt(1);
		String id_code = rs.getString(2);
		String name = rs.getString(3);
		Blob b = rs.getBlob(4);
		byte[] image = b.getBytes(1, (int) b.length());
		Department department = Department.valueOf(rs.getString(5));
		DNPosition position = DNPosition.valueOf(rs.getString(6));
		String father_name = rs.getString(7);
		String mother_name = rs.getString(8);
		Gender sex = Gender.valueOf(rs.getString(9));
		LocalDate dob = LocalDate.parse(rs.getString(10));
		String nrc = rs.getString(11);
		String degree = rs.getString(12);
		String address = rs.getString(13);
		String personal_skill = rs.getString(14);
		LocalDate start_work_date = LocalDate.parse(rs.getString(15));
		String previous_hospital = rs.getString(16);
		double salary = rs.getDouble(17);
		boolean isResign = rs.getBoolean(18);
		String resign_date = rs.getString(19);
		return new DN(id, id_code, name, image, department, position, father_name, mother_name, sex, dob, nrc, degree,
				address, personal_skill, start_work_date, previous_hospital, salary, isResign, resign_date);

	}

	public void addObject(PreparedStatement stmt, DN dns) throws SQLException {
		stmt.setString(1, dns.getId_code());
		stmt.setString(2, dns.getName());
		stmt.setBlob(3, new ByteArrayInputStream(dns.getImage()));
		stmt.setString(4, dns.getDepartment().toString());
		stmt.setString(5, dns.getPosition().toString());
		stmt.setString(6, dns.getFather_name());
		stmt.setString(7, dns.getMother_name());
		stmt.setString(8, dns.getSex().toString());
		stmt.setString(9, dns.getDob().toString());
		stmt.setString(10, dns.getNrc());
		stmt.setString(11, dns.getDegree());
		stmt.setString(12, dns.getAddress());
		stmt.setString(13, dns.getPersonal_skill());
		stmt.setString(14, dns.getStart_work_date().toString());
		stmt.setString(15, dns.getPrevious_hospital());
		stmt.setDouble(16, dns.getSalary());
		stmt.setBoolean(17, dns.isResign());
		stmt.setString(18, dns.getResign_date());

	}

	public static void main(String[] args) throws IOException {
//		 byte ant[] = Files.readAllBytes(Paths.get(""));
		// byte avater[] = Files.readAllBytes(Paths.get("D:\\Hello\\avater.jpg"));
//		byte ds[] = Files.readAllBytes(Paths.get("C:\\\\Users\\\\National TB Program\\Pictures\\"));
////	
//		DN dn1 = new DN("1002", "Htun Htun", ds, "Myo Myo", "Professor", "Qwil", "Pilos", Gender.Male,
//				LocalDate.of(1991, 15, 11), "12/007354", "Sweet Street", "Special at Eye", LocalDate.of(2012, 1, 10),
//				"GrandHamther Hospital", 1000000, false, "None");
//		int id = new DNServiceImpl().getID(dn1);
//		DN dn = new DN(1003, "Htun Htun", ds, "Myo Myo", "Professor", "Qwil", "Pilos", Gender.Male,
//				LocalDate.of(1991, 15, 11), "12/007354", "Sweet Street", "Special at Eye", LocalDate.of(2012, 1, 10),
//				"GrandHamther Hospital", 1000000, false, "None");
//		new DNServiceImpl().update(dn);

//		List<DN> list = new DNServiceImpl().getAllData();
//		for (DN dn : list) {
//			System.out.println(dn);
//		}
		new DNServiceImpl().getDNInfo("L").forEach(e -> {
			System.out.println(e);
		});

	}

	@Override
	public int getID(DN dns) {
		String sql = "SELECT dn_id FROM `doctors_nurses` WHERE id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, dns.getId_code());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return 0;
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM `doctors_nurses` WHERE dn_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("Delete one");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public DN finByID(int id) {
		DN dn = null;
		String sql = "SELECT * FROM `doctors_nurses` where dn_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dn = getObject(rs);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return dn;

	}

	@Override
	public DN findByidCode(String id_code) {
		DN dn = null;
		String sql = "SELECT * FROM `doctors_nurses` where id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, id_code);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				dn = getObject(rs);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return dn;
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
	public List<DN> findAll(DN dn) {
		List<DN> list = new ArrayList<DN>();
		String sql = "SELECT * FROM `doctors_nurses`";
		if (!dn.getId_code().isEmpty()) {
			sql = defindAndORWhere(sql);
			sql += "id_code LIKE" + "'%" + dn.getId_code() + "%'";
		}
		if (!dn.getName().isEmpty()) {
			sql = defindAndORWhere(sql);
			sql += "name LIKE" + "'%" + dn.getName() + "%'";
		}
		if (dn.getDepartment() != null) {
			sql = defindAndORWhere(sql);
			sql += "department=" + "'" + dn.getDepartment() + "'";
		}
		if (dn.getPosition() != null) {
			sql = defindAndORWhere(sql);
			sql += "position=" + "'" + dn.getPosition() + "'";

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
	public List<String> getDNInfo(String name) {
		List<String> list = new ArrayList<String>();
		String sql = "SELECT id_code,name,department,position FROM doctors_nurses WHERE name LIKE ?"
				+ "AND (position=? OR position=?)" + " AND is_resign=0";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, "%" + name + "%");
			stmt.setString(2, "Doctor");
			stmt.setString(3, "Professor");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idcode = rs.getString(1);
				String name1 = rs.getString(2);
				Department department = Department.valueOf(rs.getString(3));
				DNPosition dnPosition = DNPosition.valueOf(rs.getString(4));
				String format = String.format("%s %s %s %s", idcode, name1, department.toString(),
						dnPosition.toString());

				list.add(format);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public double getSalary(String idcode) {
		String sql = "SELECT salary FROM doctors_nurses WHERE id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, idcode);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

}
