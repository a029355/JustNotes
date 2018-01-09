package com.example.berti.justnotes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditNote extends AppCompatActivity {

    protected Intent oIntent;
    protected AdaptadorBaseDados adaptadorBaseDados;
    protected Cursor cursor, cursorCategorias;
    protected Button btnGuardar;
    protected EditText edtNome, edtTexto;
    protected Integer indexCategoria, indexNota;
    protected String titulo;
    protected Spinner spinner;
    protected List<Integer> arrIdCategorias;
    protected List<String> arrNomesCategorias;
    protected Integer pos;

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
        setContentView(R.layout.activity_edit_note);

        oIntent = getIntent();
        indexCategoria = oIntent.getExtras().getInt("indexCategoria");
        indexNota = oIntent.getExtras().getInt("indexNota");

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtTexto = (EditText) findViewById(R.id.edtTexto);
        spinner = (Spinner) findViewById(R.id.spinner);
        arrIdCategorias = new ArrayList<Integer>();
        arrNomesCategorias = new ArrayList<String>();


        adaptadorBaseDados = new AdaptadorBaseDados(this).open();

        cursor = adaptadorBaseDados.obterNota(indexNota);
        if (cursor.moveToFirst()) {
            edtNome.setText(cursor.getString(1));
            edtTexto.setText(cursor.getString(2));
        }

        cursorCategorias = adaptadorBaseDados.obterCategorias();
        if (cursorCategorias.moveToFirst() && cursor!=null) {
            Integer i=0;
            do {
                arrIdCategorias.add(cursorCategorias.getInt(0));
                arrNomesCategorias.add(cursorCategorias.getString(1));

                if(cursorCategorias.getInt(0)==indexCategoria)
                    pos=i;

                i++;
            } while (cursorCategorias.moveToNext());
        }
        ArrayAdapter<String> oAdaptador2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrNomesCategorias);
        oAdaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(oAdaptador2);
        spinner.setSelection(pos);




        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validaCampos(edtTexto)) {

                    if(edtNome.getText().toString().equals(""))
                        titulo = getCurrentDate();
                    else
                        titulo = edtNome.getText().toString();

                    cursorCategorias.moveToPosition(spinner.getSelectedItemPosition());

                    Integer idCat = cursorCategorias.getInt(spinner.getSelectedItemPosition());

                    Boolean resultado = adaptadorBaseDados.updateNota(titulo, edtTexto.getText().toString(), getCurrentDate().toString(), indexNota, idCat);

                    String[] arrMensagem = {"Erro ao guardar!", "Guardado com sucesso!"};

                    if (resultado) {
                        showToast(arrMensagem[1]);

                        if(indexCategoria == idCat) {
                            executarActivity(VerNota.class, indexCategoria, indexNota);
                        }
                        else{
                            executarActivity(Category.class, indexCategoria, null);
                        }

                    } else {
                        showToast(arrMensagem[0]);
                    }
                }

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outputState) {
        String s = edtNome.getText().toString();
        if (!s.equals("")) {
            outputState.putString("edtNome", s);
        }

        String s2 = edtTexto.getText().toString();
        if (!s2.equals("")) {
            outputState.putString("edtTexto", s2);
        }

        Integer positionSpinner = spinner.getSelectedItemPosition();
        if (positionSpinner!=null) {
            outputState.putInt("spinner", positionSpinner);
        }

        if (indexNota!=null) {
            outputState.putInt("indexNota", indexNota);
        }

        if (indexCategoria!=null) {
            outputState.putInt("indexCategoria", indexCategoria);
        }

        super.onSaveInstanceState(outputState);
    }

    protected void restoreVarsFromBundle(Bundle savedInstanceState) {
        String s = savedInstanceState.getString("edtNome");
        if (!s.equals(""))
            edtNome.setText(s);

        String s2 = savedInstanceState.getString("edtTexto");
        if (!s.equals(""))
            edtTexto.setText(s);

        Integer positionSpinner = savedInstanceState.getInt("spinner");
        if (positionSpinner!=null)
            spinner.setSelection(positionSpinner);


        Integer idNota = savedInstanceState.getInt("indexNota");
        if (indexNota!=null)
            indexNota = idNota;

        Integer idCategoria = savedInstanceState.getInt("indexCategoria");
        if (indexCategoria!=null)
            indexCategoria = idCategoria;
    }


    protected Boolean validaCampos(EditText edtTexto) {
        Boolean b = false;
        if (edtTexto.getText().toString().equals("")) {
            edtTexto.requestFocus();
            showToast("Campo Nota obrigatório!");
        } else
            b = true;

        return b;
    }

    protected void showToast(String mensagem) {
        Context context = getApplicationContext();
        CharSequence text = mensagem;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        return mdformat.format(calendar.getTime());
    }

    @Override
    public void onBackPressed() {
        executarActivity(VerNota.class, indexCategoria, indexNota);
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