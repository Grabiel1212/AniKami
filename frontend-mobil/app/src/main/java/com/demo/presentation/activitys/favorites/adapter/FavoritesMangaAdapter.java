package com.demo.presentation.activitys.favorites.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.R;
import com.demo.data.model.Manga;
import com.demo.presentation.activitys.favorites.FavoritesActivity;
import com.demo.presentation.activitys.manga.MangaActivity;

import java.util.List;

public class FavoritesMangaAdapter
        extends RecyclerView.Adapter<FavoritesMangaAdapter.MangaViewHolder> {

    private final FavoritesActivity activity;
    private final List<Manga> mangaList;
    private final int usuarioId;

    // 🔹 Constructor correcto
    public FavoritesMangaAdapter(
            FavoritesActivity activity,
            List<Manga> mangaList,
            int usuarioId
    ) {
        this.activity = activity;
        this.mangaList = mangaList;
        this.usuarioId = usuarioId;
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_favorite_manga, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MangaViewHolder holder, int position) {

        Manga manga = mangaList.get(position);

        holder.txtTitle.setText(manga.getTitulo());
        holder.txtStatus.setText(manga.getEstado());

        // ✅ CARGAR IMAGEN DESDE URL
        Glide.with(holder.itemView.getContext())
                .load(manga.getPortada_url())
                .into(holder.imgManga);

        // ❌ ELIMINAR FAVORITO (BACKEND)
        holder.btnRemove.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {

                Manga seleccionado = mangaList.get(pos);

                // 👉 Llamar a la Activity
                activity.eliminarFavorito(
                        usuarioId,
                        seleccionado.getId()
                );
            }
        });

        // ✅ CLICK → IR A MangaActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, MangaActivity.class);
            intent.putExtra("ID_MANGA", manga.getId());
            intent.putExtra("title", manga.getTitulo());
            intent.putExtra("status", manga.getEstado());
            intent.putExtra("image", manga.getPortada_url());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mangaList != null ? mangaList.size() : 0;
    }

    static class MangaViewHolder extends RecyclerView.ViewHolder {

        ImageView imgManga, btnRemove;
        TextView txtTitle, txtStatus;

        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgManga = itemView.findViewById(R.id.imgManga);
            btnRemove = itemView.findViewById(R.id.btn_remove);
            txtTitle = itemView.findViewById(R.id.txtMangaTitle);
            txtStatus = itemView.findViewById(R.id.txtMangaStatus);
        }
    }
}
