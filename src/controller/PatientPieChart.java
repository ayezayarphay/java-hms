package controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import impl.TreatmentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import serviceimpl.TreatmentService;

public class PatientPieChart implements Initializable {
	@FXML
	private PieChart pie;
	TreatmentService treatmentService;

	public void close() {
		pie.getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		treatmentService = new TreatmentServiceImpl();
		Map<String, Integer> list = treatmentService.getCureTypeTotal();
		Set<String> set = list.keySet();
		for (String string : set) {
			PieChart.Data slice1 = new PieChart.Data(string, list.get(string));
			pie.getData().add(slice1);
		}

	}
}
