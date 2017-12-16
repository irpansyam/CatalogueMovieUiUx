package com.irpansyam.cataloguemovieuiux.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.irpansyam.cataloguemovieuiux.listener.CustomOnItemClickListener;
import com.irpansyam.cataloguemovieuiux.entity.MovieItems;
import com.irpansyam.cataloguemovieuiux.R;

import java.util.ArrayList;

/**
 * Created by SONY on 11/26/2017.
 */

public class CardViewMovieAdapter  extends RecyclerView.Adapter<CardViewMovieAdapter.CardViewViewHolder>{

    private ArrayList <MovieItems> listMovie;
    private Context context;

    public CardViewMovieAdapter (Context context){
        this.context= context;
    }

    public ArrayList<MovieItems>getListMovie(){
        return listMovie;
    }
    public void setListMovie (ArrayList<MovieItems>listMovie){
        this.listMovie = listMovie;
    }
    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_movie, parent, false);
        CardViewViewHolder viewHolder = new CardViewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {
        MovieItems m = getListMovie().get(position);
        Glide.with(context)
                .load(m.getImgPoster())
                .override(350,550)
                .into(holder.imgPhoto);
        holder.tvJudul.setText(m.getJudul());
        holder.tvOverview.setText(m.getOverview());
        holder.tvTglRilis.setText(m.getTglRilis());
        holder.btnFavorite.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {

            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Favorite "+getListMovie().get(position).getJudul(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {

            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share " + getListMovie().get(position).getJudul(), Toast.LENGTH_SHORT).show();
            }
        }));

    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvJudul, tvTglRilis,tvOverview;
        Button btnFavorite, btnShare;
        public CardViewViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvJudul = itemView.findViewById(R.id.tv_item_judul);
            tvTglRilis = itemView.findViewById(R.id.tv_item_tgl_rilis);
            tvOverview = itemView.findViewById(R.id.tv_item_overview);
            btnFavorite = itemView.findViewById(R.id.btn_set_favorite);
            btnShare = itemView.findViewById(R.id.btn_set_share);
        }
    }
}
