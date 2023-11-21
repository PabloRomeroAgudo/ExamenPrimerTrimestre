package jaxb;



import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.Agenda;

/**
 * Controlador JAXB de la Bibloteca
 * 
 * @author Amadeo
 */
public class JaxbController {
    private static JaxbController instance;
    
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private Agenda agenda;

    private JaxbController() {}

    /**
     * Devuelve la instancia del controlador
     *
     * @return
     */
    public static JaxbController getInstance() {
        if (instance == null) {
            instance = new JaxbController();
        }
        return instance;
    }

    /**
     * Convierte una lista de objetos en XML
     *
     * @param biblioteca
     * @throws JAXBException
     */
    public void objectToXML(Agenda agenda) throws JAXBException {
        this.agenda = agenda;

        JAXBContext context = JAXBContext.newInstance(Agenda.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }

    /**
     * Escribe un fichero XML con el contenido de los datos
     *
     * @param uri
     * @throws JAXBException
     */
    public void writeXMLFile(String uri) throws JAXBException {
        marshaller.marshal(agenda, new File(uri));
    }

    /**
     * Convierte un XML en una lista de objetos
     *
     * @param biblioteca
     * @throws JAXBException
     */
    public Agenda xmlToObject(String uri) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Agenda.class);
        unmarshaller = context.createUnmarshaller();
        agenda = (Agenda) unmarshaller.unmarshal(new File(uri));
        return this.agenda;
    }
}