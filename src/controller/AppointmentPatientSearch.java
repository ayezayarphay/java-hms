package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import entity.RoomAppointment;
import entity.Treatment;
import impl.AppointmentServiceImpl;
import impl.RoomServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serviceimpl.AppointmentService;
import serviceimpl.RoomService;
import utils.ObjectCarrior;

public class AppointmentPatientSearch implements Initializable {
	@FXML
	private JFXTextField pname;
	@FXML
	private JFXTextField dname;
	@FXML
	private JFXDatePicker date;
	@FXML
	private TableView<RoomAppointment> table;
	@FXML
	private VBox vb;
	private AppointmentService appointmentService;

	public void clear() {
		pname.clear();
		date.setValue(null);
		date.setPromptText("Appointment date");
		dname.clear();
		load();
	}

	public void findAll() {

		RoomAppointment roomAppointment = new RoomAppointment();
		roomAppointment.setP_name(pname.getText());
		roomAppointment.setDn_name(dname.getText());
		roomAppointment.setDate(date.getValue());
		List<RoomAppointment> list = appointmentService.searchAppointmentRoomList(roomAppointment, true);
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void showView2() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddAppointmentPatientSearch.fxml"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void load() {
		List<RoomAppointment> list = appointmentService.getRoomAppointment();
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		appointmentService = new AppointmentServiceImpl();
		load();
		pname.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		dname.textProperty().addListener((a, b, c) -> {
			findAll();
		});
		date.valueProperty().addListener((a, b, c) -> {
			findAll();
		});
		table.setOnMouseClicked(e -> {
			int a = e.getClickCount();
			if (a == 2) {
				RoomAppointment p = table.getSelectionModel().getSelectedItem();
				if (p == null) {
					new Alert(AlertType.ERROR, "Please add data", ButtonType.OK).showAndWait();
				} else if (!p.getCheck().isSelected()) {
					new Alert(AlertType.ERROR, "Make sure that patient is arrive", ButtonType.OK).showAndWait();
				} else {
					Treatment treatment = new Treatment();
					treatment.setP_idcode(p.getP_id());
					treatment.setP_name(p.getP_name());
					treatment.setDn_idcode(p.getDn_id());
					treatment.setDn_name(p.getDn_name());
					treatment.setDate(p.getDate());
					treatment.setR_id(p.getRoomNum());
					ObjectCarrior.setTreatment(treatment);
					showView2();
				}
			}
		});
		MenuItem cancel = new MenuItem("Cancel");
		table.setContextMenu(new ContextMenu(cancel));
		cancel.setOnAction(e -> {
			RoomAppointment p = table.getSelectionModel().getSelectedItem();
			appointmentService.cancelAppointment(p.getP_id(), p.getDate());
			RoomService roomService = new RoomServiceImpl();
			roomService.updateRoomStatus(p.getRoomNum(), 0);
			load();
		});
	}
}
