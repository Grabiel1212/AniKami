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

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder> {

    private final List<MangaUIModel> list;
    private final Context context;

    public TopAdapter(Context context, List<MangaUIModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MangaUIModel item = list.get(position);

        holder.rank.setText(String.valueOf(position + 1));
        holder.title.setText(item.getTitulo());
        holder.status.setText(item.getEstado());

        Glide.with(context)
                .load(item.getPortadaUrl())
                .placeholder(R.mipmap.banner1)
                .error(R.mipmap.banner1)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MangaActivity.class);

            intent.putExtra("ID_MANGA", item.getIdManga()); // 👈 AQUÍ
            intent.putExtra("title", item.getTitulo());
            intent.putExtra("status", item.getEstado());
            intent.putExtra("image", item.getPortadaUrl());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank, title, status;
        ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.txtRank);
            title = itemView.findViewById(R.id.txtTitle);
            status = itemView.findViewById(R.id.txtStatus);
            image = itemView.findViewById(R.id.imgPoster);
        }
    }
}
