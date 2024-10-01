package application;


import controller.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.CreateDatabase;

public class HMSApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = null;
		if (!CreateDatabase.check()) {
			CreateDatabase.createDatabaseAndTable();
			CreateDatabase.createRoom();
			root = FXMLLoader.load(Login.class.getResource("FirstAdmin.fxml"));
			stage.setTitle("Admin Register");
		} else {
			root = FXMLLoader.load(Login.class.getResource("Login.fxml"));
			stage.setTitle("Login");
		}
		stage.getIcons().add(new Image(HMSApplication.class.getResourceAsStream("/img/logo.png")));
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
