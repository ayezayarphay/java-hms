package utils;

import java.util.List;

import entity.DN;
import entity.Patient;
import entity.Pharmacy;
import entity.PharmacyPOSEntity;
import entity.PharmacySaleHistory;
import entity.Room;
import entity.Staff;
import entity.StaffDNScuhdule;
import entity.Treatment;
import javafx.scene.control.Label;

public class ObjectCarrior {
	private static Patient patient;
	private static DN dn;
	private static Staff staff;
	private static List<PharmacyPOSEntity> list;
	private static String id_code;
	private static String passwordTitle;
	private static boolean isPasswordChange;
	private static StaffDNScuhdule staffDNScuhdule;
	private static Room room;
	private static Treatment treatment;
	private static List<Label> labelList;
	private static long duration;
	private static Pharmacy pharmacy;
	private static List<PharmacySaleHistory> histories;

	public static List<PharmacySaleHistory> getHistories() {
		return histories;
	}

	public static void setHistories(List<PharmacySaleHistory> histories) {
		ObjectCarrior.histories = histories;
	}

	public static Pharmacy getPharmacy() {
		return pharmacy;
	}

	public static void setPharmacy(Pharmacy pharmacy) {
		ObjectCarrior.pharmacy = pharmacy;
	}

	public static long getDuration() {
		return duration;
	}

	public static void setDuration(long duration) {
		ObjectCarrior.duration = duration;
	}

	public static List<Label> getLabelList() {
		return labelList;
	}

	public static void setLabelList(List<Label> labelList) {
		ObjectCarrior.labelList = labelList;
	}

	public static Room getRoom() {
		return room;
	}

	public static void setRoom(Room room) {
		ObjectCarrior.room = room;
	}

	public static Treatment getTreatment() {
		return treatment;
	}

	public static void setTreatment(Treatment treatment) {
		ObjectCarrior.treatment = treatment;
	}

	public static Room getRoomNumber() {
		return room;
	}

	public static void setRoomNumber(Room room) {
		ObjectCarrior.room = room;
	}

	public static StaffDNScuhdule getStaffDNScuhdule() {
		return staffDNScuhdule;
	}

	public static void setStaffDNScuhdule(StaffDNScuhdule staffDNScuhdule) {
		ObjectCarrior.staffDNScuhdule = staffDNScuhdule;
	}

	public static boolean isPasswordChange() {
		return isPasswordChange;
	}

	public static void setPasswordChange(boolean isPasswordChange) {
		ObjectCarrior.isPasswordChange = isPasswordChange;
	}

	public static String getPasswordTitle() {
		return passwordTitle;
	}

	public static void setPasswordTitle(String passwordTitle) {
		ObjectCarrior.passwordTitle = passwordTitle;
	}

	public static String getId_code() {
		return id_code;
	}

	public static void setId_code(String id_code) {
		ObjectCarrior.id_code = id_code;
	}

	public static List<PharmacyPOSEntity> getList() {
		return list;
	}

	public static void setList(List<PharmacyPOSEntity> list) {
		ObjectCarrior.list = list;
	}

	public static Staff getStaff() {
		return staff;
	}

	public static void setStaff(Staff staff) {
		ObjectCarrior.staff = staff;
	}

	public static void setDn(DN dn) {
		ObjectCarrior.dn = dn;
	}

	public static DN getDn() {
		return dn;
	}

	public static Patient getPatient() {
		return patient;
	}

	public static void setPatient(Patient patient1) {
		patient = patient1;
	}

}
