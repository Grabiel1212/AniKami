package com.demo.presentation.activitys.register.cammon;

import android.content.Context;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class PreferenciasLoander {
    public static void cargarPreferencias(Context context, ChipGroup chipGroup) {

        String[] preferencias = {
                "Acción",
                "Aventura",
                "Comedia",
                "Drama",
                "Fantasía",
                "Ciencia Ficción",
                "Romance"

        };


        for (String pref : preferencias) {
            Chip chip = new Chip(context);
            chip.setText(pref);
            chip.setCheckable(true);
            chip.setClickable(true);
            chipGroup.addView(chip);
        }
    }
}
