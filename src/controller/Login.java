package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import entity.Staff;
import entity.StaffPosition;
import impl.StaffServiceImpl;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import serviceimpl.StaffService;
import utils.LoadClass;
import utils.MemberVerify;

public class Login implements Initializable {
	@FXML
	private JFXTextField userID;
	@FXML
	private JFXPasswordField password;
	@FXML
	private AnchorPane ap;
	StaffService service = null;

	public void forgetPasswordView() {
		new LoadClass().showViewStage("ForgetPassword.fxml");
	}

	public void createPassword() {
		new LoadClass().showViewStage("ChangePassword.fxml");
	}

	public void login() {
		try {
			Staff staff = service.checkVaildUser(userID.getText(), password.getText());
			MemberVerify.setStaff(staff);
			StaffPosition position = staff.getPosition();
			if (staff.isAdmin() && !staff.isResign()) {
				new Alert(AlertType.INFORMATION, "Welcome " + staff.getName(), ButtonType.OK).showAndWait();
				new LoadClass().showViewStage("AdminHome.fxml");
				userID.getScene().getWindow().hide();
			} else {

				if (!staff.isAdmin() && position.equals(StaffPosition.Receptionist) && !staff.isResign()) {
					new Alert(AlertType.INFORMATION, "Welcome " + staff.getName(), ButtonType.OK).showAndWait();
					new LoadClass().showViewStage("ReceptionHome.fxml");
					userID.getScene().getWindow().hide();

				} else if (!staff.isAdmin() && position.equals(StaffPosition.Pharmacy_Receptionist)
						&& !staff.isResign()) {
					new Alert(AlertType.INFORMATION, "Welcome " + staff.getName(), ButtonType.OK).showAndWait();
					new LoadClass().showViewStage("PharmacyPOS.fxml");
					userID.getScene().getWindow().hide();

				} else {
					new Alert(AlertType.WARNING, "You are not allow to enter this system ", ButtonType.OK)
							.showAndWait();
				}
			}

		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new StaffServiceImpl();
		tran();

	}

	public void tran() {
		FadeTransition ft = new FadeTransition(Duration.seconds(1.5), ap);
		ft.setFromValue(0.05);
		ft.setToValue(1);
		ft.play();
	}
}
