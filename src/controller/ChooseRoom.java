package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import entity.Room;
import impl.RoomServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import serviceimpl.RoomService;
import utils.ObjectCarrior;

public class ChooseRoom implements Initializable {
	private RoomService roomService;
	@FXML
	private FlowPane fp1;
	@FXML
	private FlowPane fp2;
	@FXML
	private FlowPane fp3;

	public JFXButton isJfxBUtton(Node node) {
		if (node instanceof JFXButton) {
			return (JFXButton) node;
		}
		return null;
	}

	public void btnLoad(int id, JFXButton btn) {
		if (id == 1) {
			btn.setStyle(btn.getStyle() + "-fx-background-color:#f56d6d;");
		}
	}

	public void availableRoom() {
		for (Node node : fp1.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				if (!btn.getStyle().contains("-fx-background-color:#f56d6d;")) {
					Room room = new Room();
					room.setId(Integer.parseInt(btn.getText()));
					ObjectCarrior.setRoomNumber(room);
					fp1.getScene().getWindow().hide();
				}

			});
		}
		for (Node node : fp2.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				if (!btn.getStyle().contains("-fx-background-color:#f56d6d;")) {
					Room room = new Room();
					room.setId(Integer.parseInt(btn.getText()));
					ObjectCarrior.setRoomNumber(room);
					fp1.getScene().getWindow().hide();
				}
			});
		}
		for (Node node : fp3.getChildren()) {
			JFXButton btn = isJfxBUtton(node);
			int id = roomService.getStatus(Integer.parseInt(btn.getText()));
			btnLoad(id, btn);
			btn.setOnAction(e -> {
				if (!btn.getStyle().contains("-fx-background-color:#f56d6d;")) {
					Room room = new Room();
					room.setId(Integer.parseInt(btn.getText()));
					ObjectCarrior.setRoomNumber(room);
					fp1.getScene().getWindow().hide();
				}
			});
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		roomService = new RoomServiceImpl();
		availableRoom();
	}

}
