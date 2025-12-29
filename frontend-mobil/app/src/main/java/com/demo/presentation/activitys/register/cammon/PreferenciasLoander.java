package com.demo.presentation.activitys.register.cammon;

import android.content.Context;

import com.demo.data.model.Genero;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class PreferenciasLoander {


    // PreferenciasLoader.java
    public static void cargarGeneros(
            Context context,
            ChipGroup chipGroup,
            List<Genero> generos
    ) {
        chipGroup.removeAllViews();

        for (Genero genero : generos) {
            Chip chip = new Chip(context);
            chip.setText(genero.getNombre());
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setTag(genero.getId()); // 👈 guardar ID
            chipGroup.addView(chip);
        }
    }

}
