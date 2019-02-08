package com.example.rasilva.audiosearch3.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rasilva.audiosearch3.R;
import com.example.rasilva.audiosearch3.RecyclerItemClickListener;
import com.example.rasilva.audiosearch3.activity.DetailsActivity;
import com.example.rasilva.audiosearch3.adapter.TrendingMusicAdapter;
import com.example.rasilva.audiosearch3.api.IAudioSearchService;
import com.example.rasilva.audiosearch3.api.JSONResponse;
import com.example.rasilva.audiosearch3.api.ServiceGenerator;
import com.example.rasilva.audiosearch3.model.TrendingMusic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<TrendingMusic> musicList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        this.populaTrendingMusic();
        return view;
    }

    public void populaTrendingMusic() {
        IAudioSearchService audioSearchService = ServiceGenerator.createAudioSearchService();
        Call<JSONResponse> responseCall = audioSearchService.recuperarTrendingMusic("us", "itunes", "singles");
        responseCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                TrendingMusic[] trendingMusic = jsonResponse.getTrending();
                musicList = Arrays.asList(trendingMusic);

                // Configurar Adapter
                TrendingMusicAdapter adapter = new TrendingMusicAdapter(musicList);

                // Configura RecyclerView
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
                recyclerView.setAdapter(adapter);

                // Click Listener
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                        getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TrendingMusic music = musicList.get(position);
                        Intent intent = new Intent(getContext(), DetailsActivity.class);
                        intent.putExtra("musicDetails", music);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                ));

                // Torna a progressbar inisivel
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("APILOG", t.getMessage());
            }
        });
    }
}
