package entity;

public class StaffDNScuhdule {
	private int id;
	private String idcode;
	private String name;
	private String dayOfWeek;
	private String startTime;
	private String endTime;

	public StaffDNScuhdule() {
		// TODO Auto-generated constructor stub
	}

	public StaffDNScuhdule(int id, String dayOfWeek, String startTime, String endTime) {
		super();
		this.id = id;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public StaffDNScuhdule(int id, String idcode, String name, String dayOfWeek, String startTime, String endTime) {
		super();
		this.id = id;
		this.idcode = idcode;
		this.name = name;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public StaffDNScuhdule(String dayOfWeek, String startTime, String endTime) {
		super();
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public StaffDNScuhdule(String idcode, String name, String dayOfWeek, String startTime, String endTime) {
		super();
		this.idcode = idcode;
		this.name = name;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIdcode() {
		return idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return idcode + "," + name + "," + dayOfWeek + "," + startTime + "," + endTime + "\n";
	}

}
