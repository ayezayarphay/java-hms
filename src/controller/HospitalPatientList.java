package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import entity.Treatment;
import impl.TreatmentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serviceimpl.TreatmentService;
import utils.ObjectCarrior;

public class HospitalPatientList implements Initializable {
	@FXML
	private JFXTextField pname;
	@FXML
	private JFXTextField dname;
	@FXML
	private JFXDatePicker date;
	@FXML
	private TableView<Treatment> table;
	private TreatmentService treatmentService;

	public void load() {
		List<Treatment> list = treatmentService.getHospitalData();
		table.getItems().clear();
		table.getItems().addAll(list);

	}

	public void showView() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddHospitalPatient.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add() {
		showView();
	}

	public void clear() {
		pname.clear();
		date.setValue(null);
		date.setPromptText("Date");
		dname.clear();
	}

	public void findAll() {
		Treatment treatment = new Treatment();
		treatment.setP_name(pname.getText());
		treatment.setDn_name(dname.getText());
		treatment.setDate(date.getValue());
		List<Treatment> list = treatmentService.findHospitalDatatreatment(treatment);
		table.getItems().clear();
		table.getItems().addAll(list);

	}

	public void showPaymentView() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("HospitalPayment.fxml"));
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
		treatmentService = new TreatmentServiceImpl();
		load();
		pname.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		dname.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		date.valueProperty().addListener((a, b, c) -> {
			findAll();
		});
		table.setOnMouseClicked(e -> {
			int count = e.getClickCount();
			if (count == 2) {
				Treatment t = table.getSelectionModel().getSelectedItem();
				if (t == null) {
					new Alert(AlertType.WARNING, "Please add data or select data", ButtonType.OK).showAndWait();
				} else {
					ObjectCarrior.setTreatment(t);
					showPaymentView();
				}
			}
		});
	}
}
