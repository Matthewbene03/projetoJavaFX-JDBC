package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.entidades.Departamento;

public class DepartamentoDAOJDBC implements DepartamentoDAO {

    private Connection conn;

    public DepartamentoDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Departamento departamento) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into department (Name) values (?)",
                     Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, departamento.getNomeDepartamento());

            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    departamento.setIdDepartamento(id);
                }
                DB.DB.closeResultSet(rs);
            } else {
                throw new DB.DbException("ERROR! Não foi possivel cadastrar esse departamento.");
            }
        } catch (SQLException e) {
            throw new DB.DbException(e.getMessage());
        } finally {
            DB.DB.closeStatement(ps);
        }

    }

    @Override
    public void update(Departamento departamento) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");

            ps.setString(1, departamento.getNomeDepartamento());
            ps.setInt(2, departamento.getIdDepartamento());

            ps.executeUpdate();
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
            ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DB.DbException(e.getMessage());
        } finally {
            DB.DB.closeStatement(ps);
        }
    }

    @Override
    public Departamento findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select Id, Name from department where Id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Departamento dep = inicializarDepartamento(rs);
                return dep;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DB.DbException(e.getMessage());
        } finally {
            DB.DB.closeResultSet(rs);
            DB.DB.closeStatement(ps);
        }
    }

    @Override
    public List<Departamento> findAll() {
        List<Departamento> listDepartamento = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select Id, Name from department");
            rs = ps.executeQuery();
            while (rs.next()) {
                Departamento dep = inicializarDepartamento(rs);
                listDepartamento.add(dep);
            }
            return listDepartamento;
        } catch (SQLException e) {
            throw new DB.DbException(e.getMessage());
        } finally {
            DB.DB.closeResultSet(rs);
            DB.DB.closeStatement(ps);
        }
    }

    private Departamento inicializarDepartamento(ResultSet rs) throws SQLException {
        Integer IdDep = rs.getInt("Id");
        String nomeDep = rs.getString("Name");
        return new Departamento(IdDep, nomeDep);
    }
}
