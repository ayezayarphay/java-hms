package entity;

public class ESchedule {
	private int s_id;
	private int e_id;

	public ESchedule() {
		// TODO Auto-generated constructor stub
	}

	public ESchedule(int s_id, int e_id) {
		super();
		this.s_id = s_id;
		this.e_id = e_id;
	}

	public int getS_id() {
		return s_id;
	}

	public void setS_id(int s_id) {
		this.s_id = s_id;
	}

	public int getE_id() {
		return e_id;
	}

	public void setE_id(int e_id) {
		this.e_id = e_id;
	}

}
