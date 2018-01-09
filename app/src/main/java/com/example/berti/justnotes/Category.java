package com.example.berti.justnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {

    protected Intent oIntent;
    protected AdaptadorBaseDados adaptadorBaseDados;
    protected Cursor cursor;
    protected List<String> titulos;
    protected ListView listView;
    protected Integer indexCategoria;
    protected FloatingActionButton btnAdd;
    protected FloatingActionButton btnEditar, btnEliminar;
    protected final Context context = this;
    protected TextView txvCategoria;

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
        setContentView(R.layout.activity_category);

        oIntent = getIntent();
        indexCategoria = oIntent.getExtras().getInt("indexCategoria");
        txvCategoria = (TextView)findViewById(R.id.txvCategoria);
        cursor = null;
        titulos = new ArrayList<>();
        adaptadorBaseDados = new AdaptadorBaseDados(this).open();


        cursor = adaptadorBaseDados.obterCategoria(indexCategoria);
        if (cursor.moveToFirst()) {
            txvCategoria.setText(cursor.getString(1));
        }

        cursor=null;


        cursor = adaptadorBaseDados.obterTitulosCategoria(indexCategoria);

        if (cursor.moveToFirst()) {
            do {
                titulos.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }



        listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, titulos);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                cursor.moveToPosition(position);
                int index = cursor.getInt(0);



                executarActivity(VerNota.class, indexCategoria, index);


            }

        });


        btnAdd = (FloatingActionButton)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executarActivity(AddNote.class, indexCategoria, null);
            }
        });

        btnEditar = (FloatingActionButton)findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executarActivity(EditCategory.class, indexCategoria, null);
            }
        });

        btnEliminar = (FloatingActionButton)findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setTitle("Deseja mesmo eliminar esta categoria?");

                alertDialogBuilder
                        .setMessage("Se eliminar todas as notas serão perdidas!")
                        .setCancelable(false)
                        .setPositiveButton("Sim",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                adaptadorBaseDados.apagarCategoria(indexCategoria);
                                showToast("Eliminado com sucesso!");
                                executarActivity(MainActivity.class, null, null);
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
        if (indexCategoria!=null) {
            outputState.putInt("indexCategoria", indexCategoria);
        }
        super.onSaveInstanceState(outputState);
    }

    protected void restoreVarsFromBundle(Bundle savedInstanceState) {
        Integer i = savedInstanceState.getInt("indexCategoria");
        if (i!=null)
            indexCategoria = i;
    }

    protected void showToast(String mensagem){
        Context context = getApplicationContext();
        CharSequence text = mensagem;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onBackPressed() {
        executarActivity(MainActivity.class, null, null);
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
