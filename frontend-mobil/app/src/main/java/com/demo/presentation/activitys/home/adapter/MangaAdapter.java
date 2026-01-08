package com.demo.presentation.activitys.home.adapter;

import android.content.Context;
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

import com.demo.presentation.activitys.manga.MangaActivity;

import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {

    private final List<MangaUIModel> mangaList;
    private final Context context;

    public MangaAdapter(Context context, List<MangaUIModel> mangaList) {
        this.context = context;
        this.mangaList = mangaList;
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manga, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        MangaUIModel manga = mangaList.get(position);

        holder.txtTitle.setText(manga.getTitulo());
        holder.txtStatus.setText(manga.getEstado());

        Glide.with(context)
                .load(manga.getPortadaUrl())
                .placeholder(R.mipmap.banner1)
                .error(R.mipmap.banner1)
                .into(holder.imgManga);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MangaActivity.class);

            intent.putExtra("ID_MANGA", manga.getIdManga()); // 👈 AQUÍ
            intent.putExtra("title", manga.getTitulo());
            intent.putExtra("status", manga.getEstado());
            intent.putExtra("image", manga.getPortadaUrl());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mangaList != null ? mangaList.size() : 0;
    }

    static class MangaViewHolder extends RecyclerView.ViewHolder {

        ImageView imgManga;
        TextView txtTitle, txtStatus;

        MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgManga = itemView.findViewById(R.id.imgManga);
            txtTitle = itemView.findViewById(R.id.txtMangaTitle);
            txtStatus = itemView.findViewById(R.id.txtMangaStatus);
        }
    }
}
