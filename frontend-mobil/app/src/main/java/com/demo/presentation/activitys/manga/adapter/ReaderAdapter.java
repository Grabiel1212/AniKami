package com.demo.presentation.activitys.manga.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.R;
import com.demo.data.model.Pajina;

import java.util.List;

public class ReaderAdapter extends RecyclerView.Adapter<ReaderAdapter.ViewHolder> {

    private final List<Pajina> paginas;

    public ReaderAdapter(List<Pajina> paginas) {
        this.paginas = paginas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pajina pagina = paginas.get(position);

        Log.d("READER_DEBUG", "Cargando página " + pagina.getNumeroPagina() + " URL: " + pagina.getImagenUrl());

        Glide.with(holder.itemView.getContext())
                .load(pagina.getImagenUrl())
                .placeholder(R.mipmap.cargando) // mientras carga
                .error(R.mipmap.error)      // si falla
                .into(holder.imgPagina);

        holder.txtPageNumber.setText(String.valueOf(pagina.getNumeroPagina()));
    }

    @Override
    public int getItemCount() {
        return paginas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPagina;
        TextView txtPageNumber;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPagina = itemView.findViewById(R.id.imgPage);
            txtPageNumber = itemView.findViewById(R.id.txtPageNumber);
        }
    }
}

