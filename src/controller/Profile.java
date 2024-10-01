package controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import entity.Staff;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import utils.LoadClass;
import utils.MemberVerify;
import utils.MenuSlider;
import utils.ObjectCarrior;

public class Profile implements Initializable {
	@FXML
	private Label id;
	@FXML
	private Label name;
	@FXML
	private Label password;
	@FXML
	private Label position;
	@FXML
	private Label department;
	@FXML
	private Label startWorkDate;
	@FXML
	private Label previousWork;
	@FXML
	private Label salary;
	@FXML
	private Label isAdmin;
	@FXML
	private Label degree;
	@FXML
	private SVGPath path;
	@FXML
	private JFXCheckBox check;
	@FXML
	private ImageView img;
	@FXML
	private JFXHamburger ham;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private VBox vb;
	String text = "";

	public void showView() {
		ObjectCarrior.setPasswordTitle("Change Password");
		new LoadClass().showViewStage("ChangePassword.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Staff staff = MemberVerify.getStaff();
		if (staff.isAdmin()) {
			new MenuSlider().menuSlider(ham, drawer, vb, "AdminMenu.fxml", "Profile");
		} else {
			new MenuSlider().menuSlider(ham, drawer, vb, "ReceptionMenu.fxml", "Profile");
		}
		Image img = new Image(new ByteArrayInputStream(staff.getImage()));
		id.setText(staff.getId_code());
		name.setText(staff.getName());
		position.setText(staff.getPosition().toString());
		department.setText(staff.getDepartment().toString());
		startWorkDate.setText(staff.getStart_work_date().toString());
		previousWork.setText(staff.getPrevious_work());
		salary.setText(String.valueOf(staff.getSalary()));
		isAdmin.setText(staff.isAdmin() + "");
		degree.setText(staff.getDegree());
		this.img.setImage(img);
		Tooltip.install(path, new Tooltip("Change Password"));
		String pass = staff.getPassword();
		for (int i = 0; i < pass.length(); i++) {
			text += "*";
		}
		password.setText(text);
		check.selectedProperty().addListener((a, b, c) -> {
			if (c) {
				password.setText(pass);
			} else {

				password.setText(text);
			}
		});

	}
}
