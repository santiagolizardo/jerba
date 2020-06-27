package com.santiagolizardo.jerba.utilities;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigReader {

    private static final Logger logger = Logger.getLogger(ConfigReader.class.getName());

    public Config read(InputStream is) {
        Config config = new Config();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = dbFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(is);

            try {
                config.setAppspotDomainAllowed(Boolean.parseBoolean(
                        doc.getDocumentElement().getAttributes().getNamedItem("appspot-domain-allowed").getNodeValue()));

            } catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage(), e);
            }

            try {
                config.setInsecureHttpAllowed(Boolean.parseBoolean(
                        doc.getDocumentElement().getAttributes().getNamedItem("insecure-http-allowed").getNodeValue()));

            } catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage(), e);
            }

            NodeList redirectionNodes = doc.getElementsByTagName("redirection");
            for (int i = 0; i < redirectionNodes.getLength(); i++) {
                Node node = redirectionNodes.item(i);
                String pattern = node.getAttributes().getNamedItem("pattern").getNodeValue();
                String url = node.getAttributes().getNamedItem("url").getNodeValue();
                config.getRedirections().put(pattern, url);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return config;
    }
}
