package com.irpansyam.cataloguemovieuiux.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.irpansyam.cataloguemovieuiux.entity.MovieItems;
import com.irpansyam.cataloguemovieuiux.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {
    @BindView(R.id.img_backdrop_detail)
    ImageView getImgBackdropDetail;
    @BindView(R.id.tv_title_detail)
    TextView getTvTitleDetail;
    @BindView(R.id.tv_tgl_rilis_detail)
    TextView getTvTglRilisDetail;
    @BindView(R.id.tv_popularitas)
    TextView getTvPopularitas;
    @BindView(R.id.tv_overview_detail)
    TextView getTvOverviewDetail;
    public static String EXTRA_MOVIE_ITEMS = "extra_movie_items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ButterKnife.bind(this);

        MovieItems mMovieItems = getIntent().getParcelableExtra(EXTRA_MOVIE_ITEMS);
        getTvTitleDetail.setText(mMovieItems.getJudul());
        getTvTglRilisDetail.setText("Rilis : "+mMovieItems.getTglRilis());
        getTvPopularitas.setText("Popularity : "+mMovieItems.getPopularitas());
        getTvOverviewDetail.setText(mMovieItems.getOverview());
        Glide.with(this)
                .load(mMovieItems.getImgBackdrop())
                .override(550,450)
                .into(getImgBackdropDetail);

        setActionBarTitle(mMovieItems.getJudul());

    }
    private void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
