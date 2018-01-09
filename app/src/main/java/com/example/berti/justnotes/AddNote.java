package com.example.berti.justnotes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNote extends AppCompatActivity {

    protected Intent oIntent;
    protected AdaptadorBaseDados adaptadorBaseDados;
    protected Cursor cursor;
    protected Integer indexCategoria;
    protected Button btnAdd;
    protected EditText edtTitulo, edtNota;
    protected String titulo;

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
        setContentView(R.layout.activity_add_note);

        oIntent = getIntent();
        indexCategoria = oIntent.getExtras().getInt("indexCategoria");

        edtTitulo = (EditText)findViewById(R.id.edtNome);
        edtNota = (EditText)findViewById(R.id.edtTexto);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        adaptadorBaseDados = new AdaptadorBaseDados(this).open();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validaCampos(edtNota)) {

                    if(edtTitulo.getText().toString().equals(""))
                        titulo = getCurrentDate();
                    else
                        titulo = edtTitulo.getText().toString();

                    Boolean resultado = adaptadorBaseDados.insereNota(titulo, edtNota.getText().toString(), getCurrentDate().toString(), indexCategoria);

                    String[] arrMensagem = {"Erro ao inserir o registro!", "Registro inserido com sucesso!"};
                    if (resultado) {
                        showToast(arrMensagem[1]);
                        edtTitulo.setText("");
                        edtNota.setText("");
                        executarActivity(Category.class, indexCategoria, null);
                    } else {
                        showToast(arrMensagem[0]);
                    }
                }
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outputState) {
        String s = edtTitulo.getText().toString();
        if (!s.equals("")) {
            outputState.putString("edtTitulo", s);
        }

        String s2 = edtNota.getText().toString();
        if (!s2.equals("")) {
            outputState.putString("edtTexto", s2);
        }

        if (indexCategoria!=null) {
            outputState.putInt("indexCategoria", indexCategoria);
        }

        super.onSaveInstanceState(outputState);
    }

    protected void restoreVarsFromBundle(Bundle savedInstanceState) {
        String s = savedInstanceState.getString("edtTitulo");
        if (!s.equals(""))
            edtTitulo.setText(s);

        String s2 = savedInstanceState.getString("edtNota");
        if (!s.equals(""))
            edtNota.setText(s);

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

    protected Boolean validaCampos(EditText edtNota){
        Boolean b = false;
        if (edtNota.getText().toString().equals("")) {
            edtNota.requestFocus();
            showToast("Campo Nota obrigatório!");
        }
        else
            b=true;


        return b;
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        return mdformat.format(calendar.getTime());
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
