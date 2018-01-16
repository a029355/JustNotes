package com.example.berti.justnotes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by berti on 21/12/2017.
 */

public class ViewAsyncGenerator extends AsyncTask<Integer, Double, String> {
    protected double percentage;
    protected Button b;
    protected AutoCompleteTextView t;
    protected String savedLabel;
    protected int port;
    protected String host;
    protected String path;
    protected boolean ignorar;
    protected Context mContext;

    public ViewAsyncGenerator(Button botao, String host, String path, int port, AutoCompleteTextView  t, Context context) {
        b = botao;
        this.host = host;
        this.path = path;
        this.port = port;
        this.t = t;
        this.mContext = context;
    }
    @Override // runs on the GUI thread
    protected void onPreExecute() {
        ignorar = false;
        savedLabel = b.getText().toString();
        b.setText("Aguarde ... 0%");
        b.setEnabled(false);
    }
    @Override // runs on its own thread
    protected String doInBackground(Integer... args) {
        if (ignorar)
            return "";
        return Comunicar.contactar2(host, path, port);
    }
    @Override // runs on the GUI thread
    protected void onProgressUpdate(Double... percentComplete) {
        String theText;
        percentage = percentComplete[0];
        theText = "" + (int)(percentage * 100) + "%";
        b.setText(theText);
    }
    @Override // runs on the GUI thread
    protected void onPostExecute(String s) {
        List<NomesCategorias> listaCategorias = null;
        listaCategorias = new ArrayList<>();


        b.setText(savedLabel);
        b.setEnabled(true);

        ArrayList<String> aLista = new ArrayList<String>(10);
        try {

            SaxXmlParser<NomesCategorias, SaxXmlCategoriaHandler> oMeuParser =
                    new SaxXmlParser<NomesCategorias, SaxXmlCategoriaHandler>();
            oMeuParser.setHandler(new SaxXmlCategoriaHandler());
            listaCategorias = oMeuParser.parse(new StringReader(s));

            String osIds = "";
            NomesCategorias umaCategoria = null;

            if(listaCategorias.size()!=0) {
                String[] cat = new String[listaCategorias.size()];
                for (int k = 0; k < listaCategorias.size(); ++k) {

                    umaCategoria = listaCategorias.get(k);
                    aLista.add(umaCategoria.getCategoriaNome() + "\n");
                    osIds = osIds + umaCategoria.getCategoriaNome() + "\n";
                    cat[k] = umaCategoria.getCategoriaNome();

                }

                s = osIds;
                ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, cat);
                t.setThreshold(1);
                t.setAdapter(itemsAdapter);
            }
        } catch (Exception e) {
            aLista.add(e.toString());  // devolve a excepção obtida
        }
    }
}

