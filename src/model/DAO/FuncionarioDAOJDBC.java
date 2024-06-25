package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.entidades.Departamento;
import application.entidades.Funcionario;

public class FuncionarioDAOJDBC implements FuncionarioDAO {

    private Connection conn;

    public FuncionarioDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Funcionario funcionario) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO seller (Name, Email, "
                    + "BirthDate, BaseSalary, DepartmentId) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, funcionario.getNomeFuncionario());
            ps.setString(2, funcionario.getEmail());
            ps.setDate(3, new java.sql.Date(funcionario.getDataNascimento().getTime()));
            ps.setDouble(4, funcionario.getSalarioBase());
            ps.setInt(5, funcionario.getDepartamento().getIdDepartamento());

            int linhas = ps.executeUpdate();

            if (linhas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    funcionario.setIdFuncionario(id);
                    Funcionario.getListaFuncionarios().add(funcionario);
                }
                DB.DB.closeResultSet(rs);
            } else {
                throw new DB.DbException("ERROR! Não foi possivel inserir o funcionario.");
            }
        } catch (SQLException e) {
            throw new DB.DbException(e.getMessage());
        } finally {
            DB.DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Funcionario funcionario) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE seller\n SET Name = ?, Email = ?, "
                    + "BirthDate = ?, BaseSalary = ?, DepartmentId = ?\n WHERE Id = ?", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, funcionario.getNomeFuncionario());
            ps.setString(2, funcionario.getEmail());
            ps.setDate(3, new java.sql.Date(funcionario.getDataNascimento().getTime()));
            ps.setDouble(4, funcionario.getSalarioBase());
            ps.setInt(5, funcionario.getDepartamento().getIdDepartamento());
            ps.setInt(6, funcionario.getIdFuncionario());

            int linhas = ps.executeUpdate();

            if (linhas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    funcionario.setIdFuncionario(id);
                    Funcionario.getListaFuncionarios().add(funcionario);
                }
                DB.DB.closeResultSet(rs);
            } else {
                throw new DB.DbException("ERROR! Não foi possivel inserir o funcionario.");
            }
        } catch (SQLException e) {
            throw new DB.DbException(e.getMessage());
        } finally {
            DB.DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

            ps.setInt(1, id);

            ps.executeUpdate();
            //Funcionario.getListaFuncionarios().remove(id);
        } catch (SQLException e) {
            throw new DB.DbException(e.getMessage());
        } finally {
            DB.DB.closeStatement(ps);
        }
    }

    @Override
    public Funcionario findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department ON "
                    + "seller.DepartmentId = department.Id WHERE seller.Id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Departamento dep = inicializarDepartamento(rs);
                return inicializarFuncionario(rs, dep);
            }
            return null;
        } catch (SQLException e) {
            throw new DB.DbException(e.getMessage());
        } finally {
            DB.DB.closeResultSet(rs);
            DB.DB.closeStatement(ps);
        }
    }

    @Override
    public List<Funcionario> findByDepartamento(Departamento departamento) {
        List<Funcionario> listFuncionarios = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? ORDER BY Name");

            ps.setInt(1, departamento.getIdDepartamento());
            rs = ps.executeQuery();
            while (rs.next()) {
                Funcionario f = inicializarFuncionario(rs, departamento);
                listFuncionarios.add(f);
            }
            return listFuncionarios;
        } catch (SQLException e) {
            throw new DB.DbException(e.getMessage());
        } finally {
            DB.DB.closeResultSet(rs);
            DB.DB.closeStatement(ps);
        }
    }

    @Override
    public List<Funcionario> findAll() {
        List<Funcionario> listFuncionarios = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id "
                    + "ORDER BY Name");

            rs = ps.executeQuery();
            Map<Integer, Departamento> mapDepartamentos = new HashMap<>();
            while (rs.next()) {
                Departamento dep = mapDepartamentos.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = inicializarDepartamento(rs);
                    mapDepartamentos.put(dep.getIdDepartamento(), dep);
                }
                Funcionario f = inicializarFuncionario(rs, dep);
                listFuncionarios.add(f);
            }
            return listFuncionarios;
        } catch (SQLException e) {
            throw new DB.DbException(e.getMessage());
        } finally {
            DB.DB.closeResultSet(rs);
            DB.DB.closeStatement(ps);
        }
    }

    private Departamento inicializarDepartamento(ResultSet rs) throws SQLException {
        Integer IdDep = rs.getInt("DepartmentId");
        String nomeDep = rs.getString("DepName");
        return new Departamento(IdDep, nomeDep);
    }

    private Funcionario inicializarFuncionario(ResultSet rs, Departamento dep) throws SQLException {
        Integer idFun = rs.getInt("Id");
        String nameFun = rs.getString("Name");
        String emailFun = rs.getString("Email");
        Date dataNascimento = rs.getDate("BirthDate");
        Double salarioFun = rs.getDouble("BaseSalary");
        return new Funcionario(idFun, nameFun, salarioFun, dataNascimento, emailFun, dep);
    }
}
