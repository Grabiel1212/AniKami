package com.demo.data.repocitory;


import androidx.lifecycle.LiveData;

import com.demo.data.api.FavoritosApi;
import com.demo.data.api.RetrofitClient;
import com.demo.data.common.BaseResponse;
import com.demo.data.helpers.LiveDataCallAdapter;
import com.demo.data.model.Genero;
import com.demo.data.model.Manga;
import com.demo.data.request.FavoritosRequets;

import java.util.List;

public class FavoritoRepository {

    private final FavoritosApi favoritoApi;
    private final String TAG = GeneroRepository.class.getSimpleName();

    public FavoritoRepository() {
        favoritoApi = RetrofitClient.getRetrofit().create(FavoritosApi.class);
    }

    public FavoritosApi getFavoritoApi() {
        return favoritoApi;
    }

    public LiveData<BaseResponse<List<Manga>>> listarMangaFavoritos(int usuarioId) {

        FavoritosRequets request = new FavoritosRequets(usuarioId);

        return LiveDataCallAdapter.call(
                favoritoApi.listarMangaFavoritos(request)
        );
    }


    public LiveData<BaseResponse<Void>> agregarFavorito(int usuarioId, int mangaId) {

        FavoritosRequets request = new FavoritosRequets(usuarioId, mangaId);

        return LiveDataCallAdapter.call(
                favoritoApi.agregarMangaFavoritos(request)
        );
    }

    public LiveData<BaseResponse<Void>> eliminarFavorito(int usuarioId, int mangaId) {

        FavoritosRequets request = new FavoritosRequets(usuarioId, mangaId);

        return LiveDataCallAdapter.call(
                favoritoApi.eliminarMangaFavoritos(request)
        );
    }


}
