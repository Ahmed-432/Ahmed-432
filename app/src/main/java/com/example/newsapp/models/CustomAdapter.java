package com.example.newsapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

  private Context context;
  private List<NewsHeadlines> headlines;
  private SelectListener listener;
    private CustomViewHolder holder;
    private int position;


    public CustomAdapter(Context context,List<NewsHeadlines> headlines , SelectListener listener ) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        return new CustomViewHolder (LayoutInflater.from (context).inflate (R.layout.headline_list_items , parent , false));


    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder,int position) {
        this.holder = holder;
        this.position = position;

        holder.text_title.setText (headlines.get(position).getTitle ());
        holder.text_source.setText (headlines.get (position).getSource ().getName ());


        if (headlines.get(position).getUrlToImage () != null){
            Picasso.get ().load (headlines.get (position).getUrlToImage ()).into(holder.img_headline);

        }

        holder.cardView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                listener.OnNewsClicked (headlines.get (position));
            }
        });



    }


    @Override
    public int getItemCount() {
        return headlines.size ();
    }
}
