package com.demo.presentation.activitys.favorites.action;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.Manga;
import com.demo.data.repocitory.FavoritoRepository;

import java.util.List;

public class FavoritoViewModel extends ViewModel {

    private final FavoritoRepository repository;

    public FavoritoViewModel() {
        repository = new FavoritoRepository();
    }

    public LiveData<BaseResponse<List<Manga>>> listarFavoritos(int usuarioId) {
        return repository.listarMangaFavoritos(usuarioId);
    }

    public LiveData<BaseResponse<Void>> eliminarFavorito(int usuarioId, int mangaId) {
        return repository.eliminarFavorito(usuarioId, mangaId);
    }

}