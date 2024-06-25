package model.DAO;

import DB.DB;
public class DAOFactory {

    public static FuncionarioDAO createFuncionarioDAO() {
        return new FuncionarioDAOJDBC(DB.getConnection());
    }

    public static DepartamentoDAO createDepartamentoDAO() {
        return new DepartamentoDAOJDBC(DB.getConnection());
    }
}
