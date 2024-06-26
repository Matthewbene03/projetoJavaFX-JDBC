package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import DB.DbException;
import application.entidades.Departamento;
import application.exception.ValidationException;
import application.serviços.DepartamentoServiço;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable{

	private Departamento dep;
	
	private DepartamentoServiço depSer;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
		try {
			this.dep = saveDepartment();
			depSer.saveOrUpdate(dep);
			notifyDataChangeListener();
			Utils.currentStage(event).close();
		} catch(DbException e) {
			Alerts.showAlert("ERROR! Ao salvar!", null, e.getMessage(), AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessages(e.getMapErrors());
		}
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		this.dataChangeListeners.add(listener);
	}
	
	private void notifyDataChangeListener() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	private Departamento saveDepartment () {
		Departamento dep = new Departamento();
		
		ValidationException validationE = new ValidationException("Validation error!");
				
		dep.setIdDepartamento(Utils.tryParseToInt(txtId.getText()));
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			validationE.addErrors("name", "Field can't be empty");
		}
		dep.setNomeDepartamento(txtName.getText());
		
		if(validationE.getMapErrors().size()>0) {
			throw validationE;
		}
		
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

	private void setErrorMessages(Map <String, String> errors) {
		Set<String> keysErros = errors.keySet();
		
		if(keysErros.contains("name")) {
			lbMsgError.setText(errors.get("name"));
		}
		
	}
	
}
