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

import entity.DN;
import entity.DNPosition;
import entity.Department;
import impl.DNServiceImpl;
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
import serviceimpl.DNService;
import utils.ObjectCarrior;

public class DnList implements Initializable {
	@FXML
	private JFXTextField idcode;
	@FXML
	private JFXTextField name;
	@FXML
	private JFXComboBox<Department> department;
	@FXML
	private JFXComboBox<DNPosition> position;
	@FXML
	private TableView<DN> table;
	private DNService dnService;

	public void showView() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddDN.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void report() throws IOException {
		List<DN> list = table.getItems();
		List<DN> getData = new ArrayList<DN>();
		for (DN dn : list) {
			DN dn1 = dnService.findByidCode(dn.getId_code());
			getData.add(dn1);
		}
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		File file = fileChooser.showSaveDialog(idcode.getScene().getWindow());
		file.createNewFile();
		String path = file.getAbsolutePath();
		if (path.lastIndexOf(".txt") > 0) {
			for (DN dn : getData) {
				String text = dn.toString() + "\n";
				Files.write(Paths.get(file.getAbsolutePath()), text.getBytes(), StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		} else {
			String title = "ID Code,Name,Department,Position,Father Name,Mother Name,Sex,DOB,NRC,Degree,Address,Personal Skill,Start Work Date,Previous Hospital,Salary,Is Resing,Resing Date,\n";
			Files.write(Paths.get(file.getAbsolutePath()), title.getBytes(), StandardOpenOption.APPEND);
			for (DN dn : getData) {
				Files.write(Paths.get(file.getAbsolutePath()), dn.toString().getBytes(), StandardOpenOption.APPEND);
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

	public void load() {
		List<DN> list = dnService.getAllData();
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void edit() {
		DN dn = table.getSelectionModel().getSelectedItem();
		ObjectCarrior.setDn(dn);
		showView();
	}

	public void findAll() {
		DN dn = new DN();
		dn.setId_code(idcode.getText());
		dn.setName(name.getText());
		dn.setDepartment(department.getValue());
		dn.setPosition(position.getValue());
		List<DN> list = dnService.findAll(dn);
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dnService = new DNServiceImpl();
		department.getItems().addAll(Department.values());
		position.getItems().addAll(DNPosition.values());
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
