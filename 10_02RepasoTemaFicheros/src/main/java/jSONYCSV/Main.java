package jSONYCSV;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
	private final static String DATA_JSON = "Personas.json";
	private final static String DATA_CSV = "Personas.CSV";
	
	public static void main(String[] args) {
		Agenda agenda = new Agenda();
		List<Persona> contactos = List.of(new Persona("Pablo", 29),
				new Persona("Navarro", 28),
				new Persona("Lucas", 35));
		
		agenda.setLista(contactos);
		agenda.setNumContactos(contactos.size());
		//jsonEscribir(agenda);
		Agenda agenda2 = jsonLeer();
		
		csvEscribir(agenda2);
		
		Agenda agenda3 = csvLeer();
		agenda3.setNumContactos(agenda3.getLista().size());
		System.out.println(agenda3);
	}
	
//	private static void jsonEscribir(Agenda agenda) {
//        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
//		Gson gson = builder.create();
//		
//		try {
//			FileWriter fichero = new FileWriter(DATA_JSON);
//			fichero.write(gson.toJson(agenda));
//			fichero.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	// Muestra por pantalla y devuelve el objeto
	private static Agenda jsonLeer() {
        GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		Agenda agenda = new Agenda();
		try {
			FileReader fichero = new FileReader(DATA_JSON);
			agenda = gson.fromJson(fichero, Agenda.class);
			fichero.close();
			
			System.out.println("num contactos: " + agenda.getNumContactos());
			System.out.println("Personas:");
			for (Object obj : agenda.getLista()) {
				Persona persona = (Persona) obj;
				System.out.println("\t" + persona);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return agenda;
	}
	
	private static void csvEscribir(Agenda agenda) {
		List<Persona> lista = agenda.getLista();
		try {
			RandomAccessFile raf = new RandomAccessFile(DATA_CSV, "rw");
			raf.writeBytes("Nombre, Edad\n");
			for (Persona persona : lista) {
				raf.writeBytes(persona.getNombre() + ", " + persona.getEdad() + "\n");
			}
			raf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static Agenda csvLeer() {
		Agenda agenda = new Agenda();
		ArrayList<Persona> lista = new ArrayList<>();
		
		try {
			RandomAccessFile raf = new RandomAccessFile(DATA_CSV, "r");
			String linea;
			Boolean isFirstLine = true;
			while ((linea = raf.readLine()) != null) {
				if (isFirstLine) {
					isFirstLine = false;
					continue;
				}
				String[] split = linea.split(",");
				String nombre = split[0].trim();
				Integer edad = Integer.valueOf(split[1].trim());
				lista.add(new Persona(nombre, edad));
			}
			raf.close();
			agenda.setLista(lista);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return agenda;
	}
}
