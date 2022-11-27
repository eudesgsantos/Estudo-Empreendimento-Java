package com.example.empreendimento.controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.metadata.IIOMetadataNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReadXML {

    public ArrayList<String> run (String path, ArrayList<String> parameters, String name){
        File file = new File(path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder;
        {
            try {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }

        Document document;

        {
            try {
                document = documentBuilder.parse(file);
            } catch (SAXException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        ArrayList<String> results = new ArrayList<String>();

        NodeList list = document.getElementsByTagName(name);
        results.add(String.valueOf(list.getLength()));
        for (int i = 0; i < list.getLength(); i++) {
            for (String param : parameters) {
                String a = document.getElementsByTagName(param).item(i).getTextContent();
                results.add(a);
            }
        }
        return results;

    }
}
