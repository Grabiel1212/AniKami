package com.demo.presentation.activitys.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.demo.R;
import com.demo.data.session.SessionManager;
import com.demo.presentation.activitys.base.BaseActivity;
import com.demo.presentation.activitys.home.action.HomeViewModel;
import com.demo.presentation.activitys.home.adapter.CarouselAdapter;
import com.demo.presentation.activitys.home.adapter.MangaAdapter;
import com.demo.presentation.activitys.home.adapter.TopAdapter;


import java.util.Arrays;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private HomeViewModel homeViewModel;

    private ViewPager2 viewPagerCarousel;
    private Handler sliderHandler;

    private RecyclerView recyclerTop;
    private RecyclerView recyclerGenres;
    private RecyclerView recyclerPopular;
    private RecyclerView recyclerOthers;

    private List<Integer> carouselImages;
    private Runnable sliderRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        setupBottomNav(R.id.nav_home);

        initViews();
        setupCarousel();
        setupTopRecycler();
        setupGenresRecycler();
        setupPopularRecycler();
        setupOthersRecycler();
    }

    // =========================
    // INIT VIEWS
    // =========================
    private void initViews() {
        viewPagerCarousel = findViewById(R.id.viewPagerCarousel);
        recyclerTop = findViewById(R.id.recyclerTop);
        recyclerGenres = findViewById(R.id.recyclerGenres);
        recyclerPopular = findViewById(R.id.recyclerPopular);
        recyclerOthers = findViewById(R.id.recyclerOthers);

        sliderHandler = new Handler(Looper.getMainLooper());
    }

    // =========================
    // CAROUSEL
    // =========================
    private void setupCarousel() {
        carouselImages = Arrays.asList(
                R.mipmap.banner1,
                R.mipmap.banner2,
                R.mipmap.banner3,
                R.mipmap.banner4,
                R.mipmap.banner5
        );

        viewPagerCarousel.setAdapter(new CarouselAdapter(carouselImages));
        startAutoSlider();
    }

    private void startAutoSlider() {
        sliderRunnable = new Runnable() {
            int currentPage = 0;

            @Override
            public void run() {
                if (currentPage >= carouselImages.size()) currentPage = 0;
                viewPagerCarousel.setCurrentItem(currentPage++, true);
                sliderHandler.postDelayed(this, 3000);
            }
        };
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    private void stopAutoSlider() {
        if (sliderHandler != null) {
            sliderHandler.removeCallbacksAndMessages(null);
        }
    }

    // =========================
    // TOP 10
    // =========================
    private void setupTopRecycler() {
        recyclerTop.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerTop.setHasFixedSize(true);
        recyclerTop.setNestedScrollingEnabled(false);

        homeViewModel.getTop10().observe(this, list -> {
            recyclerTop.setAdapter(new TopAdapter(this, list));
        });
    }

    // =========================
    // GÉNEROS
    // =========================
    private void setupGenresRecycler() {

        recyclerGenres.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerGenres.setHasFixedSize(true);
        recyclerGenres.setNestedScrollingEnabled(false);

        // ✅ CREAR INSTANCIA (CORRECTO)
        SessionManager sessionManager = new SessionManager(this);
        int idUsuario = sessionManager.getUserId();

        if (idUsuario != -1) {
            homeViewModel.cargarRecomendados(idUsuario);
        }

        homeViewModel.getRecomendados().observe(this, list -> {
            recyclerGenres.setAdapter(new MangaAdapter(this, list));
        });
    }



    // =========================
    // POPULARES
    // =========================
    private void setupPopularRecycler() {
        recyclerPopular.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerPopular.setHasFixedSize(true);
        recyclerPopular.setNestedScrollingEnabled(false);

        homeViewModel.getPopulares().observe(this, list -> {
            recyclerPopular.setAdapter(new MangaAdapter(this, list));
        });
    }

    // =========================
    // OTROS (grid)
    // =========================
    private void setupOthersRecycler() {
        recyclerOthers.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerOthers.setHasFixedSize(true);
        recyclerOthers.setNestedScrollingEnabled(false);

        homeViewModel.getOtros().observe(this, list -> {
            recyclerOthers.setAdapter(new MangaAdapter(this, list));
        });
    }

    // =========================
    // LIFECYCLE
    // =========================
    @Override
    protected void onPause() {
        super.onPause();
        stopAutoSlider();
    }
}
