package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serviceimpl.AppointmentService;
import serviceimpl.DNService;
import serviceimpl.PatientService;
import serviceimpl.RoomService;
import serviceimpl.TreatmentService;
import utils.MemberVerify;
import utils.ObjectCarrior;

public class AddTemporyPatient implements Initializable {
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
	private Label date;
	private DNService dnService;
	private PatientService patientService;
	private RoomService roomService;
	private TreatmentService treatmentService;
	AppointmentService appointmentService;

	public void check() {
		if (pcase.getText().isEmpty()) {
			throw new HMSException("Please enter patient case");
		}
		if (chooseRoom.getText().equals("Choose Room")) {
			throw new HMSException("Please choose room");
		}
	}

	public void add() {
		try {
			check();
			DN dID = dnService.findByidCode(dnID.getText());
			Patient pID = patientService.findByIdCodeBoolean(pid.getText());
			int rID = Integer.parseInt(chooseRoom.getText());
			String case1 = pcase.getText();
			Treatment treatment = new Treatment(CureType.Tempory_Patient, case1, LocalDate.now(), "None", false, rID,
					pID.getId(), dID.getId(), MemberVerify.getStaff().getId());
			roomService.updateRoomStatus(rID, 1);
			appointmentService.updateStatus(pid.getText(), LocalDate.parse(date.getText()), 1, dnID.getText());
			treatmentService.insert(treatment);
			new Alert(AlertType.INFORMATION, "1 data added", ButtonType.OK).showAndWait();
			close();

		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void close() {
		chooseRoom.getScene().getWindow().hide();
	}

	public void showRoom() {
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ChooseRoom.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Choose Room");
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
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dnService = new DNServiceImpl();
		patientService = new PatientServiceImpl();
		roomService = new RoomServiceImpl();
		treatmentService = new TreatmentServiceImpl();
		appointmentService = new AppointmentServiceImpl();
		Treatment t = ObjectCarrior.getTreatment();
		if (t != null) {
			pid.setText(t.getP_idcode());
			pname.setText(t.getP_name());
			dnID.setText(t.getDn_idcode());
			dnName.setText(t.getDn_name());
			date.setText(t.getDate().toString());
			ObjectCarrior.setTreatment(null);
		}

	}
}
