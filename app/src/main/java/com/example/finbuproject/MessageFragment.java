package com.example.finbuproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finbuproject.adapters.UserAdapter;
import com.example.finbuproject.models.user;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {
    RecyclerView rv_users;
    List<user> usersList;
    UserAdapter userAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).SetActionBarTitle("Tin nhắn");
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        rv_users = view.findViewById(R.id.rv_users);

        usersList = new ArrayList<>();

        userAdapter = new UserAdapter(MessageFragment.this.getActivity(), usersList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MessageFragment.this.getActivity());
        rv_users.setLayoutManager(layoutManager);
        rv_users.setAdapter(userAdapter);

        listUsers();
        return view;
    }

    private void listUsers() {
//        String url = "http://192.168.43.130/Finbu/public/api/users";
        String url = "http://192.168.42.12/Finbu/public/api/users";
        Ion.with(MessageFragment.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            for(int i = 0; i < result.size(); i++) {
                                JsonObject us = result.get(i).getAsJsonObject();
                                user u = new user();
                                u.setUser_ID(us.get("User_ID").getAsInt());
                                u.setUser_Name(us.get("User_Name").getAsString());
                                u.setGender(us.get("Gender").getAsInt());
//                                u.setUser_DoB(us.get("User_DoB").getAsString());
                                u.setCellphone(us.get("Cellphone").getAsString());
                                u.setAddress(us.get("Address").getAsString());
                                u.setEmail(us.get("Email").getAsString());
                                u.setPassword(us.get("Password").getAsString());
//                                u.setRegis_Date(us.get("Regis_Date").getAsString());
//                                System.out.println(u.getUser_ID());
                                usersList.add(u);
                            }

                            userAdapter.notifyDataSetChanged();
                        } catch(Exception error) {
                            Toast.makeText(MessageFragment.this.getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
