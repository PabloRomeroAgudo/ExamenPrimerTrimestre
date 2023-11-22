package dao;



import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.Oficina;

/**
 * Controlador JAXB de la Bibloteca
 * 
 * @author Amadeo
 */
public class JaxbController {
    private static JaxbController instance;
    
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private Oficina oficina;

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
    public void objectToXML(Oficina oficina) throws JAXBException {
        this.oficina = oficina;

        JAXBContext context = JAXBContext.newInstance(Oficina.class);
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
        marshaller.marshal(oficina, new File(uri));
    }

    /**
     * Convierte un XML en una lista de objetos
     *
     * @param biblioteca
     * @throws JAXBException
     */
    public Oficina xmlToObject(String uri) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Oficina.class);
        unmarshaller = context.createUnmarshaller();
        oficina = (Oficina) unmarshaller.unmarshal(new File(uri));
        return this.oficina;
    }
}