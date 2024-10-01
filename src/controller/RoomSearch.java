package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.LoadClass;

public class RoomSearch implements Initializable {
	@FXML
	private HBox hb1;
	@FXML
	private HBox hb2;
	@FXML
	private VBox vb;
	@FXML
	private VBox mainVb;

	public void back() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ReceptionRoomList.fxml"));
			mainVb.getChildren().clear();
			mainVb.getChildren().add(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hp() {
		new LoadClass().showView("HospitalPatientSearch.fxml", vb);
		hb1.setStyle("-fx-border-color: #4059a9;" + "-fx-border-width: 0 0 2 0");
		hb2.setStyle("-fx-border-color: white;" + "-fx-border-width: 0 0 2 0");
	}

	public void app() {

		new LoadClass().showView("AppointmentPatientSearch.fxml", vb);
		hb2.setStyle("-fx-border-color: #4059a9;" + "-fx-border-width: 0 0 2 0");
		hb1.setStyle("-fx-border-color: white;" + "-fx-border-width: 0 0 2 0");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hp();
	}
}
