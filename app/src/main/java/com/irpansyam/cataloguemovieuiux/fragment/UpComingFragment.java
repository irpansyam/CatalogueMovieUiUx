package com.irpansyam.cataloguemovieuiux.fragment;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.irpansyam.cataloguemovieuiux.BuildConfig;
import com.irpansyam.cataloguemovieuiux.listener.ItemClickSupport;
import com.irpansyam.cataloguemovieuiux.loader.MyAsyncTaskLoader;
import com.irpansyam.cataloguemovieuiux.R;
import com.irpansyam.cataloguemovieuiux.adapter.CardViewMovieAdapter;
import com.irpansyam.cataloguemovieuiux.activity.DetailMovieActivity;
import com.irpansyam.cataloguemovieuiux.entity.MovieItems;

import java.util.ArrayList;
import java.util.Collections;

public class UpComingFragment extends Fragment implements LoaderManager.LoaderCallbacks <ArrayList<MovieItems>>{
    private ArrayList<MovieItems>list;
    CardViewMovieAdapter cardViewMovieAdapter;
    private RecyclerView rvCategory;
    private ProgressBar progressBar;
    static final String EXTRAS_MOVIE_UPCOMING = "EXTRAS_MOVIE";
    private  static  final String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;
    public UpComingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);

        progressBar = view.findViewById(R.id.pro_bar_up_coming);
        progressBar.setVisibility(View.VISIBLE);

        list= new ArrayList<>();
        list.addAll(0, Collections.<MovieItems>emptyList());
        rvCategory = view.findViewById(R.id.rv_up_coming);

        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardViewMovieAdapter = new CardViewMovieAdapter(getActivity());
        cardViewMovieAdapter.setListMovie(list);
        rvCategory.setAdapter(cardViewMovieAdapter);

        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String judul = cardViewMovieAdapter.getListMovie().get(position).getJudul();
                Toast.makeText(getActivity(), "Kamu memilih...... "+judul, Toast.LENGTH_SHORT).show();

                MovieItems mMovieItems = new MovieItems(Parcel.obtain());
                mMovieItems.setJudul(cardViewMovieAdapter.getListMovie().get(position).getJudul());
                mMovieItems.setTglRilis(cardViewMovieAdapter.getListMovie().get(position).getTglRilis());
                mMovieItems.setPopularitas(cardViewMovieAdapter.getListMovie().get(position).getPopularitas());
                mMovieItems.setImgBackdrop(cardViewMovieAdapter.getListMovie().get(position).getImgBackdrop());
                mMovieItems.setOverview(cardViewMovieAdapter.getListMovie().get(position).getOverview());
                Intent detailIntent = new Intent(getActivity(), DetailMovieActivity.class);
                detailIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE_ITEMS, mMovieItems);
                getActivity().startActivity(detailIntent);
            }
        });

        String upComing = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US&page=1";
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE_UPCOMING, upComing);
        LoaderManager loaderManager = getActivity().getLoaderManager();
        if (loaderManager == null){
            loaderManager.initLoader(0, bundle, UpComingFragment.this);
        }else {
            loaderManager.restartLoader(0, bundle, UpComingFragment.this);
        }
        return view;
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String judulFilm ="";
        if (args != null){
            judulFilm = args.getString(EXTRAS_MOVIE_UPCOMING);
        }
        Log.d(judulFilm,"isi judul film url");
        return new MyAsyncTaskLoader(getActivity(), judulFilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        cardViewMovieAdapter.setListMovie(data);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        cardViewMovieAdapter.setListMovie(null);
        progressBar.setVisibility(View.GONE);
    }
}
