package com.example.login;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class EditActivity extends AppCompatActivity {
    EditText tituloEdit,codigoEdit;
    TextView url,idView, highlight, owner;
    CheckBox lineas;
    int id;
    SharedPreferences sharedPref, sharedPreferencesEdit;
    ArrayList<String> estilos;
    ArrayList<String> lenguajes;
    Spinner dropdownLenguajes;
    Spinner dropdownEstilos;
    Snippet snippet;
    String snipetString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle datos = getIntent().getExtras();
        snipetString = datos.getString("snippet");
        snippet= new Gson().fromJson(snipetString, Snippet.class);
        setTitle(snippet.getId());
        id = Integer.parseInt(snippet.getId());


        tituloEdit = findViewById(R.id.editTitulo);
        codigoEdit = findViewById(R.id.editCodigo);
        url = findViewById(R.id.url);
        owner = findViewById(R.id.owner);
        lineas = findViewById(R.id.lineas);


        tituloEdit.setText(snippet.getTitle());
        codigoEdit.setText(snippet.getCode());
        url.setText(snippet.getUrl());
        owner.setText(snippet.getOwner());
        url.setEnabled(false);
        owner.setEnabled(false);

        lineas.setChecked(snippet.getLinenos());
        System.out.println(snippet.getLinenos());

        sharedPref = getDefaultSharedPreferences(
                getApplicationContext());

        lenguajes= new Gson().fromJson(sharedPref.getString("lenguajes", " "), ArrayList.class);

        estilos= new Gson().fromJson(sharedPref.getString("estilos", " "), ArrayList.class);

        dropdownLenguajes = findViewById(R.id.editLenguaje);
        ArrayAdapter<String> adapterLenguaje = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lenguajes);
        dropdownLenguajes.setAdapter(adapterLenguaje);
        dropdownLenguajes.setSelection(adapterLenguaje.getPosition(snippet.getLanguage()));

        dropdownEstilos = findViewById(R.id.editEstilo);
        ArrayAdapter<String> adapterEstilo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, estilos);
        dropdownEstilos.setAdapter(adapterEstilo);
        dropdownEstilos.setSelection(adapterEstilo.getPosition(snippet.getStyle()));


    }
    public void sendEditar() {
        if (tituloEdit.getText().toString().equals("") || codigoEdit.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "FALTAN CAMPOS POR RELLENAR", Toast.LENGTH_LONG).show();
        } else {


            JsonObject paramObject = new JsonObject();
            try {
                paramObject.addProperty("title", tituloEdit.getText().toString());
                paramObject.addProperty("code", codigoEdit.getText().toString());
                paramObject.addProperty("linenos", lineas.isChecked());
                paramObject.addProperty("language", String.valueOf(dropdownLenguajes.getSelectedItem()));
                paramObject.addProperty("style", String.valueOf(dropdownEstilos.getSelectedItem()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(paramObject.toString());
            patchSnippets(paramObject, id);
            System.out.println(dropdownLenguajes.getSelectedItem());
        }
    }


    private void patchSnippets(JsonObject body, int num){
        Call<JsonElement> call = RetrofitClient.getInstance().getMyApi().patchSnippets(sharedPref.getString("token", " "),body, num);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                System.out.println( response.body());
                if (response.isSuccessful()) {
                    Intent i = getIntent();
                    i.putExtra("snippet", response.body().toString());
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Algo ha ido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editarmeu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btnEditar:
                System.out.println("BOTON AÑADIR");
                sendEditar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}