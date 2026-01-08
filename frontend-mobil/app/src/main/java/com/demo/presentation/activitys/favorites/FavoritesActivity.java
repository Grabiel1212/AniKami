package com.demo.presentation.activitys.favorites;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.R;
import com.demo.data.session.SessionManager;
import com.demo.presentation.activitys.base.BaseActivity;
import com.demo.presentation.activitys.favorites.action.FavoritoViewModel;
import com.demo.presentation.activitys.favorites.adapter.FavoritesMangaAdapter;

public class FavoritesActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private FavoritoViewModel viewModel;
    private TextView txtEmptyFavorites;

    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites);

        setupBottomNav(R.id.nav_favorites);

        recyclerView = findViewById(R.id.rv_favorites);
        txtEmptyFavorites = findViewById(R.id.txtEmptyFavorites);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        SessionManager sessionManager = new SessionManager(this);
        usuarioId = sessionManager.getUserId();

        if (usuarioId == -1) {
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this)
                .get(FavoritoViewModel.class);

        // 🔹 Cargar por primera vez
        cargarFavoritos();
    }

    // 🔥 SE EJECUTA CADA VEZ QUE VUELVES A LA ACTIVITY
    @Override
    protected void onResume() {
        super.onResume();

        if (usuarioId != -1) {
            cargarFavoritos();
        }
    }

    private void cargarFavoritos() {
        viewModel.listarFavoritos(usuarioId)
                .observe(this, response -> {

                    if (response == null || !response.isSuccess()) {
                        mostrarVacio(true);
                        return;
                    }

                    if (response.getData() == null || response.getData().isEmpty()) {
                        mostrarVacio(true);
                    } else {
                        mostrarVacio(false);
                        recyclerView.setAdapter(
                                new FavoritesMangaAdapter(
                                        this,
                                        response.getData(),
                                        usuarioId
                                )
                        );
                    }
                });
    }

    private void mostrarVacio(boolean mostrar) {
        txtEmptyFavorites.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(mostrar ? View.GONE : View.VISIBLE);
    }

    // 🔥 LLAMADO DESDE EL ADAPTER
    public void eliminarFavorito(int usuarioId, int mangaId) {

        viewModel.eliminarFavorito(usuarioId, mangaId)
                .observe(this, response -> {

                    if (response == null) return;

                    if (response.isSuccess()) {
                        Toast.makeText(
                                this,
                                "Favorito eliminado correctamente",
                                Toast.LENGTH_SHORT
                        ).show();

                        // 🔄 Actualizar lista SIN matar la activity
                        cargarFavoritos();

                    } else {
                        Toast.makeText(
                                this,
                                "No se pudo eliminar el favorito",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}
