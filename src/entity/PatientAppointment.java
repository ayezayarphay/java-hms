package entity;

import java.time.LocalDate;

import com.jfoenix.controls.JFXCheckBox;

public class PatientAppointment {
	private int app_id;
	private int token;
	private LocalDate date;
	private String p_id;
	private String patientName;
	private String dn_id;
	private String doctorName;
	private int status;
	private JFXCheckBox check;

	public PatientAppointment() {
		// TODO Auto-generated constructor stub
	}

	public PatientAppointment(int token) {
		super();
		this.token = token;
	}

	public PatientAppointment(int app_id, int token) {
		super();
		this.app_id = app_id;
		this.token = token;
	}

	public PatientAppointment(int token, LocalDate date, String p_id, String patientName, String dn_id,
			String doctorName, int status) {
		super();
		this.token = token;
		this.date = date;
		this.p_id = p_id;
		this.patientName = patientName;
		this.dn_id = dn_id;
		this.doctorName = doctorName;
		this.status = status;
	}

	public PatientAppointment(int token, LocalDate date, String p_id, String patientName, String dn_id,
			String doctorName, JFXCheckBox check) {
		super();
		this.token = token;
		this.date = date;
		this.p_id = p_id;
		this.patientName = patientName;
		this.dn_id = dn_id;
		this.doctorName = doctorName;
		this.check = check;

	}

	public JFXCheckBox getCheck() {
		return check;
	}

	public void setCheck(JFXCheckBox check) {
		this.check = check;
	}

	public String getDn_id() {
		return dn_id;
	}

	public void setDn_id(String dn_id) {
		this.dn_id = dn_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getApp_id() {
		return app_id;
	}

	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Override
	public String toString() {
		return p_id + "," + patientName + "," + dn_id + "," + doctorName + "," + token + "," + status + "," + date;
	}

}
