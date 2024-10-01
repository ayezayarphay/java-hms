package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;

import entity.PatientAppointment;
import entity.Treatment;
import impl.AppointmentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serviceimpl.AppointmentService;
import utils.MenuSlider;
import utils.ObjectCarrior;

public class PatientAppointmentList implements Initializable {
	@FXML
	private VBox mainVb;
	@FXML
	private JFXHamburger ham;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private JFXTextField patientName;
	@FXML
	private JFXTextField doctorName;
	@FXML
	private JFXDatePicker date;
	@FXML
	private TableView<PatientAppointment> table;
	private AppointmentService appointmentService;

	public void showView() {

		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddPatientAppointment.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void load() {
		List<PatientAppointment> list = appointmentService.getPatient("JFX");
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void findAll() {
		PatientAppointment appointment = new PatientAppointment();
		appointment.setPatientName(patientName.getText());
		appointment.setDoctorName(doctorName.getText());
		appointment.setDate(date.getValue());
		List<PatientAppointment> list = appointmentService.findgetPatient(appointment, "JFX");
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void add() {
		showView();
	}

	public void clear() {
		patientName.clear();
		doctorName.clear();
		date.setValue(null);
		date.setPromptText("Choose Date");
	}

	public void showView2() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddTemporyPatient.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		appointmentService = new AppointmentServiceImpl();
		new MenuSlider().menuSlider(ham, drawer, mainVb, "ReceptionMenu.fxml", "Appointment");
		load();
		patientName.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		doctorName.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		date.valueProperty().addListener((a, b, c) -> {
			findAll();
		});
		table.setOnMouseClicked(e -> {
			int a = e.getClickCount();
			if (a == 2) {
				PatientAppointment p = table.getSelectionModel().getSelectedItem();
				if (p == null) {
					new Alert(AlertType.ERROR, "Please add data", ButtonType.OK).showAndWait();
				} else if (!p.getCheck().isSelected()) {
					new Alert(AlertType.ERROR, "Make sure that patient is arrive", ButtonType.OK).showAndWait();
				} else if (!p.getDate().equals(LocalDate.now())) {
					new Alert(AlertType.ERROR, "This patient can show on " + p.getDate(), ButtonType.OK).showAndWait();
				} else {
					Treatment treatment = new Treatment();
					treatment.setP_idcode(p.getP_id());
					treatment.setP_name(p.getPatientName());
					treatment.setDn_idcode(p.getDn_id());
					treatment.setDn_name(p.getDoctorName());
					treatment.setDate(p.getDate());
					ObjectCarrior.setTreatment(treatment);
					showView2();
				}
			}
		});
	}

}
