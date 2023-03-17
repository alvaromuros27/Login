package com.example.login;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Results datos;

    EditText usuario, pass;
    Button entrar;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        entrar = findViewById(R.id.btnentrar);
        usuario = findViewById(R.id.nombre);
        pass = findViewById(R.id.password);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usuario.getText().toString().equals("")||pass.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Campos vacios, debe de rellenarlos", Toast.LENGTH_LONG).show();
                }else{
                    JsonObject paramObject = new JsonObject();
                    try {
                        paramObject.addProperty("username", usuario.getText().toString());
                        paramObject.addProperty("password", pass.getText().toString());
//                        paramObject.addProperty("username", "admin");
//                        paramObject.addProperty("password", "password123");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getLogins(paramObject);
                }
            }
        });

    }

    public void getLogins(JsonObject body){
        Call<JsonElement> call = RetrofitClient.getInstance().getMyApi().getLogins(body);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    usuario.getText().clear();

                    Intent intent = new Intent(getApplicationContext(), ListFragmentActivity.class);
                    intent.putExtra("variable",  response.body().toString());
                    startActivity(intent);

                    sharedPref= getDefaultSharedPreferences(
                            getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("body", response.body().toString());
                    editor.apply();

                }else{
                    Toast.makeText(getApplicationContext(), "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                }

                pass.getText().clear();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }
}
