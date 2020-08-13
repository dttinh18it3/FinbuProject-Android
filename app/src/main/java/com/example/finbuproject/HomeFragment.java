package com.example.finbuproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finbuproject.adapters.NewsAdapter;
import com.example.finbuproject.models.NewsModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import static com.example.finbuproject.MainActivity.url_api;

public class HomeFragment extends Fragment {

    NewsAdapter news_adapter;
    List<NewsModel> news_list;
    RecyclerView recycler_view_news;
    TextView tv_addNews;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recycler_view_news = view.findViewById(R.id.rv_news);

        news_list = new ArrayList<>();

        news_adapter = new NewsAdapter(HomeFragment.this.getActivity(), news_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeFragment.this.getActivity());
        recycler_view_news.setLayoutManager(layoutManager);
        recycler_view_news.addItemDecoration(new DividerItemDecoration(HomeFragment.this.getActivity(), LinearLayoutManager.VERTICAL));
        recycler_view_news.setAdapter(news_adapter);

        tv_addNews = view.findViewById(R.id.tv_addNews);
        tv_addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getContext(), AddNewsActivity.class);
                startActivity(intent);
            }
        });

        listNews();
        return view;
    }


    private void listNews() {
        String url = url_api + "/home";
        Ion.with(HomeFragment.this.getActivity())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            for(int i = 0; i < result.size(); i++) {
                                System.out.println("size "+result.size());
                                JsonObject us = result.get(i).getAsJsonObject();
                                NewsModel news = new NewsModel();
                                news.setNews_ID(us.get("News_ID").getAsInt());
                                news.setUser_ID(us.get("User_ID").getAsInt());
                                news.setNews_Detail(us.get("News_Detail").getAsString());
                                news.setNews_time_upload(us.get("News_time_upload").getAsString());
                                news.setNews_Image(us.get("News_Image").getAsString());
                                news_list.add(news);
                                System.out.println("N L "+news_list.size());
                            }
                            news_adapter.notifyDataSetChanged();
                        } catch(Exception error) {
                            Toast.makeText(HomeFragment.this.getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
