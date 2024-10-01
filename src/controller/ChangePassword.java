package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import entity.Staff;
import entity.StaffPosition;
import impl.StaffServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import serviceimpl.StaffService;
import utils.MemberVerify;
import utils.ObjectCarrior;

public class ChangePassword implements Initializable {
	@FXML
	private JFXTextField id_code;
	@FXML
	private JFXPasswordField password;
	@FXML
	private JFXPasswordField confirmPassword;
	@FXML
	private Label title;
	@FXML
	private JFXButton login;
	private StaffService staffService;

	public void check() {
		Staff staff = staffService.findByIdCode(id_code.getText());
		if (id_code.getText().isEmpty()) {
			throw new HMSException("Please enter id code");
		}
		if (staff == null) {
			throw new HMSException(id_code + " is invaild.");
		}
		String cpassword = confirmPassword.getText();
		String passwordT = password.getText();
		if (passwordT.isEmpty() || cpassword.isEmpty()) {
			throw new HMSException("Please enter password");
		}
		if (!cpassword.equals(passwordT)) {
			throw new HMSException("Not a same input password");
		}
		if (!passwordT.matches("[a-zA-Z0-9]*")) {
			throw new HMSException("Special characters are not allow");
		}
		if (!passwordT.matches("[A-za-z1-9]{5,10}")) {
			throw new HMSException("Please enter password at least 5 word and at most 10 word");
		}
	}

	public void login() {
		try {
			check();
			Staff staff = staffService.findByIdCode(id_code.getText());
			if (title.getText().equals("Sign in with password")) {
				MemberVerify.setStaff(staff);
				if (staff.getPassword().equals("None")) {
					staffService.changePassword(id_code.getText(), confirmPassword.getText());
					StaffPosition position = staff.getPosition();
					if (staff.isAdmin() && !staff.isResign()) {
						new Alert(AlertType.INFORMATION,
								"Your Password is successfuly save.\n" + "Welcome " + staff.getName(), ButtonType.OK)
										.showAndWait();
					} else {
						if (!staff.isAdmin() && position.equals(StaffPosition.Receptionist) && !staff.isResign()) {
							new Alert(AlertType.INFORMATION,
									"Your Password is successfuly save.\n" + "Welcome " + staff.getName(),
									ButtonType.OK).showAndWait();
						} else if (!staff.isAdmin() && position.equals(StaffPosition.Pharmacy_Receptionist)
								&& !staff.isResign()) {
							new Alert(AlertType.INFORMATION,
									"Your Password is successfuly save.\n" + "Welcome " + staff.getName(),
									ButtonType.OK).showAndWait();
						}
					}
					id_code.getScene().getWindow().hide();

				} else {
					new Alert(AlertType.ERROR, "You have already password.", ButtonType.OK).showAndWait();
				}
			} else {
				staffService.changePassword(id_code.getText(), confirmPassword.getText());
				new Alert(AlertType.INFORMATION,
						"Your Password is successfuly save." + "\nPlease re-enter and login again", ButtonType.OK)
								.showAndWait();
				ObjectCarrior.setPasswordChange(true);
				id_code.getScene().getWindow().hide();
				System.exit(0);
			}

		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void close() {
		title.getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		staffService = new StaffServiceImpl();
		String titleT = ObjectCarrior.getPasswordTitle();
		if (titleT != null) {
			title.setText(titleT);
			login.setText("Change");
			id_code.setText(MemberVerify.getStaff().getId_code());
			id_code.setEditable(false);
			ObjectCarrior.setPasswordTitle(null);
		}

	}

}
