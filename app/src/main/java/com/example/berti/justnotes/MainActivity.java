package com.example.berti.justnotes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Intent oIntent;
    protected String tema=null;
    protected AdaptadorBaseDados adaptadorBaseDados;
    protected Cursor cursor;
    protected GridView gridView;
    private List<String> arrCategorias;
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
        tema = oIntent.getExtras().getString("tema");
        SetTheme.setThemeToActivity(this, "Green");
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.gvCatetogias);

        adaptadorBaseDados = new AdaptadorBaseDados(this).open();
        cursor = adaptadorBaseDados.obterCategorias();

        if (cursor.moveToFirst() && cursor!=null) {
            do {
                arrCategorias.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        arrCategorias = new ArrayList<String>();
        //String[] arr = new String[] { "one", "two", "three" };

        if(arrCategorias.size() !=0) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrCategorias);

            gridView.setAdapter(adapter);
        }


        btnAdd = (FloatingActionButton)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Click!");
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
}
