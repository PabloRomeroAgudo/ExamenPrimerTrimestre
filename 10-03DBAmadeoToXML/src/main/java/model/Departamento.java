package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Departamento {
	
	Integer id; 
	String nombre; 
	Empleado jefe;
	
	/**
	 * Devuelve representación de un departamento
	 * 
	 * @return string
	 */
	public String show() {		
		if (id == 0) {
			return "no departamento!!!";
		}

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%2d:%-20s:", id, nombre));
		if (jefe == null || jefe.getNombre() == null) {
			sb.append("sin jefe!!");
		} else {
			sb.append(String.format("jefe [%2d:%s]", jefe.getId(), jefe.getNombre()));
		}
		
		return sb.toString();
	}

}
