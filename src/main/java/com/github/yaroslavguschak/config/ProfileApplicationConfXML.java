package com.github.yaroslavguschak.config;

import com.github.yaroslavguschak.DAO.ContactDAO;
import com.github.yaroslavguschak.DAO.xml.XMLConverter;
import com.github.yaroslavguschak.DAO.xml.XMLdb;
import com.github.yaroslavguschak.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@Profile("xml")
public class ProfileApplicationConfXML {
    @Autowired
    private Environment env;

    @Autowired
    XMLdb xmLdb;

    @Bean
    public JAXBContext getXMLdbJaxbContext () throws JAXBException {
        return JAXBContext.newInstance(XMLdb.class);
    }

    @Bean
    public File xmlFile() throws IOException, JAXBException {
        File xmlFile = new File(env.getProperty("xmldb.home"));
        if(xmlFile.createNewFile()){
            System.out.println("creating new fileDB: " + env.getProperty("xmldb.home"));
            final String xmlCreate = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<xmldb>\n" +
                    "</xmldb>";
            PrintWriter printWriter = new PrintWriter(xmlFile);
            printWriter.write(xmlCreate);
            printWriter.flush();
            printWriter.close();

        } else {
            System.out.println("file " + env.getProperty("xmldb.home") + " already exist");
        }



        return xmlFile;
    }



}
