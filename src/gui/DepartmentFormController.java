package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.entidades.Departamento;
import application.serviços.DepartamentoServiço;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable{

	private Departamento dep;
	
	private DepartamentoServiço depSer;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private Label lbMsgError;
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(dep == null) {
			throw new NullPointerException("Departamento NULL");
		}
		if(depSer == null) {
			throw new NullPointerException("Servicço de departamento NULL");
		}
		
		this.dep = saveDepartment();
		depSer.saveOrUpdate(dep);
		Utils.currentStage(event).close();
	}
	
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	private Departamento saveDepartment () {
		Departamento dep = new Departamento();
		dep.setIdDepartamento(Utils.tryParseToInt(txtId.getText()));
		dep.setNomeDepartamento(txtName.getText());
		return dep;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateDepartamentoDate() {
		txtId.setText(String.valueOf(dep.getIdDepartamento()));
		txtName.setText(String.valueOf(dep.getNomeDepartamento()));
	}
	
	public void setDepServico(DepartamentoServiço depServ) {
		this.depSer = depServ;
	}
	
	public void setDepartamento(Departamento dep) {
		this.dep = dep;
	}

}
