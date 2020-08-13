package com.example.finbuproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.finbuproject.MainActivity.url_api;

public class AddNewsActivity extends AppCompatActivity {
    TextView tv_user_fullname;
    EditText et_news;
    ImageView image_news;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageFilePatch;
    private Bitmap imageToStorage = null;
    SharedPreferences userSharedPreferences;
    private String url_add_news = url_api + "/add-news";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tv_user_fullname = findViewById(R.id.tv_user_fullname);
        userSharedPreferences = getSharedPreferences("user_info", MODE_WORLD_READABLE);
        tv_user_fullname.setText(userSharedPreferences.getString("user_firstname", "") + " " +userSharedPreferences.getString("user_name", ""));

        et_news = findViewById(R.id.et_news);
        image_news = findViewById(R.id.image_news);

    }

    public void chooseImage (View objectView) {
        try{
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");
            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            imageFilePatch = data.getData();
            imageToStorage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePatch);
//            SpannableStringBuilder builder = new SpannableStringBuilder(et_news.getText());
//            builder.setSpan(new ImageSpan(imageToStorage), et_news.length(), et_news.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            image_news.setImageURI(imageFilePatch);
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void btncancel(View view) {
        finish();
    }

    public void UploadNews (View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_add_news, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(url_add_news);
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("insert");
                    Toast.makeText(AddNewsActivity.this, "Đã đăng thành công", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddNewsActivity.this, MainActivity.class);
                    startActivity(intent);
                    System.out.println("Response: " + Response);
                }
                catch (Exception error) {
                    System.out.println("catch: " + error.getMessage());
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
                Map<String,String> params = new HashMap<>();
                int user_id = userSharedPreferences.getInt("user_id", 0);
                userSharedPreferences = getSharedPreferences("user_info", MODE_WORLD_READABLE);
                params.put("User_ID", String.valueOf(user_id));
                params.put("News_Detail", et_news.getText().toString());
                params.put("news_image",imageToString(imageToStorage));
                return params;
            }
        };
        Singleton.getInstance(AddNewsActivity.this).addToRequestQueue(stringRequest);
    }
    private String imageToString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            System.out.println(url_add_news);
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
       return "";
    }
}