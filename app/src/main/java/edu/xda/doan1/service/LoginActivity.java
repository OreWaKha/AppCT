package edu.xda.doan1.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.xda.hongtt.R;
import edu.xda.doan1.data.MyDatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUserName, txtPassWord;
    private Button btnLogin, btnRegister;
    private MyDatabaseHelper db;

    public void init() {
        txtUserName = findViewById(R.id.edUsername);
        txtPassWord = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        db = new MyDatabaseHelper(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtUserName.getText().toString();
                String passWord = txtPassWord.getText().toString();
                if (userName.equals("")) {
                    txtUserName.setError("Moi nhap email");
                    txtUserName.requestFocus();
                } else if (passWord.equals("")) {
                    txtPassWord.setError("Moi nhap mat khau");
                    txtPassWord.requestFocus();
                }
                if (db.checkLogin(userName, passWord) == true) {
                    Toast.makeText(getApplicationContext(), "Đăng nhap thanh cong ", Toast.LENGTH_SHORT).show();

                    getSharedPreferences("user_prefs", MODE_PRIVATE)
                            .edit()
                            .putString("username", userName)
                            .apply();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Email hoac mat khau khong chinh xac", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
//    boolean CheckEmail(String email) {
//        String EMAIL_PATTERN = "^[a-z]{1,}([0-9]{0,})+@nuce\\.edu\\.vn$";
//        return Pattern.matches(EMAIL_PATTERN, email);
//    }
}