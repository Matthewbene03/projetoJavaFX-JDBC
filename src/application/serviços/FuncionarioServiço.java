package application.serviços;

import java.util.List;

import application.entidades.Funcionario;
import model.DAO.DAOFactory;
import model.DAO.FuncionarioDAO;

public class FuncionarioServiço {

	private FuncionarioDAO fucDAO = DAOFactory.createFuncionarioDAO();
	
	public List<Funcionario> findAll(){
		return fucDAO.findAll();
	}

	public void saveOrUpdate (Funcionario funcionario) {
		if(funcionario.getIdFuncionario() == null) {
			fucDAO.insert(funcionario);
		} else {
			fucDAO.update(funcionario);
		}
	}
	
	public void deleteFuncionario(Funcionario fuc) {
		fucDAO.deleteById(fuc.getIdFuncionario());
	}
}
