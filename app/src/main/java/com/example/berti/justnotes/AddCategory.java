package com.example.berti.justnotes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class AddCategory extends AppCompatActivity {

    protected Intent oIntent;
    protected AdaptadorBaseDados adaptadorBaseDados;
    protected Cursor cursor;
    protected Button btnInserir;
    protected EditText edtNome;

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
        setContentView(R.layout.activity_add_category);

        edtNome = (EditText)findViewById(R.id.edtNome);
        btnInserir = (Button)findViewById(R.id.btnInserir);
        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validaCampos(edtNome)) {

                    Boolean resultado = adaptadorBaseDados.insereCategoria(edtNome.getText().toString());

                    String[] arrMensagem = {"Erro ao inserir o registro!", "Registro inserido com sucesso!"};

                    if (resultado) {
                        showToast(arrMensagem[1]);
                        Toast.makeText(getApplicationContext(), arrMensagem[1], Toast.LENGTH_SHORT).show();
                        edtNome.setText("");


                        executarActivity(MainActivity.class, null, null);
                        //startActivity(intent);
                        /*
                        Thread thread = new Thread(){
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        thread.start();*/

                    } else {
                        showToast(arrMensagem[0]);
                        //Toast.makeText(getApplicationContext(), arrMensagem[0], Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
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

    protected void executarActivity(Class<?> subAtividade, Integer indexCategoria, Integer indexNota){
        Intent x = new Intent(this, subAtividade);
        x.putExtra("indexCategoria", indexCategoria);
        x.putExtra("indexNota", indexNota);
        startActivity(x);
    }
}
