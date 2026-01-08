package com.demo.presentation.activitys.manga.visor;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.demo.R;
import com.demo.data.model.Pajina;
import com.demo.presentation.activitys.manga.adapter.ReaderAdapter;
import com.demo.presentation.activitys.manga.visor.action.ReaderViewModel;

import java.util.List;
public class ReaderActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ReaderViewModel viewModel;
    private int idCapitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        idCapitulo = getIntent().getIntExtra("ID_CAPITULO", -1);
        Log.d("READER_DEBUG", "ID_CAPITULO recibido: " + idCapitulo);

        viewPager = findViewById(R.id.viewPagerReader);
        viewModel = new ViewModelProvider(this).get(ReaderViewModel.class);

        observarPaginas();

        if (idCapitulo != -1) {
            viewModel.cargarPaginas(idCapitulo);
        }
    }

    private void observarPaginas() {
        viewModel.getPaginas().observe(this, paginas -> {
            if (paginas != null && !paginas.isEmpty()) {
                Log.d("READER_DEBUG", "Páginas recibidas: " + paginas.size());
                ReaderAdapter adapter = new ReaderAdapter(paginas);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(0, false);
            } else {
                Log.d("READER_DEBUG", "No se recibieron páginas");
            }
        });

    }
}
