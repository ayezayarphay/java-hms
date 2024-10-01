package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import entity.CureType;
import entity.DN;
import entity.Patient;
import entity.Room;
import entity.Treatment;
import impl.AppointmentServiceImpl;
import impl.DNServiceImpl;
import impl.PatientServiceImpl;
import impl.RoomServiceImpl;
import impl.TreatmentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import serviceimpl.AppointmentService;
import serviceimpl.DNService;
import serviceimpl.PatientService;
import serviceimpl.RoomService;
import serviceimpl.TreatmentService;
import utils.MemberVerify;
import utils.ObjectCarrior;

public class AddAppointmentPatientSearch implements Initializable {
	@FXML
	private Label pid;
	@FXML
	private Label pname;
	@FXML
	private Label dnID;
	@FXML
	private Label dnName;
	@FXML
	private JFXTextField pcase;
	@FXML
	private Label chooseRoom;
	@FXML
	private JFXDatePicker date;
	private DNService dnService;
	private PatientService patientService;
	private RoomService roomService;
	private TreatmentService treatmentService;
	AppointmentService appointmentService;

	public void check() {
		if (pcase.getText().isEmpty()) {
			throw new HMSException("Please enter patient case");
		}
		if (date.getValue() == null) {
			throw new HMSException("Please choose leave date");
		}
	}

	public void add() {
		try {
			check();
			String dnID = this.dnID.getText();
			DN dn = new DN();
			dn.setId_code(dnID);
			int dID = dnService.getID(dn);
			String patientID = pid.getText();
			Patient patient = new Patient();
			patient.setP_code(patientID);
			int pID = patientService.getID(patient);
			int rID = Integer.parseInt(chooseRoom.getText());
			String case1 = pcase.getText();
			LocalDate lDate = date.getValue();
			Treatment treatment = new Treatment(CureType.Hospital_Patient, case1, LocalDate.now(), lDate.toString(),
					false, rID, pID, dID, MemberVerify.getStaff().getId());
			roomService.updateRoomStatus(rID, 1);
			treatmentService.insert(treatment);
			appointmentService.updateStatus(patientID, lDate, 1, dnID);
			new Alert(AlertType.INFORMATION, "1 data added", ButtonType.OK).showAndWait();
			close();
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}

	}

	public void showRoom() {
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ChooseHospitalRoom.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Choose Room");
			stage.setWidth(1050);
			stage.setHeight(500);
			stage.setScene(new Scene(root));
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		Room room = ObjectCarrior.getRoomNumber();
		if (room != null) {
			chooseRoom.setText(String.format("%03d", room.getId()));
			ObjectCarrior.setRoom(null);
		}
	}

	public void close() {
		chooseRoom.getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dnService = new DNServiceImpl();
		patientService = new PatientServiceImpl();
		roomService = new RoomServiceImpl();
		treatmentService = new TreatmentServiceImpl();
		appointmentService = new AppointmentServiceImpl();
		Treatment t = ObjectCarrior.getTreatment();
		date.setDayCellFactory(dayCellFactory);
		if (t != null) {
			pid.setText(t.getP_idcode());
			pname.setText(t.getP_name());
			dnID.setText(t.getDn_idcode());
			dnName.setText(t.getDn_name());
			chooseRoom.setText(t.getR_id() + "");
			ObjectCarrior.setTreatment(null);
		}

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
