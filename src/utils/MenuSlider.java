package utils;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MenuSlider {
	// private String currentPage = "";

	public void menuSlider(JFXHamburger ham, JFXDrawer drawer, VBox vb2, String fxmlFile, String currentPage) {
		HamburgerBasicCloseTransition b = new HamburgerBasicCloseTransition(ham);

		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("/controller/".concat(fxmlFile)));
			drawer.setSidePane(root);

			for (Node node : root.getChildren()) {
				if (node instanceof VBox) {
					VBox vb = (VBox) node;
					for (Node node2 : vb.getChildren()) {
						if (node2 instanceof AnchorPane) {
							AnchorPane ap = (AnchorPane) node2;
							for (Node node3 : ap.getChildren()) {
								if (node3 instanceof Label) {

									Label l = (Label) node3;
									if (l.getText().equals(currentPage)) {
										ap.setStyle(ap.getStyle() + "-fx-background-color:darkblue");
									}
									l.setOnMouseClicked(e -> {
										String text = l.getText();
										load(text, vb2, fxmlFile, currentPage);
										drawer.close();
										b.setRate(b.getRate() * -1);
										b.play();
									});
								}
							}
						}
					}

				}
			}

			b.setRate(-1);
			ham.setOnMouseClicked(e -> {
				b.setRate(b.getRate() * -1);
				b.play();
				if (drawer.isClosed()) {
					drawer.open();
				} else {
					drawer.close();
				}
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void load(String fxml, VBox vb, String fxmlFile, String currentPage) {
		if (!currentPage.equals(fxml)) {
			if (fxmlFile.equals("ReceptionMenu.fxml") && fxml.equals("Home")) {
				new LoadClass().showView("ReceptionHome.fxml", vb);
			} else if (fxmlFile.equals("PharmacyMenu.fxml") && fxml.equals("Home")) {
				new LoadClass().showView("PharmacyHome.fxml", vb);
			} else if (fxmlFile.equals("AdminMenu.fxml") && fxml.equals("Home")) {
				new LoadClass().showView("AdminHome.fxml", vb);
			} else if (fxmlFile.equals("AdminMenu.fxml") && fxml.equals("Pharmacy")) {
				new LoadClass().showView("AdminPharmacyList.fxml", vb);
			} else if (fxmlFile.equals("AdminMenu.fxml") && fxml.equals("Employee")) {
				new LoadClass().showView("EmployeeList.fxml", vb);
			} else if (fxmlFile.equals("AdminMenu.fxml") && fxml.equals("Patient")) {
				new LoadClass().showView("AdminPatientList.fxml", vb);
			} else if (fxmlFile.equals("AdminMenu.fxml") && fxml.equals("Room")) {
				new LoadClass().showView("AdminRoomList.fxml", vb);
			} else if (fxmlFile.equals("AdminMenu.fxml") && fxml.equals("Profile")) {
				new LoadClass().showView("Profile.fxml", vb);
			} else if (fxmlFile.equals("AdminMenu.fxml") && fxml.equals("Schedule")) {
				new LoadClass().showView("AdminScheduleList.fxml", vb);
			} else if (fxmlFile.equals("AdminMenu.fxml") && fxml.equals("Appointment")) {
				new LoadClass().showView("AdminAppointmentList.fxml", vb);
			} else if (fxmlFile.equals("AdminMenu.fxml") && fxml.equals("Sale History")) {
				new LoadClass().showView("AdminSalelHistoryList.fxml", vb);
			} else if (fxmlFile.equals("ReceptionMenu.fxml") && fxml.equals("Patient")) {
				new LoadClass().showView("PatientList.fxml", vb);
			} else if (fxmlFile.equals("ReceptionMenu.fxml") && fxml.equals("Appointment")) {
				new LoadClass().showView("PatientAppointmentList.fxml", vb);
			} else if (fxmlFile.equals("ReceptionMenu.fxml") && fxml.equals("Payment")) {
				new LoadClass().showView("TreatmentList.fxml", vb);
			} else if (fxmlFile.equals("ReceptionMenu.fxml") && fxml.equals("Schedule")) {
				new LoadClass().showView("ReceptionDoctorScheduleList.fxml", vb);
			} else if (fxmlFile.equals("ReceptionMenu.fxml") && fxml.equals("Room")) {
				new LoadClass().showView("ReceptionRoomList.fxml", vb);
			} else if (fxmlFile.equals("Profile.fxml") && fxml.equals("Profile")) {
				new LoadClass().showView("ReceptionRoomList.fxml", vb);
			} else if (fxml.equals("Sign out")) {
				vb.getScene().getWindow().hide();
				new LoadClass().showViewTitle("Login");
			}
		}
	}
}
