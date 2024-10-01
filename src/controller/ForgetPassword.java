package controller;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import utils.EmailSender;

public class ForgetPassword {
	@FXML
	private JFXTextField userID;
	@FXML
	private JFXTextField email;

	public void send() {
		try {
			EmailSender.changePassword(userID.getText(), email.getText());
			new Alert(AlertType.INFORMATION, "New Password is sent to your email." + "\n" + "Please check it",
					ButtonType.OK).showAndWait();
			email.getScene().getWindow().hide();
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void close() {
		email.getScene().getWindow().hide();
	}
}
