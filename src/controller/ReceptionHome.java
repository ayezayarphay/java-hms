package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.HMSApplication;
import impl.AppointmentServiceImpl;
import impl.RoomServiceImpl;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import serviceimpl.AppointmentService;
import serviceimpl.RoomService;
import utils.LoadClass;
import utils.MemberVerify;
import utils.ObjectCarrior;

public class ReceptionHome implements Initializable {
	@FXML
	private FlowPane tp;
	@FXML
	private Label userName;
	@FXML
	private Label userID;
	@FXML
	private VBox vb;
	private AppointmentService appointmentService;
	private RoomService roomService;

	public void showViewStage(String fxml) {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image(HMSApplication.class.getResourceAsStream("/img/logo.png")));
			stage.setScene(new Scene(root));
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		if (ObjectCarrior.isPasswordChange()) {
			userID.getScene().getWindow().hide();
		}

	}

	public void tran(FlowPane tp) {
		FadeTransition ft = new FadeTransition(Duration.seconds(2), tp);
		ft.setFromValue(0.05);
		ft.setToValue(1);
		ft.play();
	}

	public void patient() {
		new LoadClass().showView("PatientList.fxml", vb);
	}

	public void appointment() {
		new LoadClass().showView("PatientAppointmentList.fxml", vb);
	}

	public void room() {
		new LoadClass().showView("ReceptionRoomList.fxml", vb);
	}

	public void paymet() {
		new LoadClass().showView("TreatmentList.fxml", vb);
	}

	public void profile() {
		new LoadClass().showView("Profile.fxml", vb);
	}

	public void signout() {
		Stage stage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.setTitle("Login");
			stage.show();
			tp.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void schedule() {
		new LoadClass().showView("ReceptionDoctorScheduleList.fxml", vb);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		appointmentService = new AppointmentServiceImpl();
		roomService = new RoomServiceImpl();
		List<Integer> list = appointmentService.cancelAutoAppointment();
		for (Integer integer : list) {
			roomService.updateRoomStatus(integer, 0);
		}
		tran(tp);
		userName.setText(MemberVerify.getStaff().getName());
		userID.setText(MemberVerify.getStaff().getId_code());
	}
}
