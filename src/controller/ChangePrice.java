package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import impl.RoomServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import serviceimpl.RoomService;

public class ChangePrice implements Initializable {
	@FXML
	private JFXTextField from;
	@FXML
	private JFXTextField to;
	@FXML
	private JFXTextField price;
	@FXML
	private Label original;
	@FXML
	private Label current;
	private RoomService roomService;

	public void check() {
		if (from.getText().isEmpty()) {
			throw new HMSException("Please enter start room");
		}
		try {
			Integer.parseInt(from.getText());
		} catch (Exception e) {
			throw new HMSException("Please enter digit");
		}
		if (to.getText().isEmpty()) {
			throw new HMSException("Please enter end room");
		}
		if (Integer.parseInt(from.getText()) > Integer.parseInt(to.getText())) {
			throw new HMSException("Start room cannot be big");
		}
	}

	public void change() {
		try {
			check();
			int fromT = Integer.parseInt(from.getText());
			int toT = Integer.parseInt(to.getText());
			toSetText();
			if (price.getText().isEmpty()) {
				throw new HMSException("Please enter price");
			}
			try {
				Integer.parseInt(price.getText());
			} catch (Exception e) {
				throw new HMSException("Please enter digit");
			}
			double pri = Double.parseDouble(price.getText());

			roomService.updatePrice(fromT, toT, pri);
			new Alert(AlertType.INFORMATION, "Success", ButtonType.OK).showAndWait();
			close();

		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void toSetText() {
		int fromT = Integer.parseInt(from.getText());
		int toT = Integer.parseInt(to.getText());
		try {
			check();
			String text = roomService.getOrigionalPrice(fromT, toT);
			original.setText(text);
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void close() {
		original.getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		roomService = new RoomServiceImpl();
		price.textProperty().addListener((a, b, c) -> {
			try {
				Integer.parseInt(c);
			} catch (Exception e) {
				new Alert(AlertType.ERROR, "Please enter digit", ButtonType.OK).showAndWait();
			}
			current.setText(c);
		});
	}
}
