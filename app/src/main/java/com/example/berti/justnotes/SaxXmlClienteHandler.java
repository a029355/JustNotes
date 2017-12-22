package com.example.berti.justnotes;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;

/**
 * Created by berti on 21/12/2017.
 */

public class SaxXmlClienteHandler extends MyXmlListHandler<NomesCategorias> {
    private String tempValue;
    private NomesCategorias tempCategorias;
    public SaxXmlClienteHandler() {
        osElementos = new ArrayList<NomesCategorias>();
    }
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {
        tempValue = "";
        if (qName.equalsIgnoreCase("item"))
            tempCategorias = new NomesCategorias();


    }
    @Override
    public void characters(char[] ch, int start, int end) {
        tempValue = new String(ch, start, end);
    }
    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            tempCategorias.setNomeCategoria(tempValue);
            osElementos.add(tempCategorias);
        }
    }
}
