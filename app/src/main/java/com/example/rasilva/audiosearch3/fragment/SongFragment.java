package com.example.rasilva.audiosearch3.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rasilva.audiosearch3.R;
import com.example.rasilva.audiosearch3.adapter.AlbumAdapter;
import com.example.rasilva.audiosearch3.api.IAudioSearchService;
import com.example.rasilva.audiosearch3.api.JSONResponse;
import com.example.rasilva.audiosearch3.api.ServiceGenerator;
import com.example.rasilva.audiosearch3.model.Album;
import com.lapism.searchview.Search;
import com.lapism.searchview.widget.SearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongFragment extends Fragment {

    private SearchBar searchBar;
    private RecyclerView recyclerView;
    private ImageView imageHeader;
    private ProgressBar progressBarAlbum;
    private TextView textAlbum;
    private TextView textProcureAlbum;
    private List<Album> listaDeAlbuns = new ArrayList<>();


    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_song, container, false);

        // Seta ids
        searchBar = view.findViewById(R.id.searchBar);
        recyclerView = view.findViewById(R.id.recyclerViewAlbum);
        imageHeader = view.findViewById(R.id.imageHeader);
        progressBarAlbum = view.findViewById(R.id.progressBarAlbum);
        textAlbum = view.findViewById(R.id.textSongHeader);
        textProcureAlbum = view.findViewById(R.id.textProcureAlbum);

        // Seta logo da searchbar
        searchBar.setLogoIcon(R.drawable.ic_search_24dp);

        // Seta Hint
        searchBar.setHint("Digite algum Artista. Ex: Post Marlone");

        searchBar.setOnQueryTextListener(new Search.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(CharSequence query) {
                progressBarAlbum.setVisibility(View.VISIBLE);
                // Popula a recyclerview
                populaAlbuns(query.toString());
                return true;
            }

            @Override
            public void onQueryTextChange(CharSequence newText) {

            }
        });

        return view;
    }

    public void populaAlbuns(String query) {
        IAudioSearchService audioSearchService = ServiceGenerator.createAudioSearchService();
        retrofit2.Call<JSONResponse> responseCall = audioSearchService.recuperarDadosAlbum(query);
        responseCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(retrofit2.Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();

                if (jsonResponse.getAlbum() == null) {
                    searchBar.hasFocus();
                    Toast.makeText(getContext(), "Nada encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    Album[] albums = jsonResponse.getAlbum();

                    // Configura Header
                    Picasso.get()
                            .load(albums[0].getStrAlbumThumb())
                            .resize(500, 200)
                            .centerCrop()
                            .placeholder(R.drawable.ic_placeholder200_white)
                            .into(imageHeader);
                    imageHeader.setVisibility(View.VISIBLE);

                    listaDeAlbuns = Arrays.asList(albums);

                    // Configura Adapter
                    AlbumAdapter albumAdapter = new AlbumAdapter(listaDeAlbuns);

                    // Configura Recyclerview
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
                    recyclerView.setAdapter(albumAdapter);
                }

                textAlbum.setVisibility(View.INVISIBLE);
                textProcureAlbum.setVisibility(View.INVISIBLE);

                // Torna progressbar Invisivel
                progressBarAlbum.setVisibility(View.INVISIBLE);

                searchBar.hideKeyboard();
            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse> call, Throwable t) {

            }
        });
    }

}
