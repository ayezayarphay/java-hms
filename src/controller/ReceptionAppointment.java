package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import entity.Appointment;
import entity.DN;
import entity.Patient;
import entity.Room;
import entity.RoomAppointment;
import entity.Staff;
import impl.AppointmentServiceImpl;
import impl.DNServiceImpl;
import impl.PatientServiceImpl;
import impl.RoomServiceImpl;
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
import serviceimpl.RoomService;
import utils.MemberVerify;
import utils.ObjectCarrior;

public class ReceptionAppointment implements Initializable {
	@FXML
	private JFXTextField pname;
	@FXML
	private JFXTextField dname;
	@FXML
	private JFXListView<String> listp;
	@FXML
	private JFXListView<String> listd;

	@FXML
	private JFXDatePicker appDate;
	@FXML
	private Label room;
	private DNService dnService;
	private PatientService patientService;
	private RoomService roomService;
	private AppointmentService appointmentService;

	public void check() {
		getPatient();
		getDN();

		if (appDate.getValue() == null) {
			throw new HMSException("Please choose appointment date");
		}
		isAppointment();
	}

	public void load() {
		Room room = ObjectCarrior.getRoomNumber();
		if (room != null) {
			this.room.setText(String.format("%03d", room.getId()));
			ObjectCarrior.setRoom(null);
		}

	}

	public void isAppointment() {
		boolean a = appointmentService.isAppointment(pname.getText());
		if (a) {
			throw new HMSException("This patient is already register");
		}
	}

	public DN getDN() {
		DN dn = dnService.findByidCode(dname.getText());
		if (dn == null) {
			throw new HMSException("DN ID is invaild");
		}
		if (dn.isResign()) {
			throw new HMSException(dn.getName() + " is resign");
		}
		return dn;
	}

	public Patient getPatient() {
		Patient patient = patientService.findByIdCodeBoolean(pname.getText());
		if (patient == null) {
			throw new HMSException("patient ID is invaild");
		}
		return patient;
	}

	public void add() {
		try {
			check();
			Staff staff = MemberVerify.getStaff();
			int staffID = staff.getId();
			String dnID = dname.getText();
			DN dn = new DN();
			dn.setId_code(dnID);
			int dID = dnService.getID(dn);
			String patientID = pname.getText();
			Patient patient = new Patient();
			patient.setP_code(patientID);
			int pID = patientService.getID(patient);
			LocalDate date1 = appDate.getValue();
			Appointment appointment = new Appointment(0, date1, 0, pID, staffID, dID);
			RoomAppointment roomAppointment = new RoomAppointment();
			roomAppointment.setRoomNum(Integer.parseInt(room.getText()));
			appointmentService.insert(appointment, roomAppointment, null, "Room");
			roomService.updateRoomStatus(Integer.parseInt(room.getText()), 2);
			new Alert(AlertType.INFORMATION, "1 data added", ButtonType.OK).showAndWait();
			close();
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void close() {
		listd.getScene().getWindow().hide();
	}

	public void loadDN() {
		List<String> list = dnService.getDNInfo(dname.getText());
		listd.getItems().clear();
		listd.getItems().addAll(list);
	}

	public void loadPatient() {
		List<String> list = patientService.findPatientInfo(pname.getText());
		listp.getItems().clear();
		listp.getItems().addAll(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dnService = new DNServiceImpl();
		patientService = new PatientServiceImpl();
		roomService = new RoomServiceImpl();
		appointmentService = new AppointmentServiceImpl();
		load();
		loadDN();
		loadPatient();
		appDate.setDayCellFactory(dayCellFactory);
		pname.textProperty().addListener((a, b, c) -> {
			loadPatient();
		});

		dname.textProperty().addListener((a, b, c) -> {
			loadDN();
		});

		listp.setOnMouseClicked(e -> {
			int count = e.getClickCount();
			if (count == 2) {
				String get = listp.getSelectionModel().getSelectedItem();
				String array[] = get.split(" ");
				pname.setText(array[0]);
			}
		});
		listd.setOnMouseClicked(e -> {
			int count = e.getClickCount();
			if (count == 2) {
				String get = listd.getSelectionModel().getSelectedItem();
				String array[] = get.split(" ");
				dname.setText(array[0]);
			}
		});

	}

	final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		public DateCell call(final DatePicker datePicker) {
			return new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);

					if (LocalDate.now().isEqual(item) || LocalDate.now().isAfter(item)) {
						setDisable(true);
					}
				}
			};
		}
	};
}
