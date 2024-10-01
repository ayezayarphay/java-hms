package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import entity.Department;
import entity.Gender;
import entity.Staff;
import entity.StaffPosition;
import impl.StaffServiceImpl;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import serviceimpl.StaffService;
import utils.IDGenerator;

public class FirstAdmin implements Initializable {
	@FXML
	private Label id_code;
	@FXML
	private JFXTextField name;
	@FXML
	private ImageView image;
	@FXML
	private JFXComboBox<Department> department;
	@FXML
	private JFXComboBox<StaffPosition> position;
	@FXML
	private JFXTextField father_name;
	@FXML
	private JFXTextField mother_name;
	@FXML
	private JFXRadioButton male;
	@FXML
	private JFXRadioButton female;
	@FXML
	private JFXDatePicker dob;
	@FXML
	private JFXTextField nrc;
	@FXML
	private JFXTextArea degree;
	@FXML
	private JFXTextArea address;
	@FXML
	private JFXDatePicker start_work_date;
	@FXML
	private JFXTextArea previous_work;
	@FXML
	private JFXTextField salary;
	@FXML
	private JFXDatePicker resign_date;
	@FXML
	private VBox vb;
	@FXML
	private JFXButton add;
	private StaffService staffService;

	public void browse() {
		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png"),
				new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
		File file = fileChooser.showOpenDialog(nrc.getScene().getWindow());
		Image imge = new Image(file.toURI().toString());
		image.setImage(imge);
	}

	public void setDepartment() {
		StaffPosition dnp = position.getValue();
		if (dnp.equals(StaffPosition.Pharmacy_Receptionist)) {
			department.getItems().clear();
			department.getItems().add(Department.Pharmacy);
		} else {
			department.getItems().clear();
			department.getItems().addAll(Department.Cardiology, Department.Emergency, Department.ENT,
					Department.Gastroenterology, Department.Gynecology, Department.Lung, Department.Neurology,
					Department.Oncology, Department.Ophthalmology, Department.Orthopedics, Department.Physiotherapy,
					Department.Renal, Department.Sexual_Health, Department.Surgery, Department.Urology);
		}
	}

	public void check() {
		if (name.getText().isEmpty()) {
			throw new HMSException("Please enter name");
		}
		if (!name.getText().matches("[A-Za-z\\s+]{1,}")) {
			throw new HMSException("Name does not allow special characters");
		}
		if (image.getImage() == (null)) {
			throw new HMSException("Please input image");
		}
		if (department.getValue() == null) {
			throw new HMSException("Please choose department");
		}
		if (position.getValue() == null) {
			throw new HMSException("Please choose position");
		}
		if (father_name.getText().isEmpty()) {
			throw new HMSException("Please enter father name");
		}
		if (!father_name.getText().matches("[A-Za-z\\s+]{1,}")) {
			throw new HMSException("Father name does not allow special characters");
		}
		if (mother_name.getText().isEmpty()) {
			throw new HMSException("Please enter mother name");
		}
		if (!mother_name.getText().matches("[A-Za-z\\s+]{1,}")) {
			throw new HMSException("Mother name does not allow special characters");
		}
		if (dob.getValue() == null) {
			throw new HMSException("Please choose DOB");
		}
		if (nrc.getText().isEmpty()) {
			throw new HMSException("Please enter NRC");
		}
		if (!nrc.getText().matches("[0-9]{1,2}/[A-Z]{6,10}\\(N\\)[0-9]{6}")) {
			throw new HMSException("Please enter NRC in correct format");
		}

		if (degree.getText().isEmpty()) {
			throw new HMSException("Please enter degree");
		}
		if (address.getText().isEmpty()) {
			throw new HMSException("Please enter address");
		}
		if (start_work_date.getValue() == null) {
			throw new HMSException("Please choose start_work_date");
		}
		if (salary.getText().isEmpty()) {
			throw new HMSException("Please enter salary");
		}
		if (!salary.getText().matches("[0-9]{1,}")) {
			throw new HMSException("Please enter salary in number");
		}

	}

	public void clear() {
		image.setImage(null);
	}

	public void add() {
		try {
			check();
			String id_codeT = id_code.getText();
			String nameT = name.getText();
			BufferedImage bImage = SwingFXUtils.fromFXImage(image.getImage(), null);
			ByteArrayOutputStream s = new ByteArrayOutputStream();
			ImageIO.write(bImage, "png", s);
			byte[] img = s.toByteArray();
			s.close();
			Department departmentT = department.getValue();
			StaffPosition positionT = position.getValue();
			String father_nameT = father_name.getText();
			String mother_nameT = mother_name.getText();
			Gender genderT = male.isSelected() ? Gender.Male : Gender.Female;
			LocalDate dobT = dob.getValue();
			String nrcT = nrc.getText();
			String degreeT = degree.getText();
			String addressT = address.getText();
			LocalDate start_work_dateT = start_work_date.getValue();
			String previous_workT = previous_work.getText().isEmpty() ? "None" : previous_work.getText();
			double salaryT = Double.parseDouble(salary.getText());
			String resingDate = !resign_date.isVisible() ? "None" : resign_date.getValue().toString();

			Staff staff = new Staff(id_codeT, nameT, "None", img, departmentT, positionT, father_nameT, mother_nameT,
					genderT, dobT, nrcT, degreeT, addressT, start_work_dateT, previous_workT, salaryT, false,
					resingDate, true);
			staffService.insert(staff);
			new Alert(AlertType.INFORMATION, "Admin inserting is success\nPlease re-enter this system.", ButtonType.OK)
					.showAndWait();
			IDGenerator.increaseStaffID();
			System.exit(0);
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new Alert(AlertType.INFORMATION, "Register your information first.", ButtonType.OK).showAndWait();
		start_work_date.setValue(LocalDate.now());
		resign_date.setValue(LocalDate.now());
		staffService = new StaffServiceImpl();
		position.getItems().addAll(StaffPosition.values());
		id_code.setText(IDGenerator.generateStaffID());

	}
}
