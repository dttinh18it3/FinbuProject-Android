package com.example.finbuproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finbuproject.R;
import com.example.finbuproject.models.user;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    List<user> usersList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_user_name;
        Button btn_edit, btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }

    public UserAdapter(Context context1, List<user> usersList1) {
        this.context = context1;
        this.usersList = usersList1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        user u = usersList.get(i);
        viewHolder.tv_user_name.setText(u.getUser_Name());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
