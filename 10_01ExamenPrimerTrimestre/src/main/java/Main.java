import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import dao.BD;
import jakarta.xml.bind.JAXBException;
import jaxb.JaxbController;
import model.Agenda;
import model.Contacto;

public class Main {

	static String DATA_XML = "data/agenda.xml";
	
	public static void main(String[] args) {
		Connection conexion = BD.getConnection();
		Agenda agenda = new Agenda();
		ArrayList<Contacto> hola = new ArrayList<Contacto>();
		Integer num = 0;
		
		String SQL = """
				SELECT * FROM agenda
				""";
		try {
			ResultSet rs = conexion.createStatement().executeQuery(SQL);
			
			while(rs.next()) {
				num++;
				String sUuid = rs.getString("uuid");
				UUID uuid = UUID.fromString(sUuid);
				String nombre = rs.getString("nombre");
				String telefono = rs.getString("telefono");
				int edad = rs.getInt("edad");
				hola.add(new Contacto(uuid, nombre, telefono, edad));
			}
			
			agenda.setContactos(hola);
			agenda.setNumContacts(num);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			JaxbController controller = JaxbController.getInstance();
			
			System.out.println("* Objetos a XML");
			controller.objectToXML(agenda);
			controller.writeXMLFile(DATA_XML);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
