package escribirObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
	private static final String FILE = "fichero.bin";

	public static void main(String[] args) {
		escribir();
		leer();
	}
	
	public static void escribir() {
		// Si no es el primer objeto q se escribe y quieres q escriba al final (no sobreescribir) tienes
		// que poner true y hacer un override del metodo streamHeader como se ve en el codigo comentado
		try (ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream(FILE, true))){
			fichero.writeObject(new Persona("lucas", 20));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
			try (ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream(FILE, true)) {
				@Override
				protected void writeStreamHeader() throws IOException {
					//do nothing
				}
			}){
				fichero.writeObject(new Persona("lucas", 20));
			} catch (Exception e) {
				e.printStackTrace();
			}
		*/
	}
	
	public static void leer() {
		try (ObjectInputStream fichero = new ObjectInputStream(new FileInputStream(FILE))){
			Persona p;
			while (true) {
				p = (Persona) fichero.readObject();
				System.out.println(p);
			}
		} catch (Exception e) {
			
		}
	}

}
