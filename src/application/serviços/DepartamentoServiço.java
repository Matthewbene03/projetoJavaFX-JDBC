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

	public void saveOrUpdate (Departamento departamento) {
		if(departamento.getIdDepartamento() == null) {
			depDAO.insert(departamento);
		} else {
			depDAO.update(departamento);
		}
	}
	
	public void deleteDepartamento(Departamento dep) {
		depDAO.deleteById(dep.getIdDepartamento());
	}
}
