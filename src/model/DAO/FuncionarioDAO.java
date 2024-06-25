package model.DAO;

import java.util.List;

import application.entidades.Departamento;
import application.entidades.Funcionario;

public interface FuncionarioDAO {
    public void insert(Funcionario funcionario);
    public void update(Funcionario funcionario);
    public void deleteById(Integer id);
    public Funcionario findById(Integer id);
    public List<Funcionario> findAll();
    public List<Funcionario> findByDepartamento(Departamento departamento);
}
