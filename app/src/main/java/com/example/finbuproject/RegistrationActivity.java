package com.example.finbuproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Calendar;

import static com.example.finbuproject.MainActivity.url_api;

public class RegistrationActivity extends AppCompatActivity {
    DatePickerDialog date_picker_dialog;
    EditText et_first_name, et_name, et_DoB, et_cellphone, et_email, et_password, et_confirm_password;
    Button btn_registration;
    RadioButton radio_btn_male, radio_btn_female;
    RadioGroup radio_group_gender;
    int gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        radio_group_gender = findViewById(R.id.radio_group_gender);

        radio_btn_male = findViewById(R.id.radio_btn_male);
        radio_btn_female = findViewById(R.id.radio_btn_female);
        et_first_name = findViewById(R.id.et_first_name);
        et_name = findViewById(R.id.et_name);
        et_cellphone = findViewById(R.id.et_celphone);
        String cellphone = et_cellphone.getText().toString();
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        String confirm_password = et_confirm_password.getText().toString();

        et_DoB = findViewById(R.id.tv_DoB);
        et_DoB.setInputType(InputType.TYPE_NULL);
        et_DoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                date_picker_dialog = new DatePickerDialog(RegistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                et_DoB.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                date_picker_dialog.show();
            }
        });

        btn_registration = findViewById(R.id.btn_registration);
        btn_registration.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (et_first_name.getText().toString().equals("") || et_name.getText().toString().equals("") || et_DoB.getText().toString().equals("")
                        || et_cellphone.getText().toString().equals("") || et_email.getText().toString().equals("")
                            || et_password.getText().toString().equals("") || et_confirm_password.getText().toString().equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                    alertDialog.setTitle("Cảnh báo!");
                    alertDialog.setMessage("Vui lòng điền đầy đủ thông tin của bạn. \nXin cảm ơn!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (et_first_name.getText().toString().equals("")) {
                                        et_first_name.setError("Nhập họ");
                                    }
                                    if (et_name.getText().toString().equals("")) {
                                        et_name.setError("Nhập tên");
                                    }
                                    if (radio_group_gender.getCheckedRadioButtonId()<=0) {
                                        radio_btn_female.setError("Chọn giới tính");
                                    }
                                    if (et_DoB.getText().toString().equals("")) {
                                        et_DoB.setError("Chọn ngày sinh");
                                    }
                                    if (et_cellphone.getText().toString().equals("")) {
                                        et_cellphone.setError("Nhập số điện thoại");
                                    }
                                    if (et_email.getText().toString().equals("")) {
                                        et_email.setError("Nhập email");
                                    }
                                    if (et_password.getText().toString().equals("")) {
                                        et_password.setError("Nhập mật khẩu");
                                    }
                                    if (et_confirm_password.getText().toString().equals("")) {
                                        et_confirm_password.setError("Xác nhận mật khẩu");
                                    }
                                }
                            });
                    alertDialog.show();
                } else {
                    RegistrationValidation(et_first_name.getText().toString(), et_name.getText().toString(), String.valueOf(gender), et_DoB.getText().toString(),
                            et_cellphone.getText().toString(), et_email.getText().toString(), et_password.getText().toString());
                }
            }
        });
    }

    public int onRBGenderClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_btn_male:
                if (checked) {
                    radio_btn_male.setTextColor(Color.parseColor("#000000"));
                    radio_btn_female.setTextColor(Color.parseColor("#aaaaaa"));
                    radio_btn_female.setError(null);
                    gender = 1;
                }
                    break;
            case R.id.radio_btn_female:
                if (checked) {
                    radio_btn_female.setTextColor(Color.parseColor("#000000"));
                    radio_btn_male.setTextColor(Color.parseColor("#aaaaaa"));
                    radio_btn_female.setError(null);
                    gender = 0;
                }
                    break;
        }
        return gender;
    }

    private void RegistrationValidation(String first_name, String name, String gender, String DoB, String cellphone, String email, String password) {
        String url = url_api + "/users";
        System.out.println(first_name);
        System.out.println(name);
        System.out.println(gender);
        System.out.println(DoB);
        System.out.println(cellphone);
        System.out.println(email);
        System.out.println("p" + password);


        Ion.with(RegistrationActivity.this)
                .load("POST", url)
                .setBodyParameter("User_Name", name)
                .setBodyParameter("User_Firstname", first_name)
                .setBodyParameter("Gender", gender)
                .setBodyParameter("User_DoB", DoB)
                .setBodyParameter("Cellphone", cellphone)
                .setBodyParameter("Email", email)
                .setBodyParameter("Password", password)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            String rs = result.get("insert").getAsString();
                            if (rs.equals("OK")) {
                                AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                                alertDialog.setTitle("Chúc mừng!");
                                alertDialog.setMessage("Bạn đã đăng kí tài khoản thành công!");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đăng nhập ngay!", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                alertDialog.show();
                            }
                            else Toast.makeText(RegistrationActivity.this, rs, Toast.LENGTH_SHORT).show();

                        } catch (Exception error) {
                            Toast.makeText(RegistrationActivity.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void backToLogin(View view) {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
