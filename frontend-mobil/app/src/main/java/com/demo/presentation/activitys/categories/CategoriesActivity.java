package com.demo.presentation.activitys.categories;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.R;
import com.demo.presentation.activitys.base.BaseActivity;
import com.demo.presentation.activitys.categories.adapter.MangaAdapter;
import com.demo.presentation.activitys.categories.action.CategoriesViewModel;
import com.demo.presentation.activitys.home.adapter.MangaUIModel;
import com.demo.data.model.Genero;
import com.demo.data.model.Manga;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends BaseActivity {

    private ChipGroup chipGroupGeneros;
    private TextView txtGeneroDescripcion;
    private RecyclerView rvMangas;

    private CategoriesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categories);

        setupBottomNav(R.id.nav_genres);

        initViews();
        setupRecycler();

        viewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        cargarGeneros();
    }

    private void initViews() {
        chipGroupGeneros = findViewById(R.id.chipGroupGeneros);
        txtGeneroDescripcion = findViewById(R.id.txtGeneroDescripcion);
        rvMangas = findViewById(R.id.rvMangas);
    }

    private void setupRecycler() {
        rvMangas.setLayoutManager(new LinearLayoutManager(this));
    }

    // 🔹 CARGAR GÉNEROS DESDE API
    private void cargarGeneros() {
        viewModel.obtenerGeneros().observe(this, response -> {

            if (response != null && response.isSuccess()) {

                chipGroupGeneros.removeAllViews();

                for (Genero genero : response.getData()) {

                    Chip chip = new Chip(this);
                    chip.setText(genero.getNombre());
                    chip.setCheckable(true);

                    chip.setOnClickListener(v -> {
                        txtGeneroDescripcion.setText(genero.getDescripcion());
                        txtGeneroDescripcion.setVisibility(View.VISIBLE);

                        cargarMangasPorGenero(genero.getId());
                    });

                    chipGroupGeneros.addView(chip);
                }
            }
        });
    }

    // 🔹 LISTAR MANGAS POR GÉNERO
    private void cargarMangasPorGenero(int generoId) {
        viewModel.obtenerMangasPorGenero(generoId).observe(this, response -> {

            if (response != null && response.isSuccess()) {

                List<MangaUIModel> uiModels = new ArrayList<>();

                for (Manga manga : response.getData()) {
                    uiModels.add(new MangaUIModel(
                            manga.getId(),
                            manga.getTitulo(),
                            manga.getEstado(),
                            manga.getPortada_url()
                    ));
                }

                rvMangas.setAdapter(
                        new MangaAdapter(uiModels, this)
                );
            }
        });
    }
}
