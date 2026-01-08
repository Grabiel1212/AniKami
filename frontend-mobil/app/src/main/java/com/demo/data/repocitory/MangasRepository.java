package com.demo.data.repocitory;


import androidx.lifecycle.LiveData;

import com.demo.data.api.MangasApi;
import com.demo.data.api.RetrofitClient;
import com.demo.data.common.BaseResponse;
import com.demo.data.helpers.LiveDataCallAdapter;
import com.demo.data.model.Capitulo;
import com.demo.data.model.Genero;
import com.demo.data.model.Manga;
import com.demo.data.model.MangaDetalle;
import com.demo.data.model.Pajina;
import com.demo.data.request.GeneroRequest;
import com.demo.data.request.MangaRequest;
import com.demo.data.request.PaginaRequets;
import com.demo.data.request.UsuarioRequest;

import java.util.List;

public class MangasRepository {

    private final MangasApi mangasApi;
    private final String TAG = MangasRepository.class.getSimpleName();

    public MangasRepository() {
        mangasApi = RetrofitClient
                .getRetrofit()
                .create(MangasApi.class);
    }

    public LiveData<BaseResponse<List<Manga>>> listarMangas() {
        return LiveDataCallAdapter.call(
                mangasApi.listarMangas()
        );
    }

    public LiveData<BaseResponse<List<Manga>>> listarTop10() {
        return LiveDataCallAdapter.call(
                mangasApi.listarTop10()
        );
    }

    public LiveData<BaseResponse<List<Manga>>> listarPopulares() {
        return LiveDataCallAdapter.call(
                mangasApi.listarMangasPopulares()
        );
    }

    public LiveData<BaseResponse<List<Manga>>> listarRecomendados(
            UsuarioRequest request
    ) {
        return LiveDataCallAdapter.call(
                mangasApi.listarMangaPreferencias(request)
        );
    }

// =========================
// 🔥 DETALLE DEL MANGA
// =========================
    public LiveData<BaseResponse<MangaDetalle>> verMangaDetalle(
            MangaRequest request
    ) {
        return LiveDataCallAdapter.call(
                mangasApi.verMangaDetalle(request)
        );
    }



    // =========================
// 📚 CAPÍTULOS POR RANGO
// =========================
    public LiveData<BaseResponse<List<Capitulo>>> listarCapitulosRango(
            MangaRequest request
    ) {
        return LiveDataCallAdapter.call(
                mangasApi.listarCapitulosRango(request)
        );
    }


    // =========================
    // 📄 PÁGINAS DEL CAPÍTULO
    // =========================
    public LiveData<BaseResponse<List<Pajina>>> listarPajinasCapitulo(
            PaginaRequets request
    ) {
        return LiveDataCallAdapter.call(
                mangasApi.listarPajinaCapitulo(request)
        );
    }


    // 🔹 LISTAR MANGAS POR GÉNERO
    public LiveData<BaseResponse<List<Manga>>> listarMangaGenero(GeneroRequest request) {
        return LiveDataCallAdapter.call(
                mangasApi.listarMangaGenero(request)
        );
    }
}
