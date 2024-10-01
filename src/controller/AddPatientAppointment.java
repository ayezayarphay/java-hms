package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import entity.Appointment;
import entity.DN;
import entity.Patient;
import entity.PatientAppointment;
import entity.Staff;
import entity.StaffDNScuhdule;
import impl.AppointmentServiceImpl;
import impl.DNServiceImpl;
import impl.PatientServiceImpl;
import impl.ScheduleServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Callback;
import serviceimpl.AppointmentService;
import serviceimpl.DNService;
import serviceimpl.PatientService;
import serviceimpl.ScheduleService;
import utils.MemberVerify;

public class AddPatientAppointment implements Initializable {
	@FXML
	private Label title;
	@FXML
	private JFXTextField searchDnName;
	@FXML
	private JFXListView<String> listDN;
	@FXML
	private JFXTextField serachPatientName;
	@FXML
	private JFXListView<String> listPatient;
	@FXML
	private Label token;
	@FXML
	private Label time;
	@FXML
	private JFXDatePicker date;
	@FXML
	private JFXCheckBox available;
	@FXML
	private JFXButton add;
	private DNService dnService;
	private PatientService patientService;
	private ScheduleService scheduleService;
	private AppointmentService appointmentService;

	public void check() {
		if (searchDnName.getText().isEmpty()) {
			throw new HMSException("Please search and choice Doctor Name");
		}
		getDN();
		if (serachPatientName.getText().isEmpty()) {
			throw new HMSException("Please search and choice Patient Name");
		}
		getPatient();
		if (date.getValue() == null) {
			throw new HMSException("Please select appointment date");
		}
		if (token.getText().equals("0")) {
			throw new HMSException("Token cannot be zero");
		}
	}

	public DN getDN() {
		DN dn = dnService.findByidCode(searchDnName.getText());
		if (dn == null) {
			throw new HMSException("DN ID is invaild");
		}
		if (dn.isResign()) {
			throw new HMSException(dn.getName() + " is resign");
		}
		return dn;
	}

	public Patient getPatient() {
		Patient patient = patientService.findByIdCodeBoolean(serachPatientName.getText());
		if (patient == null) {
			throw new HMSException("patient ID is invaild");
		}
		return patient;
	}

	public void loadDN() {
		List<String> list = dnService.getDNInfo(searchDnName.getText());
		listDN.getItems().clear();
		listDN.getItems().addAll(list);
	}

	public void loadPatient() {
		List<String> list = patientService.findPatientInfo(serachPatientName.getText());
		listPatient.getItems().clear();
		listPatient.getItems().addAll(list);
	}

	public void add() {
		try {
			check();
			Staff staff = MemberVerify.getStaff();
			int staffID = staff.getId();
			String dnID = searchDnName.getText();
			DN dn = new DN();
			dn.setId_code(dnID);
			int dID = dnService.getID(dn);
			String patientID = serachPatientName.getText();
			Patient patient = new Patient();
			patient.setP_code(patientID);
			int pID = patientService.getID(patient);
			int tokenNum = Integer.parseInt(token.getText());
			LocalDate date1 = date.getValue();
			int status = available.isSelected() ? 1 : 0;
			Appointment appointment = new Appointment(status, date1, 0, pID, staffID, dID);
			PatientAppointment patientAppointment = new PatientAppointment(tokenNum);
			if (appointmentService.isBoken(serachPatientName.getText(), date.getValue(), searchDnName.getText())) {
				throw new HMSException("This patient is already got appointment");
			}
			appointmentService.insert(appointment, null, patientAppointment, "Patient");
			new Alert(AlertType.INFORMATION, "1 appointment added", ButtonType.OK).showAndWait();
			close();

		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void close() {
		searchDnName.getScene().getWindow().hide();
	}

	public void setToken() {
		LocalDate get = date.getValue();
		if (get != null) {
			int token = appointmentService.getToken(get, searchDnName.getText()) + 1;
			System.out.println(token);
			this.token.setText(String.valueOf(token));
		}

	}

	public int getDNID(String idcode) {

		DNService dnService = new DNServiceImpl();
		DN dn = new DN();
		dn.setId_code(idcode);
		return dnService.getID(dn);
	}

	public int getPatientID(String idcode) {
		PatientService patientService = new PatientServiceImpl();
		Patient patient = new Patient();
		patient.setP_code(idcode);
		return patientService.getID(patient);
	}

	final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		public DateCell call(final DatePicker datePicker) {
			return new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					if (LocalDate.now().isAfter(item)) {
						setDisable(true);
					}
					String a[] = scheduleService.getDayOfWeek(searchDnName.getText()).split(" ");
					for (String string : a) {
						if (item.getDayOfWeek().toString().equals(string)) {
							setDisable(true);
						}
					}

				}
			};
		}
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dnService = new DNServiceImpl();
		patientService = new PatientServiceImpl();
		scheduleService = new ScheduleServiceImpl();
		appointmentService = new AppointmentServiceImpl();
		date.setDisable(true);
		loadDN();
		loadPatient();
		searchDnName.textProperty().addListener((a, b, c) -> {
			loadDN();
			if (c.length() == 7) {
				date.setDisable(false);
			} else {
				date.setDisable(true);
			}
			if (c.isEmpty()) {
				token.setText("0");
				time.setText("Time");
			}
		});

		date.setDayCellFactory(dayCellFactory);
		serachPatientName.textProperty().addListener((a, b, c) -> {
			loadPatient();
		});
		listDN.setOnMouseClicked(e -> {
			int count = e.getClickCount();
			if (count == 2) {
				String get = listDN.getSelectionModel().getSelectedItem();
				String array[] = get.split(" ");
				searchDnName.setText(array[0]);
				setToken();
				StaffDNScuhdule staffDNScuhdule = scheduleService.getStartAndEndTime(searchDnName.getText());
				time.setText(staffDNScuhdule.getStartTime() + ":" + staffDNScuhdule.getEndTime());
			}
		});

		listPatient.setOnMouseClicked(e -> {
			int count = e.getClickCount();
			if (count == 2) {
				String get = listPatient.getSelectionModel().getSelectedItem();
				String array[] = get.split(" ");
				serachPatientName.setText(array[0]);
			}
		});
		date.valueProperty().addListener((a, b, c) -> {
			if (!searchDnName.getText().isEmpty()) {
				setToken();
			}
		});
	}
}
