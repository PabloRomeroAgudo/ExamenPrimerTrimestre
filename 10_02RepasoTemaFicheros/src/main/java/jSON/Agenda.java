package jSON;

import java.util.List;

public class Agenda {
	private List<Persona> contactos;
	
	private Integer numContactos;

	public List<Persona> getLista() {
		return contactos;
	}

	public void setLista(List<Persona> lista) {
		this.contactos = lista;
	}

	public Integer getNumContactos() {
		return numContactos;
	}

	public void setNumContactos(Integer numContactos) {
		this.numContactos = numContactos;
	}
	
	
}
