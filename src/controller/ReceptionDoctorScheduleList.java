package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;

import entity.StaffDNScuhdule;
import impl.EmployeeScheduleServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import serviceimpl.EmployeeSchedule;
import utils.MenuSlider;

public class ReceptionDoctorScheduleList implements Initializable {
	@FXML
	private TableView<StaffDNScuhdule> table;
	@FXML
	private JFXTextField idcode;
	@FXML
	private JFXTextField name;
	@FXML
	private JFXHamburger ham;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private VBox vb;

	private EmployeeSchedule employeeSchedule;

	public void clear() {
		idcode.clear();
		name.clear();
		load();
	}

	public void load() {
		List<StaffDNScuhdule> list = employeeSchedule.getAll("DN");
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void finAll() {
		StaffDNScuhdule staffDNScuhdule = new StaffDNScuhdule();
		staffDNScuhdule.setIdcode(idcode.getText());
		staffDNScuhdule.setName(name.getText());
		List<StaffDNScuhdule> list = employeeSchedule.findAll("DN", staffDNScuhdule);
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		employeeSchedule = new EmployeeScheduleServiceImpl();
		new MenuSlider().menuSlider(ham, drawer, vb, "ReceptionMenu.fxml", "Schedule");
		load();

		idcode.textProperty().addListener((a, b, c) -> {
			finAll();
		});
		name.textProperty().addListener((a, b, c) -> {
			finAll();
		});

	}

}
