package view;

import java.util.List;

import io.IO;

public class View {
	
	static final List<String> opciones = List.of(
			"Empleados", "Departamentos",  "Escribir XML", "Salir");
	
	public static Character getOption() {
		IO.println(opciones);
		return (Character.toUpperCase(IO.readChar()));
	}
	
}

