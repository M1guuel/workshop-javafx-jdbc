package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import entities.Departament;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DepartamentViewController implements Initializable {
	@FXML
	private TableView<Departament> tableViewDepartament;
	@FXML
	private TableColumn<Departament, Integer> tbColumnId;
	@FXML
	private TableColumn<Departament, String> tbColumnName;
	@FXML
	private Button btNew;

	@FXML
	public void onBtNewAction() {
		System.out.println("btNemAction");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializableNode();

	}

	private void initializableNode() {
		tbColumnId.setCellValueFactory(new PropertyValueFactory<>("ID"));
		tbColumnName.setCellValueFactory(new PropertyValueFactory<>("NOME"));
		Stage stage = (Stage) Main.getmainScene().getWindow();
		tableViewDepartament.prefHeightProperty().bind(stage.heightProperty());
	}

}
