package entity;

public class ShowPharmacyData {
	private String id_code;
	private String name;
	private double price;
	private int total;

	public ShowPharmacyData() {
		// TODO Auto-generated constructor stub
	}

	public ShowPharmacyData(String id_code, String name, double price, int total) {
		super();
		this.id_code = id_code;
		this.name = name;
		this.price = price;
		this.total = total;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public double getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return id_code + "," + name + "," + price + "," + total;
	}

}
