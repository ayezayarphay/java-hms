package entity;

import java.time.LocalDate;

import com.jfoenix.controls.JFXCheckBox;

public class RoomAppointment {
	private String p_id;
	private String p_name;
	private String dn_id;
	private String dn_name;
	private LocalDate date;
	private String pcase;
	private int roomNum;
	private int status;
	private int cancelD;
	private String floor;
	private JFXCheckBox check;

	public RoomAppointment() {
		// TODO Auto-generated constructor stub
	}

	public RoomAppointment(String p_id, String p_name, String dn_id, String dn_name, LocalDate date, int roomNum,
			int status) {
		super();
		this.p_id = p_id;
		this.p_name = p_name;
		this.dn_id = dn_id;
		this.dn_name = dn_name;
		this.date = date;
		this.roomNum = roomNum;
		this.status = status;
	}

	public RoomAppointment(String p_id, String p_name, String dn_id, String dn_name, LocalDate date, int roomNum,
			int status, JFXCheckBox check) {
		super();
		this.p_id = p_id;
		this.p_name = p_name;
		this.dn_id = dn_id;
		this.dn_name = dn_name;
		this.date = date;
		this.roomNum = roomNum;
		this.status = status;
		this.check = check;

	}

	public RoomAppointment(String p_id, String p_name, String dn_id, String dn_name, LocalDate date, int roomNum,
			int status, int cancelD) {
		super();
		this.p_id = p_id;
		this.p_name = p_name;
		this.dn_id = dn_id;
		this.dn_name = dn_name;
		this.date = date;
		this.roomNum = roomNum;
		this.status = status;
		this.cancelD = cancelD;
	}

	public JFXCheckBox getCheck() {
		return check;
	}

	public void setCheck(JFXCheckBox check) {
		this.check = check;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getPcase() {
		return pcase;
	}

	public void setPcase(String pcase) {
		this.pcase = pcase;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getDn_id() {
		return dn_id;
	}

	public void setDn_id(String dn_id) {
		this.dn_id = dn_id;
	}

	public String getDn_name() {
		return dn_name;
	}

	public void setDn_name(String dn_name) {
		this.dn_name = dn_name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCancelD() {
		return cancelD;
	}

	public void setCancelD(int cancelD) {
		this.cancelD = cancelD;
	}

	@Override
	public String toString() {
		return p_id + "," + p_name + "," + dn_id + "," + dn_name + "," + date + "," + roomNum + "," + status + ","
				+ cancelD;
	}

}
