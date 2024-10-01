package entity;

public class Patient {

	private int id;
	private String p_code;
	private String name;
	private int age;
	private Gender sex;
	private String family_phone;
	private String address;
	private String other_case;
	private String allergic;

	public Patient(int id, String p_code, String name, int age, Gender sex, String family_phone, String address,
			String other_case, String allergic) {
		super();
		this.id = id;
		this.p_code = p_code;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.family_phone = family_phone;
		this.address = address;
		this.other_case = other_case;
		this.allergic = allergic;
	}

	public Patient(String p_code, String name, int age, Gender sex, String family_phone, String address,
			String other_case, String allergic) {
		super();
		this.p_code = p_code;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.family_phone = family_phone;
		this.address = address;
		this.other_case = other_case;
		this.allergic = allergic;
	}

	public Patient() {
		// TODO Auto-generated constructor stub
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setSex(Gender sex) {
		this.sex = sex;
	}

	public void setFamily_phone(String family_phone) {
		this.family_phone = family_phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setOther_case(String other_case) {
		this.other_case = other_case;
	}

	public void setAllergic(String allergic) {
		this.allergic = allergic;
	}

	public String getP_code() {
		return p_code;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Gender getSex() {
		return sex;
	}

	public String getFamily_phone() {
		return family_phone;
	}

	public String getAddress() {
		return address;
	}

	public String getOther_case() {
		return other_case;
	}

	public String getAllergic() {
		return allergic;
	}

	@Override
	public String toString() {
		return p_code + "," + name + "," + age + "," + family_phone + "," + sex + "," + address + "," + other_case + ","
				+ allergic;

	}

}
