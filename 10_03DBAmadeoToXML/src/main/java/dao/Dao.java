package dao;

import jakarta.xml.bind.JAXBException;
import lombok.NoArgsConstructor;
import model.Oficina;

@NoArgsConstructor
public class Dao {
	private EmpleadoDao emp = new EmpleadoDao();
	private DepartamentoDao dep = new DepartamentoDao();
	private JaxbController controller = JaxbController.getInstance();
	
	private final static String FILE = "oficina.xml";
	
	public Boolean writeToXML() {
		Oficina oficina = new Oficina();
		oficina.setEmpleados(emp.findAll());
		oficina.setDepartamentos(dep.findAll());
		try {
			controller.objectToXML(oficina);
			controller.writeXMLFile(FILE);
			
			return true;
		} catch (JAXBException e) {
			e.printStackTrace();
			return false;
		}
	}
}
