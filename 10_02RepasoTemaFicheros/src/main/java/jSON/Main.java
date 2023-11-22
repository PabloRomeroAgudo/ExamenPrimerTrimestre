package jSON;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
	private final static String DATA_JSON = "Personas.json";
	
	public static void main(String[] args) {
		Agenda agenda = new Agenda();
		List<Persona> contactos = List.of(new Persona("Pablo", 29),
				new Persona("Navarro", 28),
				new Persona("Lucas", 35));
		
		agenda.setLista(contactos);
		agenda.setNumContactos(contactos.size());
		jsonEscribir(agenda);
		jsonLeer();
	}
	
	private static void jsonEscribir(Agenda agenda) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();
		
		try {
			FileWriter fichero = new FileWriter(DATA_JSON);
			fichero.write(gson.toJson(agenda));
			fichero.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void jsonLeer() {
        GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		try {
			FileReader fichero = new FileReader(DATA_JSON);
			Agenda agenda = gson.fromJson(fichero, Agenda.class);
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
	}
}
