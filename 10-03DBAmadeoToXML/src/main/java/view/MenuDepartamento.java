package view;

import java.util.List;

import dao.DepartamentoDao;
import dao.EmpleadoDao;
import io.IO;
import model.Departamento;
import model.Empleado;

public class MenuDepartamento {
	
	public static void menu() {
		
		DepartamentoDao dao = new DepartamentoDao();
		
		List<String> opciones = List.of( 
				"buscar por Código", 
				"buscar por Nombre", 
				"Mostrar", 
				"Añadir",
				"modiFicar",
				"Eliminar",
				"Salir");
		
		while (true) {
			IO.println("Departamentos: " + opciones);
			switch (Character.toUpperCase(IO.readChar())) {
			case 'C':
				buscarPorCodigo(dao);
				break;
			case 'N':
				buscarPorInicioDelNombre(dao);
				break;
			case 'M':
				mostrar(dao);
				break;
			case 'A':
				anadir(dao);
				break;
			case 'F':
				modificar(dao);
				break;
			case 'E':
				borrar(dao);
				break;
			case 'S':
				return;
			default:
			}
		}		
		
	}

	private static void borrar(DepartamentoDao dao) {
		IO.print("Código ? ");
		Integer id = IO.readInt();
		boolean borrado = dao.delete(id);
		IO.println(borrado ? "Borrado" : "No se ha podido borrar");
	}

	private static void anadir(DepartamentoDao dao) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();		
		Departamento d = Departamento.builder().nombre(nombre).build();		
		boolean anadido = dao.create(d);
		IO.println(anadido ? "Añadido" : "No se ha podido añadir");
	}

	private static void modificar(DepartamentoDao dao) {
		IO.print("Código del departamento a modificar ? ");
		Integer id = IO.readInt();
		Departamento d = dao.findById(id);
		if (d == null) {
			IO.println("No se ha encontrado el departamento");		
			return;
		}
		IO.printf("Nombre [%s] ? ", d.getNombre());
		String nombre = IO.readString();
		if (!nombre.isBlank()) {
			d.setNombre(nombre);
		}
		IO.printf("Jefe [%s] ? ", d.getJefe().show());
		Integer jefe = IO.readIntOrNull();
		if (jefe != null) {
			EmpleadoDao daoEmpleado = new EmpleadoDao();
			d.setJefe(daoEmpleado.findById(jefe));
		}
		boolean anadido = dao.update(d);
		IO.println(anadido ? "Modificado" : "No se ha podido modificar");		
	}

	private static void mostrar(DepartamentoDao dao) {
		for (Departamento d : dao.findAll()) {
			IO.println(d.show());
		}
	}

	private static void buscarPorInicioDelNombre(DepartamentoDao dao) {
		IO.print("El nombre empieza por ? ");
		String inicio = IO.readString();
		for (Departamento d : dao.findByName(inicio)) {
			IO.println(d.show());
		}
	}

	private static void buscarPorCodigo(DepartamentoDao dao) {
		IO.print("Código ? ");
		Integer id = IO.readInt();
		Departamento d = dao.findById(id);
		if (d != null) {
			IO.println(d.show());
			IO.println("* Empleados del departamento :");
			for (Empleado e : dao.getEmpleados(id)) {
				IO.println(e.show());
			}
		}
	}

}
