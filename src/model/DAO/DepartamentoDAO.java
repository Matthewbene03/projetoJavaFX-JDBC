package model.DAO;

import java.util.List;

import application.entidades.Departamento;

public interface DepartamentoDAO {
    public void insert(Departamento departamento);
    public void update(Departamento departamento);
    public void deleteById(Integer id);
    public Departamento findById(Integer id);
    public List<Departamento> findAll();
}
