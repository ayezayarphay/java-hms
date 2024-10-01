package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXDatePicker;

import entity.PharmacySaleHistory;
import impl.SaleDetailsServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import serviceimpl.SaleDetailsService;

public class AdminPharmacySaleHistoryList implements Initializable {

	@FXML
	private JFXDatePicker date;
	@FXML
	private TableView<PharmacySaleHistory> table;
	@FXML
	private BarChart<String, Double> chart;
	private SaleDetailsService saleDetailsService;

	public void print() throws IOException {
		List<PharmacySaleHistory> list = table.getItems();

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		File file = fileChooser.showSaveDialog(table.getScene().getWindow());
		file.createNewFile();
		String path = file.getAbsolutePath();
		if (path.lastIndexOf(".txt") > 0) {
			for (PharmacySaleHistory app : list) {
				String text = app.toString() + "\n";
				Files.write(Paths.get(file.getAbsolutePath()), text.getBytes(), StandardOpenOption.APPEND);
			}
			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		} else {
			String title = "StaffID,PatientID,PharmacyName,Price,Qty,Total\n";
			String text = "";
			Files.write(Paths.get(file.getAbsolutePath()), title.getBytes(), StandardOpenOption.APPEND);
			for (PharmacySaleHistory app : list) {
				text += app.toString() + "\n";
			}
			Files.write(Paths.get(file.getAbsolutePath()), text.getBytes(), StandardOpenOption.APPEND);

			new Alert(AlertType.INFORMATION, "File create success\nLocation:" + file.getAbsolutePath(), ButtonType.OK)
					.showAndWait();
		}
	}

	public void clear() {
		date.setValue(null);
		date.setPromptText("Choose date");
		load();
	}

	public void load() {
		List<PharmacySaleHistory> list = saleDetailsService.getPharmacySaleSummary();
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void findAll() {

		List<PharmacySaleHistory> list = saleDetailsService.findDate(date.getValue());
		table.getItems().clear();
		table.getItems().addAll(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		saleDetailsService = new SaleDetailsServiceImpl();

		date.valueProperty().addListener((a, b, c) -> {
			findAll();
		});
		load();
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		MenuItem chart = new MenuItem("Chart");
		table.setContextMenu(new ContextMenu(chart));
		chart.setOnAction(e -> {
			this.chart.getData().clear();
			List<PharmacySaleHistory> list = table.getSelectionModel().getSelectedItems();
			if (list == null) {
				new Alert(AlertType.ERROR, "Please select data", ButtonType.OK).showAndWait();
			} else {
				setBarchat(list);
			}
		});

	}

	private void setBarchat(List<PharmacySaleHistory> list) {

		Map<String, Double> map = new LinkedHashMap<String, Double>();
		for (PharmacySaleHistory pharmacySaleHistory : list) {
			map.put(pharmacySaleHistory.getDate().toString(), pharmacySaleHistory.getTotal());
		}

		Set<String> set = map.keySet();
		for (String string : set) {
			XYChart.Series<String, Double> series = new Series<>();
			series.setName(string);
			series.getData().add(new XYChart.Data<String, Double>(string, map.get(string)));
			chart.getData().add(series);
		}

	}
}
