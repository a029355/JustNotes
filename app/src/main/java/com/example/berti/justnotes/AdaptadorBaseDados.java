package com.example.berti.justnotes;

import android.content.ContentValues;
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

    public Cursor obterCategorias(){
        Cursor cursor = database.rawQuery(
                "select idCategoria, nomeCategoria, (select count(*) from notas where notas.idCategoria = categorias.idCategoria) from categorias", null);


        return cursor;
    }

    public Cursor obterCategoria(Integer index){
        Cursor cursor = database.rawQuery(
                "select idCategoria, nomeCategoria from categorias where idCategoria=?", new String[] { index.toString() });


        return cursor;
    }

    public boolean insereCategoria(String nome){
        ContentValues valores;
        long resultado;

        database = dbHelper.getWritableDatabase();
        valores = new ContentValues();
        valores.put("nomeCategoria", nome);

        resultado = database.insert("categorias", null, valores);
        database.close();

        if (resultado ==-1)
            return false;
        else
            return true;

    }

    public boolean updateCategoria(Integer oId, String oNome) {
        long resultado;
        String whereClause = "idCategoria = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = new Integer(oId).toString();
        ContentValues values = new ContentValues();
        values.put("nomeCategoria", oNome);
        resultado =  database.update("categorias", values, whereClause, whereArgs);
        if (resultado ==-1)
            return false;
        else
            return true;
    }


    public Cursor obterTitulosCategoria(Integer index){
        Cursor cursor = database.rawQuery(
                "select idNota, tituloNota, textoNota, dataNota, idCategoria from notas where idCategoria=?", new String[] { index.toString() });


        return cursor;

    }

    public boolean insereNota(String titulo, String nota, String data, Integer index){
        ContentValues valores;
        long resultado;

        database = dbHelper.getWritableDatabase();
        valores = new ContentValues();
        valores.put("tituloNota", titulo);
        valores.put("textoNota", nota);
        valores.put("dataNota", data);
        valores.put("idCategoria", index);

        resultado = database.insert("notas", null, valores);
        database.close();

        if (resultado ==-1)
            return false;
        else
            return true;

    }

    public int apagarCategoria(Integer index) {
        String whereClause = "idCategoria = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = ""+index;
        database.delete("notas", whereClause, whereArgs);
        return database.delete("categorias", whereClause, whereArgs);
    }


    public Cursor obterNota(Integer index){
        Cursor cursor = database.rawQuery(
                "select idNota, tituloNota, textoNota, dataNota, idCategoria from notas where idNota=?", new String[] { index.toString() });


        return cursor;

    }

    public int apagarNota(Integer index) {
        String whereClause = "idNota = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = ""+index;
        return database.delete("notas", whereClause, whereArgs);
    }

    public boolean updateNota(String oTitulo, String oNota, String oData, Integer oId, Integer oIdCat) {
        long resultado;
        String whereClause = "idNota = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = new Integer(oId).toString();

        ContentValues values = new ContentValues();
        values.put("tituloNota", oTitulo);
        values.put("textoNota", oNota);
        values.put("dataNota", oData);
        values.put("idCategoria", oIdCat);

        resultado =  database.update("notas", values, whereClause, whereArgs);
        if (resultado ==-1)
            return false;
        else
            return true;
    }
}
