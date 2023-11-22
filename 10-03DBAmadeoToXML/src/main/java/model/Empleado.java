package model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
	
	Integer id; 
	String nombre; 
	Double salario; 
	LocalDate nacido;
	Departamento departamento;

	/**
	 * Devuelve representación de un empleado
	 * 
	 * @return string
	 */
	public String show() {
		if (id == 0) {
			return "no empleado!!!";
		}

		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%2d:%-20s:%4.2f:", id, nombre, salario));
		if (nacido == null) {
			sb.append("sin fecha de nacimiento!!");
		} else {
			sb.append(String.format("%s", nacido));
		}
		sb.append(":");
		if (departamento == null || departamento.getNombre() == null) {
			sb.append("sin departamento!!");
		} else {
			sb.append(String.format("Departamento [%2d:%s]", departamento.getId(), departamento.getNombre()));
		}
		
		return sb.toString();
	}
	
}
