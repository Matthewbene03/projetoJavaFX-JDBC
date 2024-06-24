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

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemFunc;
	
	@FXML
	private MenuItem menuItemDep;
	
	@FXML
	private MenuItem menuItemAbout;
	
	public void onMenuItemFuncAction() {
		System.out.println("Funcionario");
	}
	
	public void onMenuItemDepAction() {
		System.out.println("Departamento");
	}
	
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}
	
	private void loadView(String caminho) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			VBox newVbox = loader.load();
			
			Scene scene = Main.getScene();
			
			VBox mainVBox = (VBox)((ScrollPane)scene.getRoot()).getContent();
			Node nodeFilhos = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(nodeFilhos);
			mainVBox.getChildren().addAll(newVbox);
			
		} catch(IOException e) {
			Alerts.showAlert("Error!!!", "Erro de carregamento", e.getMessage(), AlertType.ERROR);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
	}

}
