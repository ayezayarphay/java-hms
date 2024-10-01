package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entity.Treatment;
import impl.TreatmentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import serviceimpl.TreatmentService;

public class HospitalPatientSearch implements Initializable {
	@FXML
	private JFXTextField pname;
	@FXML
	private JFXTextField dname;
	@FXML
	private JFXTextField pcase;
	@FXML
	private TableView<Treatment> table;
	@FXML
	private VBox vb;
	private TreatmentService treatmentService;

	public void clear() {
		pname.clear();
		pcase.clear();
		dname.clear();
	}

	public void findAll() {
		Treatment treatment = new Treatment();
		treatment.setP_name(pname.getText());
		treatment.setDn_name(dname.getText());
		treatment.setPatient_case(pcase.getText());
		List<Treatment> list = treatmentService.findHospitalData(treatment);
		table.getItems().clear();
		table.getItems().addAll(list);

	}

	public void load() {

		List<Treatment> list = treatmentService.getHospitalData();
		table.getItems().clear();
		table.getItems().addAll(list);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		treatmentService = new TreatmentServiceImpl();
		load();
		pname.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		dname.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		pcase.textProperty().addListener((a, b, c) -> {
			findAll();
		});
	}
}
