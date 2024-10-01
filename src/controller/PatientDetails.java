package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import entity.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import utils.ObjectCarrior;

public class PatientDetails implements Initializable {
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
	private Label gender;
	@FXML
	private JFXTextArea address;
	@FXML
	private JFXTextArea other_case;

	public void close() {
		name.getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Patient patient = ObjectCarrior.getPatient();
		if (patient != null) {
			id_code.setText(patient.getP_code());
			name.setText(patient.getName());
			age.setText(String.valueOf(patient.getAge()));
			gender.setText(patient.getSex().toString());
			phone.setText(patient.getFamily_phone());
			address.setText(patient.getAddress());
			other_case.setText(patient.getOther_case().equals("None") ? "" : patient.getOther_case());
			allergic.setText(patient.getAllergic().equals("None") ? "" : patient.getAllergic());
		}

	}

}
