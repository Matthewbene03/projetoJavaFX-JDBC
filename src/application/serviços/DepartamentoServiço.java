package application.serviços;

import java.util.List;

import application.entidades.Departamento;
import model.DAO.DAOFactory;
import model.DAO.DepartamentoDAO;

public class DepartamentoServiço {

	private DepartamentoDAO depDAO = DAOFactory.createDepartamentoDAO();
	
	public List<Departamento> findAll(){
		return depDAO.findAll();
	}

}
