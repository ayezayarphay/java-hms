package test;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

public class C extends Application {

	SVGPath path = new SVGPath();
	Circle c = new Circle(10, Color.BLUEVIOLET);
	Canvas cv = new Canvas(500, 500);
	GraphicsContext pen = cv.getGraphicsContext2D();
	PathTransition pt = new PathTransition(Duration.seconds(5), path, c);
	double x = -1, y = -1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage st) throws Exception {
		Pane root = new Pane(path, cv);

		path.setContent("m 40 60 l 50 50");
		path.setStroke(Color.BLUE);
		path.setFill(Color.TRANSPARENT);

		Scene sc = new Scene(root);
		st.setScene(sc);
		st.setTitle("Path Transition");
		st.show();
		pt.play();

		pen.setStroke(Color.RED);
		pen.setLineWidth(3);

		pt.setOnFinished(e -> {
			x = -1;
			y = -1;
		});
		pt.currentTimeProperty().addListener((ob, ov, nv) -> {
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

}
