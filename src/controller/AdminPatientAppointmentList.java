package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import entity.PatientAppointment;
import impl.AppointmentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import serviceimpl.AppointmentService;

public class AdminPatientAppointmentList implements Initializable {
	@FXML
	private JFXTextField patientName;
	@FXML
	private JFXTextField doctorName;
	@FXML
	private JFXDatePicker date;
	@FXML
	private TableView<PatientAppointment> table;
	private AppointmentService appointmentService;

	public void load() {
		List<PatientAppointment> list = appointmentService.getPatient("Admin");
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void print() throws IOException {
		List<PatientAppointment> list = table.getItems();

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		File file = fileChooser.showSaveDialog(table.getScene().getWindow());
		file.createNewFile();
		String path = file.getAbsolutePath();
		if (path.lastIndexOf(".txt") > 0) {
			for (PatientAppointment app : list) {
				String text = app.toString() + "\n";
				Files.write(Paths.get(file.getAbsolutePath()), text.getBytes(), StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		} else {
			String title = "PatientID,PatientName,DoctorID,DoctorName,Token,Status,Date,\n";
			String text = "";
			Files.write(Paths.get(file.getAbsolutePath()), title.getBytes(), StandardOpenOption.APPEND);
			for (PatientAppointment app : list) {
				text += app.toString() + "\n";
			}
			Files.write(Paths.get(file.getAbsolutePath()), text.getBytes(), StandardOpenOption.APPEND);

			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		}
	}

	public void clear() {
		patientName.clear();
		doctorName.clear();
		date.setValue(null);
		date.setPromptText("Choose Date");
	}

	public void findAll() {
		PatientAppointment appointment = new PatientAppointment();
		appointment.setPatientName(patientName.getText());
		appointment.setDoctorName(doctorName.getText());
		appointment.setDate(date.getValue());
		List<PatientAppointment> list = appointmentService.findgetPatient(appointment, "J");
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		appointmentService = new AppointmentServiceImpl();
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
	}
}
