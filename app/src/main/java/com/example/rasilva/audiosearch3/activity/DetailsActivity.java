package com.example.rasilva.audiosearch3.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rasilva.audiosearch3.R;
import com.example.rasilva.audiosearch3.model.TrendingMusic;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class DetailsActivity extends AppCompatActivity {

    private Button buttonBack;
    private TrendingMusic music;
    private TextView textTrack;
    private ImageView thumbAlbum;
    private View viewAlbum;
    private TextView textAlbumName;
    private TextView textPosition;
    private ProgressBar progressBarDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Seta ids
        textTrack = findViewById(R.id.textTrackname);
        viewAlbum = findViewById(R.id.view2);
        thumbAlbum = findViewById(R.id.imageThumbDetails);
        textAlbumName = findViewById(R.id.textAlbumName);
        progressBarDetails = findViewById(R.id.progressBarDetails);
        textPosition = findViewById(R.id.textPosition);

        // Recupera dados enviados pela HomeFragment
        music = getIntent().getParcelableExtra("musicDetails");
        textTrack.setText(music.getStrTrack());
        textAlbumName.setText(music.getStrAlbum());
        textPosition.setText(music.getIntChartPlace());

        Picasso.get()
                .load(music.getStrTrackThumb())
                .resize(200, 200)
                .centerCrop()
                .placeholder(R.drawable.placeholder_200)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(@Nullable Palette palette) {
                                        Palette.Swatch swatch = palette.getVibrantSwatch();
                                        if (swatch == null) {
                                            viewAlbum.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                                            Toast.makeText(DetailsActivity.this, "Null swatch :(", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        viewAlbum.setBackgroundColor(swatch.getRgb());
                                        Window window = getWindow();
                                        window.setStatusBarColor(swatch.getRgb());
                                    }
                                });
                        progressBarDetails.setVisibility(View.INVISIBLE);
                        thumbAlbum.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        // Botão de voltar
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
