package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entity.StaffDNScuhdule;
import impl.EmployeeScheduleServiceImpl;
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
import serviceimpl.EmployeeSchedule;
import utils.ObjectCarrior;

public class DNScheduleList implements Initializable {
	@FXML
	private TableView<StaffDNScuhdule> table;
	@FXML
	private JFXTextField idcode;
	@FXML
	private JFXTextField name;

	private EmployeeSchedule employeeSchedule;

	public void add() {
		ObjectCarrior.setPasswordTitle("Add DN Schedule");
		showView();
	}

	public void showView() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddSchedule.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		List<StaffDNScuhdule> list = employeeSchedule.getAll("DN");
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void print() throws IOException {
		List<StaffDNScuhdule> list = table.getItems();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		File file = fileChooser.showSaveDialog(idcode.getScene().getWindow());
		file.createNewFile();
		String path = file.getAbsolutePath();
		if (path.lastIndexOf(".txt") > 0) {
			for (StaffDNScuhdule staff : list) {
				String text = staff.toString() + "\n";
				Files.write(Paths.get(file.getAbsolutePath()), text.getBytes(), StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		} else {
			String title = "ID Code,Name,Day of Week,Duty Department,Start time,End time\n";
			Files.write(Paths.get(file.getAbsolutePath()), title.getBytes(), StandardOpenOption.APPEND);
			for (StaffDNScuhdule staff : list) {
				Files.write(Paths.get(file.getAbsolutePath()), staff.toString().getBytes(), StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		}
	}

	public void clear() {
		idcode.clear();
		name.clear();
		load();
	}

	public void finAll() {
		StaffDNScuhdule staffDNScuhdule = new StaffDNScuhdule();
		staffDNScuhdule.setIdcode(idcode.getText());
		staffDNScuhdule.setName(name.getText());
		List<StaffDNScuhdule> list = employeeSchedule.findAll("DN", staffDNScuhdule);
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		employeeSchedule = new EmployeeScheduleServiceImpl();
		load();

		idcode.textProperty().addListener((a, b, c) -> {
			finAll();
		});
		name.textProperty().addListener((a, b, c) -> {
			finAll();
		});
		MenuItem edit = new MenuItem("Edit");
		table.setContextMenu(new ContextMenu(edit));
		edit.setOnAction(e -> {
			edit();
		});
	}

	private void edit() {
		StaffDNScuhdule staffDNScuhdule = table.getSelectionModel().getSelectedItem();
		if (staffDNScuhdule == null) {
			new Alert(AlertType.WARNING, "Please add data first", ButtonType.OK).showAndWait();
		} else {
			ObjectCarrior.setStaffDNScuhdule(staffDNScuhdule);
			ObjectCarrior.setPasswordTitle("Edit DN Schedule");
			showView();
		}
	}
}
