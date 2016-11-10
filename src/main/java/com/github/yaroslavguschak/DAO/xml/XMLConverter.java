package com.github.yaroslavguschak.DAO.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
@Profile("xml")
public class XMLConverter {
    @Autowired
    private Environment env;

    @Autowired
    JAXBContext jaxbContext;

    @Autowired
    File xmlFile;

//    private final String XMLDB_PATH =  env.getProperty("xmldb.home");
//    private File xmlFile = new File(XMLDB_PATH);

    public void marshalingXMLDB(XMLdb xmLdb) throws JAXBException {
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(xmLdb, xmlFile);
    }

    public XMLdb unmarshalingXMLDB() {
        try {
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return  (XMLdb)jaxbUnmarshaller.unmarshal(xmlFile);

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createXMLDB() throws JAXBException {
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(new XMLdb(), new File( env.getProperty("xmldb.home")));
    }


}
