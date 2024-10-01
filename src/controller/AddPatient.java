package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import entity.Gender;
import entity.Patient;
import impl.PatientServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import serviceimpl.PatientService;
import utils.IDGenerator;
import utils.ObjectCarrior;

public class AddPatient implements Initializable {
	@FXML
	private Label id_code;
	@FXML
	private JFXTextField name;
	@FXML
	private JFXTextField age;
	@FXML
	private JFXTextField phone;
	@FXML
	private JFXTextField allergic;
	@FXML
	private JFXRadioButton male;
	@FXML
	private JFXRadioButton female;
	@FXML
	private JFXTextArea address;
	@FXML
	private JFXTextArea other_case;
	@FXML
	private Label title;
	@FXML
	private JFXButton add;

	private PatientService patientService;
	private int p_id = 0;

	public void check() {
		String nameT = name.getText();
		if (nameT.isEmpty()) {
			throw new HMSException("Please enter patient name");
		}
		if (!nameT.matches("[A-Za-z\\s+]{1,}")) {
			throw new HMSException("Name does not allow special characters");
		}
		String ageT = age.getText();
		if (ageT.isEmpty()) {
			throw new HMSException("Please enter patient age");
		}
		if (!ageT.matches("[0-9]{1,}")) {
			throw new HMSException("Age must be number");
		}
		String phoneT = phone.getText();
		if (phoneT.isEmpty()) {
			throw new HMSException("Please enter patient phone or family phone");
		}
		if (!phoneT.matches("[0-9]{1,}")) {
			throw new HMSException("phone number must be number");
		}
		if (!(phoneT.length() >= 9 && phoneT.length() <= 11)) {
			throw new HMSException("Please enter phone number in correct format");
		}

		String addressT = address.getText();
		if (addressT.isEmpty()) {
			throw new HMSException("Please enter patient address");
		}

	}

	public void add() {
		try {
			check();
			String other = other_case.getText();
			String all = allergic.getText();
			if (p_id == 0) {
				Patient patient = new Patient(id_code.getText(), name.getText(), Integer.parseInt(age.getText()),
						male.isSelected() ? Gender.Male : Gender.Female, phone.getText(), address.getText(),
						other.isEmpty() ? "None" : other, all.isEmpty() ? "None" : all);
				patientService.insert(patient);
				new Alert(AlertType.INFORMATION, "1 patient added.", ButtonType.OK).showAndWait();
				IDGenerator.increaseID();
			} else {
				Patient patient = new Patient(p_id, id_code.getText(), name.getText(), Integer.parseInt(age.getText()),
						male.isSelected() ? Gender.Male : Gender.Female, phone.getText(), address.getText(),
						other.isEmpty() ? "None" : other, all.isEmpty() ? "None" : all);
				patientService.update(patient);
				new Alert(AlertType.INFORMATION, "1 patient updated.", ButtonType.OK).showAndWait();
			}
			close();
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();

		}

	}

	public void close() {
		other_case.getScene().getWindow().hide();
		ObjectCarrior.setPatient(null);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		patientService = new PatientServiceImpl();
		id_code.setText(IDGenerator.generatePatientID());
		setData();
	}

	public void setData() {
		Patient patient = ObjectCarrior.getPatient();
		if (patient != null) {
			title.setText("Edit Patient");
			add.setText("Edit");
			p_id = patientService.getID(patient);
			patient = patientService.findByID(p_id);
			id_code.setText(patient.getP_code());
			name.setText(patient.getName());
			age.setText(String.valueOf(patient.getAge()));
			if (patient.getSex().equals(Gender.Male)) {
				male.setSelected(true);
			} else {
				female.setSelected(true);
			}
			phone.setText(patient.getFamily_phone());
			address.setText(patient.getAddress());
			other_case.setText(patient.getOther_case().equals("None") ? "" : patient.getOther_case());
			allergic.setText(patient.getAllergic().equals("None") ? "" : patient.getAllergic());
		}

	}
}
