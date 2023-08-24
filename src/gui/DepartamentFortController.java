	package gui;
	
	import java.net.URL;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;
	import java.util.ResourceBundle;
	import java.util.Set;
	
	import db.DbExeption;
	import entities.Departament;
	import gui.listener.DataChangeListener;
	import gui.util.Alerts;
	import gui.util.Constraints;
	import gui.util.Utils;
	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.fxml.Initializable;
	import javafx.scene.control.Alert.AlertType;
	import javafx.scene.control.Button;
	import javafx.scene.control.Label;
	import javafx.scene.control.TextField;
	import model.exceptions.ValidationException;
	import model.services.DepartamentService;
	
	public class DepartamentFortController implements Initializable {
	
		private DepartamentService service;
		private Departament entity;
		private List<DataChangeListener> dataChangeListener = new ArrayList<>();
	
		@FXML
		private TextField txtId;
		@FXML
		private TextField txtName;
		@FXML
		private Label labelErroForName;
		@FXML
		private Button btCancel;
		@FXML
		private Button btSave;
	
		public void setService(DepartamentService service) {
			this.service = service;
		}
	
		public void setEntity(Departament entity) {
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
	
		private Departament getFormData() {
			Departament obj = new Departament();
	
			ValidationException exception = new ValidationException("Validation exception");
	
			obj.setId(Utils.tryParseToInt(txtId.getText()));
	
			if (txtName.getText() == null || txtName.getText().trim().isEmpty()) {
				exception.addError("name", "Field can't be empty");
			}
	
			obj.setNome(txtName.getText());
	
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
			txtName.setText(entity.getNome());
		}
	
		@Override
		public void initialize(URL url, ResourceBundle rb) {
			inicializeNodes();
	
		}
	
		private void inicializeNodes() {
			Constraints.setTextFieldInteger(txtId);
			Constraints.setTextFieldMaxLength(txtName, 25);
	
		}
	
		private void setErrorMessages(Map<String, String> errors) {
			Set<String> fields = errors.keySet();
			if (fields.contains("name")) {
				labelErroForName.setText(errors.get("name"));
			}
		}
	
	}
