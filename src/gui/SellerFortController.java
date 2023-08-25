package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbExeption;
import entities.Departament;
import entities.Seller;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.exceptions.ValidationException;
import model.services.DepartamentService;
import model.services.SellerService;

public class SellerFortController implements Initializable {

	private SellerService service;
	private DepartamentService depService;
	private Seller entity;
	private List<DataChangeListener> dataChangeListener = new ArrayList<>();

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;
	@FXML
	private DatePicker dpBirthDate;
	@FXML
	private Label labelErroForData;
	@FXML
	private Label labelErroForEmail;
	@FXML
	private Label labelErroForBaseSalary;
	@FXML
	private TextField txtbaseSalary;
	@FXML
	private ComboBox<Departament> comboBoxDepartment;
	@FXML
	private Label labelErroForName;
	@FXML
	private Button btCancel;
	@FXML
	private Button btSave;

	private ObservableList<Departament> obsList;

	public void setServices(SellerService service, DepartamentService depService) {
		this.service = service;
		this.depService = depService;
	}

	public void setEntity(Seller entity) {
		this.entity = entity;
	}

	public void subcribeDataChangeListener(DataChangeListener listener) {
		dataChangeListener.add(listener);

	}

	@FXML
	public void onBtSave(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity war null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());

		} catch (DbExeption e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);

		}
	}

	private void notifyChangeListeners() {
		for (DataChangeListener listener : dataChangeListener) {
			listener.onDataChanged();
		}

	}

	private Seller getFormData() {
		Seller obj = new Seller();

		ValidationException exception = new ValidationException("Validation exception");

		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().isEmpty()) {
			exception.addError("name", "Field can't be empty");
		}

		obj.setName(txtName.getText());

		if (txtEmail.getText() == null || txtEmail.getText().trim().isEmpty()) {
			exception.addError("email", "Field can't be empty");
		}

		obj.setEmail(txtEmail.getText());

		if (dpBirthDate.getValue() == null) {
			exception.addError("birthDate", "Field can't be empty");
		} else {
			Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setBirthDate(Date.from(instant));
		}

		if (txtbaseSalary.getText() == null || txtbaseSalary.getText().trim().isEmpty()) {
			exception.addError("baseSalary", "Field car´t be empty");
		}
		obj.setBaseSalary(Utils.tryParseToDouble(txtbaseSalary.getText()));
		
		obj.setDepartaement(comboBoxDepartment.getValue());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}

	@FXML
	public void onBtCancel(ActionEvent event) {
		Utils.currentStage(event).close();
		;
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity is null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		txtbaseSalary.setText(String.format("$.2f", entity.getBaseSalary()));
		if (entity.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
		if (entity.getDepartaement() == null) {
			comboBoxDepartment.getSelectionModel().selectFirst();
		} else {
			comboBoxDepartment.setValue(entity.getDepartaement());
		}
	}

	public void loardAssociaterObjects() {
		if (depService == null) {
			throw new IllegalStateException("Department was null");
		}
		List<Departament> list = depService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxDepartment.setItems(obsList);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializeNodes();

	}

	private void inicializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldDouble(txtbaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		initializeComboBoxDepartment();

	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelErroForName.setText(fields.contains("name") ? errors.get("name") : "");
		labelErroForEmail.setText(fields.contains("email") ? errors.get("Email") : "");
		labelErroForData.setText(fields.contains("birthDate") ? errors.get("birthDate") : "");
		labelErroForBaseSalary.setText(fields.contains("baseSalary") ? errors.get("baseSalary") : "");

	}

	private void initializeComboBoxDepartment() {
		Callback<ListView<Departament>, ListCell<Departament>> factory = lv -> new ListCell<Departament>() {
			@Override
			protected void updateItem(Departament item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxDepartment.setCellFactory(factory);
		comboBoxDepartment.setButtonCell(factory.call(null));
	}

}
