package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.entidades.Departamento;
import application.serviços.DepartamentoServiço;
import gui.util.Constraints;
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
	public void onBtSaveAction() {
		if(depSer == null) {
			throw new NullPointerException("Servicço de departamento NULL");
		}
		Departamento auxDep = this.saveDepartment();
		depSer.insertDep(auxDep);
		System.out.println("Salvo com sucesso!");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("cancel");
	}
	
	private Departamento saveDepartment () {
		Departamento dep = new Departamento();
		dep.setNomeDepartamento(String.valueOf(txtName.getText()));
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
