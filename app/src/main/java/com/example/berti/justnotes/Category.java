package com.example.berti.justnotes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Category extends Activity {

    Intent oIntent;
    protected String tema=null;
    protected AdaptadorBaseDados adaptadorBaseDados;
    protected Cursor cursor;
    protected List<String> titulos;
    ListView listView;
    protected Integer indexCategoria;
    private FloatingActionButton btnAdd;

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

        oIntent = getIntent();
        indexCategoria = oIntent.getExtras().getInt("indexCategoria");
        SetTheme.setThemeToActivity(this, "Green");

        setContentView(R.layout.activity_category);




        cursor = null;
        titulos = new ArrayList<>();
        adaptadorBaseDados = new AdaptadorBaseDados(this).open();

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


                //executarActivity(Main3Activity.class, index);


            }

        });


        btnAdd = (FloatingActionButton)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //executarActivity(AddCategory.class, tema);
            }
        });


    }

    private void executarActivity(Class<?> subAtividade, Integer valor){
        Intent x = new Intent(this, subAtividade);
        x.putExtra("index", valor);
        startActivity(x);
    }
}
