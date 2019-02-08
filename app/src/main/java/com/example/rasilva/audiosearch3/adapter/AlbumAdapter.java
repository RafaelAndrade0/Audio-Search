package com.example.rasilva.audiosearch3.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rasilva.audiosearch3.R;
import com.example.rasilva.audiosearch3.model.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    List<Album> listaAlbuns;

    public AlbumAdapter(List<Album> listaAlbuns) {
        this.listaAlbuns = listaAlbuns;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_album, viewGroup, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Album album = listaAlbuns.get(i);
        myViewHolder.strAlbum.setText(album.getStrAlbum());
        myViewHolder.intYearReleased.setText(album.getIntYearReleased());
        myViewHolder.strGenre.setText(album.getStrGenre());
        myViewHolder.strGenre.setText(album.getIntSales());
        myViewHolder.intSales.setText(album.getIntSales());

        // Inserindo a imagem com a biblioteca picasso
        Picasso.get()
                .load(album.getStrAlbumThumb())
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.placeholder_100)
                .into(myViewHolder.strAlbumThumb);
    }

    @Override
    public int getItemCount() {
        return listaAlbuns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView strAlbum;
        TextView intYearReleased;
        TextView strGenre;
        ImageView strAlbumThumb;
        TextView intSales;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            strAlbum = itemView.findViewById(R.id.textTitleAlbum);
            intYearReleased = itemView.findViewById(R.id.textYear);
            strGenre = itemView.findViewById(R.id.textGenre);
            strAlbumThumb = itemView.findViewById(R.id.imageViewAlbum);
            intSales = itemView.findViewById(R.id.textSales);
        }
    }
}
