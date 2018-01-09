package com.example.berti.justnotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VerNota extends AppCompatActivity {

    protected Intent oIntent;
    protected AdaptadorBaseDados adaptadorBaseDados;
    protected Cursor cursor;
    protected Integer indexCategoria, indexNota;
    protected Button btnEditar, btnEliminar;
    protected TextView txvTitulo, txvData, txvTexto;
    protected final Context context = this;

    @Override
    protected void onStart() {
        super.onStart();
        adaptadorBaseDados = new AdaptadorBaseDados(this).open();
    }
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adaptadorBaseDados.close();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_nota);

        oIntent = getIntent();
        indexCategoria = oIntent.getExtras().getInt("indexCategoria");
        indexNota = oIntent.getExtras().getInt("indexNota");

        txvTitulo = (TextView)findViewById(R.id.txvTitulo);
        txvData = (TextView)findViewById(R.id.txvData);
        txvTexto = (TextView)findViewById(R.id.txvTexto);
        txvTexto.setMovementMethod(new ScrollingMovementMethod());
        cursor = null;
        adaptadorBaseDados = new AdaptadorBaseDados(this).open();


        cursor = adaptadorBaseDados.obterNota(indexNota);
        if (cursor.moveToFirst()) {
            txvTitulo.setText("Titulo: "+cursor.getString(1));
            txvData.setText("Data: "+cursor.getString(3));
            txvTexto.setText("Nota: "+cursor.getString(2));
        }

        btnEditar = (Button)findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executarActivity(EditNote.class, indexCategoria, indexNota);
            }
        });

        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setTitle("Deseja mesmo eliminar esta nota?");

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Sim",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                adaptadorBaseDados.apagarNota(indexNota);
                                showToast("Eliminado com sucesso!");
                                executarActivity(Category.class, indexCategoria, null);
                            }
                        })
                        .setNegativeButton("Não",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle outputState) {
        if (indexNota!=null) {
            outputState.putInt("indexNota", indexNota);
        }

        if (indexCategoria!=null) {
            outputState.putInt("indexCategoria", indexCategoria);
        }
        super.onSaveInstanceState(outputState);
    }

    protected void restoreVarsFromBundle(Bundle savedInstanceState) {
        Integer idNota = savedInstanceState.getInt("indexNota");
        if (indexNota!=null)
            indexNota = idNota;

        Integer idCategoria = savedInstanceState.getInt("indexCategoria");
        if (indexCategoria!=null)
            indexCategoria = idCategoria;
    }



    protected void showToast(String mensagem){
        Context context = getApplicationContext();
        CharSequence text = mensagem;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        executarActivity(Category.class, indexCategoria, null);
        finish();
        super.onBackPressed();
    }

    protected void executarActivity(Class<?> subAtividade, Integer indexCategoria, Integer indexNota){
        Intent x = new Intent(this, subAtividade);
        x.putExtra("indexCategoria", indexCategoria);
        x.putExtra("indexNota", indexNota);
        startActivity(x);
    }
}