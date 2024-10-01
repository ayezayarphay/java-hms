package entity;

import java.time.LocalDate;

public class TreatmentDetails {
	private int t_id;
	private int phar_id;
	private int qty;
	private LocalDate date;

	public TreatmentDetails(int t_id, int phar_id, int qty, LocalDate date) {
		super();
		this.t_id = t_id;
		this.phar_id = phar_id;
		this.qty = qty;
		this.date = date;
	}

	public TreatmentDetails() {
		// TODO Auto-generated constructor stub
	}

	public int getT_id() {
		return t_id;
	}

	public void setT_id(int t_id) {
		this.t_id = t_id;
	}

	public int getPhar_id() {
		return phar_id;
	}

	public void setPhar_id(int phar_id) {
		this.phar_id = phar_id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
