package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
		System.out.println("About");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
	}

}
