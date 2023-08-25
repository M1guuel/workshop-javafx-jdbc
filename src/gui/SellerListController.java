	package gui;
	
	import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DBIntergrityExeception;
import entities.Seller;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.services.SellerService;
	
	public class SellerListController implements Initializable, DataChangeListener {
	
		public SellerService service;
	
		@FXML
		private TableView<Seller> tableViewSeller;
		@FXML
		private TableColumn<Seller, Integer> tbColumnId;
		@FXML
		private TableColumn<Seller, String> tbColumnName;
		@FXML
		private Button btNew;
		@FXML
		private TableColumn<Seller, Seller> tableColumnEDIT;
		@FXML
		private TableColumn<Seller, Seller> tableColumnREMOVE;
	
		private ObservableList<Seller> obsList;
	
		@FXML
		public void onBtNewAction(ActionEvent event) {
			Stage parentStage = Utils.currentStage(event);
			Seller obj = new Seller();
			createDialogForm(obj, "/gui/Seller.fxml", parentStage);
		}
	
		public void setService(SellerService service) {
			this.service = service;
		}
	
		@Override
		public void initialize(URL url, ResourceBundle urb) {
			initializableNode();
	
		}
	
		private void initializableNode() {
			tbColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
			tbColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
			Stage stage = (Stage) Main.getmainScene().getWindow();
			tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
		}
	
		public void updateTableView() {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			List<Seller> list = service.findAll();
			obsList = FXCollections.observableArrayList(list);
			tableViewSeller.setItems(obsList);
			initEditButtons();
			initRemoveButtons();
	
		}
	
		private void createDialogForm(Seller obj, String uri, Stage parentStage) {
//			try {
//				FXMLLoader loard = new FXMLLoader(getClass().getResource(uri));
//				Pane pane = loard.load();
//	
//				SellerFortController controller = loard.getController();
//	
//				controller.setEntity(obj);
//				controller.updateFormData();
//				controller.subcribeDataChangeListener(this);
//				controller.setService(new SellerService());
//	
//				Stage dialogStage = new Stage();
//				dialogStage.setTitle("Entre com os dados do Seller");
//				dialogStage.setScene(new Scene(pane));
//				dialogStage.setResizable(false);
//				dialogStage.initOwner(parentStage);
//				dialogStage.initModality(Modality.WINDOW_MODAL);
//				dialogStage.showAndWait();
//			} catch (IOException e) {
//				Alerts.showAlert("IO EXCEPTION", "ERROR", e.getMessage(), AlertType.ERROR);
//			}
		}
	
		@Override
		public void onDataChanged() {
			updateTableView();
	
		}
	
		private void initEditButtons() {
			tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
				private final Button button = new Button("edit");
	
				@Override
				protected void updateItem(Seller obj, boolean empty) {
					super.updateItem(obj, empty);
					if (obj == null) {
						setGraphic(null);
						return;
					}
					setGraphic(button);
					button.setOnAction(
							event -> createDialogForm(obj, "/gui/Seller.fxml", Utils.currentStage(event)));
				}
			});
		}
	
		private void initRemoveButtons() {
			tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
				private final Button button = new Button("remove");
	
				@Override
				protected void updateItem(Seller obj, boolean empty) {
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
	
		private void removeEntity(Seller obj) {
	
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
