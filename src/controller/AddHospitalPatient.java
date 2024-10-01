package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import entity.CureType;
import entity.DN;
import entity.Patient;
import entity.Room;
import entity.Treatment;
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
import serviceimpl.DNService;
import serviceimpl.PatientService;
import serviceimpl.RoomService;
import serviceimpl.TreatmentService;
import utils.MemberVerify;
import utils.ObjectCarrior;

public class AddHospitalPatient implements Initializable {
	@FXML
	private JFXTextField pname;
	@FXML
	private JFXTextField dname;
	@FXML
	private JFXListView<String> listp;
	@FXML
	private JFXListView<String> listd;
	@FXML
	private JFXTextField pcase;
	@FXML
	private JFXDatePicker leaveDate;
	@FXML
	private Label chooseRoom;
	private DNService dnService;
	private PatientService patientService;
	private RoomService roomService;
	private TreatmentService treatmentService;

	public void check() {
		getPatient();
		getDN();
		if (pcase.getText().isEmpty()) {
			throw new HMSException("Please enter patient case");
		}
		if (chooseRoom.getText().equals("Choose Room")) {
			throw new HMSException("Please choose room");
		}
		if (leaveDate.getValue() == null) {
			throw new HMSException("Please choose leave date");
		}
		isHospital();
	}

	public void add() {
		try {
			check();
			String dnID = dname.getText();
			DN dn = new DN();
			dn.setId_code(dnID);
			int dID = dnService.getID(dn);
			String patientID = pname.getText();
			Patient patient = new Patient();
			patient.setP_code(patientID);
			int pID = patientService.getID(patient);
			int rID = Integer.parseInt(chooseRoom.getText());
			String case1 = pcase.getText();
			LocalDate lDate = leaveDate.getValue();
			Treatment treatment = new Treatment(CureType.Hospital_Patient, case1, LocalDate.now(), lDate.toString(),
					false, rID, pID, dID, MemberVerify.getStaff().getId());
			roomService.updateRoomStatus(rID, 1);
			treatmentService.insert(treatment);
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

	public void isHospital() {
		Treatment treatment = new Treatment();
		treatment.setP_idcode(pname.getText());
		boolean a = treatmentService.isHospital(treatment);
		if (a) {
			throw new HMSException("This patient is already in hospital");
		}
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
		treatmentService = new TreatmentServiceImpl();
		loadDN();
		loadPatient();
		leaveDate.setDayCellFactory(dayCellFactory);
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
