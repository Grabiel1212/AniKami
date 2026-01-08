package com.demo.presentation.activitys.manga;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.demo.R;
import com.demo.data.model.Capitulo;
import com.demo.data.model.MangaDetalle;
import com.demo.data.session.SessionManager;
import com.demo.presentation.activitys.manga.action.MangaViewModel;
import com.demo.presentation.activitys.manga.visor.ReaderActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
public class MangaActivity extends AppCompatActivity {

    private MangaViewModel viewModel;
    private int mangaId;

    private ChipGroup chipGroup;
    private LinearLayout container;

    private TextView titulo;
    private TextView descripcion;
    private ImageView portada;

    private ImageView imgAutor;
    private TextView txtAutorNombre;
    private TextView txtAutorDesc;
    private TextView txtEstado;

    private ImageView btnFavorito; // ❤️ CORAZÓN
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        mangaId = getIntent().getIntExtra("ID_MANGA", -1);
        viewModel = new ViewModelProvider(this).get(MangaViewModel.class);

        titulo = findViewById(R.id.txt_manga_title);
        descripcion = findViewById(R.id.txt_description);
        portada = findViewById(R.id.img_cover);
        chipGroup = findViewById(R.id.chipGroupCapitulos);
        container = findViewById(R.id.container_chapters);
        imgAutor = findViewById(R.id.img_author);
        txtAutorNombre = findViewById(R.id.txt_author_name);
        txtAutorDesc = findViewById(R.id.txt_author_desc);
        txtEstado = findViewById(R.id.txt_status);
        btnFavorito = findViewById(R.id.btn_favorito); // inicializamos el corazón

        sessionManager = new SessionManager(this);

        // =======================
        // CLICK EN EL CORAZÓN
        // =======================
        btnFavorito.setOnClickListener(v -> {
            int usuarioId = sessionManager.getUserId();
            if (usuarioId == -1) {
                Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show();
                return;
            }
            agregarAFavoritos(usuarioId);
        });

        observarDetalle();
        observarCapitulos();

        if (mangaId != -1) {
            viewModel.cargarDetalle(mangaId);
        }

        Log.d("MANGA_DEBUG", "ID_MANGA recibido: " + mangaId);
    }

    // =========================
    // ❤️ AGREGAR A FAVORITOS
    // =========================
    private void agregarAFavoritos(int usuarioId) {

        if (mangaId == -1) {
            Toast.makeText(this, "Manga inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.agregarFavorito(usuarioId, mangaId)
                .observe(this, response -> {

                    if (response == null) {
                        Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 🔥 MENSAJE DIRECTO DEL API
                    Toast.makeText(
                            this,
                            response.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                });
    }

    // =========================
    // OBSERVAR DETALLE
    // =========================
    private void observarDetalle() {
        viewModel.getDetalle().observe(this, detalle -> {

            if (detalle == null) return;

            // 📘 Manga
            titulo.setText(detalle.getTitulo());
            descripcion.setText(detalle.getDescripcion());
            txtEstado.setText(detalle.getEstado());

            Glide.with(this)
                    .load(detalle.getPortadaUrl())
                    .into(portada);

            // 👤 Autor
            txtAutorNombre.setText(detalle.getAutorNombre());
            txtAutorDesc.setText(detalle.getAutorDescripcion());

            Glide.with(this)
                    .load(detalle.getAutorFoto())
                    .placeholder(R.mipmap.banner1)
                    .error(R.mipmap.banner1)
                    .circleCrop()
                    .into(imgAutor);

            generarChips(detalle.getTotalCapitulos());
        });
    }

    // =========================
    // OBSERVAR CAPÍTULOS
    // =========================
    private void observarCapitulos() {
        viewModel.getCapitulos().observe(this, lista -> {

            if (lista == null) return;

            container.removeAllViews();

            for (Capitulo cap : lista) {

                var view = getLayoutInflater()
                        .inflate(R.layout.item_chapter, container, false);

                TextView txt = view.findViewById(R.id.txt_chapter_title);
                ImageView img = view.findViewById(R.id.img_chapter_cover);

                txt.setText("Capítulo " + cap.getNumero());

                Glide.with(this)
                        .load(cap.getImagen_url())
                        .into(img);

                view.setOnClickListener(v -> {
                    Intent intent = new Intent(this, ReaderActivity.class);
                    intent.putExtra("ID_CAPITULO", cap.getId());
                    startActivity(intent);
                });

                container.addView(view);
            }
        });
    }

    // =========================
    // GENERAR CHIPS
    // =========================
    private void generarChips(int totalCapitulos) {

        chipGroup.removeAllViews();
        chipGroup.setSingleSelection(true);

        int bloque = 5;
        boolean first = true;

        for (int i = 1; i <= totalCapitulos; i += bloque) {

            int inicio = i;
            int fin = Math.min(i + bloque - 1, totalCapitulos);

            Chip chip = new Chip(this);
            chip.setText(inicio + " - " + fin);
            chip.setCheckable(true);

            chip.setOnClickListener(v ->
                    viewModel.cargarCapitulos(mangaId, inicio, fin)
            );

            chipGroup.addView(chip);

            // ✅ AUTO CARGAR 1-5 AL ENTRAR
            if (first) {
                chip.setChecked(true);
                chip.performClick();
                first = false;
            }
        }
    }
}
