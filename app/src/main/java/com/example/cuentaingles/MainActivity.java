package com.example.cuentaingles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuentaingles.interfaces.historialAPI;
import com.example.cuentaingles.models.Registro;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button btn_campoInsertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_campoInsertar = (Button)findViewById(R.id.btn_insertarTiempo);

        btn_campoInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insertarPantallaRegistro = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(insertarPantallaRegistro);
            }
        });


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.142/apiCuentaIngles/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        historialAPI historialAPI = retrofit.create(historialAPI.class);
        Call<List<Registro>> call = historialAPI.verTodosLosproductos();
        call.enqueue(new Callback<List<Registro>>() {
            @Override
            public void onResponse(Call<List<Registro>> call, Response<List<Registro>> response) {
                try {
                    if(response.isSuccessful()) {
                        textView = (TextView) findViewById(R.id.txt_total);
                        float contador = 0;

                        //meto en una lista todo lo que le llega de la consulta
                        List<Registro> posts = response.body();

                        //recorro dicha lista
                        for (Registro registro : posts) {
                            //sumo los tiempos de los registros
                            contador += registro.getTiempo();

                        }

                        //Log.d("prueba", String.valueOf(contador));
                        //transformo de minutos a horas
                        double convertidor = contador/60;

                        //para poner dos decimales
                        DecimalFormat precision = new DecimalFormat("0.00");

                        //añado la cantidad del convertidor al textView
                        textView.setText(String.valueOf(precision.format(convertidor)) + " h");


                    }


                }catch (Exception ex) {
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Registro>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}