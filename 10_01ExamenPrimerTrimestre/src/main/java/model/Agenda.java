package model;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@XmlRootElement(namespace = "org.agenda")
public class Agenda {
	ArrayList<Contacto> contactos = new ArrayList<Contacto>();
	
	Integer numContacts;
	
	@XmlElementWrapper(name = "contactitos")
	@XmlElement(name = "contacto")
	public ArrayList<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(ArrayList<Contacto> contactos) {
		this.contactos = contactos;
	}

	@XmlElement(name = "Numero_de_contactos")
	public Integer getNumContacts() {
		return numContacts;
	}

	public void setNumContacts(Integer numContacts) {
		this.numContacts = numContacts;
	}
	
	
}
