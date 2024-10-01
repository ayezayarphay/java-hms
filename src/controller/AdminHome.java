package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.HMSApplication;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.LoadClass;
import utils.MemberVerify;

public class AdminHome implements Initializable {
	@FXML
	private FlowPane tp;
	@FXML
	private Label userName;
	@FXML
	private Label userID;
	@FXML
	private VBox vb;

	public void signout() {
		Stage stage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.getIcons().add(new Image(HMSApplication.class.getResourceAsStream("/img/logo.png")));
			stage.setTitle("Login");
			stage.show();
			tp.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void employee() {
		new LoadClass().showView("EmployeeList.fxml", vb);
	}

	public void profile() {
		new LoadClass().showView("Profile.fxml", vb);
	}

	public void room() {
		new LoadClass().showView("AdminRoomList.fxml", vb);
	}

	public void pharmamcy() {
		new LoadClass().showView("AdminPharmacyList.fxml", vb);
	}

	public void schedule() {
		new LoadClass().showView("AdminScheduleList.fxml", vb);
	}

	public void appointment() {
		new LoadClass().showView("AdminAppointmentList.fxml", vb);
	}

	public void saleHistory() {
		new LoadClass().showView("AdminSalelHistoryList.fxml", vb);
	}

	public void patient() {
		new LoadClass().showView("AdminPatientList.fxml", vb);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tran(tp);
		userName.setText(MemberVerify.getStaff().getName());
		userID.setText(MemberVerify.getStaff().getId_code());
	}

	public void tran(FlowPane tp) {
		FadeTransition ft = new FadeTransition(Duration.seconds(2), tp);
		ft.setFromValue(0.05);
		ft.setToValue(1);
		ft.play();
	}

}
