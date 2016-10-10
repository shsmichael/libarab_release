package com.example.michaelg.myapplication.Trivia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michaelg.myapplication.ListviewActivity.Book;
import com.example.michaelg.myapplication.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Pcp on 02/10/2016.
 */

public class ListviewQAdapter extends ArrayAdapter<ItemsQ>{
    ArrayList<ItemsQ> bookList;
    LayoutInflater vi;
    int Resource;
    com.example.michaelg.myapplication.Trivia.ListviewQAdapter.ViewHolder holder;

    public ListviewQAdapter(Context context, int resource, ArrayList<ItemsQ> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        bookList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new com.example.michaelg.myapplication.Trivia.ListviewQAdapter.ViewHolder();
            v = vi.inflate(Resource, null);
            holder.title = (TextView) v.findViewById(R.id.textView2);
            holder.author = (TextView) v.findViewById(R.id.tv_title);
            v.setTag(holder);
        } else {
            holder = (com.example.michaelg.myapplication.Trivia.ListviewQAdapter.ViewHolder) v.getTag();
        }
        holder.title.setText(bookList.get(position).getItemName());
        //holder.author.setText(bookList.get(position).getAuthor());
        return v;

    }

    static class ViewHolder {
        public TextView title;
        //public TextView tvDescription;
        public TextView author;

    }

}
