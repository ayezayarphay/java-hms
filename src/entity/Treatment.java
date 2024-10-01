package entity;

import java.time.LocalDate;

import com.jfoenix.controls.JFXDatePicker;

import impl.PatientServiceImpl;
import impl.TreatmentServiceImpl;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import serviceimpl.PatientService;
import serviceimpl.TreatmentService;

public class Treatment {
	private int t_id;
	private CureType cureType;
	private String patient_case;
	private LocalDate date;
	private String leaveDate;
	private int r_id;
	private int p_id;
	private int dn_id;
	private int staff_id;
	private String p_idcode;
	private String dn_idcode;
	private String p_name;
	private String dn_name;
	private boolean iscash;
	private JFXDatePicker dateObj;

	public Treatment(CureType cureType, String patient_case, LocalDate date, String leaveDate, boolean iscash, int r_id,
			int p_id, int dn_id, int staff_id) {
		super();
		this.cureType = cureType;
		this.patient_case = patient_case;
		this.date = date;
		this.leaveDate = leaveDate;
		this.iscash = iscash;
		this.r_id = r_id;
		this.p_id = p_id;
		this.dn_id = dn_id;
		this.staff_id = staff_id;
	}

	public Treatment(CureType cureType, String patient_case, LocalDate date, String leaveDate, boolean iscash,
			String p_name, String dn_name) {
		super();
		this.cureType = cureType;
		this.patient_case = patient_case;
		this.date = date;
		this.leaveDate = leaveDate;
		this.iscash = iscash;
		this.p_name = p_name;
		this.dn_name = dn_name;
	}

	public Treatment(String patient_case, LocalDate date, String leaveDate, boolean iscash, int r_id, String p_idcode,
			String dn_idcode, String p_name, String dn_name) {
		super();
		this.patient_case = patient_case;
		this.date = date;
		this.leaveDate = leaveDate;
		this.iscash = iscash;
		this.r_id = r_id;
		this.p_idcode = p_idcode;
		this.dn_idcode = dn_idcode;
		this.p_name = p_name;
		this.dn_name = dn_name;
	}

	public boolean isIscash() {
		return iscash;
	}

	public Treatment(String patient_case, LocalDate date, String leaveDate, int r_id, String p_idcode, String dn_idcode,
			String p_name, String dn_name, JFXDatePicker dateObj) {
		super();
		this.patient_case = patient_case;
		this.date = date;
		this.leaveDate = leaveDate;
		this.r_id = r_id;
		this.p_idcode = p_idcode;
		this.dn_idcode = dn_idcode;
		this.p_name = p_name;
		this.dn_name = dn_name;
		this.dateObj = dateObj;
		dateObj.setDayCellFactory(dayCellFactory);
		dateObj.valueProperty().addListener((a, b, c) -> {
			PatientService patientService = new PatientServiceImpl();
			TreatmentService treatmentService = new TreatmentServiceImpl();
			Patient patient = new Patient();
			patient.setP_code(p_idcode);
			int pID = patientService.getID(patient);
			treatmentService.updateLeaveDate(dateObj.getValue(), pID, date);
		});
	}

	public void setIscash(boolean iscash) {
		this.iscash = iscash;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public JFXDatePicker getDateObj() {
		return dateObj;
	}

	public void setDateObj(JFXDatePicker dateObj) {
		this.dateObj = dateObj;
	}

	public String getP_idcode() {
		return p_idcode;
	}

	public void setP_idcode(String p_idcode) {
		this.p_idcode = p_idcode;
	}

	public String getDn_idcode() {
		return dn_idcode;
	}

	public void setDn_idcode(String dn_idcode) {
		this.dn_idcode = dn_idcode;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getDn_name() {
		return dn_name;
	}

	public void setDn_name(String dn_name) {
		this.dn_name = dn_name;
	}

	public int getT_id() {
		return t_id;
	}

	public void setT_id(int t_id) {
		this.t_id = t_id;
	}

	public CureType getCureType() {
		return cureType;
	}

	public void setCureType(CureType cureType) {
		this.cureType = cureType;
	}

	public String getPatient_case() {
		return patient_case;
	}

	public void setPatient_case(String patient_case) {
		this.patient_case = patient_case;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public int getR_id() {
		return r_id;
	}

	public void setR_id(int r_id) {
		this.r_id = r_id;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public int getDn_id() {
		return dn_id;
	}

	public void setDn_id(int dn_id) {
		this.dn_id = dn_id;
	}

	public Treatment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return p_idcode + "," + p_name + "," + dn_idcode + "," + dn_name + "," + date + ", " + leaveDate + ","
				+ patient_case + "," + +r_id;

	}

	final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		public DateCell call(final DatePicker datePicker) {
			return new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);

					if (dateObj.getValue().isAfter(item)) {
						setDisable(true);
					}
				}
			};
		}
	};
}
