package com.example.newsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsapp.models.CustomAdapter;
import com.example.newsapp.models.CustomViewHolder;
import com.example.newsapp.models.DetailsActivity;
import com.example.newsapp.models.NewsApiResponse;
import com.example.newsapp.models.NewsHeadlines;
import com.example.newsapp.models.OnFetchDataListener;
import com.example.newsapp.models.SelectListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse> () {

        @Override
        public void OnFetchData(List<NewsHeadlines> list,String message) {


            if (list.isEmpty ()) {
                Toast.makeText (MainActivity.this,"No data found ",Toast.LENGTH_SHORT).show ();
            } else {
                showNews (list);
            }
        }


        @Override
        public void OnError(String message) {

            Toast.makeText (MainActivity.this,"An Error Occurred!!!",Toast.LENGTH_SHORT).show ();

        }
    };

    Button b1, b2, b3, b4, b5, b6, b7;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder (MainActivity.this);
        builder.setCancelable (false);
        builder.setView (R.layout.layout_loading_dialog );
        AlertDialog dialog = builder.create ();
        dialog.setTitle ("Fetching news articles...");
        dialog.show ();
        //Create a handler instance on the main thread
        Handler handler = new Handler ();
        //Create and start new thread
        new Thread (new Runnable () {
            @Override
            public void run() {
                try {
                    Thread.sleep (2000);
                } catch (Exception e) {
                    // just catch the InterruptException
                    e.printStackTrace ();

                }
                handler.post (new Runnable () {
                    @Override
                    public void run() {
                        // set the view's visibility back on the main UI thread
                        dialog.dismiss ();

                    }
                });
            }
        }).start ();

        RequestManager manager = new RequestManager (this);
        manager.getNewsHeadlines (listener,null,"general");

        b1 = findViewById (R.id.btn_1);
        b1.setOnClickListener (this);
        b2 = findViewById (R.id.btn_2);
        b2.setOnClickListener (this);
        b3 = findViewById (R.id.btn_3);
        b3.setOnClickListener (this);
        b4 = findViewById (R.id.btn_4);
        b4.setOnClickListener (this);
        b5 = findViewById (R.id.btn_5);
        b5.setOnClickListener (this);
        b6 = findViewById (R.id.btn_6);
        b6.setOnClickListener (this);
        b7 = findViewById (R.id.btn_7);
        b7.setOnClickListener (this);

        searchView = findViewById (R.id.search_view);
        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AlertDialog.Builder builder = new AlertDialog.Builder (MainActivity.this);
                builder.setCancelable (false);
                builder.setView (R.layout.layout_loading_dialog);
                AlertDialog dialog = builder.create ();
                dialog.setTitle ("Fetching news articles of " + query);
                dialog.show ();
                //Create handler instance on the main thread

                //Create and start new thread
                new Thread (new Runnable () {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep (2000);
                        }catch (Exception e){
                            //just catch InterruptException
                            e.printStackTrace ();
                        }
                        handler.post (new Runnable () {
                            @Override
                            public void run() {
                                //set the view's visibility back on the main UI thread
                                dialog.dismiss ();
                            }
                        });
                    }

                }).start ();


                RequestManager manager = new RequestManager (MainActivity.this);
                manager.getNewsHeadlines (listener,query,"general");

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById (R.id.recycle_main);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new GridLayoutManager (this,1));
        customAdapter = new CustomAdapter (this,list,this);
        recyclerView.setAdapter (customAdapter);

    }


    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity (new Intent (MainActivity.this,DetailsActivity.class)
                .putExtra ("Data",headlines));


    }


    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText ().toString ();
        AlertDialog.Builder builder = new AlertDialog.Builder (MainActivity.this);
        builder.setCancelable (false);
        builder.setView (R.layout.layout_loading_dialog);
        AlertDialog bar = builder.create ();
        bar.setTitle ("Fetching news articles of " + category);
        bar.show ();
        // Create a handler instance on main thread
        Handler handler = new Handler ();
        //Create and start the new thread
        new Thread (new Runnable () {
            @Override
            public void run() {
                try {
                    Thread.sleep (2000);
                } catch (Exception e) {
                    // just catch the Interrupt Exception
                    e.printStackTrace ();

                }
                handler.post (new Runnable () {
                    @Override
                    public void run() {
                        //set the view's visibility on the main UI thread
                        bar.dismiss ();
                    }
                });
            }
        }).start ();

        RequestManager manager = new RequestManager (this);
        manager.getNewsHeadlines (listener,null,category);

    }
}