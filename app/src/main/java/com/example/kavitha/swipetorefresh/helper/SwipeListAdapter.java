package com.example.kavitha.swipetorefresh.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kavitha.swipetorefresh.R;

import java.util.List;

/**
 * Created by Kavitha on 06-06-2017.
 */

public class SwipeListAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater layoutInflater;
    List<Movie> movieList;
    String[] bgColors;
    String TAG = "SwipeListAdapter";

    public SwipeListAdapter(Activity activity,List<Movie> movieList){
        this.activity = activity;
        this.movieList = movieList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);

    }

    @Override
    public int getCount() {
        return movieList.size();
    }


    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutInflater == null)
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.list_row,null);


        TextView serialTextView = (TextView) convertView.findViewById(R.id.serial);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title);

       Movie eachMovie = movieList.get(position);

        serialTextView.setText(String.valueOf(eachMovie.getId()));
        titleTextView.setText(eachMovie.getTitle());

        String colorBg = bgColors[position % bgColors.length];

        serialTextView.setBackgroundColor(Color.parseColor(colorBg));

        return convertView;
    }
}
