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
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import entity.Pharmacy;
import entity.PharmacyType;
import impl.PharmacyInventoryImpl;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serviceimpl.PharmacyInventoryService;
import utils.MenuSlider;
import utils.ObjectCarrior;

public class AdminPharmacyList implements Initializable {
	@FXML
	private TextField searchID;
	@FXML
	private TextField searchName;
	@FXML
	private JFXComboBox<PharmacyType> category;
	@FXML
	private TableView<Pharmacy> table;
	@FXML
	private VBox vb;
	@FXML
	private JFXHamburger ham;
	@FXML
	private JFXDrawer drawer;
	private PharmacyInventoryService pharmacyInventoryService;

	public void showView() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AdminAddPharmamcy.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		searchID.clear();
		searchName.clear();
		category.setValue(null);
		category.setPromptText("Category");
		load();

	}

	public void add() {
		showView();
	}

	public void report() throws IOException {
		List<Pharmacy> list = table.getItems();
		List<Pharmacy> getData = new ArrayList<Pharmacy>();
		for (Pharmacy pharmacy : list) {
			Pharmacy p = pharmacyInventoryService.findByIDCode(pharmacy.getId_code());
			getData.add(p);
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		File file = fileChooser.showSaveDialog(searchID.getScene().getWindow());
		file.createNewFile();
		String path = file.getAbsolutePath();
		if (path.lastIndexOf(".txt") > 0) {
			for (Pharmacy pharmacy : getData) {
				String text = pharmacy.toString() + "\n";
				Files.write(Paths.get(file.getAbsolutePath()), text.getBytes(), StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		} else {
			String title = "ID Code,Name,Category,Price,Available status,\n";
			Files.write(Paths.get(file.getAbsolutePath()), title.getBytes(), StandardOpenOption.APPEND);
			for (Pharmacy pharmacy : getData) {
				Files.write(Paths.get(file.getAbsolutePath()), pharmacy.toString().getBytes(),
						StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		}
	}

	public void load() {
		List<Pharmacy> list = pharmacyInventoryService.find();
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void edit() {
		Pharmacy p = table.getSelectionModel().getSelectedItem();
		ObjectCarrior.setPharmacy(p);
		showView();
	}

	public void findAll() {
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId_code(searchID.getText());
		pharmacy.setName(searchName.getText());
		pharmacy.setPharmacyType(category.getValue());
		List<Pharmacy> list = pharmacyInventoryService.findAll(pharmacy);
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pharmacyInventoryService = new PharmacyInventoryImpl();
		new MenuSlider().menuSlider(ham, drawer, vb, "AdminMenu.fxml", "Pharmacy");
		category.getItems().addAll(PharmacyType.values());
		load();

		searchID.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		searchName.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		category.valueProperty().addListener((a, b, c) -> {
			findAll();
		});
		MenuItem edit = new MenuItem("Edit");
		table.setContextMenu(new ContextMenu(edit));
		edit.setOnAction(e -> {
			edit();
		});
	}

}
