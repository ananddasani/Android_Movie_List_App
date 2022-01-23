package com.example.apimovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context context;
    ArrayList<MovieModel> data;

    public CustomAdapter(Context context, ArrayList<MovieModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.movieName.setText(data.get(position).getTitle());
        holder.movieRating.setText(String.valueOf(data.get(position).getRating()));
        holder.movieDescription.setText(data.get(position).getOverView());

        String uri = data.get(position).getPoster();
        Uri mImage = Uri.parse(uri);
        Glide.with(context).load(mImage).into(holder.movieImage);

        //move to detailed activity with data clicked
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("TITLE", data.get(position).getTitle());
                bundle.putDouble("RATING", data.get(position).getRating());
                bundle.putString("DESCRIPTION", data.get(position).getOverView());
                bundle.putString("POSTER", data.get(position).getPoster());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        ImageView movieImage;
        TextView movieName, movieRating, movieDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieName = itemView.findViewById(R.id.textViewMovieName);
            movieRating = itemView.findViewById(R.id.textViewRating);
            movieDescription = itemView.findViewById(R.id.textViewDescription);
            movieImage = itemView.findViewById(R.id.imageViewMoviePoster);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
