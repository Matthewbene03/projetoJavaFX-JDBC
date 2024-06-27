package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import DB.DbException;
import DB.DbIntegrityException;
import application.Main;
import application.entidades.Departamento;
import application.serviços.DepartamentoServiço;
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

public class DepartamentoListController implements Initializable, DataChangeListener {

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
	private TableColumn<Departamento, Departamento> tableColumnEDIT;

	@FXML
	private TableColumn<Departamento, Departamento> tableColumnREMOVE;

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
		Departamento auxDep = new Departamento(null, "");
		this.createDialogForm(auxDep, "/gui/DepartmentForm.fxml", stage);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("idDepartamento"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nomeDepartamento"));

		Stage stage = (Stage) Main.getScene().getWindow();
		tableViewDep.prefHeightProperty().bind(stage.heightProperty());
	}

	public void uptadeTableView() {
		obsListDep = FXCollections.observableList(depServico.findAll());
		tableViewDep.setItems(obsListDep);
		this.initEditButtons();
		this.initRemoveButtons();
	}

	public void createDialogForm(Departamento auxDep, String caminho, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
			Pane pane = loader.load();

			DepartmentFormController controller = loader.getController();
			controller.setDepartamento(auxDep);
			controller.updateDepartamentoDate();
			controller.subscribeDataChangeListener(this);
			controller.setDepServico(new DepartamentoServiço());

			Stage newStage = new Stage();
			newStage.setTitle("Enter departament: ");
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Departamento obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Departamento obj, boolean empty) {
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

	private void removeEntity(Departamento obj) {
		Optional<ButtonType> btAlerts = Alerts.showConfirmation("Confirmation:", "Are you sure to delete?");
		
		if(btAlerts.get() == ButtonType.OK) {
			try {
				depServico.deleteDepartamento(obj);
				uptadeTableView();
			} catch(DbException e) {
				Alerts.showAlert("Error removing object ", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}
