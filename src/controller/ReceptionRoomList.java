package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import entity.Room;
import impl.RoomServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serviceimpl.RoomService;
import utils.MenuSlider;
import utils.ObjectCarrior;

public class ReceptionRoomList implements Initializable {

	@FXML
	private FlowPane fp1;
	@FXML
	private FlowPane fp2;
	@FXML
	private FlowPane fp3;
	@FXML
	private FlowPane fp4;
	@FXML
	private FlowPane fp5;
	@FXML
	private FlowPane fp6;
	@FXML
	private FlowPane fp7;
	@FXML
	private FlowPane fp8;
	@FXML
	private FlowPane fp9;
	@FXML
	private FlowPane fp10;

	private RoomService roomService;
	@FXML
	private JFXHamburger ham;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private VBox vb;

	public void searchView() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("RoomSearch.fxml"));
			vb.getChildren().clear();
			vb.getChildren().add(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void search() {
		searchView();
	}

	public void btnLoad(int id, JFXButton btn) {
		if (id == 1) {
			btn.setStyle(btn.getStyle() + "-fx-background-color:#f56d6d;");
		} else if (id == 2) {
			btn.setStyle(btn.getStyle() + "-fx-background-color:#58ee3a;");
		} else if (id == 0) {
			btn.setStyle(btn.getStyle() + "-fx-background-color:white;");
		}
	}

	public void show(JFXButton btn) {
		Room room = new Room();
		room.setId(Integer.parseInt(btn.getText()));
		ObjectCarrior.setRoom(room);
		if (btn.getStyle().contains("-fx-background-color:white;")) {
			showAppointmentView();
		}
	}

	public void showAppointmentView() {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ReceptionAppointment.fxml"));
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setOnHidden(e -> load());
			stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public JFXButton isJfxBUtton(Node node) {
		if (node instanceof JFXButton) {
			return (JFXButton) node;
		}
		return null;
	}

	public void load() {
		for (Node node : fp1.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
		for (Node node : fp2.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
		for (Node node : fp3.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
		for (Node node : fp1.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
		for (Node node : fp4.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
		for (Node node : fp5.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
		for (Node node : fp6.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
		for (Node node : fp7.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
		for (Node node : fp8.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
		for (Node node : fp9.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
		for (Node node : fp10.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				show(btn);
			});
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		roomService = new RoomServiceImpl();
		new MenuSlider().menuSlider(ham, drawer, vb, "ReceptionMenu.fxml", "Room");
		load();

	}
}
