package view;

import java.util.List;

import io.IO;
import model.Departamento;
import model.Empleado;

public class DepartamentosView {

	final List<String> opciones = List.of("buscar por Código", "buscar por Nombre", "Mostrar", "Añadir", "modiFicar",
			"Eliminar", "Salir");

	public Character getOption() {
		IO.println("Departamentos: " + opciones);
		return Character.toUpperCase(IO.readChar());
	}

	public Departamento anadir() {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		Departamento d = Departamento.builder().nombre(nombre).build();
		return d;
	}

	public Departamento modificar(Departamento d) {
		if (d == null) {
			IO.println("No se ha encontrado el departamento");
			return null;
		}
		IO.printf("Nombre [%s] ? ", d.getNombre());
		String nombre = IO.readString();
		if (!nombre.isBlank()) {
			d.setNombre(nombre);
		}
		IO.printf("Jefe [%s] ? ", d.getJefe() == null ? "sin jefe!!!" : d.getJefe().show());
		Integer jefe = IO.readIntOrNull();
		if (jefe != null) {
			d.setJefe(Empleado.builder().id(jefe).build());
		}
		return d;
	}

	public String buscarPorInicioDelNombre() {
		IO.print("El nombre empieza por ? ");
		return IO.readString();
	}

	public int buscarPorCodigo() {
		IO.print("Código ? ");
		return IO.readInt();
	}

	public void mostrar(List<Departamento> list) {
		for (Departamento d : list) {
			IO.println(d.show());
		}
	}

	public void mostrar(Departamento d, List<Empleado> emps) {
		if (d == null) {
			return;
		}
		IO.println(d.show());
		IO.println("* Empleados del departamento :");
		for (Empleado e : emps) {
			IO.println(e.show());
		}
	}

	public void result(String msg) {
		IO.println(msg);
	}

}
