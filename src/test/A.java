package test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author Cool IT Help
 */
public class A implements Initializable {
	@FXML
	private Label label;
	@FXML
	private ProgressBar progressBar;
	Task<?> copyWorker;

	@FXML
	private void handleButtonAction(ActionEvent event) {
		progressBar.setProgress(0.0);
		copyWorker = createWorker();
		progressBar.progressProperty().unbind();
		progressBar.progressProperty().bind(copyWorker.progressProperty());
		copyWorker.messageProperty().addListener((a, b, c) -> {
			System.out.println(c);
			label.setText(c);
		});
		new Thread(copyWorker).start();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	public Task<?> createWorker() {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {
				int index = 0;
				for (int i = 0; i < 10; i++) {
					Thread.sleep(2000);
					updateMessage("Task Completed : " + ((i * 10) + 10) + "%");
					if (i == index) {
						System.out.println(i);
					}
					updateProgress(i + 1, 10);
					index++;
				}
				return true;
			}
		};
	}
}