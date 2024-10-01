package utils;

import java.io.IOException;
import java.util.Optional;

import application.HMSApplication;
import controller.Login;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LoadClass {
	public void showMenu(String fxml, AnchorPane ap) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("/controller/".concat(fxml)));
			TranslateTransition tt = new TranslateTransition(Duration.seconds(1), root);
			ap.getChildren().add(root);
			tt.setFromX(-380);
			tt.setToX(0);
			tt.play();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showViewStage(String fxml) {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/controller/".concat(fxml)));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image(HMSApplication.class.getResourceAsStream("/img/logo.png")));
			stage.setScene(new Scene(root));
			stage.setOnCloseRequest(e -> {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure want to exit?", ButtonType.YES,
						ButtonType.NO);
				Optional<ButtonType> op = alert.showAndWait();
				if (op.get() == ButtonType.NO) {
					e.consume();
					alert.close();
				}
			});
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showView(String fxml, VBox v) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/controller/".concat(fxml)));

			v.getChildren().clear();
			v.getChildren().add(root);

			tran(v);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tran(Node vb) {
		FadeTransition ft = new FadeTransition(Duration.seconds(1), vb);
		ft.setFromValue(0.05);
		ft.setToValue(1);
		ft.play();
	}

	public void showViewTitle(String title) {
		Stage stage = new Stage();
		try {
			Parent root = FXMLLoader.load(Login.class.getResource("Login.fxml"));
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image(HMSApplication.class.getResourceAsStream("/img/logo.png")));
			stage.setResizable(false);
			stage.setTitle(title);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
