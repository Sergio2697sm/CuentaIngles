package com.example.cuentaingles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cuentaingles.interfaces.historialAPI;
import com.example.cuentaingles.models.Registro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {
    EditText tiempo,descripcion;
    Button btn_insertarRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        btn_insertarRegistro = (Button)findViewById(R.id.btn_insertar);

        btn_insertarRegistro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tiempo = (EditText) findViewById(R.id.numberTiempo);
                descripcion = (EditText) findViewById(R.id.txt_descripcion);
                int tiempoget = Integer.parseInt(tiempo.getText().toString());
                String descripcionGet = descripcion.getText().toString();
                //Log.d("prueba", String.valueOf(tiempoget));
               // Toast.makeText(getApplicationContext(), descripcionget, Toast.LENGTH_SHORT);
                insertar(tiempoget,descripcionGet);
            }
        });

    }

    private void insertar(int tiempoPasado, String descripcionPasada) {
        //Log.d("prueba", "hola");
        //Toast.makeText(getApplicationContext(), "hola", Toast.LENGTH_SHORT);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://apicuentas.sergiomartinez2m.es/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        historialAPI historialAPI = retrofit.create(historialAPI.class);
        Call<Registro> call = historialAPI.subirRegistro(tiempoPasado,descripcionPasada);
        call.enqueue(new Callback<Registro>() {
            @Override
            public void onResponse(Call<Registro> call, Response<Registro> response) {
                try {
                    if(response.isSuccessful()) {
                        Toast.makeText(MainActivity2.this, "subido con exito", Toast.LENGTH_SHORT).show();
                        Intent pantallaInicio = new Intent(MainActivity2.this, MainActivity.class);
                        startActivity(pantallaInicio);
                    }

                }catch (Exception ex){
                    Log.d("pruebaException", "error");
                   // Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Registro> call, Throwable t) {

                Log.d("pruebaonFailure", t.getMessage());
               // Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT);

            }


        }
        );

    }


}