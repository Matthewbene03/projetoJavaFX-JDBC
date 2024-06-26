package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.entidades.Departamento;
import application.serviços.DepartamentoServiço;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DepartamentoListController implements Initializable{

	private DepartamentoServiço depServico;
	
	@FXML
	private Button btNew;
	
	@FXML
	private TableView<Departamento> tableViewDep;
	
	@FXML
	private TableColumn<Departamento, Integer> tableColumnID;
	
	@FXML
	private TableColumn<Departamento, String> tableColumnName;
	
	@FXML
	private ObservableList<Departamento> obsListDep;
	
	public DepartamentoServiço getDepServico() {
		return depServico;
	}


	public void setDepServico(DepartamentoServiço depServico) {
		this.depServico = depServico;
	}
	
	
	public void onBtNewAction(ActionEvent actionEv) {
		Stage stage = Utils.currentStage(actionEv);
		Departamento auxDep = new Departamento();
		this.createDialogForm(auxDep, "/gui/DepartmentForm.fxml", stage);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}


	private void initializeNodes() {
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("idDepartamento"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nomeDepartamento"));
		
		Stage stage = (Stage)Main.getScene().getWindow();
		tableViewDep.prefHeightProperty().bind(stage.heightProperty());
	}

	public void uptadeTableView() {
		obsListDep = FXCollections.observableList(depServico.findAll());
		tableViewDep.setItems(obsListDep);
	}
	
	public void createDialogForm (Departamento auxDep, String caminho, Stage parentStage) {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
		Pane pane = loader.load();
		
		DepartmentFormController controller = loader.getController();
		controller.setDepartamento(auxDep);
		controller.updateDepartamentoDate();
		controller.setDepServico(new DepartamentoServiço());
		
		Stage newStage = new Stage();
		newStage.setTitle("Enter departament: ");
		newStage.setScene(new Scene(pane));
		newStage.setResizable(false);
		newStage.initOwner(parentStage);
		newStage.initModality(Modality.WINDOW_MODAL);
		newStage.showAndWait();
		
		} catch(IOException e) {
			Alerts.showAlert("IO exception", "Error para carregar pagina", e.getMessage(), AlertType.ERROR);
		}
	}

}
