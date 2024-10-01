package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;

import entity.Patient;
import impl.PatientServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serviceimpl.PatientService;
import utils.MenuSlider;
import utils.ObjectCarrior;

public class AdminPatientList implements Initializable {
	@FXML
	private JFXTextField seacrhPhone;
	@FXML
	private JFXTextField searchName;
	@FXML
	private JFXTextField searchID;
	@FXML
	private TableView<Patient> table;
	@FXML
	private JFXHamburger ham;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private VBox vb;
	private PatientService patientService;

	public void pie() {
		showViewPie();
	}

	public void showView() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("PatientDetails.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showViewPie() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("PatientPieChart.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		List<Patient> list = patientService.getAllData();
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void clear() {
		seacrhPhone.clear();
		searchName.clear();
		load();
	}

	public void findAll() {
		Patient patient = new Patient();
		patient.setP_code(searchID.getText());
		patient.setName(searchName.getText());
		patient.setFamily_phone(seacrhPhone.getText());
		List<Patient> list = patientService.findAll(patient);
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		patientService = new PatientServiceImpl();
		new MenuSlider().menuSlider(ham, drawer, vb, "AdminMenu.fxml", "Patient");
		load();
		searchID.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		searchName.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		seacrhPhone.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		MenuItem edit = new MenuItem("Details");
		table.setContextMenu(new ContextMenu(edit));
		edit.setOnAction(e -> {
			details();
		});
	}

	private void details() {
		Patient p = table.getSelectionModel().getSelectedItem();
		if (p.getP_code().equals("EP-1001")) {
			new Alert(AlertType.ERROR, "You cannot edit this patient", ButtonType.OK).showAndWait();
		} else {
			ObjectCarrior.setPatient(p);
			showView();
		}

	}
}
