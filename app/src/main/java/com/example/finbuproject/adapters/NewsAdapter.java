package com.example.finbuproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.finbuproject.NewsDetailActivity;
import com.example.finbuproject.R;
import com.example.finbuproject.Singleton;
import com.example.finbuproject.models.NewsModel;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_WORLD_READABLE;
import static com.example.finbuproject.MainActivity.url_api;
import static com.example.finbuproject.MainActivity.url_image_storage;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    Context context;
    List<NewsModel> newsList;
    SharedPreferences userSharedPreferences;
    private String url_insert_like =  url_api + "/insert-like";
    private String url_like_amount =  url_api + "/like-amount/";
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_caption, tv_fullname, upload_time, tv_amount_like;
        ImageView img_news;
        ImageButton btn_like;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_caption = itemView.findViewById(R.id.tv_caption);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
            img_news = itemView.findViewById(R.id.img_news);
            upload_time = itemView.findViewById(R.id.upload_time);
            btn_like = itemView.findViewById(R.id.btn_like);
            tv_amount_like = itemView.findViewById(R.id.tv_amount_like);

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

                            userSharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                            String like_amount_url = url_like_amount + news.getNews_ID() + "/" + userSharedPreferences.getInt("user_id", 0);
                            System.out.println(like_amount_url);
                            Ion.with(context)
                                    .load(like_amount_url)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            try {
                                                news.setNews_Amount_Like(result.get("like-amount").getAsString());
                                                if(result.get("isSelfLike").getAsString().equals("true")) {
                                                    news.setSelfLike(true);
                                                    viewHolder.btn_like.setImageResource(R.drawable.ic_favorite_black_30dp);
                                                }
                                                else {
                                                    news.setSelfLike(false);
                                                    viewHolder.btn_like.setImageResource(R.drawable.ic_favorite_border_black_30dp);
                                                }
                                                System.out.println("selfLike "+ news.isSelfLike());
                                                System.out.println("news like " + news.getNews_Amount_Like());
                                                viewHolder.tv_amount_like.setText(news.getNews_Amount_Like());

                                            } catch (Exception error) {
                                                error.printStackTrace();
                                            }
                                        }
                                    });



//                            viewHolder.tv_amount_like.setText(news.getNews_Amount_Like());
                            ImageRequest imageRequest = new ImageRequest(url_image, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap response) {
                                    if (response != null) {
                                        viewHolder.img_news.setImageBitmap(response);
                                    }
                                }
                            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, (CharSequence) error, Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();
                                }
                            });
                            Singleton.getInstance(context).addToRequestQueue(imageRequest);
                        } catch (Exception error) {

                        }

                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BitmapDrawable bitmapDrawable = (BitmapDrawable) viewHolder.img_news.getDrawable();
                                Bitmap bitmap =bitmapDrawable.getBitmap();
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
                                final byte[] bytes =byteArrayOutputStream.toByteArray();

                                Intent intentNewsDetail = new Intent(context, NewsDetailActivity.class);
                                Bundle news_bundle = new Bundle();
                                news_bundle.putString("user_name", viewHolder.tv_fullname.getText().toString());
                                news_bundle.putString("news_detail", viewHolder.tv_caption.getText().toString());
                                news_bundle.putString("news_like_amount", viewHolder.tv_amount_like.getText().toString());
                                news_bundle.putByteArray("img_news_bytes", bytes);

                                intentNewsDetail.putExtras(news_bundle);
                                context.startActivity(intentNewsDetail);
                            }
                        });

                        viewHolder.btn_like.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!news.isSelfLike()) {
                                    news.setSelfLike(true);
                                    viewHolder.btn_like.setImageResource(R.drawable.ic_favorite_black_30dp);
                                    int like_current = Integer.parseInt(viewHolder.tv_amount_like.getText().toString()) + 1;
                                    viewHolder.tv_amount_like.setText(String.valueOf(like_current));
                                }
                                else {
                                    news.setSelfLike(false);
                                    viewHolder.btn_like.setImageResource(R.drawable.ic_favorite_border_black_30dp);
                                    int like_current = Integer.parseInt(viewHolder.tv_amount_like.getText().toString()) - 1;
                                    viewHolder.tv_amount_like.setText(String.valueOf(like_current));
                                }
                                Like(news.getNews_ID(), news.isSelfLike());
                            }
                        });
                    }
                });


    }

    @Override
    public int getItemCount() {
        System.out.println("newslistsize "+newsList.size());
        return newsList.size();
    }


    private void Like(final int news_id, final boolean selfLike) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_insert_like, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("insert");
                    System.out.println("Response: " + Response);
                }catch (Exception error) {
                    System.out.println("like catch: " + error.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("volley error: " + error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                userSharedPreferences = context.getSharedPreferences("user_info", MODE_WORLD_READABLE);
                int user_id = userSharedPreferences.getInt("user_id", 0);
                params.put("action", String.valueOf(selfLike));
                params.put("User_ID", String.valueOf(user_id));
                params.put("News_ID", String.valueOf(news_id));
                return params;
            }
        };
        Singleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
