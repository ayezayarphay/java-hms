package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entity.PharmacyPOSEntity;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import utils.LoadClass;
import utils.ObjectCarrior;

public class Receipt implements Initializable {
	@FXML
	private FlowPane tile;
	@FXML
	private JFXTextField total;
	@FXML
	private JFXTextField cash;
	@FXML
	private JFXTextField charge;
	@FXML
	private VBox vb;
	DecimalFormat formatter = new DecimalFormat("#,###,###");

	public void OK() {
		total.getScene().getWindow().hide();
		new LoadClass().showViewStage("PharmacyPOS.fxml");
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
		double total = 0;
		tran(vb);
		int count = 1;
		List<PharmacyPOSEntity> list = ObjectCarrior.getList();
		if (list != null) {
			for (PharmacyPOSEntity pharmacyPOSEntity : list) {

				Label no = new Label();
				no.setPrefSize(38, 90);
				no.setFont(new Font(15));
				no.setStyle("-fx-font-weight: bold;");
				no.setAlignment(Pos.TOP_LEFT);
				no.setText(String.valueOf(count));

				Label name = new Label();
				name.setPrefSize(214, 90);
				name.setFont(new Font(15));
				name.setStyle("-fx-font-weight: bold;");
				name.setAlignment(Pos.TOP_LEFT);
				name.setWrapText(true);
				name.setText(pharmacyPOSEntity.getName());

				Label qty = new Label();
				qty.setPrefSize(38, 90);
				qty.setStyle("-fx-font-weight: bold;");
				qty.setFont(new Font(15));
				qty.setAlignment(Pos.TOP_LEFT);
				qty.setPrefSize(83, 95);
				qty.setText(String.valueOf(pharmacyPOSEntity.getQty()));

				Label price = new Label();
				price.setPrefSize(80, 90);
				price.setFont(new Font(15));
				price.setAlignment(Pos.TOP_LEFT);
				price.setStyle("-fx-font-weight: bold;");
				price.setPrefSize(83, 95);
				price.setText(String.valueOf(pharmacyPOSEntity.getPrice()));

				Label amt = new Label();
				amt.setPrefSize(165, 90);
				amt.setFont(new Font(15));
				amt.setAlignment(Pos.TOP_LEFT);
				amt.setStyle("-fx-font-weight: bold;");
				amt.setPrefSize(83, 95);
				amt.setText(String.valueOf(pharmacyPOSEntity.getAmount()));

				tile.getChildren().addAll(no, name, qty, price, amt);
				count++;
				total += pharmacyPOSEntity.getAmount();
			}
			String format = formatter.format(total);
			this.total.setText(format);
		}
	}

	private void tran(VBox tp) {
		FadeTransition ft = new FadeTransition(Duration.seconds(2), tp);
		ft.setFromValue(0.05);
		ft.setToValue(1);
		ft.play();
	}
}
