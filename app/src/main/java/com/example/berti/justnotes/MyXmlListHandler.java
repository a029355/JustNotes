package com.example.berti.justnotes;

import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

/**
 * Created by berti on 21/12/2017.
 */

public class MyXmlListHandler<E> extends DefaultHandler {
    protected List<E> osElementos;
    public List<E> obterElementos() {
        return osElementos;
    }
}
