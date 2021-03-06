package com.example.berti.justnotes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected Intent oIntent;
    protected AdaptadorBaseDados adaptadorBaseDados;
    protected Cursor cursor;
    protected GridView gridView;
    protected List<Integer> arrIdCategorias;
    protected List<String> arrNomesCategorias;
    protected FloatingActionButton btnAdd;
    protected GridviewAdapter mAdapter;

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
        setContentView(R.layout.activity_main);


        arrIdCategorias = new ArrayList<Integer>();
        arrNomesCategorias = new ArrayList<String>();

        gridView = (GridView)findViewById(R.id.gvCatetogias);

        adaptadorBaseDados = new AdaptadorBaseDados(this).open();
        cursor = adaptadorBaseDados.obterCategorias();

        if (cursor.moveToFirst() && cursor!=null) {
            do {
                arrIdCategorias.add(cursor.getInt(0));
                arrNomesCategorias.add(cursor.getString(1) +" ("+cursor.getInt(2)+")");
            } while (cursor.moveToNext());
        }

        if(arrNomesCategorias.size() !=0) {

            mAdapter = new GridviewAdapter(this, arrNomesCategorias);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrCategorias);

            gridView.setAdapter(mAdapter);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                cursor.moveToPosition(position);
                int index = cursor.getInt(0);

                //Toast.makeText(MainActivity.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();

                executarActivity(Category.class, index, null);

            }
        });



        btnAdd = (FloatingActionButton)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executarActivity(AddCategory.class, null, null);
            }
        });
    }


    protected void showToast(String mensagem){
        Context context = getApplicationContext();
        CharSequence text = mensagem;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                moveTaskToBack(true);
                return true;
        }
        return false;
    }

    protected void executarActivity(Class<?> subAtividade, Integer indexCategoria, Integer indexNota){
        Intent x = new Intent(this, subAtividade);
        x.putExtra("indexCategoria", indexCategoria);
        x.putExtra("indexNota", indexNota);
        startActivity(x);
    }
}
