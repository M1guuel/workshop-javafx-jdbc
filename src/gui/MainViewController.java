package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartament;
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("Seller");
	}

	@FXML
	public void onMenuItemDepartamentAction() {
		System.out.println("Departament");
	}

	@FXML
	public void onMenuItemAbountAction() {
		loadView("/gui/Abount.fxml");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	public void loadView(String uri) {
		try {
			FXMLLoader loard = new FXMLLoader(getClass().getResource(uri));
			VBox newVbox = loard.load();

			Scene mainScene = Main.getmainScene();
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
		} catch (IOException e) {
			Alerts.showAlert("IOException", "erro", e.getMessage(), AlertType.ERROR);
		}
	}

}
