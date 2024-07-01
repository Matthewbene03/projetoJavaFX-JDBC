package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import DB.DbException;
import application.entidades.Departamento;
import application.entidades.Funcionario;
import application.exception.ValidationException;
import application.serviços.DepartamentoServiço;
import application.serviços.FuncionarioServiço;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class FuncionarioFormController implements Initializable {

	private Funcionario fuc;

	private FuncionarioServiço fucSer;

	private DepartamentoServiço depSer;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private ObservableList<Departamento> obsList;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtSalario;

	@FXML
	private ComboBox<Departamento> comboBoxDep;

	@FXML
	private DatePicker datePickerDataNascimento;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	@FXML
	private Label lbErrorNome;

	@FXML
	private Label lbErrorEmail;

	@FXML
	private Label lbErrorDataNascimento;

	@FXML
	private Label lbErrorSalario;

	@FXML
	private Label lbErrorDep;

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (fuc == null) {
			throw new NullPointerException("Funcionario NULL");
		}
		if (fucSer == null) {
			throw new NullPointerException("Servicço de fucartamento NULL");
		}
		try {
			this.fuc = saveFucartment();
			fucSer.saveOrUpdate(fuc);
			notifyDataChangeListener();
			Utils.currentStage(event).close();
		} catch (DbException e) {
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

	private Funcionario saveFucartment() {
		Funcionario fuc = new Funcionario();

		ValidationException validationE = new ValidationException("Validation error!");

		fuc.setIdFuncionario(Utils.tryParseToInt(txtId.getText()));
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			validationE.addErrors("name", "Field can't be empty");
		}
		fuc.setNomeFuncionario(txtName.getText());

		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			validationE.addErrors("email", "Field can't be empty");
		}

		if (datePickerDataNascimento == null) {
			validationE.addErrors("Data", "Field can't be empty");
		}

		if (validationE.getMapErrors().size() > 0) {
			throw validationE;
		}

		return fuc;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 100);
		Constraints.setTextFieldMaxLength(txtEmail, 100);
		Constraints.setTextFieldDouble(txtSalario);
		Utils.formatDatePicker(datePickerDataNascimento, "dd/MM/yyyy");
		this.initializeComboBoxDepartment();

	}

	public void updateFuncionarioDate() {
		txtId.setText(String.valueOf(fuc.getIdFuncionario()));
		txtName.setText(String.valueOf(fuc.getNomeFuncionario()));
		txtEmail.setText(String.valueOf(fuc.getEmail()));
		txtSalario.setText(String.valueOf(fuc.getSalarioBase()));
		if(fuc.getDataNascimento() != null) {
			datePickerDataNascimento.setValue(LocalDate.ofInstant(fuc.getDataNascimento().toInstant(), ZoneId.systemDefault()));
		}
		
		if(fuc.getDepartamento() == null) {
			comboBoxDep.getSelectionModel().selectLast();
		} else {
			comboBoxDep.setValue(fuc.getDepartamento());
		}
	}

	public void loadAssociatedObjects() {
		obsList = FXCollections.observableArrayList(depSer.findAll());
		comboBoxDep.setItems(obsList);
	}

	public void setFucServico(FuncionarioServiço fucServ) {
		this.fucSer = fucServ;
	}

	public void setFuncionario(Funcionario fuc) {
		this.fuc = fuc;
	}

	public void setDepSer(DepartamentoServiço depSer) {
		this.depSer = depSer;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> keysErros = errors.keySet();

		if (keysErros.contains("name")) {
			lbErrorNome.setText(errors.get("name"));
		}

		if (keysErros.contains("email")) {
			lbErrorEmail.setText(errors.get("email"));
		}

		if (keysErros.contains("Data")) {
			lbErrorDataNascimento.setText(errors.get("Data"));
		}

	}

	private void initializeComboBoxDepartment() {
		Callback<ListView<Departamento>, ListCell<Departamento>> factory = lv -> new ListCell<Departamento>() {
			@Override
			protected void updateItem(Departamento item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNomeDepartamento());
			}
		};
		comboBoxDep.setCellFactory(factory);
		comboBoxDep.setButtonCell(factory.call(null));
	}

}
