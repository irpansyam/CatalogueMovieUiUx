package com.irpansyam.cataloguemovieuiux.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.irpansyam.cataloguemovieuiux.entity.MovieItems;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by SONY on 11/22/2017.
 */

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {
    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;
    private String mUrlMovie;

    public MyAsyncTaskLoader (final Context context, String movieUrl){
        super(context);
        onContentChanged();
        this.mUrlMovie = movieUrl;
    }
    @Override
    protected void onStartLoading(){
        Log.d("Content Changed","1");
        if (takeContentChanged())
            forceLoad();
        else  if (mHasResult)
            deliverResult(mData);
    }
    @Override
    public void deliverResult(final ArrayList<MovieItems>data){
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }
    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<MovieItems> movieItemses = new ArrayList<>();
        client.get(mUrlMovie, new AsyncHttpResponseHandler() {
            @Override
            public void onStart(){
                super.onStart();
                setUseSynchronousMode(true);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i =0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        movieItemses.add(movieItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItemses;
    }
    protected void onReleaseResources(ArrayList<MovieItems> data) {
    }
}
