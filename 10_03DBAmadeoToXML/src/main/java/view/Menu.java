package view;

import java.util.List;

import dao.BD;
import io.IO;

public class Menu {
	
	public static void main(String[] args) {
		
		List<String> opciones = List.of( 
				"Empleados", 
				"Departamentos", 
				"Salir");
		
		while (true) {
			IO.println(opciones);
			switch (Character.toUpperCase(IO.readChar())) {
			case 'E':
				MenuEmpleado.menu();
				break;
			case 'D':
				MenuDepartamento.menu();
				break;
			case 'S':
				BD.close();
				return;
			default:
			}
		}		
				
	}

}

