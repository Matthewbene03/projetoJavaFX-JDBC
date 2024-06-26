package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import application.serviços.DepartamentoServiço;
import application.serviços.FuncionarioServiço;
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
	private MenuItem menuItemFunc;

	@FXML
	private MenuItem menuItemDep;

	@FXML
	private MenuItem menuItemAbout;

	public void onMenuItemFuncAction() {
		loadView("/gui/FuncionarioList.fxml", (FuncionarioListController controller) ->{
			controller.setFucServico(new FuncionarioServiço());
			controller.uptadeTableView();
		});
	}

	public void onMenuItemDepAction() {
		loadView("/gui/DepartamentoList.fxml", (DepartamentoListController controller) ->{
			controller.setDepServico(new DepartamentoServiço());
			controller.uptadeTableView();
		});
	}

	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}

	private synchronized <T> void loadView(String caminho, Consumer<T> consumer) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			VBox newVbox = loader.load();

			Scene scene = Main.getScene();

			VBox mainVBox = (VBox) ((ScrollPane) scene.getRoot()).getContent();
			Node nodeFilhos = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(nodeFilhos);
			mainVBox.getChildren().addAll(newVbox.getChildren());
			
			T controller = loader.getController();
			consumer.accept(controller);

		} catch (IOException e) {
			Alerts.showAlert("É aqui!", "Erro de carregamento", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}
