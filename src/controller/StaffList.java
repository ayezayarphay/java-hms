package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import entity.Department;
import entity.Staff;
import entity.StaffPosition;
import impl.StaffServiceImpl;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serviceimpl.StaffService;
import utils.ObjectCarrior;

public class StaffList implements Initializable {
	@FXML
	private JFXTextField idcode;
	@FXML
	private JFXTextField name;
	@FXML
	private JFXComboBox<Department> department;
	@FXML
	private JFXComboBox<StaffPosition> position;
	@FXML
	private TableView<Staff> table;
	private StaffService staffService;

	public void showView() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddStaff.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void report() throws IOException {
		List<Staff> list = table.getItems();
		List<Staff> getData = new ArrayList<Staff>();
		for (Staff staff : list) {
			Staff stf = staffService.findByIdCode(staff.getId_code());
			getData.add(stf);
		}
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		File file = fileChooser.showSaveDialog(idcode.getScene().getWindow());
		file.createNewFile();
		String path = file.getAbsolutePath();
		if (path.lastIndexOf(".txt") > 0) {
			for (Staff staff : getData) {
				String text = staff.toString() + "\n";
				Files.write(Paths.get(file.getAbsolutePath()), text.getBytes(), StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		} else {
			String title = "ID Code,Name,Department,Position,Father Name,Mother Name,Sex,Dob,NRC,Degree,Address,Start Work Date,Previous Work,Salary,Is Resing, Resign Date,\n";
			Files.write(Paths.get(file.getAbsolutePath()), title.getBytes(), StandardOpenOption.APPEND);
			for (Staff staff : getData) {
				Files.write(Paths.get(file.getAbsolutePath()), staff.toString().getBytes(), StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		}
	}

	public void add() {
		showView();
	}

	public void clear() {
		idcode.clear();
		name.clear();
		department.setValue(null);
		position.setValue(null);
		department.setPromptText("Department");
		position.setPromptText("Position");
		load();
	}

	public void findAll() {
		Staff staff = new Staff();
		staff.setId_code(idcode.getText());
		staff.setName(name.getText());
		staff.setDepartment(department.getValue());
		staff.setPosition(position.getValue());

		List<Staff> list = staffService.findAll(staff);
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void load() {
		List<Staff> list = staffService.getAllData();
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void edit() {
		Staff staff = table.getSelectionModel().getSelectedItem();
		ObjectCarrior.setStaff(staff);
		showView();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		staffService = new StaffServiceImpl();
		department.getItems().addAll(Department.values());
		position.getItems().addAll(StaffPosition.values());
		load();

		idcode.textProperty().addListener((a, b, c) -> {
			findAll();
		});

		name.textProperty().addListener((a, b, c) -> {
			findAll();
		});

		department.valueProperty().addListener((a, b, c) -> {
			findAll();
		});

		position.valueProperty().addListener((a, b, c) -> {
			findAll();
		});

		MenuItem edit = new MenuItem("Edit");
		table.setContextMenu(new ContextMenu(edit));
		edit.setOnAction(e -> {
			edit();
		});

	}

}
