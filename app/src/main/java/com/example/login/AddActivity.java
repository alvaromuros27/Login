package com.example.login;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    Login parametros;
    EditText titulo, codigo;
    CheckBox lineas;
    String credenciales;
    String token;
    SharedPreferences sharedPref;
    ArrayList<String> estilos;
    ArrayList<String> lenguajes;
    Spinner dropdownLenguajes;
    Spinner dropdownEstilos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("ADD");

        Bundle datos = getIntent().getExtras();
        credenciales = datos.getString("credenciales");

        titulo = findViewById(R.id.editTitulo);
        codigo = findViewById(R.id.editCodigo);
        lineas = findViewById(R.id.lineas);

        sharedPref = getDefaultSharedPreferences(
                getApplicationContext());

        lenguajes= new Gson().fromJson(sharedPref.getString("lenguajes", " "), ArrayList.class);

        estilos= new Gson().fromJson(sharedPref.getString("estilos", " "), ArrayList.class);

        dropdownLenguajes = findViewById(R.id.editLenguaje);
        ArrayAdapter<String> adapterLenguaje = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lenguajes);
        dropdownLenguajes.setAdapter(adapterLenguaje);

        dropdownEstilos = findViewById(R.id.editEstilo);
        ArrayAdapter<String> adapterEstilo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, estilos);
        dropdownEstilos.setAdapter(adapterEstilo);

        parametros= new Gson().fromJson(credenciales, Login.class);
        token = parametros.getToken();


    }
    public void sendEditar() {
        if(titulo.getText().toString().equals("")||codigo.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "FALTAN CAMPOS POR RELLENAR", Toast.LENGTH_LONG).show();
        }else{
            JsonObject paramObject = new JsonObject();
            try {
                paramObject.addProperty("title", titulo.getText().toString());
                paramObject.addProperty("code",codigo.getText().toString());
                paramObject.addProperty("linenos", lineas.isChecked());
                paramObject.addProperty("language",  String.valueOf(dropdownLenguajes.getSelectedItem()));
                paramObject.addProperty("style", String.valueOf(dropdownEstilos.getSelectedItem()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(paramObject.toString());
            postSnippets(paramObject);
        }
    }
    private void postSnippets(JsonObject body){
        Call<JsonElement> call = RetrofitClient.getInstance().getMyApi().postSnippets(token,body);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    System.out.println(response);
                    setResult(Activity.RESULT_OK, getIntent());
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