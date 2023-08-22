package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import entities.Departament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.services.DepartamentService;

public class DepartamentViewController implements Initializable {

	public DepartamentService service;

	@FXML
	private TableView<Departament> tableViewDepartament;
	@FXML
	private TableColumn<Departament, Integer> tbColumnId;
	@FXML
	private TableColumn<Departament, String> tbColumnName;
	@FXML
	private Button btNew;

	private ObservableList<Departament> obsList;

	@FXML
	public void onBtNewAction() {
		System.out.println("btNemAction");
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

	}

}
