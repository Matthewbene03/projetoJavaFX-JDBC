package application.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Departamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer idDepartamento;
    private String nomeDepartamento;

    public Departamento (){}
    
    public Departamento(Integer idDepartamento, String nomeDepartamento) {
        this.idDepartamento = idDepartamento;
        this.nomeDepartamento = nomeDepartamento;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idDepartamento);
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
        final Departamento other = (Departamento) obj;
        return Objects.equals(this.idDepartamento, other.idDepartamento);
    }

    
    @Override
    public String toString() {
        return "Departamento{" + "idDepartamento=" + idDepartamento + ", nomeDepartamento=" + nomeDepartamento + '}';
    }
}
