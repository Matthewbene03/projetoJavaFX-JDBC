package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.entidades.Departamento;
import application.serviços.DepartamentoServiço;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	
	public void onBtNewAction() {
		System.out.println("btNew");
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

}
