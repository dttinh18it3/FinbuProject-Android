package com.example.finbuproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static com.example.finbuproject.MainActivity.url_api;

public class LoginActivity extends AppCompatActivity {
    private EditText et_login_email, et_login_password;
    private Button btnLogin,btnRegistration, btnLoginWithFacebook, btnLoginWithGmail;
    private SharedPreferences userSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_login_email = findViewById(R.id.et_login_email);
        et_login_password = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistration = findViewById(R.id.btnRegistration);
        btnLoginWithFacebook = findViewById(R.id.btnLoginWithFacebook);
        btnLoginWithGmail = findViewById(R.id.btnLoginWithGmail);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_login_email.getText().toString().equals("") || et_login_password.getText().toString().equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Cảnh báo!");
                    alertDialog.setMessage("Xin vui lòng điền đủ thông tin tài khoản!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (et_login_email.getText().toString().equals("")) {
                                        et_login_email.setError("Nhập email!");
                                    }
                                    if (et_login_password.getText().toString().equals("")) {
                                        et_login_password.setError("Nhập mật khẩu!");
                                    }
                                }
                            });
                    alertDialog.show();
                } else {
                    LoginValidation(et_login_email.getText().toString(), et_login_password.getText().toString());
                }
            }
        });

    }

    private void LoginValidation(String email, final String password) {
        String url = url_api + "/user-login/" + email;
        System.out.println(url);
        Ion.with(LoginActivity.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            if (result.get("Password").getAsString().equals(password)) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                userSharedPreferences = getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                SharedPreferences.Editor user_editor = userSharedPreferences.edit();
                                user_editor.putInt("user_id", result.get("User_ID").getAsInt());
                                user_editor.putString("user_firstname", result.get("User_Firstname").getAsString());
                                user_editor.putString("user_name", result.get("User_Name").getAsString());
                                user_editor.putString("user_email", result.get("Email").getAsString());
                                user_editor.apply();

                                startActivity(intent);
                                finish();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                alertDialog.setTitle("Cảnh báo!");
                                alertDialog.setMessage("Mật khẩu không chính xác. \nVui lòng nhập lại!");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        } catch(Exception error) {
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle("Cảnh báo!");
                            alertDialog.setMessage("Tài khoản không tồn tại. \nVui lòng nhập lại!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                });
    }

}
