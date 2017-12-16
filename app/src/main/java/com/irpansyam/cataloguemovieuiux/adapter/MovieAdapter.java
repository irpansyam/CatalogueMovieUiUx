package com.irpansyam.cataloguemovieuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.irpansyam.cataloguemovieuiux.listener.CustomOnItemClickListener;
import com.irpansyam.cataloguemovieuiux.R;
import com.irpansyam.cataloguemovieuiux.entity.MovieItems;
import com.irpansyam.cataloguemovieuiux.activity.DetailMovieActivity;

import java.util.ArrayList;

/**
 * Created by SONY on 11/22/2017.
 */

public class MovieAdapter extends BaseAdapter {
    private ArrayList<MovieItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    public MovieAdapter (Context context){
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setData(ArrayList<MovieItems>items){
        mData = items;
        notifyDataSetChanged();
    }
    public void addItem(final  MovieItems item){
        mData.add(item);
        notifyDataSetChanged();
    }
    public void clearData(){
        mData.clear();
    }
    @Override
    public int getItemViewType(int position){
        return 0;
    }
    @Override
    public int getViewTypeCount(){
        return 1;
    }
    @Override
    public int getCount() {
        if (mData == null){
            return  0;
        }else {
            return mData.size();
        }
    }

    @Override
    public MovieItems getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.movie_items, null);
            holder.tvJudul = (TextView)convertView.findViewById(R.id.tv_judul_film);
            holder.tvDescription = (TextView)convertView.findViewById(R.id.tv_description_film);
            holder.tvRilis = (TextView)convertView.findViewById(R.id.tv_tgl_rilis);
            holder.imgPoster = (ImageView)convertView.findViewById(R.id.img_poster);
            holder.viewItemMovie = convertView.findViewById(R.id.view_movie_item);
            holder.imgBackdrop = (ImageView)convertView.findViewById(R.id.img_backdrop_detail);
            convertView.setTag(holder);
        }else {
            holder  = (ViewHolder)convertView.getTag();
        }
        holder.tvJudul.setText(mData.get(position).getJudul());
        holder.tvDescription.setText(mData.get(position).getOverview());
        holder.tvRilis.setText(mData.get(position).getTglRilis());
        Glide.with(context)
                .load(mData.get(position).getImgPoster())
                .override(100,150)
                .crossFade()
                .into(holder.imgPoster);

        holder.viewItemMovie.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                MovieItems mMovieItems = new MovieItems(Parcel.obtain());
                mMovieItems.setJudul(mData.get(position).getJudul());
                mMovieItems.setTglRilis(mData.get(position).getTglRilis());
                mMovieItems.setPopularitas(mData.get(position).getPopularitas());
                mMovieItems.setImgBackdrop(mData.get(position).getImgBackdrop());
                mMovieItems.setOverview(mData.get(position).getOverview());
                Intent detailIntent = new Intent(context, DetailMovieActivity.class);
                detailIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE_ITEMS, mMovieItems);
                context.startActivity(detailIntent);

            }
        }));
        return convertView;
    }
    static class ViewHolder {
        TextView tvJudul;
        TextView tvDescription;
        TextView tvRilis;
        ImageView imgPoster;
        LinearLayout viewItemMovie;
        ImageView imgBackdrop;
    }
}
