package com.demo.presentation.activitys.categories.action;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.Genero;
import com.demo.data.model.Manga;
import com.demo.data.repocitory.GeneroRepository;
import com.demo.data.repocitory.MangasRepository;
import com.demo.data.request.GeneroRequest;

import java.util.List;

public class CategoriesViewModel extends ViewModel {

    private final GeneroRepository generoRepository = new GeneroRepository();
    private final MangasRepository mangaRepository = new MangasRepository();

    public LiveData<BaseResponse<List<Genero>>> obtenerGeneros() {
        return generoRepository.listarGeneros();
    }

    public LiveData<BaseResponse<List<Manga>>> obtenerMangasPorGenero(int generoId) {
        return mangaRepository.listarMangaGenero(
                new GeneroRequest(generoId)
        );
    }
}
