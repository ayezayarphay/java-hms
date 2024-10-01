package entity;

import java.time.LocalDate;

public class SaleDetails {
	private int saleId;
	private int pharmacyId;
	private int qty;
	private LocalDate date;

	public SaleDetails(int saleId, int pharmacyId, int qty, LocalDate date) {
		super();
		this.saleId = saleId;
		this.pharmacyId = pharmacyId;
		this.qty = qty;
		this.date = date;
	}

	public SaleDetails() {
		// TODO Auto-generated constructor stub
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public int getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(int pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}
