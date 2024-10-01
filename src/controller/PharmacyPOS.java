package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import application.HMSException;
import entity.DN;
import entity.Patient;
import entity.Pharmacy;
import entity.PharmacyPOSEntity;
import entity.SaleDetails;
import entity.ShowPharmacyData;
import entity.Staff;
import entity.TreatmentDetails;
import impl.DNServiceImpl;
import impl.PatientServiceImpl;
import impl.PharmacyInventoryImpl;
import impl.SaleDetailsServiceImpl;
import impl.SaleServiceImpl;
import impl.StaffServiceImpl;
import impl.TreatmentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import serviceimpl.DNService;
import serviceimpl.PatientService;
import serviceimpl.PharmacyInventoryService;
import serviceimpl.SaleDetailsService;
import serviceimpl.SaleService;
import serviceimpl.StaffService;
import serviceimpl.TreatmentService;
import utils.LoadClass;
import utils.MemberVerify;
import utils.ObjectCarrior;

public class PharmacyPOS implements Initializable {
	@FXML
	private Label totalCost;
	@FXML
	private TableView<PharmacyPOSEntity> table;
	@FXML
	private JFXTextField patient_id;
	@FXML
	private JFXTextField pharmacy_id;
	@FXML
	private JFXTextField pharmacy_name;
	@FXML
	private JFXTextField price;
	@FXML
	private JFXTextField qty;
	@FXML
	private Label username;
	@FXML
	private Label userID;
	@FXML
	private VBox vb;
	@FXML
	private SVGPath powerOff;
	@FXML
	private SVGPath edit;
	@FXML
	private JFXTextField searchPharmacy;
	@FXML
	private JFXListView<ShowPharmacyData> list;
	@FXML
	private AnchorPane pf;
	@FXML
	private AnchorPane cp;
	@FXML
	private JFXButton add;
	@FXML
	private HBox hb;
	@FXML
	private SVGPath changePassword;
	private PatientService patientService;
	private PharmacyInventoryService pharmacyInventoryService;
	private StaffService staffService;
	private SaleService saleService;
	private SaleDetailsService saleDetailsService;
	private DNService dnService;
	private TreatmentService treatmentService;
	JFXTextField dnId = new JFXTextField();
	int index = 0;
	boolean cond = false;

	public void check() {

		checkPatientID();
		checkPharmacyID();
		if (qty.getText().isEmpty()) {
			throw new HMSException("Qty is empty");
		}

		try {
			Integer.parseInt(qty.getText());
		} catch (Exception e) {
			throw new HMSException("Qty must be number");
		}
		if (Integer.parseInt(qty.getText()) <= 0) {
			throw new HMSException("Qty cannot br zero or minus value");
		}
		if (cond) {
			isDN();
		}

		List<PharmacyPOSEntity> list = table.getItems();
		if (list != null && add.getText().equals("Add")) {
			for (PharmacyPOSEntity pharmacyPOSEntity : list) {
				if (pharmacyPOSEntity.getName().equals(pharmacy_name.getText())) {
					throw new HMSException(pharmacy_name.getText() + " is already buy.\nPlease Edit");
				}
			}
		}

	}

	public DN isDN() {
		DN dN = dnService.findByidCode(dnId.getText());
		if (dnId.getText().isEmpty()) {
			throw new HMSException("Please enter doctor ID");
		}
		if (dN == null) {
			throw new HMSException("DN ID is invalid");
		}
		return dN;
	}

	public void isQuantity(String text) {
		int qty = Integer.parseInt(text);
		int resultQty = pharmacyInventoryService.getQty(pharmacy_id.getText());
		int result = resultQty - qty;
		if (result < 0) {
			throw new HMSException(pharmacy_name.getText() + " is out of stock");
		}

	}

	public Pharmacy checkPharmacyID() {
		Pharmacy p = pharmacyInventoryService.findByIDCode(pharmacy_id.getText());
		if (pharmacy_id.getText().isEmpty()) {
			throw new HMSException("Pharmacy ID is empty");
		}

		if (p == null) {
			throw new HMSException("Pharmacy ID is invalid");
		}

		return p;
	}

	public void checkPatientID() {
		if (patient_id.getText().isEmpty()) {
			throw new HMSException("Patient ID is empty");
		}
		Patient patient = patientService.findByIdCodeBoolean(patient_id.getText());
		if (patient == null) {
			throw new HMSException("Patient ID is invalid");
		}
	}

