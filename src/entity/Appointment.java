package entity;

import java.time.LocalDate;

public class Appointment {
	private int app_id;
	private int status;
	private LocalDate date;
	private int cash;
	private int p_id;
	private int staff_id;
	private int dn_id;

	public Appointment(int app_id, int status, LocalDate date, int p_id, int staff_id, int dn_id) {
		super();
		this.app_id = app_id;
		this.status = status;
		this.date = date;
		this.p_id = p_id;
		this.staff_id = staff_id;
		this.dn_id = dn_id;
	}

	public Appointment(int status, LocalDate date, int cash, int p_id, int staff_id, int dn_id) {
		super();
		this.status = status;
		this.date = date;
		this.cash = cash;
		this.p_id = p_id;
		this.staff_id = staff_id;
		this.dn_id = dn_id;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getApp_id() {
		return app_id;
	}

	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public int getDn_id() {
		return dn_id;
	}

	public void setDn_id(int dn_id) {
		this.dn_id = dn_id;
	}

}
