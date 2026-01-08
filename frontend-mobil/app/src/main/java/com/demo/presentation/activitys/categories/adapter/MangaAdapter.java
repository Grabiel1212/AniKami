package com.demo.presentation.activitys.categories.adapter;

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
import com.demo.presentation.activitys.home.adapter.MangaUIModel;

import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {

    private final List<MangaUIModel> mangaList;
    private final Context context;

    public MangaAdapter(List<MangaUIModel> mangaList, Context context) {
        this.mangaList = mangaList;
        this.context = context;
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manga_card, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {

        MangaUIModel manga = mangaList.get(position);

        holder.txtNombre.setText(manga.getTitulo());
        holder.txtEstado.setText(manga.getEstado());

        Glide.with(context)
                .load(manga.getPortadaUrl())
                .placeholder(R.mipmap.banner1)
                .into(holder.imgManga);

        // 🔹 CLICK → ENVÍA ID_MANGA
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MangaActivity.class);
            intent.putExtra("ID_MANGA", manga.getIdManga());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

    static class MangaViewHolder extends RecyclerView.ViewHolder {

        ImageView imgManga;
        TextView txtNombre, txtEstado;

        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgManga = itemView.findViewById(R.id.imgManga);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtEstado = itemView.findViewById(R.id.txtEstado);
        }
    }
}
