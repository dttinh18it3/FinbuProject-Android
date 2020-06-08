package com.example.finbuproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).SetActionBarTitle("Hồ sơ cá nhân");

        Bundle bundle_profile = getArguments();
        if (bundle_profile != null) {
            System.out.println("xxxxxx "+ bundle_profile.getString("user_fullname"));
        }

        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

//    public void User_Profile() {
//        String url = "http://192.168.31.20/Finbu/public/api/users/" + bundle.getInt("user_id");
//        Ion.with(ProfileFragment.this)
//                .load(url)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        Intent intent_profile = new Intent(ProfileFragment.this.getContext(), ProfileFragment.class);
//                        Bundle bundle_profile = new Bundle();
//                        bundle_profile.putInt("user_id", result.get("User_ID").getAsInt());
//                        bundle_profile.putString("user_fullname", result.get("User_Firstname").getAsString() + " " + result.get("User_Name").getAsString());
//                        bundle_profile.putInt("gender", result.get("Gender").getAsInt());
//                        bundle_profile.putString("user_dob", result.get("User_DoB").getAsString());
//                        bundle_profile.putString("cellphone", result.get("Cellphone").getAsString());
//                        bundle_profile.putString("address", result.get("Address").getAsString());
//                        bundle_profile.putString("email", result.get("Email").getAsString());
//                        intent_profile.putExtras(bundle_profile);
//                        startActivity(intent_profile);
//                    }
//                });
//    }
}
