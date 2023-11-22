package controller;

import java.util.logging.Logger;

import dao.Dao;
import view.View;

public class Controller {
	private final Logger logger = Logger.getLogger(Controller.class.getName());
	
	private Dao dao = new Dao();

	public Controller() {
		
		DepartamentosController departamento = new DepartamentosController();
		EmpleadosController empleado = new EmpleadosController();
		
		while (true) {
			Character opt = View.getOption();
			logger.info("Menu");
			switch (opt) {
				case 'E':
					empleado.menu();
					break;
				case 'D':
					departamento.menu();
					break;
				case 'X':
					writeToXML();
					break;
				case 'S':
					return;
				default:
			}		
		}
	}
	
	private void writeToXML() {
		Boolean escrito = dao.writeToXML();
		System.out.println(escrito ? "Escrito" : "No se ha podido escribir");
	}
	
}
