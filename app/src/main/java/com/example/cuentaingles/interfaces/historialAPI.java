package com.example.cuentaingles.interfaces;

import com.example.cuentaingles.models.Registro;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface historialAPI {

    @POST("post.php")
    @FormUrlEncoded
    Call<Registro> subirRegistro(
            @Field("tiempo") int tiempo,
            @Field("descripcion") String descripcion
    );

    @GET("get.php")
    //Call<Registro> verTodosLosproductos();
    Call<List<Registro>> verTodosLosproductos();
    //void verTodosLosproductos(Callback<List<Registro>> cb);

}
