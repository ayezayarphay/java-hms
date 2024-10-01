package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entity.Treatment;
import impl.DNServiceImpl;
import impl.PaymentDetailsServiceImpl;
import impl.PaymentServiceImpl;
import impl.RoomServiceImpl;
import impl.TreatmentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import serviceimpl.DNService;
import serviceimpl.PaymentDetailsService;
import serviceimpl.PaymentService;
import serviceimpl.RoomService;
import serviceimpl.TreatmentService;
import utils.ObjectCarrior;

public class Payment implements Initializable {
	@FXML
	private Label pID;
	@FXML
	private Label date;
	@FXML
	private VBox vb;
	@FXML
	private JFXTextField total;
	@FXML
	private JFXTextField cash;
	@FXML
	private JFXTextField charge;
	private DNService dnService;
	private TreatmentService treatmentService;
	private PaymentService paymentService;
	private PaymentDetailsService paymentDetailsService;
	private RoomService roomService;
	DecimalFormat formatter = new DecimalFormat("#,###,###");
	double total1 = 0;
	int roomNumber = 0;
	double salary = 0;
	Treatment t = null;

	public void ok() {
		int pay_id = paymentService.insert(LocalDate.now(), salary, Integer.parseInt(pID.getText()));
		List<Label> list = ObjectCarrior.getLabelList();
		for (int i = 1; i < list.size(); i += 2) {
			Label l1 = list.get(i - 1);
			Label l2 = list.get(i);
			String getCost = l2.getText();
			if (getCost.contains(",")) {
				getCost = getCost.replace(",", "");
			}
			double num = Double.parseDouble(getCost);
			paymentDetailsService.insert(pay_id, l1.getText(), num);
		}
		treatmentService.updateCash(t);
		roomService.updateRoomStatus(roomNumber, 0);
		ObjectCarrior.setLabelList(null);
		total.getScene().getWindow().hide();
	}

	public void cash() {
		if (cash.getText().isEmpty()) {
			new Alert(AlertType.WARNING, "Please enter cash", ButtonType.OK).showAndWait();
		} else {
			String getCost = total.getText();
			if (getCost.contains(",")) {
				getCost = getCost.replace(",", "");
			}
			double get = Double.parseDouble(cash.getText());
			double totalT = Double.parseDouble(getCost);
			double result = get - totalT;
			if (result < 0) {
				String text = String.valueOf(result).replace("-", "");
				new Alert(AlertType.WARNING, "Patient need to pay " + text, ButtonType.OK).showAndWait();
			} else {
				charge.setText(String.valueOf(result));
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		dnService = new DNServiceImpl();
		treatmentService = new TreatmentServiceImpl();
		paymentService = new PaymentServiceImpl();
		paymentDetailsService = new PaymentDetailsServiceImpl();
		roomService = new RoomServiceImpl();
		t = ObjectCarrior.getTreatment();
		if (t != null) {
			pID.setText(String.valueOf(treatmentService.getTemporaryID(t)));
			salary = dnService.getSalary(t.getDn_idcode());
			roomNumber = t.getR_id();
			ObjectCarrior.setTreatment(null);
		}

		date.setText(LocalDate.now().toString());

		List<Label> list = ObjectCarrior.getLabelList();
		if (list != null) {
			for (int i = 1; i < list.size(); i += 2) {
				HBox hb = new HBox(10);
				hb.setAlignment(Pos.TOP_CENTER);
				Label l1 = list.get(i - 1);
				Label l2 = list.get(i);
				double num = Double.parseDouble(l2.getText());
				total1 += num;
				l2.setText(formatter.format(num));
				l1.setFont(new Font(13));
				l1.setStyle("-fx-font-weight: bold;");
				l1.setAlignment(Pos.TOP_LEFT);
				l1.setPrefWidth(100);
				l2.setFont(new Font(13));
				l2.setStyle("-fx-font-weight: bold;");
				l2.setAlignment(Pos.TOP_LEFT);
				l2.setPrefWidth(100);
				hb.getChildren().addAll(l1, l2);
				vb.getChildren().add(hb);
			}

			Label l1 = new Label("Doctor Price");
			Label l2 = new Label(String.valueOf(salary));
			double num = Double.parseDouble(l2.getText());
			total1 += num;
			l2.setText(formatter.format(num));
			l1.setFont(new Font(13));
			l1.setStyle("-fx-font-weight: bold;");
			l1.setAlignment(Pos.TOP_LEFT);
			l1.setPrefWidth(100);
			l2.setFont(new Font(13));
			l2.setStyle("-fx-font-weight: bold;");
			l2.setAlignment(Pos.TOP_LEFT);
			l2.setPrefWidth(100);
			Label l3 = new Label("Room Price");
			double price = roomService.getPrice(roomNumber);
			Label l4 = new Label(String.valueOf(price));
			double num2 = Double.parseDouble(l4.getText());
			l4.setText(formatter.format(num2));
			l3.setFont(new Font(13));
			l3.setStyle("-fx-font-weight: bold;");
			l3.setAlignment(Pos.TOP_LEFT);
			l3.setPrefWidth(100);
			l4.setFont(new Font(13));
			l4.setStyle("-fx-font-weight: bold;");
			l4.setAlignment(Pos.TOP_LEFT);
			l4.setPrefWidth(100);
			HBox hb2 = new HBox(10, l1, l2);
			HBox hb3 = new HBox(10, l3, l4);
			hb2.setAlignment(Pos.TOP_CENTER);
			hb3.setAlignment(Pos.TOP_CENTER);
			vb.getChildren().addAll(hb2, hb3);
			long duration = ObjectCarrior.getDuration();
			ObjectCarrior.setDuration(0);
			if (duration != 0) {
				Label l5 = new Label("Total Stay Price");
				Label l6 = new Label(String.valueOf(price + "x" + duration + "=" + (price * duration)));
				double num3 = num2 * duration;
				total1 += num3;
				l6.setText(formatter.format(num3));
				l5.setFont(new Font(13));
				l5.setStyle("-fx-font-weight: bold;");
				l5.setAlignment(Pos.TOP_LEFT);
				l5.setPrefWidth(100);
				l6.setFont(new Font(13));
				l6.setStyle("-fx-font-weight: bold;");
				l6.setAlignment(Pos.TOP_LEFT);
				l6.setPrefWidth(100);

				HBox hb4 = new HBox(10, l5, l6);

				hb4.setAlignment(Pos.TOP_CENTER);
				vb.getChildren().addAll(hb4);
			}

		}
		total.setText(String.valueOf(formatter.format(total1)));
	}
}