	public void pharmacyID() {
		try {
			Pharmacy p = checkPharmacyID();
			pharmacy_name.setText(p.getName());
			price.setText(String.valueOf(p.getPrice()));
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void showProfile() {
		new LoadClass().showView("PharmacyProfile.fxml", vb);
	}

	public void patientID() throws Exception {
		try {
			checkPatientID();
			if (!patient_id.getText().equalsIgnoreCase("EP-1001")) {
				cond = true;
				dnId.setPrefSize(77, 31);
				dnId.setFont(new Font(15));
				dnId.setPromptText("Doctor ID");
				hb.getChildren().add(1, dnId);
				dnId.setOnAction(e -> {
					try {
						isDN();
						dnId.setEditable(false);
					} catch (Exception e1) {
						new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.OK).showAndWait();
					}
				});
			}
			patient_id.setEditable(false);
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void add() {

		try {
			check();
			String pharID = pharmacy_id.getText();
			String name = pharmacy_name.getText();
			double priceT = Double.parseDouble(price.getText());
			int qtyT = Integer.parseInt(qty.getText());
			PharmacyPOSEntity pos = new PharmacyPOSEntity();
			pos.setPharmacy_id(pharID);
			pos.setName(name);
			pos.setPrice(priceT);
			pos.setQty(qtyT);
			pos.setAmount(qtyT * priceT);
			if (add.getText().equals("Add")) {
				isQuantity(qty.getText());
				table.getItems().add(pos);
				pharmacyInventoryService.updateQty("-", pharmacy_id.getText(), Integer.parseInt(qty.getText()));
				if (patient_id.isEditable()) {
					patient_id.setEditable(false);
				}
			} else {
				progressEdit(pos);
				add.setText("Add");
				edit.setContent("M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z");
			}

			loadPharData();
			searchPharmacy.clear();
			setAmount();
			clear();
		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void setAmount() {
		double total = 0.0;
		List<PharmacyPOSEntity> list = table.getItems();
		for (PharmacyPOSEntity pharmacyPOSEntity : list) {
			total += pharmacyPOSEntity.getAmount();
		}
		DecimalFormat format = new DecimalFormat("#,###,###");
		String value = format.format(total);
		totalCost.setText(value);

	}

	public void signOut() {
		table.getScene().getWindow().hide();
		new LoadClass().showViewTitle("Login");
	}

	public void clear() {
		pharmacy_id.clear();
		pharmacy_name.clear();
		price.clear();
		qty.clear();
		add.setText("Add");
		edit.setContent("M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		patientService = new PatientServiceImpl();
		pharmacyInventoryService = new PharmacyInventoryImpl();
		staffService = new StaffServiceImpl();
		saleService = new SaleServiceImpl();
		saleDetailsService = new SaleDetailsServiceImpl();
		treatmentService = new TreatmentServiceImpl();
		dnService = new DNServiceImpl();
		userID.setText(MemberVerify.getStaff().getId_code());
		username.setText(MemberVerify.getStaff().getName());
		loadPharData();
		MenuItem edit = new MenuItem("Edit");
		MenuItem remove = new MenuItem("Remove");
		MenuItem buy = new MenuItem("Buy");
		table.setContextMenu(new ContextMenu(edit, remove));
		list.setContextMenu(new ContextMenu(buy));
		edit.setOnAction(e -> {
			edit();
		});
		remove.setOnAction(e -> {
			remove();
		});
		list.setOnMouseClicked(e -> {
			int count = e.getClickCount();
			if (count == 2) {
				buy();
			}
		});
		searchPharmacy.textProperty().addListener((a, b, c) -> {
			loadPharData();
		});

		Tooltip.install(changePassword, new Tooltip("Profile"));
		Tooltip.install(powerOff, new Tooltip("Sign out"));
		Tooltip.install(cp, new Tooltip("Profile"));
		Tooltip.install(pf, new Tooltip("Sign out"));
	}

	public void buy() {
		try {
			ShowPharmacyData spd = list.getSelectionModel().getSelectedItem();
			if (spd == null) {
				new Alert(AlertType.WARNING, "Please add data first or select data", ButtonType.OK).showAndWait();
			} else if (spd.getTotal() == 0) {
				new Alert(AlertType.WARNING, spd.getName() + " is out of stock", ButtonType.OK).showAndWait();
			} else {
				Pharmacy p = pharmacyInventoryService.findByIDCode(spd.getId_code());

				pharmacy_id.setText(p.getId_code());
				pharmacy_name.setText(p.getName());
				price.setText(String.valueOf(p.getPrice()));
				searchPharmacy.clear();
			}

		} catch (Exception e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
		}
	}

	public void loadPharData() {
		List<ShowPharmacyData> list = pharmacyInventoryService.findPharmacy(searchPharmacy.getText());
		this.list.getItems().clear();
		this.list.getItems().addAll(list);
	}

	public void edit() {
		PharmacyPOSEntity pos = table.getSelectionModel().getSelectedItem();
		if (pos == null) {
			new Alert(AlertType.WARNING, "Please add data first or select data", ButtonType.OK).showAndWait();
		} else {
			index = table.getSelectionModel().getSelectedIndex();
			pharmacy_id.setText(pos.getPharmacy_id());
			pharmacy_name.setText(pos.getName());
			price.setText(String.valueOf(pos.getPrice()));
			qty.setText(String.valueOf(pos.getQty()));
			add.setText("Edit");
			edit.setContent(
					"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z");
			qty.requestFocus();
		}

	}

	public void progressEdit(PharmacyPOSEntity pos) {
		int count = 0;
		List<PharmacyPOSEntity> posList = table.getItems();
		List<PharmacyPOSEntity> list = new ArrayList<PharmacyPOSEntity>();
		for (PharmacyPOSEntity pharmacyPOSEntity : posList) {
			if (count == index) {
				list.add(pos);
				int num1 = pos.getQty();
				int num2 = pharmacyPOSEntity.getQty();
				int result = num2 - num1;
				System.out.println(result);
				if (result > 0) {
					pharmacyInventoryService.updateQty("+", pos.getPharmacy_id(), result);
				} else if (result < 0) {
					isQuantity(String.valueOf(-1 * result));
					pharmacyInventoryService.updateQty("-", pos.getPharmacy_id(), -1 * result);
				}

			} else {
				list.add(pharmacyPOSEntity);
			}
			count++;

		}
		table.getItems().clear();
		table.getItems().addAll(list);

	}

	public void remove() {
		List<PharmacyPOSEntity> list = table.getItems();
		PharmacyPOSEntity pos = table.getSelectionModel().getSelectedItem();
		if (list.isEmpty() || pos == null) {
			new Alert(AlertType.WARNING, "Please add data first or select data", ButtonType.OK).showAndWait();

		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure want to remove?", ButtonType.YES,
					ButtonType.NO);
			Optional<ButtonType> btn = alert.showAndWait();
			if (btn.get() == ButtonType.YES) {

				pharmacyInventoryService.updateQty("+", pos.getPharmacy_id(), pos.getQty());
				loadPharData();
				int index = table.getSelectionModel().getSelectedIndex();
				table.getItems().remove(index);
				setAmount();
			} else {
				alert.close();
			}
		}

	}

	public void addTreatment() {
		TreatmentDetails treatmentDetails = new TreatmentDetails();
		int t_id = treatmentService.getTreatmentID(patient_id.getText(), LocalDate.now(), dnId.getText());
		List<TreatmentDetails> list = new ArrayList<TreatmentDetails>();
		List<PharmacyPOSEntity> ll = table.getItems();
		for (PharmacyPOSEntity pharmacyPOSEntity : ll) {
			treatmentDetails.setT_id(t_id);
			Pharmacy phar = new Pharmacy();
			phar.setId_code(pharmacyPOSEntity.getPharmacy_id());
			int phar_id = pharmacyInventoryService.getPharmacyID(phar);
			treatmentDetails.setPhar_id(phar_id);
			treatmentDetails.setQty(pharmacyPOSEntity.getQty());
			treatmentDetails.setDate(LocalDate.now());
			list.add(treatmentDetails);
		}
		ObjectCarrior.setList(ll);
		treatmentService.insertTreatmentDetails(list);
	}

	public void voucher() {
		cond = false;
		if (patient_id.getText().equals("EP-1001")) {

			addSaleDetails();
		} else {
			addTreatment();
		}
		patient_id.getScene().getWindow().hide();
		new LoadClass().showViewStage("Receipt.fxml");

	}

	public void addSaleDetails() {
		Patient p = new Patient();
		p.setP_code(patient_id.getText());
		int p_id = patientService.getID(p);
		Staff staff = new Staff();
		staff.setId_code(userID.getText());
		int staff_id = staffService.getID(staff);
		int s_id = saleService.insert(staff_id, p_id);
		List<PharmacyPOSEntity> list = table.getItems();
		if (list.isEmpty()) {
			new Alert(AlertType.ERROR, "Please add data first", ButtonType.OK).showAndWait();
		} else {
			ObjectCarrior.setList(list);
			List<SaleDetails> sdL = new ArrayList<SaleDetails>();
			for (PharmacyPOSEntity pharmacyPOSEntity : list) {
				Pharmacy phar = new Pharmacy();
				phar.setId_code(pharmacyPOSEntity.getPharmacy_id());
				int phar_id = pharmacyInventoryService.getPharmacyID(phar);
				SaleDetails sd = new SaleDetails();
				sd.setSaleId(s_id);
				sd.setPharmacyId(phar_id);
				sd.setQty(pharmacyPOSEntity.getQty());
				sd.setDate(LocalDate.now());
				sdL.add(sd);
			}
			saleDetailsService.insert(sdL);
		}
	}

	public void delete() {
		List<PharmacyPOSEntity> list = table.getItems();
		if (list.isEmpty() && patient_id.isEditable()) {
			new Alert(AlertType.ERROR, "Please add data first", ButtonType.OK).showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure want to delete?", ButtonType.YES,
					ButtonType.NO);
			Optional<ButtonType> btn = alert.showAndWait();
			if (btn.get() == ButtonType.YES) {
				patient_id.clear();
				patient_id.setEditable(true);
				clear();
				if (cond) {
					hb.getChildren().remove(1);
					cond = false;
				}
				for (PharmacyPOSEntity pos : list) {
					pharmacyInventoryService.updateQty("+", pos.getPharmacy_id(), pos.getQty());
				}
				loadPharData();
				table.getItems().clear();
				totalCost.setText("0");
				add.setText("Add");
			} else {
				alert.close();
			}
		}
	}
}
