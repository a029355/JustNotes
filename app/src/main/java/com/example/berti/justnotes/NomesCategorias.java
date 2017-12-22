package com.example.berti.justnotes;

/**
 * Created by berti on 21/12/2017.
 */

public class NomesCategorias {

    protected String categoriaNome;

    public NomesCategorias(String nome) {
        categoriaNome = nome;
    }
    public NomesCategorias() {
        categoriaNome = null;
    }
    public void setNomeCategoria(String nome) {
        categoriaNome = nome;
    }
    public String getCategoriaNome() {
        return categoriaNome;
    }

    @Override
    public String toString() {
        return categoriaNome;
    }
}
