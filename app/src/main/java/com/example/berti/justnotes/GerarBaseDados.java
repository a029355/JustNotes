package com.example.berti.justnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by berti on 19/12/2017.
 */

public class GerarBaseDados extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "baseDados.db";
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

        String listas = "CREATE TABLE listas(idLista integer primary key autoincrement, idCategoria integer, tituloLista varchar(40), dataLista date, FOREIGN KEY(idCategoria) REFERENCES categorias(idCategoria))";
        db.execSQL(listas);

        String tarefas = "CREATE TABLE tarefas(idTarefa integer primary key autoincrement, idLista integer, tituloLista varchar(40), nomeTarefa varchar(40), checkedTarefa integer, FOREIGN KEY(idLista) REFERENCES listas(idLista))";
        db.execSQL(tarefas);

        String definicoes = "CREATE TABLE definicoes(idDefinicao integer primary key autoincrement, nomeTema varchar(40))";
        db.execSQL(definicoes);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categorias");
        db.execSQL("DROP TABLE IF EXISTS notas");
        db.execSQL("DROP TABLE IF EXISTS listas");
        db.execSQL("DROP TABLE IF EXISTS tarefas");
        db.execSQL("DROP TABLE IF EXISTS definicoes");
        onCreate(db);
    }

}
