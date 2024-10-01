package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entity.Treatment;
import impl.TreatmentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import serviceimpl.TreatmentService;
import utils.ObjectCarrior;

public class HospitalPayment implements Initializable {
	@FXML
	private Label pID;

	@FXML
	private JFXTextField serviceName;
	@FXML
	private JFXTextField servicePrice;
	@FXML
	private VBox vb;
	@FXML
	private VBox mainVb;
	private TreatmentService treatmentService;
	Treatment t = null;

	public void close() {
		pID.getScene().getWindow().hide();
	}

	public void showView() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Payment.fxml"));
			mainVb.getChildren().clear();
			mainVb.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add() {
		SVGPath remove = new SVGPath();
		remove.setContent("M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z");
		remove.setScaleX(1.3);
		remove.setScaleY(1.3);
		remove.setCursor(Cursor.HAND);
		JFXTextField t1 = new JFXTextField();
		t1.setPrefSize(225, 25);
		t1.setPromptText("Service name");
		JFXTextField t2 = new JFXTextField();
		t2.setPrefSize(80, 25);
		t2.setPromptText("Service price");
		HBox hb = new HBox(10, t1, t2, remove);
		hb.setPadding(new Insets(10, 10, 10, 10));
		hb.setAlignment(Pos.TOP_CENTER);
		vb.getChildren().add(hb);
		remove.setOnMouseClicked(e -> {
			Node node = remove.getParent();
			Node node2 = node.getParent();
			change(node, node2);
		});
	}

	public void change(Node n, Node n2) {
		HBox v = (HBox) n;
		VBox vv = (VBox) n2;
		for (int i = 0; i < vv.getChildren().size(); i++) {
			if (vv.getChildren().get(i).equals(v)) {
				vv.getChildren().remove(v);
			}
		}
	}

	public void voucher() {
		int cond = 1;
		boolean con = true;
		List<Label> list = new ArrayList<Label>();
		list.add(new Label(serviceName.getText()));
		list.add(new Label(servicePrice.getText()));
		for (Node node : vb.getChildren()) {
			if (node instanceof HBox) {
				HBox hb = (HBox) node;
				for (Node node2 : hb.getChildren()) {
					if (node2 instanceof JFXTextField) {
						JFXTextField jfxtext = (JFXTextField) node2;
						list.add(new Label(jfxtext.getText()));
					}
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			String text = list.get(i).getText();
			if (cond == 1) {
				if (text.isEmpty()) {
					con = false;
					new Alert(AlertType.ERROR, "Service name cannot be empty", ButtonType.OK).showAndWait();
					break;
				}
				cond = 2;
			} else if (cond == 2) {
				if (text.isEmpty()) {
					con = false;
					new Alert(AlertType.ERROR, "Service price cannot be empty", ButtonType.OK).showAndWait();
					break;
				} else {
					try {
						Integer.parseInt(text);
					} catch (Exception e) {
						con = false;
						new Alert(AlertType.ERROR, "Service price must be digit", ButtonType.OK).showAndWait();
						break;
					}
				}
				cond = 1;
			}

		}
		if (con) {
			Label l1 = new Label("Total Stay");
			LocalDate date1 = t.getDate();
			LocalDate date2 = t.getDateObj().getValue();
			long duration = ChronoUnit.DAYS.between(date1, date2);
			Label l2 = new Label(String.valueOf(duration));
			list.add(l1);
			list.add(l2);
			ObjectCarrior.setLabelList(list);
			ObjectCarrior.setDuration(duration);
			showView();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		treatmentService = new TreatmentServiceImpl();
		t = ObjectCarrior.getTreatment();
		if (t != null) {
			pID.setText(String.valueOf(treatmentService.getTemporaryID(t)));
		}
	}
}
