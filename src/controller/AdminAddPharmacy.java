package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import entity.Pharmacy;
import entity.PharmacyType;
import impl.PharmacyInventoryImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import serviceimpl.PharmacyInventoryService;
import utils.IDGenerator;
import utils.ObjectCarrior;

public class AdminAddPharmacy implements Initializable {
	@FXML
	private Label id_code;
	@FXML
	private JFXTextField name;
	@FXML
	private JFXComboBox<PharmacyType> category;
	@FXML
	private JFXTextField price;
	@FXML
	private JFXTextField totalQty;
	@FXML
	private Label title;
	@FXML
	private JFXButton add;
	@FXML
	private VBox vb;
	private PharmacyInventoryService pharmacyInventoryService;
	private int phar_id = 0;

	public void check() {
		if (name.getText().isEmpty()) {
			throw new HMSException("Please enter name");
		}
		if (category.getValue() == null) {
			throw new HMSException("Please enter category");
		}
		if (price.getText().isEmpty()) {
			throw new HMSException("Please enter price");
		}
		if (!price.getText().matches("[0-9]{1,}")) {
			throw new HMSException("Please enter price in number");
		}
		if (totalQty.getText().isEmpty()) {
			throw new HMSException("Please enter total quantity of medicine");
		}

	}

	public void add() {
		try {
			check();
			String id_codeT = id_code.getText();
			String nameT = name.getText();
			PharmacyType categoryT = category.getValue();
			double priceT = Double.parseDouble(price.getText());
			int total = Integer.parseInt(totalQty.getText());
			if (phar_id == 0) {
				Pharmacy pharmacy = new Pharmacy(id_codeT, nameT, categoryT, priceT, total);
				pharmacyInventoryService.insert(pharmacy);
				new Alert(AlertType.INFORMATION, "1 pharmacy added.", ButtonType.OK).showAndWait();
				IDGenerator.increasePharmacyID();
			} else {
				Pharmacy pharmacy = new Pharmacy(phar_id, id_codeT, nameT, categoryT, priceT, total);
				pharmacyInventoryService.update(pharmacy);
				new Alert(AlertType.INFORMATION, "1 pharmacy update.", ButtonType.OK).showAndWait();
			}
			close();
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void close() {
		price.getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id_code.setText(IDGenerator.generatePharmacyID());
		pharmacyInventoryService = new PharmacyInventoryImpl();
		category.getItems().addAll(PharmacyType.values());
		setData();
	}

	public void setData() {
		Pharmacy pharmacy = ObjectCarrior.getPharmacy();
		if (pharmacy != null) {
			title.setText("Edit Pharmacy");
			add.setText("Edit");
			phar_id = pharmacyInventoryService.getPharmacyID(pharmacy);
			id_code.setText(pharmacy.getId_code());
			name.setText(pharmacy.getName());
			category.setValue(pharmacy.getPharmacyType());
			price.setText(String.valueOf((int) pharmacy.getPrice()));
			totalQty.setText(String.valueOf(pharmacy.getTotal_qty()));
			totalQty.setEditable(false);
			ObjectCarrior.setPharmacy(null);
		}
	}
}
