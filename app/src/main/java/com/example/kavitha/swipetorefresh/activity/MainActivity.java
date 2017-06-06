package com.example.kavitha.swipetorefresh.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.kavitha.swipetorefresh.app.MyApplication;
import  com.example.kavitha.swipetorefresh.helper.*;

import com.example.kavitha.swipetorefresh.R;


/*You might have noticed that lot of android apps like Twitter,
 Google+ provides an option to swipe / pull down to refresh it’s content.
 Whenever user swipes down from top, a loader will be shown and will disappear once the new content is loaded

Previously we used to implement a custom swipe view to detect the swipe down.
        But android made our day easier by introducing SwipeRefreshLayout in android.support.v4
        (android.support.v4.widget.SwipeRefreshLayout)
        to detect the vertical swipe on any view.*/

public class MainActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener{

    private String TAG = MainActivity.this.getClass().getSimpleName();

    private String URL_TOP_250 = "http://api.androidhive.info/json/imdb_top_250.php?offset=";

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private SwipeListAdapter adapter;
    private List<Movie> movieList;

    private int offSet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

  movieList = new ArrayList<>();
        adapter = new SwipeListAdapter(this,movieList);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                fetchMovies();
            }
        });
    }


    private void  fetchMovies(){
        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        // appending offset to url
        String url = URL_TOP_250 + offSet;

        Log.i(TAG,"url ? "+url);

        // Volley's json array request object
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                if (response.length() > 0) {

                    // looping through json and adding to movies list
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            int rank = jsonObject.getInt("rank");
                            String title = jsonObject.getString("title");

                            Movie eachMovie = new Movie(rank,title);

                            movieList.add(0,eachMovie);



                            // updating offset value to highest value
                            if (rank >= offSet){
                                offSet = rank;
                            }
                        }catch (Exception e){
                            Log.e(TAG, "Server Error: " + e.getMessage());
                        }


                    }
                    adapter.notifyDataSetChanged();

                }

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    /**
     * This method is called when swipe refresh is pulled down
     */

    @Override
    public void onRefresh() {
            fetchMovies();
    }
}
