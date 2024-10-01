package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;

import entity.Treatment;
import impl.TreatmentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import serviceimpl.TreatmentService;
import utils.LoadClass;
import utils.MenuSlider;

public class AdminRoomList implements Initializable {
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
	@FXML
	private JFXHamburger ham;
	@FXML
	private JFXDrawer drawer;

	private TreatmentService treatmentService;

	public void clear() {
		pname.clear();
		pcase.clear();
		dname.clear();
	}

	public void change() {
		new LoadClass().showViewStage("ChangePrice.fxml");
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

	public void print() throws IOException {
		List<Treatment> list = table.getItems();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		File file = fileChooser.showSaveDialog(table.getScene().getWindow());
		file.createNewFile();
		String path = file.getAbsolutePath();
		if (path.lastIndexOf(".txt") > 0) {
			for (Treatment staff : list) {
				String text = staff.toString() + "\n";
				Files.write(Paths.get(file.getAbsolutePath()), text.getBytes(), StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		} else {
			String title = "PatientID,PatientName,DoctorID,DoctorName,StartDate,LeaveDate,PatientCase,Room\n";
			Files.write(Paths.get(file.getAbsolutePath()), title.getBytes(), StandardOpenOption.APPEND);
			for (Treatment staff : list) {
				String text = staff.toString() + "\n";
				Files.write(Paths.get(file.getAbsolutePath()), text.getBytes(), StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		treatmentService = new TreatmentServiceImpl();
		new MenuSlider().menuSlider(ham, drawer, vb, "AdminMenu.fxml", "Room");
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
