package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.LoadClass;
import utils.MenuSlider;

public class TreatmentList implements Initializable {
	@FXML
	private VBox vb;
	@FXML
	private JFXHamburger ham;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private VBox mainVb;
	@FXML
	private Label title;
	@FXML
	private HBox hb1;
	@FXML
	private HBox hb2;

	public void tempory() {
		title.setText("Temporary");
		new LoadClass().showView("TemporyPaymentList.fxml", vb);
		hb1.setStyle("-fx-border-color: #4059a9;" + "-fx-border-width: 0 0 2 0");
		hb2.setStyle("-fx-border-color: white;" + "-fx-border-width: 0 0 2 0");
	}

	public void hospital() {
		title.setText("Hospital");
		new LoadClass().showView("HospitalPatientList.fxml", vb);
		hb2.setStyle("-fx-border-color: #4059a9;" + "-fx-border-width: 0 0 2 0");
		hb1.setStyle("-fx-border-color: white;" + "-fx-border-width: 0 0 2 0");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new MenuSlider().menuSlider(ham, drawer, mainVb, "ReceptionMenu.fxml", "Payment");
		tempory();
	}
}
