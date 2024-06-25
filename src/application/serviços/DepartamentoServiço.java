package application.serviços;

import java.util.Arrays;
import java.util.List;

import application.entidades.Departamento;

public class DepartamentoServiço {
	
	public List<Departamento> findAll(){
		Departamento d1 = new Departamento(1, "Livros");
		Departamento d2 = new Departamento(2, "Computador");
		Departamento d3 = new Departamento(3, "Eletronicos");
		List <Departamento> dep = Arrays.asList(d1, d2,d3);
		return dep;
	}

}
