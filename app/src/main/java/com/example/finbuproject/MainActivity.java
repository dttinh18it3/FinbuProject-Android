package com.example.finbuproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.finbuproject.ui.BottomNavigationBehavior;

public class MainActivity extends AppCompatActivity {
    public static String url_project = "http://192.168.43.130/Finbuproject/";
    public static String url_api = url_project+ "public/api";
    public static String url_image_storage = url_project+ "storage/app/uploads/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        Hide/show bottom navigation
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            if(item.getItemId() == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();
            }
            if(item.getItemId() == R.id.nav_notification) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new NotificationFragment()).commit();
            }
            if(item.getItemId() == R.id.nav_message) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MessageFragment()).commit();
            }
            if(item.getItemId() == R.id.nav_profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ProfileFragment()).commit();
            }
            if(item.getItemId() == R.id.nav_menu) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PhoneFragment()).commit();
//                Intent intent_setting = new Intent(MainActivity.this, SettingActivity.class);
//                startActivity(intent_setting);
            }
            return true;
        }
    };

}
