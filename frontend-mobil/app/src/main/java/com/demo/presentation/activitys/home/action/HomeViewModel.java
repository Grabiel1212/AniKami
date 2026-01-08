package com.demo.presentation.activitys.home.action;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.data.repocitory.MangasRepository;
import com.demo.data.request.UsuarioRequest;
import com.demo.presentation.activitys.home.adapter.MangaUIModel;

import java.util.List;
import java.util.stream.Collectors;

public class HomeViewModel extends ViewModel {

    private final MangasRepository repository;

    private final MutableLiveData<List<MangaUIModel>> top10 = new MutableLiveData<>();
    private final MutableLiveData<List<MangaUIModel>> populares = new MutableLiveData<>();
    private final MutableLiveData<List<MangaUIModel>> otros = new MutableLiveData<>();
    private final MutableLiveData<List<MangaUIModel>> recomendados = new MutableLiveData<>();

    public HomeViewModel() {
        repository = new MangasRepository();
        cargarTop10();
        cargarPopulares();
        cargarOtros();
    }

    // =========================
    // TOP 10
    // =========================
    private void cargarTop10() {
        repository.listarTop10().observeForever(response -> {
            if (response != null && response.isSuccess()) {
                top10.setValue(mapToUI(response.getData()));
            }
        });
    }

    public LiveData<List<MangaUIModel>> getTop10() {
        return top10;
    }

    // =========================
    // POPULARES
    // =========================
    private void cargarPopulares() {
        repository.listarPopulares().observeForever(response -> {
            if (response != null && response.isSuccess()) {
                populares.setValue(mapToUI(response.getData()));
            }
        });
    }

    public LiveData<List<MangaUIModel>> getPopulares() {
        return populares;
    }

    // =========================
    // OTROS
    // =========================
    private void cargarOtros() {
        repository.listarMangas().observeForever(response -> {
            if (response != null && response.isSuccess()) {
                otros.setValue(mapToUI(response.getData()));
            }
        });
    }

    public LiveData<List<MangaUIModel>> getOtros() {
        return otros;
    }

    // =========================
    // 🔥 RECOMENDADOS POR USUARIO
    // =========================
    public void cargarRecomendados(Integer idUsuario) {

        UsuarioRequest request = new UsuarioRequest();
        request.setUsuarioId(idUsuario);

        repository.listarRecomendados(request)
                .observeForever(response -> {
                    if (response != null && response.isSuccess()) {
                        recomendados.setValue(mapToUI(response.getData()));
                    }
                });
    }

    public LiveData<List<MangaUIModel>> getRecomendados() {
        return recomendados;
    }

    // =========================
    // MAPPER
    // =========================
    private List<MangaUIModel> mapToUI(List<com.demo.data.model.Manga> mangas) {
        return mangas.stream()
                .map(m -> new MangaUIModel(
                        m.getId(),              // ✅ ID REAL
                        m.getTitulo(),
                        m.getEstado(),
                        m.getPortada_url()
                ))
                .collect(Collectors.toList());
    }

}
