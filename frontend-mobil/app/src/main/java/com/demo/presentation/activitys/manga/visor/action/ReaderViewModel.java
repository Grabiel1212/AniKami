package com.demo.presentation.activitys.manga.visor.action;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.demo.data.model.Pajina;
import com.demo.data.repocitory.MangasRepository;
import com.demo.data.request.PaginaRequets;

import java.util.List;

public class ReaderViewModel extends ViewModel {

    private final MangasRepository repository = new MangasRepository();

    private final MutableLiveData<List<Pajina>> paginas = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public LiveData<List<Pajina>> getPaginas() {
        return paginas;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    // =========================
    // 📖 CARGAR PÁGINAS
    // =========================
    public void cargarPaginas(int idCapitulo) {
        loading.setValue(true);

        PaginaRequets request = new PaginaRequets();
        request.setCapituloId(idCapitulo);

        repository.listarPajinasCapitulo(request)
                .observeForever(response -> {
                    loading.postValue(false);
                    if (response != null && response.isSuccess()) {
                        paginas.postValue(response.getData()); // esto disparará el observer
                    } else {
                        paginas.postValue(null);
                    }
                });
    }

}