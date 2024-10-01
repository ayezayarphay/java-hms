package entity;

import java.time.LocalDate;

public class DN {

	private int id;
	private String id_code;
	private String name;
	private byte image[];
	private Department department;
	private DNPosition position;
	private String father_name;
	private String mother_name;
	private Gender sex;
	private LocalDate dob;
	private String nrc;
	private String degree;
	private String address;
	private String personal_skill;
	private LocalDate start_work_date;
	private String previous_hospital;
	private double salary;
	private boolean isResign;
	private String resign_date;

	public DN() {
		// TODO Auto-generated constructor stub
	}

	public DN(int id, String id_code, String name, byte[] image, Department department, DNPosition position,
			String father_name, String mother_name, Gender sex, LocalDate dob, String nrc, String degree,
			String address, String personal_skill, LocalDate start_work_date, String previous_hospital, double salary,
			boolean isResign, String resign_date) {
		super();
		this.id = id;
		this.id_code = id_code;
		this.name = name;
		this.image = image;
		this.department = department;
		this.position = position;
		this.father_name = father_name;
		this.mother_name = mother_name;
		this.sex = sex;
		this.dob = dob;
		this.nrc = nrc;
		this.degree = degree;
		this.address = address;
		this.personal_skill = personal_skill;
		this.start_work_date = start_work_date;
		this.previous_hospital = previous_hospital;
		this.salary = salary;
		this.isResign = isResign;
		this.resign_date = resign_date;
	}

	public DN(String id_code, String name, byte[] image, Department department, DNPosition position, String father_name,
			String mother_name, Gender sex, LocalDate dob, String nrc, String degree, String address,
			String personal_skill, LocalDate start_work_date, String previous_hospital, double salary, boolean isResign,
			String resign_date) {
		super();
		this.id_code = id_code;
		this.name = name;
		this.image = image;
		this.department = department;
		this.position = position;
		this.father_name = father_name;
		this.mother_name = mother_name;
		this.sex = sex;
		this.dob = dob;
		this.nrc = nrc;
		this.degree = degree;
		this.address = address;
		this.personal_skill = personal_skill;
		this.start_work_date = start_work_date;
		this.previous_hospital = previous_hospital;
		this.salary = salary;
		this.isResign = isResign;
		this.resign_date = resign_date;
	}

	public String getDegree() {
		return degree;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId_code(String id_code) {
		this.id_code = id_code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void setPosition(DNPosition position) {
		this.position = position;
	}

	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}

	public void setMother_name(String mother_name) {
		this.mother_name = mother_name;
	}

	public void setSex(Gender sex) {
		this.sex = sex;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPersonal_skill(String personal_skill) {
		this.personal_skill = personal_skill;
	}

	public void setStart_work_date(LocalDate start_work_date) {
		this.start_work_date = start_work_date;
	}

	public void setPrevious_hospital(String previous_hospital) {
		this.previous_hospital = previous_hospital;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public void setResign(boolean isResign) {
		this.isResign = isResign;
	}

	public void setResign_date(String resign_date) {
		this.resign_date = resign_date;
	}

	public String getId_code() {
		return id_code;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public byte[] getImage() {
		return image;
	}

	public Department getDepartment() {
		return department;
	}

	public DNPosition getPosition() {
		return position;
	}

	public String getFather_name() {
		return father_name;
	}

	public String getMother_name() {
		return mother_name;
	}

	public Gender getSex() {
		return sex;
	}

	public LocalDate getDob() {
		return dob;
	}

	public String getNrc() {
		return nrc;
	}

	public String getAddress() {
		return address;
	}

	public String getPersonal_skill() {
		return personal_skill;
	}

	public LocalDate getStart_work_date() {
		return start_work_date;
	}

	public String getPrevious_hospital() {
		return previous_hospital;
	}

	public double getSalary() {
		return salary;
	}

	public boolean isResign() {
		return isResign;
	}

	public String getResign_date() {
		return resign_date;
	}

	@Override
	public String toString() {
		return id_code + "," + name + "," + department + "," + position + "," + father_name + "," + mother_name + ","
				+ sex + "," + dob + "," + nrc + "," + "\"" + degree + "\"" + "," + "\"" + address + "\"" + "," + "\""
				+ personal_skill + "\"" + "," + start_work_date + "," + "\"" + previous_hospital + "\"" + "," + salary
				+ "," + isResign + "," + resign_date + "\n";
	}

}
