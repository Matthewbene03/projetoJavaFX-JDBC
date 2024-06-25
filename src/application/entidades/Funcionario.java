package application.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer idFuncionario;
    private String nomeFuncionario;
    private Double salarioBase;
    private Date dataNascimento;
    private String email;

    private Departamento departamento;
    private static List<Funcionario> listaFuncionarios;

    public Funcionario() {}

    public Funcionario(Integer idFuncionario, String nomeFuncionario, Double salarioBase, Date dataNascimento, String email, Departamento departamento) {
        this.idFuncionario = idFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.salarioBase = salarioBase;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.departamento = departamento;
        this.listaFuncionarios = new ArrayList<>();
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public Double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(Double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idFuncionario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Funcionario other = (Funcionario) obj;
        return Objects.equals(this.idFuncionario, other.idFuncionario);
    }

    @Override
    public String toString() {
        return "Funcionario{" + "idFuncionario=" + idFuncionario + ", nomeFuncionario=" + nomeFuncionario + ", salarioBase=" + salarioBase + ", dataNascimento=" + dataNascimento + ", email=" + email + ", departamento=" + departamento + '}';
    }

}
