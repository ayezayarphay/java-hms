package entity;

public class PharmacyPOSEntity {

	private String pharmacy_id;
	private String name;
	private int qty;
	private double price;
	private double amount;

	public PharmacyPOSEntity(String pharmacy_id, String name, int qty, double price, double amount) {
		super();

		this.pharmacy_id = pharmacy_id;
		this.name = name;
		this.qty = qty;
		this.price = price;
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PharmacyPOSEntity() {
	}

	public void setPharmacy_id(String pharmacy_id) {
		this.pharmacy_id = pharmacy_id;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPharmacy_id() {
		return pharmacy_id;
	}

	public int getQty() {
		return qty;
	}

	public double getPrice() {
		return price;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return pharmacy_id + "," + name + "," + qty + "," + price + "," + amount + "\n";
	}

}
