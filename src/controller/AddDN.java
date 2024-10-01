package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import entity.DN;
import entity.DNPosition;
import entity.Department;
import entity.Gender;
import impl.DNServiceImpl;
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
import serviceimpl.DNService;
import utils.IDGenerator;
import utils.ObjectCarrior;

public class AddDN implements Initializable {
	@FXML
	private Label id_code;
	@FXML
	private JFXTextField name;
	@FXML
	private ImageView image;
	@FXML
	private JFXComboBox<Department> department;
	@FXML
	private JFXComboBox<DNPosition> position;
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
	private JFXTextArea personal_skill;
	@FXML
	private JFXDatePicker start_work_date;
	@FXML
	private JFXTextArea previous_hospital;
	@FXML
	private JFXTextField salary;
	@FXML
	private JFXCheckBox is_resign;
	@FXML
	private JFXDatePicker resign_date;
	@FXML
	private JFXButton add;
	@FXML
	private VBox vb;
	private DNService dnService;

	private int dn_id = 0;

	public void browse() {
		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png"),
				new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
		File file = fileChooser.showOpenDialog(nrc.getScene().getWindow());
		Image imge = new Image(file.toURI().toString());
		image.setImage(imge);
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
			throw new HMSException("Name does not allow special characters");
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
		if (personal_skill.getText().isEmpty()) {
			throw new HMSException("Please enter personal skill");
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
		if (is_resign.isSelected() && resign_date.getValue() == null) {
			throw new HMSException("Please choose resign date");
		}
	}

	public void setDepartment() {
		DNPosition dnp = position.getValue();
		if (dnp.equals(DNPosition.Pharmacy_Assistant) || dnp.equals(DNPosition.Pharmacy_Dispenser)) {
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

	public void resignShow() {

		resign_date.setValue(null);
		resign_date.setVisible(is_resign.isSelected());

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
			DNPosition positionT = position.getValue();
			String father_nameT = father_name.getText();
			String mother_nameT = mother_name.getText();
			Gender genderT = male.isSelected() ? Gender.Male : Gender.Female;
			LocalDate dobT = dob.getValue();
			String nrcT = nrc.getText();
			String degreeT = degree.getText();
			String addressT = address.getText();
			String personal_skillT = personal_skill.getText();
			LocalDate start_work_dateT = start_work_date.getValue();
			String previous_hospitalT = previous_hospital.getText().isEmpty() ? "None" : previous_hospital.getText();
			double salaryT = Double.parseDouble(salary.getText());
			boolean isResing = is_resign.isSelected();
			String resingDate = !resign_date.isVisible() ? "None" : resign_date.getValue().toString();
			if (dn_id == 0) {
				DN dn = new DN(id_codeT, nameT, img, departmentT, positionT, father_nameT, mother_nameT, genderT, dobT,
						nrcT, degreeT, addressT, personal_skillT, start_work_dateT, previous_hospitalT, salaryT,
						isResing, resingDate);
				dnService.insert(dn);
				new Alert(AlertType.INFORMATION, "1 employee added.", ButtonType.OK).showAndWait();
				IDGenerator.increaseDNID();
			} else {
				DN dn = new DN(dn_id, id_codeT, nameT, img, departmentT, positionT, father_nameT, mother_nameT, genderT,
						dobT, nrcT, degreeT, addressT, personal_skillT, start_work_dateT, previous_hospitalT, salaryT,
						isResing, resingDate);
				dnService.update(dn);
				new Alert(AlertType.INFORMATION, "1 employee updated.", ButtonType.OK).showAndWait();
			}
			close();
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}

	}

	public void close() {
		name.getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		start_work_date.setValue(LocalDate.now());
		resign_date.setValue(LocalDate.now());
		dnService = new DNServiceImpl();
		position.getItems().addAll(DNPosition.values());
		id_code.setText(IDGenerator.generateDNID());
		setData();
	}

	public void setData() {
		DN dn = ObjectCarrior.getDn();
		if (dn != null) {
			add.setText("Edit");
			dn_id = dnService.getID(dn);
			dn = dnService.finByID(dn_id);
			id_code.setText(dn.getId_code());
			name.setText(dn.getName());
			Image img = new Image(new ByteArrayInputStream(dn.getImage()));
			image.setImage(img);
			father_name.setText(dn.getFather_name());
			mother_name.setText(dn.getMother_name());
			department.setValue(dn.getDepartment());
			position.setValue(dn.getPosition());
			if (dn.getSex().equals(Gender.Male)) {
				male.setSelected(true);
			} else {
				female.setSelected(true);
			}
			dob.setValue(dn.getDob());
			nrc.setText(dn.getNrc());
			degree.setText(dn.getDegree());
			address.setText(dn.getAddress());
			personal_skill.setText(dn.getPersonal_skill());
			start_work_date.setValue(dn.getStart_work_date());
			String get = dn.getPrevious_hospital();
			if (get.equals("None")) {
				previous_hospital.setText("");

			} else {
				previous_hospital.setText(get);

			}
			salary.setText(String.valueOf((int) dn.getSalary()));
			is_resign.setSelected(dn.isResign());
			if (!dn.getResign_date().equals("None")) {
				resignShow();
				resign_date.setValue(LocalDate.parse(dn.getResign_date()));
			}
			ObjectCarrior.setDn(null);
		}
	}
}
