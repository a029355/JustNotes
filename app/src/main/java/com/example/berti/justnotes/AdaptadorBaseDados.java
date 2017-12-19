package com.example.berti.justnotes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by berti on 19/12/2017.
 */

public class AdaptadorBaseDados {

    private GerarBaseDados dbHelper;
    private SQLiteDatabase database;

    public AdaptadorBaseDados(Context context) {
        dbHelper = new GerarBaseDados(context);
    }

    public AdaptadorBaseDados open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }
/*
    public boolean insereDados(String nome, String morada, String telefone){
        ContentValues valores;
        long resultado;

        database = dbHelper.getWritableDatabase();
        valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("morada", morada);
        valores.put("telefone", telefone);

        resultado = database.insert("registos", null, valores);
        database.close();

        if (resultado ==-1)
            return false;
        else
            return true;

    }

    public Cursor obterTodosRegistosIdNome() {
        String[] colunas = new String[2];
        colunas[0] = "_id";
        colunas[1] = "nome";
        return database.query("registos", colunas, null, null, null, null, null, null);
    }


    public Cursor obterDadosEspecificos(Integer index){
        Cursor cursor = database.rawQuery(
                "select _id, nome, morada, telefone from registos where _id=?", new String[] { index.toString() });


        return cursor;

    }
*/

    public Cursor obterDefinicoes(){
        String[] colunas = new String[2];
        colunas[0] = "idDefinicao";
        colunas[1] = "nomeTema";
        return database.query("definicoes", colunas, null, null, null, null, null, null);
    }

    public Cursor obterCategorias(){
        Cursor cursor = database.rawQuery(
                "select idCategoria, nomeCategoria from categorias", null);


        return cursor;
    }
}
