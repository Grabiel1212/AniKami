package com.demo.presentation.activitys.base;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.R;
import com.demo.presentation.activitys.categories.CategoriesActivity;
import com.demo.presentation.activitys.favorites.FavoritesActivity;
import com.demo.presentation.activitys.home.HomeActivity;
import com.demo.presentation.activitys.profile.ProfileActivity;
import com.demo.presentation.activitys.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected void setupBottomNav(int selectedItemId) {

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // 🔴 PROTECCIÓN CONTRA CRASH
        if (bottomNav == null) return;

        // ✅ MARCAR ÍTEM ACTIVO SIN DISPARAR EL LISTENER
        bottomNav.getMenu().findItem(selectedItemId).setChecked(true);

        bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == selectedItemId) {
                return true; // no recargar la misma activity
            }

            Intent intent = null;

            if (item.getItemId() == R.id.nav_home) {
                intent = new Intent(this, HomeActivity.class);
            } else if (item.getItemId() == R.id.nav_genres) {
                intent = new Intent(this, CategoriesActivity.class);
            } else if (item.getItemId() == R.id.nav_search) {
                intent = new Intent(this, SearchActivity.class);
            } else if (item.getItemId() == R.id.nav_favorites) {
                intent = new Intent(this, FavoritesActivity.class);
            } else if (item.getItemId() == R.id.nav_profile) {
                intent = new Intent(this, ProfileActivity.class);
            }

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(0, 0); // sin parpadeo
            }

            return true;
        });
    }
}