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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    EditText  first, second, name, email;
    String recuuser;
    Login  parametros;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        sharedPref = getDefaultSharedPreferences(
                getApplicationContext());

        recuuser = sharedPref.getString("body", " ");
        parametros= new Gson().fromJson(recuuser, Login.class);

        setTitle(parametros.getUser().get("email").getAsString());

        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        name = findViewById(R.id.name);

        first.setText(parametros.getUser().get("first_name").getAsString());
        second.setText(parametros.getUser().get("last_name").getAsString());
        name.setText(parametros.getUser().get("username").getAsString());



    }

    public void actualizarPerfil(){
        if(first.getText().toString().equals("") || second.getText().toString().equals("") || name.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Necesita rellenar los datos", Toast.LENGTH_LONG).show();

        }else {
            JsonObject paramObject = new JsonObject();
            try {
                paramObject.addProperty("username", name.getText().toString());
                paramObject.addProperty("first_name", first.getText().toString());
                paramObject.addProperty("last_name", second.getText().toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(paramObject.toString());
            patchUser(paramObject);
        }
    }


    private void patchUser(JsonObject body){
        Call<JsonElement> call = RetrofitClient.getInstance().getMyApi().patchUser(parametros.getToken(),body);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    parametros.setUser((JsonObject) response.body());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("body", parametros.toString());
                    editor.apply();
                    setResult(Activity.RESULT_OK, getIntent());
                    finish();

                }else{
                    System.out.println("Se pasa al else");
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
                actualizarPerfil();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}