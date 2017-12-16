package com.irpansyam.cataloguemovieuiux.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SONY on 11/22/2017.
 */

public class MovieItems implements Parcelable {
    private String judul;
    private String overview;
    private String tglRilis;
    private String imgPoster;
    private String popularitas;
    private String imgBackdrop;

    public MovieItems(JSONObject object){
        try {
            String judul = object.getString("title");
            String overview = object.getString("overview");
            String realese_date = object.getString("release_date");
            String imgPoster = object.getString("poster_path");
            double popularity = object.getDouble("popularity");
            String popularitas = new DecimalFormat("##.#####").format(popularity);
            String imgBackdrop = object.getString("backdrop_path");

            this.judul = judul;
            this.overview = overview;
            Log.d(realese_date, "Isi release date");
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = newDateFormat.parse(realese_date);
            newDateFormat.applyPattern("EEEE, MMM d, yyyy");
            String finalDate = newDateFormat.format(myDate);
            this.tglRilis = finalDate;
            Log.d(finalDate, "isi final date rilis");
            Log.d(imgPoster,"Isi poster");
            this.imgPoster = "http://image.tmdb.org/t/p/w185/"+imgPoster;
            this.popularitas = popularitas;
            this.imgBackdrop = "http://image.tmdb.org/t/p/w185/"+imgBackdrop;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getJudul(){
        return judul;
    }
    public void setJudul(String judul){
        this.judul=judul;
    }
    public String getOverview(){
        return overview;
    }
    public void setOverview(String overview){
        this.overview = overview;
    }
    public String getTglRilis(){
        return tglRilis;
    }
    public void setTglRilis (String tglRilis){
        this.tglRilis = tglRilis;
    }
    public String getImgPoster(){
        return imgPoster;
    }
    public void setImgPoster(String imgPoster){
        this.imgPoster = imgPoster;
    }
    public String getPopularitas(){
        return popularitas;
    }
    public void setPopularitas( String popularitas){
        this.popularitas = popularitas;
    }
    public String getImgBackdrop(){
        return imgBackdrop;
    }
    public void setImgBackdrop (String imgBackdrop){
        this.imgBackdrop = imgBackdrop;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul);
        dest.writeString(this.overview);
        dest.writeString(this.tglRilis);
        dest.writeString(this.imgPoster);
        dest.writeString(this.popularitas);
        dest.writeString(this.imgBackdrop);
    }

    public MovieItems(Parcel in) {
        this.judul = in.readString();
        this.overview = in.readString();
        this.tglRilis = in.readString();
        this.imgPoster = in.readString();
        this.popularitas = in.readString();
        this.imgBackdrop = in.readString();
    }

    public static final Parcelable.Creator<MovieItems> CREATOR = new Parcelable.Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}
