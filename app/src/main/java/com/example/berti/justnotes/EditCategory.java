package com.example.berti.justnotes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditCategory extends AppCompatActivity {

    protected Intent oIntent;
    protected AdaptadorBaseDados adaptadorBaseDados;
    protected Cursor cursor;
    protected Button btnGuardar;
    protected AutoCompleteTextView edtNome;
    protected Integer indexCategoria;
    protected final String _LINK1 = "miang.pt";
    protected final String _LINK2 = "teste/xml/categorias.xml";

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
        setContentView(R.layout.activity_edit_category);

        oIntent = getIntent();
        indexCategoria = oIntent.getExtras().getInt("indexCategoria");
        edtNome = (AutoCompleteTextView)findViewById(R.id.edtNome);

        adaptadorBaseDados = new AdaptadorBaseDados(this).open();

        cursor = adaptadorBaseDados.obterCategoria(indexCategoria);
        if(cursor.moveToFirst()){
            edtNome.setText(cursor.getString(1));
        }




        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        executarViewAsyncGenerator(btnGuardar, edtNome);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validaCampos(edtNome)) {

                    Boolean resultado = adaptadorBaseDados.updateCategoria(indexCategoria, edtNome.getText().toString());

                    String[] arrMensagem = {"Erro ao guardar!", "Guardado com sucesso!"};

                    if (resultado) {
                        showToast(arrMensagem[1]);
                        edtNome.setText("");

                        executarActivity(Category.class, indexCategoria, null);

                    } else {
                        showToast(arrMensagem[0]);
                        //Toast.makeText(getApplicationContext(), arrMensagem[0], Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    protected void executarViewAsyncGenerator(Button btnInserir, AutoCompleteTextView edtNome){
        new ViewAsyncGenerator(btnInserir, _LINK1, _LINK2, 80, edtNome, getApplicationContext()).execute(0);
    }

    protected Boolean validaCampos(EditText edtNome){
        Boolean b = false;
        if (edtNome.getText().toString().equals("")) {
            edtNome.requestFocus();
            showToast("Todos os campos são obrigatórios!");
        }
        else
            b=true;

        return b;
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