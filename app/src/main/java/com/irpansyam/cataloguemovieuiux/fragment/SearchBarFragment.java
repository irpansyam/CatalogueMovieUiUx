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


public class SearchBarFragment extends Fragment implements LoaderManager.LoaderCallbacks <ArrayList<MovieItems>>{
    private ArrayList<MovieItems>list;
    CardViewMovieAdapter cardViewMovieAdapter;
    private RecyclerView rvCategory;
    private ProgressBar progressBar;
    static final String EXTRAS_MOVIE_SEARCH_BAR = "EXTRAS_MOVIE";
    private  static  final String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;
    public static String EXTRA_QUERY="extra_query";
    String query;
    String judulFilm;

    public SearchBarFragment() {
        // Required empty public constructor
    }
    public String getJudulFilm (){
        return judulFilm;
    }
    public void setJudulFilm (String judulFilm){
        this.judulFilm = judulFilm;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_bar, container, false);

        progressBar = view.findViewById(R.id.pro_bar_search_bar);
        progressBar.setVisibility(View.VISIBLE);

        list= new ArrayList<>();
        list.addAll(0, Collections.<MovieItems>emptyList());
        rvCategory = view.findViewById(R.id.rv_search_bar);

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

        Log.d(judulFilm,"isi query");
        String searchBar = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+judulFilm;
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE_SEARCH_BAR, searchBar);
        LoaderManager loaderManager = getActivity().getLoaderManager();
        if (loaderManager == null){
            loaderManager.initLoader(0, bundle, SearchBarFragment.this);
        }else {
            loaderManager.restartLoader(0, bundle, SearchBarFragment.this);
        }
        return view;
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String juduleFilm ="";
        if (args != null){
            juduleFilm = args.getString(EXTRAS_MOVIE_SEARCH_BAR);
        }
        Log.d(juduleFilm,"isi judul film url");
        return new MyAsyncTaskLoader(getActivity(), juduleFilm);
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
