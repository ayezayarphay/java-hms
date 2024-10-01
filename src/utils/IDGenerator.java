package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import entity.Gender;
import entity.Patient;
import impl.PatientServiceImpl;
import serviceimpl.PatientService;

public class IDGenerator {
	static String p_id = "";
	static String dn_id = "";
	static String staff_id = "";
	static String phar_id = "";

	public static String buildPatientID() {
		PatientService p = new PatientServiceImpl();
		String loaction = "C:\\my hospital\\patient";
		Path path = Paths.get(loaction);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				loaction = loaction.concat("\\patientID.txt");
				path = Paths.get(loaction);
				Files.createFile(path);
				Files.write(path, "P-1000\nEP-1001".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
				Patient empty = p.findByIdCodeBoolean("EP-1001");
				if (empty == null) {
					p.insert(new Patient("EP-1001", "Unknown", 0, Gender.Unknown, "Unknown", "Unknown", "Unknown",
							"Unknown"));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loaction.contains("patientID.txt") ? loaction : loaction.concat("\\patientID.txt");
	}

	public static String buildDNID() {
		String loaction = "C:\\my hospital\\dn";
		Path path = Paths.get(loaction);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				loaction = loaction.concat("\\dnID.txt");
				path = Paths.get(loaction);
				Files.createFile(path);
				Files.write(path, "DN-1000".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loaction.contains("dnID.txt") ? loaction : loaction.concat("\\dnID.txt");
	}

	public static String buildStaffID() {
		String loaction = "C:\\my hospital\\staff";
		Path path = Paths.get(loaction);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				loaction = loaction.concat("\\staffID.txt");
				path = Paths.get(loaction);
				Files.createFile(path);
				Files.write(path, "Staff-1000".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loaction.contains("staffID.txt") ? loaction : loaction.concat("\\staffID.txt");
	}

	public static String buildPharmacyID() {
		String loaction = "C:\\my hospital\\pharmacy";
		Path path = Paths.get(loaction);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				loaction = loaction.concat("\\pharmamcyID.txt");
				path = Paths.get(loaction);
				Files.createFile(path);
				Files.write(path, "Phar-1000".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loaction.contains("pharmamcyID.txt") ? loaction : loaction.concat("\\pharmamcyID.txt");
	}

	public static String generatePatientID() {
		Path path = Paths.get(buildPatientID());

		try {
			List<String> list = Files.readAllLines(path);
			String array[] = list.get(0).split("-");
			int id = Integer.parseInt(array[1].trim()) + 1;
			p_id = array[0] + "-" + id;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return p_id;
	}

	public static String generateDNID() {
		Path path = Paths.get(buildDNID());

		try {
			List<String> list = Files.readAllLines(path);
			String array[] = list.get(0).split("-");
			int id = Integer.parseInt(array[1].trim()) + 1;
			dn_id = array[0] + "-" + id;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return dn_id;
	}

	public static String generateStaffID() {
		Path path = Paths.get(buildStaffID());

		try {
			List<String> list = Files.readAllLines(path);
			String array[] = list.get(0).split("-");
			int id = Integer.parseInt(array[1].trim()) + 1;
			staff_id = array[0] + "-" + id;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return staff_id;
	}

	public static String generatePharmacyID() {
		Path path = Paths.get(buildPharmacyID());

		try {
			List<String> list = Files.readAllLines(path);
			String array[] = list.get(0).split("-");
			int id = Integer.parseInt(array[1].trim()) + 1;
			phar_id = array[0] + "-" + id;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return phar_id;
	}

	public static void increaseID() {
		Path path = Paths.get(buildPatientID());
		try {
			String text = p_id + "\n" + "EP-1001";
			Files.write(path, text.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void increaseDNID() {
		Path path = Paths.get(buildDNID());
		try {
			Files.write(path, dn_id.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void increaseStaffID() {
		Path path = Paths.get(buildStaffID());
		try {
			Files.write(path, staff_id.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void increasePharmacyID() {
		Path path = Paths.get(buildPharmacyID());
		try {
			Files.write(path, phar_id.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
