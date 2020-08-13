package com.example.finbuproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.finbuproject.adapters.NewsOfUserAdaper;
import com.example.finbuproject.models.NewsModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import static com.example.finbuproject.MainActivity.url_api;

public class ProfileFragment extends Fragment {
    private SharedPreferences userSharedPreferences;
    TextView tv_user_fullname, tv_user_DoB, tv_user_email, tv_user_gender, tv_user_cellphone;
    TextView tv_addNews;

    NewsOfUserAdaper newsOfUserAdapter;
    List<NewsModel> newsOfUserList;
    RecyclerView rv_newsOfUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        newsOfUserList = new ArrayList<>();

        rv_newsOfUser = view.findViewById(R.id.rv_newsOfUser);

        tv_user_fullname = view.findViewById(R.id.tv_user_fullname);
        tv_user_DoB = view.findViewById(R.id.tv_user_DoB);
        tv_user_email = view.findViewById(R.id.tv_user_email);
        tv_user_cellphone = view.findViewById(R.id.tv_user_cellphone);
        tv_user_gender = view.findViewById(R.id.tv_user_gender);
        tv_addNews = view.findViewById(R.id.tv_addNews);
        tv_addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileFragment.this.getContext(), AddNewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_fullname", tv_user_fullname.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        userSharedPreferences = ProfileFragment.this.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String url_user_profile = url_api + "/users/" + userSharedPreferences.getInt("user_id", 0);
        System.out.println(url_user_profile);

        Ion.with(ProfileFragment.this.getContext())
                .load(url_user_profile)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            tv_user_fullname.setText(result.get("User_Firstname").getAsString() + " " + result.get("User_Name").getAsString());
                            tv_user_DoB.setText(result.get("User_DoB").getAsString());
                            tv_user_email.setText(result.get("Email").getAsString());
                            tv_user_cellphone.setText(result.get("Cellphone").getAsString());
                            if (result.get("Gender").getAsInt() == 1) {
                                    tv_user_gender.setText("NAM");
                            } else {
                                tv_user_gender.setText("NỮ");
                            }
                        } catch (Exception $error) {

                        }
                    }
                });

        newsOfUserAdapter = new NewsOfUserAdaper(ProfileFragment.this.getActivity(), newsOfUserList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProfileFragment.this.getContext());
        rv_newsOfUser.setLayoutManager(layoutManager);
        rv_newsOfUser.addItemDecoration(new DividerItemDecoration(ProfileFragment.this.getContext(), LinearLayoutManager.VERTICAL));
        rv_newsOfUser.setAdapter(newsOfUserAdapter);
        listUsers();
        return view;

    }

    private void listUsers() {
        String url = url_api + "/news-of-user/" + userSharedPreferences.getInt("user_id", 0);
        System.out.println("User news: " + url);
        Ion.with(ProfileFragment.this.getActivity())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            for(int i = 0; i < result.size(); i++) {
                                System.out.println("user new " + result.size());
                                JsonObject us = result.get(i).getAsJsonObject();
                                NewsModel news = new NewsModel();
                                news.setNews_ID(us.get("News_ID").getAsInt());
                                news.setUser_ID(us.get("User_ID").getAsInt());
                                news.setNews_Detail(us.get("News_Detail").getAsString());
                                news.setNews_Image(us.get("News_Image").getAsString());
                                news.setNews_time_upload(us.get("News_time_upload").getAsString());
                                newsOfUserList.add(news);
                            }
                            newsOfUserAdapter.notifyDataSetChanged();
                        } catch(Exception error) {
                            System.out.println(error);
                            Toast.makeText(ProfileFragment.this.getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
