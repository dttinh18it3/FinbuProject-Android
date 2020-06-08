package com.example.finbuproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finbuproject.R;
import com.example.finbuproject.models.NewsModel;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    Context context;
    List<NewsModel> newsList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_caption, tv_fullname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_caption = itemView.findViewById(R.id.tv_caption);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
        }
    }

    public NewsAdapter(Context ctxt, List<NewsModel> list) {
        this.context = ctxt;
        this.newsList = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final NewsModel news = newsList.get(i);
        //        PhuongVyCafe
        String url = "http://192.168.31.20/Finbu/public/api/users/" + news.getUser_ID();
//        String url = "http://192.168.1.100/Finbu/public/api/users/" + news.getUser_ID();
        System.out.println("url user  " + url);
        Ion.with(context)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            viewHolder.tv_caption.setText(news.getNews_Detail());
                            viewHolder.tv_fullname.setText(result.get("User_Firstname").getAsString() + " " + result.get("User_Name").getAsString());
                        } catch (Exception error) {

                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
