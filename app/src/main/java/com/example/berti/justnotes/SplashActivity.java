package com.example.berti.justnotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashActivity extends Activity {

    // Timer da splash screen
    private static int SPLASH_TIME_OUT = 3000;
    protected AdaptadorBaseDados adaptadorBaseDados;
    protected Cursor cursor;
    protected String tema=null;


    @Override
    protected void onStart() {
        super.onStart();
        //showToast("MainActivity onStart()");
        //Toast.makeText(this, "MainActivity onStart()", Toast.LENGTH_SHORT).show();
        adaptadorBaseDados = new AdaptadorBaseDados(this).open();
    }
    protected void onPause() {
        super.onPause();
        //showToast("MainActivity onPause()");
        //Toast.makeText(this, "MainActivity onPause()", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        //showToast("MainActivity onStop()");
        //Toast.makeText(this, "MainActivity onStop()", Toast.LENGTH_SHORT).show();
        adaptadorBaseDados.close();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        adaptadorBaseDados = new AdaptadorBaseDados(this).open();
        cursor = adaptadorBaseDados.obterDefinicoes();

        if (cursor.moveToFirst() && cursor!=null) {
            tema = cursor.getString(1);
        }

        if(tema!=null){
            super.setTheme(R.style.AppTheme);
        }
        else{
            super.setTheme(R.style.Green);
        }


        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                i.putExtra("tema", tema);
                startActivity(i);

                // Fecha esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    protected void showToast(String mensagem){
        Context context = getApplicationContext();
        CharSequence text = mensagem;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
