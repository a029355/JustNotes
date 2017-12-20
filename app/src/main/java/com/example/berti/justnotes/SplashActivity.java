package com.example.berti.justnotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Toast;

public class SplashActivity extends Activity {

    protected static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onStart() {
        super.onStart();
    }
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {

                executarActivity(MainActivity.class, null, null);
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
