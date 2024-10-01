package impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.HMSException;
import entity.Department;
import entity.Gender;
import entity.Staff;
import entity.StaffPosition;
import serviceimpl.StaffService;
import utils.Connector;
import utils.IDGenerator;
import utils.PasswordEncypt;

public class StaffServiceImpl implements StaffService {
	public void loginError(String username, String password) {
		if (username.isEmpty() || password.isEmpty()) {
			throw new HMSException("Username or password cannot be empty.");
		}
	}

	@Override
	public void insert(Staff staff) {
		String sql = "INSERT INTO `staff`(`id_code`, `name`, `password`, `image`, `department`, `position`, `father_name`, `mother_name`, `sex`, `dob`, `nrc`, `degree`, `address`, `start_work_date`, `previous_work`, `salary`, `is_resign`, `resign_date`, `is_admin`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, staff);
			stmt.executeUpdate();
			System.out.println("Add One");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void update(Staff staff) {
		String sql = "UPDATE `staff` SET `id_code`=?,`name`=?,`password`=?,`image`=?,`department`=?,`position`=?,`father_name`=?,`mother_name`=?,`sex`=?,`dob`=?,`nrc`=?,`degree`=?,`address`=?,`start_work_date`=?,`previous_work`=?,`salary`=?,`is_resign`=?,`resign_date`=?, `is_admin`=? WHERE staff_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			addObject(stmt, staff);
			stmt.setInt(20, staff.getId());
			stmt.executeUpdate();
			System.out.println("Update One");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Staff> getAllData() {
		List<Staff> list = new ArrayList<Staff>();
		String sql = "SELECT * FROM staff";
		try (ResultSet rs = Connector.getResultSet(sql)) {
			while (rs.next()) {
				list.add(getObject(rs));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public Staff getObject(ResultSet rs) throws SQLException {
		int id = rs.getInt(1);
		String id_code = rs.getString(2);
		String name = rs.getString(3);
		String hash_password = rs.getString(4);
		String password = PasswordEncypt.decode(hash_password);
		Blob b = rs.getBlob(5);
		byte[] image = b.getBytes(1, (int) b.length());
		Department department = Department.valueOf(rs.getString(6));
		StaffPosition position = StaffPosition.valueOf(rs.getString(7));
		String father_name = rs.getString(8);
		String mother_name = rs.getString(9);
		Gender sex = Gender.valueOf(rs.getString(10));
		LocalDate dob = LocalDate.parse(rs.getString(11));
		String nrc = rs.getString(12);
		String degree = rs.getString(13);
		String address = rs.getString(14);
		LocalDate start_work_date = LocalDate.parse(rs.getString(15));
		String previous_work = rs.getString(16);
		double salary = rs.getDouble(17);
		boolean isResign = rs.getBoolean(18);
		String resign_date = rs.getString(19);
		boolean is_admin = rs.getBoolean(20);
		return new Staff(id, id_code, name, password, image, department, position, father_name, mother_name, sex, dob,
				nrc, degree, address, start_work_date, previous_work, salary, isResign, resign_date, is_admin);
	}

	public void addObject(PreparedStatement stmt, Staff staff) throws SQLException {
		stmt.setString(1, staff.getId_code());
		stmt.setString(2, staff.getName());
		String password = staff.getPassword();
		String encrypt = PasswordEncypt.encode(password);
		stmt.setString(3, encrypt);
		stmt.setBlob(4, new ByteArrayInputStream(staff.getImage()));
		stmt.setString(5, staff.getDepartment().toString());
		stmt.setString(6, staff.getPosition().toString());
		stmt.setString(7, staff.getFather_name());
		stmt.setString(8, staff.getMother_name());
		stmt.setString(9, staff.getSex().toString());
		stmt.setString(10, staff.getDob().toString());
		stmt.setString(11, staff.getNrc());
		stmt.setString(12, staff.getDegree());
		stmt.setString(13, staff.getAddress());
		stmt.setString(14, staff.getStart_work_date().toString());
		stmt.setString(15, staff.getPrevious_work());
		stmt.setDouble(16, staff.getSalary());
		stmt.setBoolean(17, staff.isResign());
		stmt.setString(18, staff.getResign_date());
		stmt.setBoolean(19, staff.isAdmin());
	}

	public static void main(String[] args) throws IOException {
		byte ant[] = Files.readAllBytes(Paths.get("C:\\Users\\National TB Program\\Pictures\\D.jpg"));

	
		Staff staff = new Staff(IDGenerator.generateStaffID(), "Thomas", "admin", ant, Department.Lung,
				StaffPosition.Receptionist, "STA", "SSM", Gender.Male, LocalDate.of(2000, 7, 7), "12/SAKANA(N)000000",
				"MBBS,Lung", "Room2,Room 4,Strret Yangon Sangyoung", LocalDate.of(2020, 8, 11), "Sakura and Asia Royal",
				100000.0, false, "None", true);
		new StaffServiceImpl().insert(staff);
		IDGenerator.increaseStaffID();
//
//		new StaffServiceImpl().getAllData().forEach(e -> {
//			System.out.println(e.getPassword());
//		});
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM `staff` WHERE staff_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("Delete one");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public int getID(Staff staff) {
		String sql = "SELECT staff_id FROM `staff` WHERE id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, staff.getId_code());
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
	public Staff checkVaildUser(String userID, String password) {
		Staff staff = null;
		loginError(userID, password);
		String sql = "SELECT * FROM staff where id_code=? AND password=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, userID);
			String encrypt = PasswordEncypt.encode(password);
			stmt.setString(2, encrypt);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				staff = getObject(rs);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (staff == null) {
			throw new HMSException("Username or Password is wrong");
		}
		return staff;
	}

	@Override
	public void changePassword(String id_code, String password) {
		String sql = "UPDATE `staff` SET `password`=? WHERE id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			String encrypt = PasswordEncypt.encode(password);
			stmt.setString(1, encrypt);
			stmt.setString(2, id_code);
			stmt.executeUpdate();
			System.out.println("Password is change.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Staff findById(int id) {
		Staff staff = null;
		String sql = "SELECT * FROM staff where staff_id=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				staff = getObject(rs);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return staff;
	}

	@Override
	public Staff findByIdCode(String id_code) {
		Staff staff = null;
		String sql = "SELECT * FROM staff where id_code=?";
		try (PreparedStatement stmt = Connector.getPrepareStatement(sql)) {
			stmt.setString(1, id_code);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				staff = getObject(rs);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		if (staff == null) {
			throw new HMSException("Id code is invaild");
		}
		return staff;
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
	public List<Staff> findAll(Staff staff) {
		List<Staff> list = new ArrayList<Staff>();
		String sql = "SELECT * FROM `staff`";
		if (!staff.getId_code().isEmpty()) {
			sql = defindAndORWhere(sql);
			sql += "id_code LIKE" + "'%" + staff.getId_code() + "%'";
		}
		if (!staff.getName().isEmpty()) {
			sql = defindAndORWhere(sql);
			sql += "name LIKE" + "'%" + staff.getName() + "%'";
		}
		if (staff.getDepartment() != null) {
			sql = defindAndORWhere(sql);
			sql += "department=" + "'" + staff.getDepartment() + "'";
		}
		if (staff.getPosition() != null) {
			sql = defindAndORWhere(sql);
			sql += "position=" + "'" + staff.getPosition() + "'";

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
}
