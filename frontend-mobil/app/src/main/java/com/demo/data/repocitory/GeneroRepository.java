package com.demo.data.repocitory;

import androidx.lifecycle.LiveData;

import com.demo.data.api.GeneroApi;
import com.demo.data.api.RetrofitClient;
import com.demo.data.api.UsuarioApi;
import com.demo.data.common.BaseResponse;
import com.demo.data.helpers.LiveDataCallAdapter;
import com.demo.data.model.Genero;

import java.util.List;

public class GeneroRepository {

    private final GeneroApi generoApi;
    private final String TAG = GeneroRepository.class.getSimpleName();

    public GeneroRepository() {
        generoApi = RetrofitClient.getRetrofit().create(GeneroApi.class);
    }

    public GeneroApi getGeneroApi() {
        return generoApi;
    }
    public LiveData<BaseResponse<List<Genero>>> listarGeneros() {
        return LiveDataCallAdapter.call(
                generoApi.listarGeneros()
        );
    }


}
