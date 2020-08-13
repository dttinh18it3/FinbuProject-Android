package com.example.finbuproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.finbuproject.R;
import com.example.finbuproject.Singleton;
import com.example.finbuproject.models.NewsModel;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

import static com.example.finbuproject.MainActivity.url_api;
import static com.example.finbuproject.MainActivity.url_image_storage;

public class NewsOfUserAdaper extends RecyclerView.Adapter<NewsOfUserAdaper.ViewHolder> {
    Context context;
    List<NewsModel> newsOfUserList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_caption, tv_fullname, upload_time;
        ImageView img_news;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_caption = itemView.findViewById(R.id.tv_caption);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
            img_news = itemView.findViewById(R.id.img_news);
            upload_time = itemView.findViewById(R.id.upload_time);
        }
    }
    public NewsOfUserAdaper(Context ctxt, List<NewsModel> list) {
        this.context = ctxt;
        this.newsOfUserList = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new NewsOfUserAdaper.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final NewsModel news = newsOfUserList.get(i);
        String url = url_api + "/users/" + news.getUser_ID();
        final String url_image = url_image_storage + news.getNews_Image();
        Ion.with(context)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            viewHolder.tv_caption.setText(news.getNews_Detail());
                            viewHolder.upload_time.setText(news.getNews_time_upload());
                            viewHolder.tv_fullname.setText(result.get("User_Firstname").getAsString() + " " + result.get("User_Name").getAsString());
                            ImageRequest imageRequest = new ImageRequest(url_image, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap response) {
                                    viewHolder.img_news.setImageBitmap(response);
                                }
                            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, (CharSequence) error, Toast.LENGTH_SHORT).show();
                                    System.out.print("N o U - volley ");
                                    error.printStackTrace();
                                }
                            });
                            Singleton.getInstance(context).addToRequestQueue(imageRequest);
                        } catch (Exception error) {
                            System.out.println("NoU catch " + error);
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        System.out.println("newouser   " + newsOfUserList.size());
        return newsOfUserList.size();
    }


}
