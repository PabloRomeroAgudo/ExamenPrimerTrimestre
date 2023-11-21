package escribirYLeerProps;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Main {

	private final static String propiedades = "configuracion.props";
	private final static String propiedadesXML = "configuracion.xml";
	
	public static void main(String[] args) {
		escribirFicheros();
		leerFicheroProp();
		leerFicheroXML();
	}
	
	public static void escribirFicheros() {
		try {
			Properties props = new Properties();
			props.setProperty("Nombre", "Lucas");
			props.setProperty("Edad", "20");
			
			props.store(new FileOutputStream(propiedades), "Prueba hecha por pablete y lukete");
			
			props.storeToXML(new FileOutputStream(propiedadesXML), "Prueba hecha por pablete y lukete");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void leerFicheroProp() {
		try {
			Properties props = new Properties();

			props.load(new FileInputStream(propiedades));
			System.out.println(props.getProperty("Nombre"));
			System.out.println(props.getProperty("Edad"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void leerFicheroXML() {
		try {
			Properties props = new Properties();

			props.loadFromXML(new FileInputStream(propiedadesXML));
			System.out.println(props.getProperty("Nombre"));
			System.out.println(props.getProperty("Edad"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
