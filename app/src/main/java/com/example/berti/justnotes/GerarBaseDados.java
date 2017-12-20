package com.example.berti.justnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by berti on 19/12/2017.
 */

public class GerarBaseDados extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int VERSION = 1;

    public GerarBaseDados(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String categorias = "CREATE TABLE categorias(idCategoria integer primary key autoincrement, nomeCategoria varchar(40))";
        db.execSQL(categorias);

        String notas = "CREATE TABLE notas(idNota integer primary key autoincrement, idCategoria integer, tituloNota varchar(40), textoNota text, dataNota date, FOREIGN KEY(idCategoria) REFERENCES categorias(idCategoria))";
        db.execSQL(notas);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categorias");
        db.execSQL("DROP TABLE IF EXISTS notas");
        onCreate(db);
    }

}
