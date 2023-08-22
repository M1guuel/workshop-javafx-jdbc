package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Departament;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.services.DepartamentService;

public class DepartamentFortController implements Initializable {
	DepartamentService serv;
	private Departament dep = new Departament();
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

	public void setDep(Departament dep) {
		this.dep = dep;
	}

	@FXML
	public void onBtSave() {
		
	}

	@FXML
	public void onBtCancel() {

	}
	public void updateFormData() {
		if (dep == null) {
			throw new IllegalStateException("Entity is null");
		}
		txtId.setText(String.valueOf(dep.getId()));
		txtName.setText(dep.getNome());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializeNodes();

	}

	private void inicializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 25);

	}

}
