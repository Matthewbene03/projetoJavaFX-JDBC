package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import DB.DbException;
import application.Main;
import application.entidades.Funcionario;
import application.serviços.DepartamentoServiço;
import application.serviços.FuncionarioServiço;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FuncionarioListController implements Initializable, DataChangeListener {

	private FuncionarioServiço fucServico;

	@FXML
	private Button btNew;

	@FXML
	private TableView<Funcionario> tableViewFuc;

	@FXML
	private TableColumn<Funcionario, Integer> tableColumnID;

	@FXML
	private TableColumn<Funcionario, String> tableColumnName;
	
	@FXML
	private TableColumn<Funcionario, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Funcionario, Date> tableColumnDataNascimento;
	
	@FXML
	private TableColumn<Funcionario, Double> tableColumnSalario;

	@FXML
	private TableColumn<Funcionario, Funcionario> tableColumnEDIT;

	@FXML
	private TableColumn<Funcionario, Funcionario> tableColumnREMOVE;

	@FXML
	private ObservableList<Funcionario> obsListFuc;

	public FuncionarioServiço getFucServico() {
		return fucServico;
	}

	public void setFucServico(FuncionarioServiço depServico) {
		this.fucServico = depServico;
	}

	public void onBtNewAction(ActionEvent actionEv) {
		Stage stage = Utils.currentStage(actionEv);
		Funcionario auxFuc = new Funcionario(null, "", null, null, "", null);		
		this.createDialogForm(auxFuc, "/gui/FuncionarioForm.fxml", stage);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("idFuncionario"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
		Utils.formatTableColumnDate(tableColumnDataNascimento, "dd/MM/yyyy");
		tableColumnSalario.setCellValueFactory(new PropertyValueFactory<>("salarioBase"));
		Utils.formatTableColumnDouble(tableColumnSalario, 2);

		Stage stage = (Stage) Main.getScene().getWindow();
		tableViewFuc.prefHeightProperty().bind(stage.heightProperty());
	}

	public void uptadeTableView() {
		obsListFuc = FXCollections.observableList(fucServico.findAll());
		tableViewFuc.setItems(obsListFuc);
		this.initEditButtons();
		this.initRemoveButtons();
	}

	public void createDialogForm(Funcionario auxFuc, String caminho, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			Pane pane = loader.load();

			FuncionarioFormController controller = loader.getController();
			controller.setFuncionario(auxFuc);
			controller.updateFuncionarioDate();
			controller.subscribeDataChangeListener(this);
			controller.setFucServico(new FuncionarioServiço());
			controller.setDepSer(new DepartamentoServiço());
			controller.loadAssociatedObjects();

			Stage newStage = new Stage();
			newStage.setTitle("Enter funcionario: ");
			newStage.setScene(new Scene(pane));
			newStage.setResizable(false);
			newStage.initOwner(parentStage);
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO exception", "Error para carregar pagina", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		this.uptadeTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Funcionario, Funcionario>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Funcionario obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/FuncionarioForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Funcionario, Funcionario>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Funcionario obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Funcionario obj) {
		Optional<ButtonType> btAlerts = Alerts.showConfirmation("Confirmation:", "Are you sure to delete?");
		
		if(btAlerts.get() == ButtonType.OK) {
			try {
				fucServico.deleteFuncionario(obj);
				uptadeTableView();
			} catch(DbException e) {
				Alerts.showAlert("Error removing object ", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}
