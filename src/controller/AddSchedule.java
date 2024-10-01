package controller;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import application.HMSException;
import entity.DN;
import entity.ESchedule;
import entity.Staff;
import entity.StaffDNScuhdule;
import impl.DNServiceImpl;
import impl.EmployeeScheduleServiceImpl;
import impl.ScheduleServiceImpl;
import impl.StaffServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import serviceimpl.DNService;
import serviceimpl.EmployeeSchedule;
import serviceimpl.ScheduleService;
import serviceimpl.StaffService;
import utils.ObjectCarrior;

public class AddSchedule implements Initializable {
	@FXML
	private JFXTextField e_id;
	@FXML
	private Label name;
	@FXML
	private FlowPane dow;
	@FXML
	private JFXTimePicker startTime;
	@FXML
	private JFXTimePicker endTime;
	@FXML
	private Label title;
	@FXML
	private JFXButton add;
	private StaffService staffService;
	private DNService dnService;
	private String dayOfWeek = "";
	private ScheduleService scheduleService;
	private EmployeeSchedule employeeSchedule;
	int s_id = 0;

	public void check() {
		if (e_id.getText().isEmpty()) {
			throw new HMSException("Please enter employee ID");
		}
		if (title.getText().contains("Staff")) {
			getStaff();
		}
		if (title.getText().contains("DN")) {
			getDN();
		}
		if (startTime.getValue() == null) {
			throw new HMSException("Please select start time");
		}
		if (endTime.getValue() == null) {
			throw new HMSException("Please select end time");
		}
		if (!startTime.getValue().isBefore(endTime.getValue())) {
			throw new HMSException("Start time cannot be greather than End time");
		}

	}

	public int getEmployeeID(String idcode) {
		int id = 0;
		if (idcode.contains("Staff")) {
			Staff staff = new Staff();
			staff.setId_code(idcode);
			id = staffService.getID(staff);
		} else {
			DN dn = new DN();
			dn.setId_code(idcode);
			id = dnService.getID(dn);
		}
		return id;

	}

	public void add() {
		try {
			check();
			String getId = e_id.getText();
			dow.getChildren().stream().filter(f -> f instanceof JFXCheckBox).map(m -> (JFXCheckBox) m).forEach(e -> {
				if (e.isSelected()) {
					dayOfWeek += e.getText() + " ";
				}
			});
			if (dayOfWeek.isEmpty()) {
				throw new HMSException("Please select day of week");
			}
			if (s_id == 0) {
				StaffDNScuhdule schedule = new StaffDNScuhdule(dayOfWeek.trim(), startTime.getValue().toString(),
						endTime.getValue().toString());
				int s_id = scheduleService.insert(schedule);
				int e_id = getIDFromEmployee(getId);
				ESchedule eSchedule = new ESchedule(s_id, e_id);
				employeeSchedule.insert(eSchedule, title.getText().contains("Staff") ? "Staff" : "DN");
				new Alert(AlertType.INFORMATION, "1 schedule is added", ButtonType.OK).showAndWait();
			} else {
				StaffDNScuhdule schedule = new StaffDNScuhdule(s_id, dayOfWeek.trim(), startTime.getValue().toString(),
						endTime.getValue().toString());
				scheduleService.update(schedule);
				new Alert(AlertType.INFORMATION, "1 schedule is updated", ButtonType.OK).showAndWait();
			}
			dayOfWeek = "";
			close();
		} catch (Exception e) {
			new Alert(AlertType.WARNING, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public int getIDFromEmployee(String idCode) {
		int id = 0;
		if (title.getText().equals("Add Staff Schedule")) {
			Staff staff = new Staff();
			staff.setId_code(idCode);
			id = staffService.getID(staff);
		} else {
			DN dn = new DN();
			dn.setId_code(idCode);
			id = dnService.getID(dn);
		}
		return id;
	}

	public void close() {
		title.getScene().getWindow().hide();
		dayOfWeek = "";
	}

	public Staff getStaff() {
		Staff staff = staffService.findByIdCode(e_id.getText());
		if (staff == null) {
			throw new HMSException("Staff ID is invaild");
		}
		if (staff.isResign()) {
			throw new HMSException(staff.getName() + " is resign");
		}
		return staff;
	}

	public DN getDN() {
		DN dn = dnService.findByidCode(e_id.getText());
		if (dn == null) {
			throw new HMSException("DN ID is invaild");
		}
		if (dn.isResign()) {
			throw new HMSException(dn.getName() + " is resign");
		}
		return dn;
	}

	public void setEmployeeName() {
		try {
			if (title.getText().contains("Staff")) {
				Staff staff = getStaff();
				name.setText(staff.getName());
			} else {
				DN dn = getDN();
				name.setText(dn.getName());
			}
		} catch (Exception e) {
			new Alert(AlertType.WARNING, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		staffService = new StaffServiceImpl();
		dnService = new DNServiceImpl();
		scheduleService = new ScheduleServiceImpl();
		employeeSchedule = new EmployeeScheduleServiceImpl();
		String tit = ObjectCarrior.getPasswordTitle();
		ObjectCarrior.setPasswordTitle(null);
		if (!tit.isEmpty()) {
			title.setText(tit);
			e_id.setPromptText(tit.contains("Staff") ? "Staff ID" : "DN ID");
			ObjectCarrior.setPasswordTitle(null);
		}

		StaffDNScuhdule staffDNScuhdule = ObjectCarrior.getStaffDNScuhdule();
		if (staffDNScuhdule != null) {
			int sordID = getEmployeeID(staffDNScuhdule.getIdcode());
			s_id = employeeSchedule.getScheduleID(sordID, tit.contains("Staff") ? "Staff" : "DN");
			e_id.setText(staffDNScuhdule.getIdcode());
			name.setText(staffDNScuhdule.getName());
			String get = staffDNScuhdule.getDayOfWeek();
			String array[] = get.split(" ");
			for (String string : array) {
				dow.getChildren().stream().filter(f -> f instanceof JFXCheckBox).map(m -> (JFXCheckBox) m)
						.forEach(e -> {
							if (string.equals(e.getText())) {
								e.setSelected(true);
							}
						});
			}

			startTime.setValue(LocalTime.parse(staffDNScuhdule.getStartTime()));

			endTime.setValue(LocalTime.parse(staffDNScuhdule.getEndTime()));

			add.setText("Edit");
			ObjectCarrior.setStaffDNScuhdule(null);
		}
	}
}
