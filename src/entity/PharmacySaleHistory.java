package entity;

import java.time.LocalDate;

public class PharmacySaleHistory {
	private String staffID;
	private String staffName;
	private String patientID;
	private String phatID;
	private String pharName;
	private LocalDate date;
	private int qty;
	private double price;
	private double total;

	public PharmacySaleHistory() {
		// TODO Auto-generated constructor stub
	}

	public PharmacySaleHistory(String staffID, String staffName, String patientID, String phatID, String pharName,
			LocalDate date, int qty, double price, double total) {
		super();
		this.staffID = staffID;
		this.staffName = staffName;
		this.patientID = patientID;
		this.phatID = phatID;
		this.pharName = pharName;
		this.date = date;
		this.qty = qty;
		this.price = price;
		this.total = total;
	}

	public PharmacySaleHistory(LocalDate date, double total) {
		super();
		this.date = date;
		this.total = total;
	}

	public PharmacySaleHistory(String staffID, String patientID, String phatID, String pharName, LocalDate date,
			int qty, double price) {
		super();
		this.staffID = staffID;
		this.patientID = patientID;
		this.phatID = phatID;
		this.pharName = pharName;
		this.date = date;
		this.qty = qty;
		this.price = price;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public String getPhatID() {
		return phatID;
	}

	public void setPhatID(String phatID) {
		this.phatID = phatID;
	}

	public String getPharName() {
		return pharName;
	}

	public void setPharName(String pharName) {
		this.pharName = pharName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
