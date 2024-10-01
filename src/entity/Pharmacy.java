package entity;

public class Pharmacy {
	private int phra_id;
	private String id_code;
	private String name;
	private PharmacyType pharmacyType;
	private double price;
	private int total_qty;

	public Pharmacy(int phra_id, String id_code, String name, PharmacyType pharmacyType, double price, int total_qty) {
		super();
		this.phra_id = phra_id;
		this.id_code = id_code;
		this.name = name;
		this.pharmacyType = pharmacyType;
		this.price = price;
		this.total_qty = total_qty;
	}

	public Pharmacy(String id_code, String name, PharmacyType pharmacyType, double price, int total_qty) {
		super();
		this.id_code = id_code;
		this.name = name;
		this.pharmacyType = pharmacyType;
		this.price = price;
		this.total_qty = total_qty;
	}

	public Pharmacy() {
		// TODO Auto-generated constructor stub
	}

	public int getPhra_id() {
		return phra_id;
	}

	public void setPhra_id(int phra_id) {
		this.phra_id = phra_id;
	}

	public String getId_code() {
		return id_code;
	}

	public void setId_code(String id_code) {
		this.id_code = id_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PharmacyType getPharmacyType() {
		return pharmacyType;
	}

	public void setPharmacyType(PharmacyType pharmacyType) {
		this.pharmacyType = pharmacyType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getTotal_qty() {
		return total_qty;
	}

	public void setTotal_qty(int total_qty) {
		this.total_qty = total_qty;
	}

	@Override
	public String toString() {
		return id_code + "," + name + "," + pharmacyType + "," + price + "," + total_qty;
	}

}
