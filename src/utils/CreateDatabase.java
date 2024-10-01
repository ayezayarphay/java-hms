package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.Room;
import entity.RoomType;
import impl.RoomServiceImpl;
import serviceimpl.RoomService;

public class CreateDatabase {
	private static String url = "jdbc:mysql://localhost";
	private static String user = "root";
	private static String password = "";

	public static Connection getConnection(String url) throws Exception {
		// String url = "jdbc:mysql://" + InetAddress.getLocalHost().getHostAddress() +
		// "/hospital";
		return DriverManager.getConnection(url, user, password);
	}

	public static PreparedStatement getPrepareStatement(String sql) throws Exception {
		return getConnection(url).prepareStatement(sql);
	}

	public static ResultSet getResultSet(String sql) throws Exception {
		return getPrepareStatement(sql).executeQuery();
	}

	public static boolean check() {
		String sql = "SHOW DATABASES";
		try (ResultSet rs = getResultSet(sql)) {
			while (rs.next()) {
				String r = rs.getString(1);
				if (r.equals("hospital")) {
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static void createDatabaseAndTable() {
		String sql1 = "CREATE DATABASE hospital";
		try (PreparedStatement stmt = getPrepareStatement(sql1)) {
			stmt.execute();
			System.out.println("Create database");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql2 = "CREATE TABLE `appointment` (\r\n" + "  `app_id` int(11) NOT NULL,\r\n"
				+ "  `status` int(11) NOT NULL,\r\n" + "  `cancel` int(11) NOT NULL,\r\n"
				+ "  `date` date NOT NULL,\r\n" + "  `p_id` int(11) NOT NULL,\r\n"
				+ "  `staff_id` int(11) NOT NULL,\r\n" + "  `dn_id` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql2)) {
			stmt.execute();

			System.out.println("Create appointment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		String sql3 = "CREATE TABLE `appointment_patient` (\r\n" + "  `app_id` int(11) NOT NULL,\r\n"
				+ "  `token` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql3)) {
			stmt.execute();

			System.out.println("Create appointment_patient table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql4 = "CREATE TABLE `appointment_room` (\r\n" + "  `app_id` int(11) NOT NULL,\r\n"
				+ "  `r_id` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql4)) {
			stmt.execute();

			System.out.println("Create appointment_room table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql5 = "CREATE TABLE `dn_schedule` (\r\n" + "  `s_id` int(11) NOT NULL,\r\n"
				+ "  `dn_id` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql5)) {
			stmt.execute();

			System.out.println("Create dn_schedule table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql6 = "CREATE TABLE `doctors_nurses` (\r\n" + "  `dn_id` int(11) NOT NULL,\r\n"
				+ "  `id_code` varchar(10) NOT NULL,\r\n" + "  `name` varchar(45) NOT NULL,\r\n"
				+ "  `image` mediumblob NOT NULL,\r\n" + "  `department` varchar(30) NOT NULL,\r\n"
				+ "  `position` varchar(30) NOT NULL,\r\n" + "  `father_name` varchar(45) NOT NULL,\r\n"
				+ "  `mother_name` varchar(45) NOT NULL,\r\n" + "  `sex` varchar(10) NOT NULL,\r\n"
				+ "  `dob` date NOT NULL,\r\n" + "  `nrc` varchar(45) NOT NULL,\r\n"
				+ "  `degree` varchar(225) DEFAULT NULL,\r\n" + "  `address` text NOT NULL,\r\n"
				+ "  `personal_skill` varchar(100) NOT NULL,\r\n" + "  ` start_work_date` date NOT NULL,\r\n"
				+ "  `previous_hospital` varchar(70) NOT NULL,\r\n" + "  `salary` double NOT NULL,\r\n"
				+ "  `is_resign` tinyint(1) NOT NULL,\r\n" + "  `resign_date` varchar(20) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql6)) {
			stmt.execute();

			System.out.println("Create doctors_nurses table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql7 = "CREATE TABLE `patient` (\r\n" + "  `p_id` int(11) NOT NULL,\r\n"
				+ "  `p_code` varchar(20) NOT NULL,\r\n" + "  `name` varchar(45) NOT NULL,\r\n"
				+ "  `age` int(11) NOT NULL,\r\n" + "  `sex` varchar(10) NOT NULL,\r\n"
				+ "  `family_phone` varchar(15) NOT NULL,\r\n" + "  `address` text NOT NULL,\r\n"
				+ "  `other_case` varchar(80) NOT NULL,\r\n" + "  `allergic` varchar(80) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql7)) {
			stmt.execute();

			System.out.println("Create patient table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql8 = "CREATE TABLE `payment` (\r\n" + "  `pay_id` int(11) NOT NULL,\r\n"
				+ "  `date` int(11) NOT NULL,\r\n" + "  `doctor_price` int(11) NOT NULL,\r\n"
				+ "  `t_id` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql8)) {
			stmt.execute();

			System.out.println("Create payment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql9 = "CREATE TABLE `payment_details` (\r\n" + "  `service_name` varchar(100) NOT NULL,\r\n"
				+ "  `service_price` double NOT NULL,\r\n" + "  `pay_id` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql9)) {
			stmt.execute();

			System.out.println("Create payment_details table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql10 = "CREATE TABLE `pharmacy_inventory` (\r\n" + "  `pi_id` int(11) NOT NULL,\r\n"
				+ "  `id_code` varchar(10) NOT NULL,\r\n" + "  `name` varchar(45) NOT NULL,\r\n"
				+ "  `category` varchar(20) NOT NULL,\r\n" + "  `price` double NOT NULL,\r\n"
				+ "  `total_qty` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql10)) {
			stmt.execute();

			System.out.println("Create pharmacy_inventory table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql11 = "CREATE TABLE `room` (\r\n" + "  `r_id` int(11) NOT NULL,\r\n"
				+ "  `type` varchar(20) NOT NULL,\r\n" + "  `floor` varchar(20) NOT NULL,\r\n"
				+ "  `price` double NOT NULL,\r\n" + "  `status` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql11)) {
			stmt.execute();

			System.out.println("Create room table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql12 = "CREATE TABLE `sale` (\r\n" + "  `sale_id` int(11) NOT NULL,\r\n"
				+ "  `staff_id` int(11) NOT NULL,\r\n" + "  `p_id` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql12)) {
			stmt.execute();

			System.out.println("Create sale table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql13 = "CREATE TABLE `sale_details` (\r\n" + "  `s_id` int(11) NOT NULL,\r\n"
				+ "  `phar_id` int(11) NOT NULL,\r\n" + "  `qty` int(11) NOT NULL,\r\n" + "  `date` date NOT NULL\r\n"
				+ ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql13)) {
			stmt.execute();

			System.out.println("Create sale_details table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql14 = "CREATE TABLE `schedule` (\r\n" + "  `s_id` int(11) NOT NULL,\r\n"
				+ "  `day_of_week` varchar(100) NOT NULL,\r\n" + "  `start_time` varchar(20) NOT NULL,\r\n"
				+ "  `end_time` varchar(20) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql14)) {
			stmt.execute();

			System.out.println("Create schedule table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql15 = "CREATE TABLE `staff`(\r\n" + "  `staff_id` int(11) NOT NULL,\r\n"
				+ "  `id_code` varchar(10) NOT NULL,\r\n" + "  `name` varchar(45) NOT NULL,\r\n"
				+ "  `password` varchar(20) NOT NULL,\r\n" + "  `image` mediumblob NOT NULL,\r\n"
				+ "  `department` varchar(30) NOT NULL,\r\n" + "  `position` varchar(30) NOT NULL,\r\n"
				+ "  `father_name` varchar(45) NOT NULL,\r\n" + "  `mother_name` varchar(45) NOT NULL,\r\n"
				+ "  `sex` varchar(10) NOT NULL,\r\n" + "  `dob` date NOT NULL,\r\n"
				+ "  `nrc` varchar(45) NOT NULL,\r\n" + "  `degree` varchar(225) DEFAULT NULL,\r\n"
				+ "  `address` text NOT NULL,\r\n" + "  `start_work_date` date NOT NULL,\r\n"
				+ "  `previous_work` varchar(70) NOT NULL,\r\n" + "  `salary` double NOT NULL,\r\n"
				+ "  `is_resign` tinyint(1) NOT NULL,\r\n" + "  `resign_date` varchar(20) NOT NULL,\r\n"
				+ "  `is_admin` tinyint(1) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql15)) {
			stmt.execute();

			System.out.println("Create staff table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql16 = "CREATE TABLE `staff_schedule` (\r\n" + "  `s_id` int(11) NOT NULL,\r\n"
				+ "  `staff_id` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql16)) {
			stmt.execute();

			System.out.println("Create staff_schedule table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql17 = "CREATE TABLE `treatment` (\r\n" + "  `t_id` int(11) NOT NULL,\r\n"
				+ "  `cureType` varchar(30) NOT NULL,\r\n" + "  `pcase` varchar(100) NOT NULL,\r\n"
				+ "  `date` date NOT NULL,\r\n" + "  `leave_date` varchar(30) NOT NULL,\r\n"
				+ "  `iscash` tinyint(1) NOT NULL,\r\n" + "  `r_id` int(11) NOT NULL,\r\n"
				+ "  `p_id` int(11) NOT NULL,\r\n" + "  `dn_id` int(11) NOT NULL,\r\n"
				+ "  `staff_id` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql17)) {
			stmt.execute();

			System.out.println("Create treatment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql18 = "CREATE TABLE `treatment_details`(\r\n" + "  `t_id` int(11) NOT NULL,\r\n"
				+ "  `phar_id` int(11) NOT NULL,\r\n" + "  `staff_id` int(11) NOT NULL,\r\n"
				+ "  `qty` int(11) NOT NULL,\r\n" + "  `date` int(11) NOT NULL\r\n" + ");";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql18)) {
			stmt.execute();

			System.out.println("Create treatment_details table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql19 = "ALTER TABLE `appointment`\r\n" + "  ADD PRIMARY KEY (`app_id`),\r\n"
				+ "  ADD KEY `p_id` (`p_id`),\r\n" + "  ADD KEY `dn_id` (`dn_id`),\r\n"
				+ "  ADD KEY `staff_id` (`staff_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql19)) {
			stmt.execute();

			System.out.println("Alter appointment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql20 = "ALTER TABLE `appointment_patient`\r\n" + "  ADD KEY `app_id` (`app_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql20)) {
			stmt.execute();

			System.out.println("Alter appointment_patient table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql21 = "ALTER TABLE `appointment_room`\r\n" + "  ADD KEY `app_id` (`app_id`),\r\n"
				+ "  ADD KEY `r_id` (`r_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql21)) {
			stmt.execute();

			System.out.println("Alter appointment_room table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql22 = "ALTER TABLE `dn_schedule`\r\n" + "  ADD KEY `s_id` (`s_id`),\r\n"
				+ "  ADD KEY `dn_id` (`dn_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql22)) {
			stmt.execute();

			System.out.println("Alter dn_schedule table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql23 = "ALTER TABLE `doctors_nurses`\r\n" + "  ADD PRIMARY KEY (`dn_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql23)) {
			stmt.execute();

			System.out.println("Alter doctors_nurses table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql24 = "ALTER TABLE `patient`\r\n" + "  ADD PRIMARY KEY (`p_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql24)) {
			stmt.execute();

			System.out.println("Alter patient table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql25 = "ALTER TABLE `payment`\r\n" + "  ADD PRIMARY KEY (`pay_id`),\r\n" + "  ADD KEY `t_id` (`t_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql25)) {
			stmt.execute();

			System.out.println("Alter payment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql26 = "ALTER TABLE `payment_details`\r\n" + "  ADD KEY `pay_id` (`pay_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql26)) {
			stmt.execute();

			System.out.println("Alter payment_details table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql27 = "ALTER TABLE `pharmacy_inventory`\r\n" + "  ADD PRIMARY KEY (`pi_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql27)) {
			stmt.execute();

			System.out.println("Alter pharmacy_inventory table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql28 = "ALTER TABLE `room`\r\n" + "  ADD PRIMARY KEY (`r_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql28)) {
			stmt.execute();

			System.out.println("Alter room table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql29 = "ALTER TABLE `sale`\r\n" + "  ADD PRIMARY KEY (`sale_id`),\r\n"
				+ "  ADD KEY `staff_id` (`staff_id`),\r\n" + "  ADD KEY `p_id` (`p_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql29)) {
			stmt.execute();

			System.out.println("Alter sale table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql30 = "ALTER TABLE `sale_details`\r\n" + "  ADD KEY `s_id` (`s_id`),\r\n"
				+ "  ADD KEY `phar_id` (`phar_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql30)) {
			stmt.execute();

			System.out.println("Alter sale_details table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql31 = "ALTER TABLE `schedule`\r\n" + "  ADD PRIMARY KEY (`s_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql31)) {
			stmt.execute();

			System.out.println("Alter schedule table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql32 = "ALTER TABLE `staff`\r\n" + "  ADD PRIMARY KEY (`staff_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql32)) {
			stmt.execute();

			System.out.println("Alter staff table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql33 = "ALTER TABLE `staff_schedule`\r\n" + "  ADD KEY `s_id` (`s_id`),\r\n"
				+ "  ADD KEY `staff_id` (`staff_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql33)) {
			stmt.execute();

			System.out.println("Alter staff_schedule table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql34 = "ALTER TABLE `treatment`\r\n" + "  ADD PRIMARY KEY (`t_id`),\r\n"
				+ "  ADD KEY `p_id` (`p_id`),\r\n" + "  ADD KEY `dn_id` (`dn_id`),\r\n"
				+ "  ADD KEY `r_id` (`r_id`),\r\n" + "  ADD KEY `staff_id` (`staff_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql34)) {
			stmt.execute();

			System.out.println("Alter treatment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql35 = "ALTER TABLE `treatment_details`\r\n" + "  ADD KEY `t_id` (`t_id`),\r\n"
				+ "  ADD KEY `phar_id` (`phar_id`),\r\n" + "  ADD KEY `staff_id` (`staff_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql35)) {
			stmt.execute();

			System.out.println("Alter treatment_details table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql36 = "ALTER TABLE `appointment`\r\n" + "  MODIFY `app_id` int(11) NOT NULL AUTO_INCREMENT;";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql36)) {
			stmt.execute();

			System.out.println("Modify appointment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql37 = "ALTER TABLE `doctors_nurses`\r\n" + "  MODIFY `dn_id` int(11) NOT NULL AUTO_INCREMENT;";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql37)) {
			stmt.execute();

			System.out.println("Modify doctors_nurses table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql38 = "ALTER TABLE `patient`\r\n" + "  MODIFY `p_id` int(11) NOT NULL AUTO_INCREMENT;";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql38)) {
			stmt.execute();

			System.out.println("Modify patient table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql39 = "ALTER TABLE `payment`\r\n" + "  MODIFY `pay_id` int(11) NOT NULL AUTO_INCREMENT;";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql39)) {
			stmt.execute();

			System.out.println("Modify payment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql40 = "ALTER TABLE `pharmacy_inventory`\r\n" + "  MODIFY `pi_id` int(11) NOT NULL AUTO_INCREMENT;";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql40)) {
			stmt.execute();

			System.out.println("Modify pharmacy_inventory table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql41 = "ALTER TABLE `room`\r\n" + "  MODIFY `r_id` int(11) NOT NULL AUTO_INCREMENT;";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql41)) {
			stmt.execute();

			System.out.println("Modify room table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql42 = "ALTER TABLE `sale`\r\n" + "  MODIFY `sale_id` int(11) NOT NULL AUTO_INCREMENT;";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql42)) {
			stmt.execute();

			System.out.println("Modify sale table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql43 = "ALTER TABLE `schedule`\r\n" + "  MODIFY `s_id` int(11) NOT NULL AUTO_INCREMENT;";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql43)) {
			stmt.execute();

			System.out.println("Modify schedule table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql44 = "ALTER TABLE `staff`\r\n" + "  MODIFY `staff_id` int(11) NOT NULL AUTO_INCREMENT;";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql44)) {
			stmt.execute();

			System.out.println("Modify staff table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql45 = "ALTER TABLE `treatment`\r\n" + "  MODIFY `t_id` int(11) NOT NULL AUTO_INCREMENT;";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql45)) {
			stmt.execute();

			System.out.println("Modify treatment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql46 = "ALTER TABLE `appointment`\r\n"
				+ "  ADD CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `patient` (`p_id`),\r\n"
				+ "  ADD CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`dn_id`) REFERENCES `doctors_nurses` (`dn_id`),\r\n"
				+ "  ADD CONSTRAINT `appointment_ibfk_3` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql46)) {
			stmt.execute();

			System.out.println("Constraint foreign key appointment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql47 = "ALTER TABLE `appointment_patient`\r\n"
				+ "  ADD CONSTRAINT `appointment_patient_ibfk_1` FOREIGN KEY (`app_id`) REFERENCES `appointment` (`app_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql47)) {
			stmt.execute();

			System.out.println("Constraint foreign key appointment_patient table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql48 = "ALTER TABLE `appointment_room`\r\n"
				+ "  ADD CONSTRAINT `appointment_room_ibfk_1` FOREIGN KEY (`app_id`) REFERENCES `appointment` (`app_id`),\r\n"
				+ "  ADD CONSTRAINT `appointment_room_ibfk_2` FOREIGN KEY (`r_id`) REFERENCES `room` (`r_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql48)) {
			stmt.execute();

			System.out.println("Constraint foreign key appointment_room table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql49 = "ALTER TABLE `dn_schedule`\r\n"
				+ "  ADD CONSTRAINT `dn_schedule_ibfk_1` FOREIGN KEY (`s_id`) REFERENCES `schedule` (`s_id`),\r\n"
				+ "  ADD CONSTRAINT `dn_schedule_ibfk_2` FOREIGN KEY (`dn_id`) REFERENCES `doctors_nurses` (`dn_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql49)) {
			stmt.execute();

			System.out.println("Constraint foreign key dn_schedule table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql50 = "ALTER TABLE `payment`\r\n"
				+ "  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`t_id`) REFERENCES `treatment` (`t_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql50)) {
			stmt.execute();

			System.out.println("Constraint foreign key payment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql51 = "ALTER TABLE `payment_details`\r\n"
				+ "  ADD CONSTRAINT `payment_details_ibfk_1` FOREIGN KEY (`pay_id`) REFERENCES `payment` (`pay_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql51)) {
			stmt.execute();

			System.out.println("Constraint foreign key payment_details table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql52 = "ALTER TABLE `sale`\r\n"
				+ "  ADD CONSTRAINT `sale_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`),\r\n"
				+ "  ADD CONSTRAINT `sale_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `patient` (`p_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql52)) {
			stmt.execute();

			System.out.println("Constraint foreign key sale table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql53 = "ALTER TABLE `sale_details`\r\n"
				+ "  ADD CONSTRAINT `sale_details_ibfk_1` FOREIGN KEY (`s_id`) REFERENCES `sale` (`sale_id`),\r\n"
				+ "  ADD CONSTRAINT `sale_details_ibfk_2` FOREIGN KEY (`phar_id`) REFERENCES `pharmacy_inventory` (`pi_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql53)) {
			stmt.execute();

			System.out.println("Constraint foreign key sale_details table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql54 = "ALTER TABLE `staff_schedule`\r\n"
				+ "  ADD CONSTRAINT `staff_schedule_ibfk_1` FOREIGN KEY (`s_id`) REFERENCES `sale` (`sale_id`),\r\n"
				+ "  ADD CONSTRAINT `staff_schedule_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql54)) {
			stmt.execute();

			System.out.println("Constraint foreign key staff_schedule table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql55 = "ALTER TABLE `treatment`\r\n"
				+ "  ADD CONSTRAINT `treatment_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `patient` (`p_id`),\r\n"
				+ "  ADD CONSTRAINT `treatment_ibfk_2` FOREIGN KEY (`dn_id`) REFERENCES `doctors_nurses` (`dn_id`),\r\n"
				+ "  ADD CONSTRAINT `treatment_ibfk_3` FOREIGN KEY (`r_id`) REFERENCES `room` (`r_id`),\r\n"
				+ "  ADD CONSTRAINT `treatment_ibfk_4` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql55)) {
			stmt.execute();

			System.out.println("Constraint foreign key treatment table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql56 = "ALTER TABLE `treatment_details`\r\n"
				+ "  ADD CONSTRAINT `treatment_details_ibfk_1` FOREIGN KEY (`t_id`) REFERENCES `treatment` (`t_id`),\r\n"
				+ "  ADD CONSTRAINT `treatment_details_ibfk_2` FOREIGN KEY (`phar_id`) REFERENCES `pharmacy_inventory` (`pi_id`),\r\n"
				+ "  ADD CONSTRAINT `treatment_details_ibfk_3` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`);";
		try (Connection con = getConnection("jdbc:mysql://localhost/hospital");
				PreparedStatement stmt = con.prepareStatement(sql56)) {
			stmt.execute();

			System.out.println("Constraint foreign key treatment_details table");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void createRoom() {
		Room r = null;
		RoomService roomService = new RoomServiceImpl();
		for (int i = 1; i <= 130; i++) {
			if (i <= 10) {
				r = new Room(RoomType.CureRoom, "Floor 1", 0, 0);
			} else if (i >= 11 && i <= 20) {
				r = new Room(RoomType.CureRoom, "Floor 2", 0, 0);
			} else if (i >= 21 && i <= 30) {
				r = new Room(RoomType.CureRoom, "Floor 3", 0, 0);
			} else if (i >= 31 && i <= 40) {
				r = new Room(RoomType.Normal, "Floor 4", 50000, 0);
			} else if (i >= 41 && i <= 50) {
				r = new Room(RoomType.Normal, "Floor 5", 50000, 0);
			} else if (i >= 51 && i <= 60) {
				r = new Room(RoomType.Normal, "Floor 6", 50000, 0);
			} else if (i >= 61 && i <= 70) {
				r = new Room(RoomType.Normal, "Floor 7", 50000, 0);
			} else if (i >= 71 && i <= 80) {
				r = new Room(RoomType.Normal, "Floor 8", 50000, 0);
			} else if (i >= 81 && i <= 90) {
				r = new Room(RoomType.Special, "Floor 9", 80000, 0);
			} else if (i >= 91 && i <= 100) {
				r = new Room(RoomType.Special, "Floor 10", 80000, 0);
			} else if (i >= 101 && i <= 110) {
				r = new Room(RoomType.Special, "Floor 11", 80000, 0);
			} else if (i >= 111 && i <= 120) {
				r = new Room(RoomType.Special, "Floor 12", 80000, 0);
			} else if (i >= 121 && i <= 130) {
				r = new Room(RoomType.Special, "Floor 13", 80000, 0);
			}
			roomService.insert(r);
		}
	}

	public static void main(String[] args) throws IOException {
		if (!check()) {
			createDatabaseAndTable();
			createRoom();
		} else {
			System.out.println("Already Exists");
		}

	}

}
