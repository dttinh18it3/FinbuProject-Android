package com.example.finbuproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDetailActivity extends AppCompatActivity {
    TextView tv_blogger_fullname, tv_caption, tv_like_amount;
    ImageView image_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        tv_blogger_fullname = findViewById(R.id.tv_blogger_fullname);
        tv_caption = findViewById(R.id.tv_caption);
        tv_like_amount = findViewById(R.id.tv_like_amount);
        image_detail = findViewById(R.id.image_detail);

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user_name");
        tv_blogger_fullname.setText(user_name);
        String news_detail = intent.getStringExtra("news_detail");
        tv_caption.setText(news_detail);
        String news_like_amount = intent.getStringExtra("news_like_amount");
        tv_like_amount.setText(news_like_amount);
        byte[] image_bytes = intent.getByteArrayExtra("img_news_bytes");
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length);
        image_detail.setImageBitmap(bitmap);
    }

    public void backToHome(View view) {
        finish();
//        Intent intentHome = new Intent(NewsDetailActivity.this, MainActivity.class);
//        startActivity(intentHome);
    }
}