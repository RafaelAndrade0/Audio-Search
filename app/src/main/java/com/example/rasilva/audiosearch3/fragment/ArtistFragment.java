package com.example.rasilva.audiosearch3.fragment;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rasilva.audiosearch3.R;
import com.example.rasilva.audiosearch3.api.IAudioSearchService;
import com.example.rasilva.audiosearch3.api.JSONResponse;
import com.example.rasilva.audiosearch3.api.ServiceGenerator;
import com.example.rasilva.audiosearch3.model.Artist;
import com.lapism.searchview.Search;
import com.lapism.searchview.widget.SearchBar;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {

    private ImageView imageHeader;
    private ImageView imageThumb;
    private SearchBar searchBar;
    private ScrollView scrollView;
    private TextView artistaHeader;
    private TextView procureArtista;
    private ProgressBar progressBar;
    private TextView textTituloArtist;
    private TextView textDescricaoArtista;

    public ArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist, container, false);

        // Configura ids dos componentes
        imageHeader = view.findViewById(R.id.imageHeader);
        imageThumb = view.findViewById(R.id.imageThumb);
        searchBar = view.findViewById(R.id.searchBar);
        scrollView = view.findViewById(R.id.scrollView2);
        artistaHeader = view.findViewById(R.id.textSongHeader);
        procureArtista = view.findViewById(R.id.textProcureAlbum);
        progressBar = view.findViewById(R.id.progressBar2);
        textTituloArtist = view.findViewById(R.id.textTituloArtista);
        textDescricaoArtista = view.findViewById(R.id.textDescricaoArtista);

        // Configurações da Searchbar
        searchBar.setHint("Digite algum Artista. Ex: Eminem");
        searchBar.setLogoIcon(R.drawable.ic_search_24dp);
        searchBar.setOnQueryTextListener(new Search.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(CharSequence query) {
                progressBar.setVisibility(View.VISIBLE);
                procurarArtista(query.toString());
                return true;
            }

            @Override
            public void onQueryTextChange(CharSequence newText) {

            }
        });

        return view;
    }

    //Faz a busca na API pelo artista passado
    public void procurarArtista(final String artista) {
        IAudioSearchService iAudioSearchService = ServiceGenerator.createAudioSearchService();
        Call<JSONResponse> listCall = iAudioSearchService.recuperarDadosArtista(artista);
        listCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();

                if (jsonResponse.getArtists() == null) {
                    searchBar.hasFocus();
                    Toast.makeText(getContext(), "Não existe esse Artista no Banco", Toast.LENGTH_LONG).show();
                } else {
                    Artist[] artist = jsonResponse.getArtists();
                    Log.d("Resposta", artist[0].getStrArtist());

                    textTituloArtist.setText(artist[0].getStrArtist());
                    textDescricaoArtista.setText(artist[0].getStrBiographyEN());

                    // Carrega Header
                    Picasso.get()
                            .load(artist[0].getStrArtistWideThumb())
                            .resize(500, 200)
                            .centerCrop()
                            .placeholder(R.drawable.ic_placeholder200_white)
                            .into(imageHeader);

                    // Carrega Thumb Circular
                    Picasso.get()
                            .load(artist[0].getStrArtistThumb())
                            .resize(100, 100)
                            .centerCrop()
                            .into(imageThumb, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    Bitmap imageBitmap = ((BitmapDrawable) imageThumb.getDrawable()).getBitmap();
                                    RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                                    imageDrawable.setCircular(true);
                                    imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                                    imageThumb.setImageDrawable(imageDrawable);
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });
                    scrollView.setVisibility(View.VISIBLE);
                    artistaHeader.setVisibility(View.GONE);
                    procureArtista.setVisibility(View.GONE);
                    imageHeader.setVisibility(View.VISIBLE);
                }

                // Depois do Json ser retornado torna a progressbar invisivel
                progressBar.setVisibility(View.INVISIBLE);
                searchBar.hideKeyboard();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Resposta", "Erro " + t.getMessage());
            }
        });
    }

}
