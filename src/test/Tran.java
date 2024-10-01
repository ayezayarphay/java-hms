package test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Tran extends Application implements Initializable {
	@FXML
	private Label lab;
	@FXML
	private VBox vb;
	double x = -1, y = -1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Circle c = new Circle(10, Color.BLUEVIOLET);
		SVGPath path = new SVGPath();
		Canvas cv = new Canvas(500, 500);
		GraphicsContext pen = cv.getGraphicsContext2D();
		PathTransition pt2 = new PathTransition(Duration.seconds(5), path, c);
		pen.setStroke(Color.RED);
		pen.setLineWidth(3);
		path.setContent("M100,0 H170Z");
		pt2.play();
		vb.getChildren().addAll(path, cv);
		TranslateTransition tt = new TranslateTransition(Duration.seconds(1.5), lab);
		tt.setFromX(-100);
		tt.setToX(0);
		FadeTransition ft = new FadeTransition(Duration.seconds(3), lab);
		ft.setFromValue(0);
		ft.setToValue(1);
		ParallelTransition pt = new ParallelTransition(tt, ft);
		pt.play();
		pt2.currentTimeProperty().addListener((ob, ov, nv) -> {
			if (x == -1) {
				x = c.getTranslateX();
				y = c.getTranslateY();
			} else {
				double x2 = c.getTranslateX(), y2 = c.getTranslateY();
				pen.strokeLine(x, y, x2, y2);
				x = x2;
				y = y2;
			}
		});

	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Tran.fxml"));
		stage.setScene(new Scene(root));
		stage.show();

	}
}
