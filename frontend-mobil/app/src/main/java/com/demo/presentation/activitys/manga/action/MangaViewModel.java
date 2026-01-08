package com.demo.presentation.activitys.manga.action;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.data.common.BaseResponse;
import com.demo.data.model.Capitulo;
import com.demo.data.model.MangaDetalle;
import com.demo.data.repocitory.FavoritoRepository;
import com.demo.data.repocitory.MangasRepository;
import com.demo.data.request.MangaRequest;

import java.util.List;
public class MangaViewModel extends ViewModel {

    private final MangasRepository repository = new MangasRepository();
    private final FavoritoRepository favoritoRepository = new FavoritoRepository();

    private final MutableLiveData<MangaDetalle> detalle = new MutableLiveData<>();
    private final MutableLiveData<List<Capitulo>> capitulos = new MutableLiveData<>();

    public LiveData<MangaDetalle> getDetalle() {
        return detalle;
    }

    public LiveData<List<Capitulo>> getCapitulos() {
        return capitulos;
    }

    // =========================
    // 🔥 DETALLE DEL MANGA
    // =========================
    public void cargarDetalle(int mangaId) {

        Log.d("MANGA_DEBUG", "ViewModel -> cargarDetalle(): " + mangaId);

        MangaRequest request = new MangaRequest(mangaId);

        repository.verMangaDetalle(request)
                .observeForever(response -> {

                    Log.d("MANGA_DEBUG", "Response recibido: " + response);

                    if (response != null && response.isSuccess()) {
                        Log.d("MANGA_DEBUG", "Detalle OK: " + response.getData().getTitulo());
                        detalle.postValue(response.getData());
                    } else {
                        Log.e("MANGA_DEBUG", "Response null o error");
                    }
                });
    }


    // =========================
    // 📚 CAPÍTULOS
    // =========================
    public void cargarCapitulos(int mangaId, int inicio, int fin) {

        MangaRequest request = new MangaRequest(mangaId, inicio, fin);

        repository.listarCapitulosRango(request)
                .observeForever(response -> {
                    if (response != null && response.isSuccess()) {
                        capitulos.postValue(response.getData());
                    }
                });
    }


    // =========================
// ❤️ AGREGAR A FAVORITOS
// =========================
    public LiveData<BaseResponse<Void>> agregarFavorito(int usuarioId, int mangaId) {
        return favoritoRepository.agregarFavorito(usuarioId, mangaId);
    }
}
