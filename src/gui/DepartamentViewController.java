	package gui;
	
	import java.io.IOException;
	import java.net.URL;
	import java.util.List;
	import java.util.Optional;
	import java.util.ResourceBundle;
	
	import application.Main;
	import db.DBIntergrityExeception;
	import entities.Departament;
	import gui.listener.DataChangeListener;
	import gui.util.Alerts;
	import gui.util.Utils;
	import javafx.beans.property.ReadOnlyObjectWrapper;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.fxml.FXMLLoader;
	import javafx.fxml.Initializable;
	import javafx.scene.Scene;
	import javafx.scene.control.Alert.AlertType;
	import javafx.scene.control.Button;
	import javafx.scene.control.ButtonType;
	import javafx.scene.control.TableCell;
	import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;
	import javafx.scene.control.cell.PropertyValueFactory;
	import javafx.scene.layout.Pane;
	import javafx.stage.Modality;
	import javafx.stage.Stage;
	import model.services.DepartamentService;
	
	public class DepartamentViewController implements Initializable, DataChangeListener {
	
		public DepartamentService service;
	
		@FXML
		private TableView<Departament> tableViewDepartament;
		@FXML
		private TableColumn<Departament, Integer> tbColumnId;
		@FXML
		private TableColumn<Departament, String> tbColumnName;
		@FXML
		private Button btNew;
		@FXML
		private TableColumn<Departament, Departament> tableColumnEDIT;
		@FXML
		private TableColumn<Departament, Departament> tableColumnREMOVE;
	
		private ObservableList<Departament> obsList;
	
		@FXML
		public void onBtNewAction(ActionEvent event) {
			Stage parentStage = Utils.currentStage(event);
			Departament obj = new Departament();
			createDialogForm(obj, "/gui/DepartmenForm.fxml", parentStage);
		}
	
		public void setService(DepartamentService service) {
			this.service = service;
		}
	
		@Override
		public void initialize(URL url, ResourceBundle urb) {
			initializableNode();
	
		}
	
		private void initializableNode() {
			tbColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
			tbColumnName.setCellValueFactory(new PropertyValueFactory<>("nome"));
			Stage stage = (Stage) Main.getmainScene().getWindow();
			tableViewDepartament.prefHeightProperty().bind(stage.heightProperty());
		}
	
		public void updateTableView() {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			List<Departament> list = service.findAll();
			obsList = FXCollections.observableArrayList(list);
			tableViewDepartament.setItems(obsList);
			initEditButtons();
			initRemoveButtons();
	
		}
	
		private void createDialogForm(Departament obj, String uri, Stage parentStage) {
			try {
				FXMLLoader loard = new FXMLLoader(getClass().getResource(uri));
				Pane pane = loard.load();
	
				DepartamentFortController controller = loard.getController();
	
				controller.setEntity(obj);
				controller.updateFormData();
				controller.subcribeDataChangeListener(this);
				controller.setService(new DepartamentService());
	
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Entre com os dados do Departamento");
				dialogStage.setScene(new Scene(pane));
				dialogStage.setResizable(false);
				dialogStage.initOwner(parentStage);
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.showAndWait();
			} catch (IOException e) {
				Alerts.showAlert("IO EXCEPTION", "ERROR", e.getMessage(), AlertType.ERROR);
			}
		}
	
		@Override
		public void onDataChanged() {
			updateTableView();
	
		}
	
		private void initEditButtons() {
			tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableColumnEDIT.setCellFactory(param -> new TableCell<Departament, Departament>() {
				private final Button button = new Button("edit");
	
				@Override
				protected void updateItem(Departament obj, boolean empty) {
					super.updateItem(obj, empty);
					if (obj == null) {
						setGraphic(null);
						return;
					}
					setGraphic(button);
					button.setOnAction(
							event -> createDialogForm(obj, "/gui/DepartmenForm.fxml", Utils.currentStage(event)));
				}
			});
		}
	
		private void initRemoveButtons() {
			tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableColumnREMOVE.setCellFactory(param -> new TableCell<Departament, Departament>() {
				private final Button button = new Button("remove");
	
				@Override
				protected void updateItem(Departament obj, boolean empty) {
					super.updateItem(obj, empty);
					if (obj == null) {
						setGraphic(null);
						return;
					}
					setGraphic(button);
					button.setOnAction(event -> removeEntity(obj));
				}
			});
		}
	
		private void removeEntity(Departament obj) {
	
			Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete ? ");
			if (result.get() == ButtonType.OK) {
				if (service == null) {
					throw new IllegalStateException("Service was null");
				}
				try {
					service.remove(obj);
					updateTableView();
				} catch (DBIntergrityExeception e) {
					Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
				}
	
			}
		}
	
	}
