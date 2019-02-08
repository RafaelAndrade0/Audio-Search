package com.example.rasilva.audiosearch3.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rasilva.audiosearch3.R;
import com.example.rasilva.audiosearch3.model.TrendingMusic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrendingMusicAdapter extends RecyclerView.Adapter<TrendingMusicAdapter.MyViewHolder> {

    private List<TrendingMusic> trendingMusicList;

    public TrendingMusicAdapter(List<TrendingMusic> trendingMusicList) {
        this.trendingMusicList = trendingMusicList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_trending, viewGroup, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        TrendingMusic trendingMusic = trendingMusicList.get(i);
        myViewHolder.strTrack.setText(trendingMusic.getStrTrack());
        myViewHolder.strArtist.setText(trendingMusic.getStrArtist());
        myViewHolder.intChartPlace.setText(trendingMusic.getIntChartPlace());
        myViewHolder.strAlbum.setText(trendingMusic.getStrAlbum());
        Picasso.get()
                .load(trendingMusic.getStrTrackThumb())
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.placeholder_100)
                .into(myViewHolder.strTrackThumb);
    }

    @Override
    public int getItemCount() {
        return trendingMusicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView strArtist;
        TextView strTrack;
        TextView intChartPlace;
        TextView strAlbum;
        ImageView strTrackThumb;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            strArtist = itemView.findViewById(R.id.textArtistDetails);
            strTrack = itemView.findViewById(R.id.textTrack2);
            intChartPlace = itemView.findViewById(R.id.textPlaceDetails);
            strAlbum = itemView.findViewById(R.id.textAlbumDetails);
            strTrackThumb = itemView.findViewById(R.id.imageView);
        }
    }
}


