package com.example.hotgearvn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotgearvn.MainActivity;
import com.example.hotgearvn.R;
import com.example.hotgearvn.dao.UsersDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.utils.HandleEvent;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etEmail, etRepassword, etPhone, etFullname;
    Button btnSignup;
    private final String REQUIRE = "Require";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etFullname = (EditText) findViewById(R.id.etFullName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRepassword = (EditText) findViewById(R.id.etRepassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInput()) {
                    return;
                } else {
                    String Username = etUsername.getText().toString();
                    String Email = etEmail.getText().toString();
                    String Fullname = etFullname.getText().toString();
                    String Phone = etPhone.getText().toString();
                    String Password = etPassword.getText().toString();
                    String Repassword = etRepassword.getText().toString();
                    //Do insert operation
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    String phonePattern = "^[+]?[0-9]{10,13}$";
                    HotGearDatabase database = HotGearDatabase.getDatabase(RegisterActivity.this);
                    UsersDao usersDao = database.usersDao();
                    Users existedUser = (Users) usersDao.getUser(Username);
                    Log.d("repassword", Repassword);
                    Log.d("password", Password);
                    if (existedUser != null) {
                        Log.d("existed", existedUser.toString());
                        Toast.makeText(RegisterActivity.this, "Username existed!", Toast.LENGTH_SHORT).show();
                    } else if (!Email.matches(emailPattern)) {
                        Toast.makeText(RegisterActivity.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                    } else if (!Phone.matches(phonePattern)) {
                        Toast.makeText(RegisterActivity.this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
                    } else if (!Repassword.equals(Password)) {
                        Toast.makeText(RegisterActivity.this, "Confirm password do not match!", Toast.LENGTH_SHORT).show();
                    } else {
                        Users user = new Users(Username, Password, Email, Fullname, Phone);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                usersDao.add(user);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }).start();
                    }
                }
            }
        });
    }

    private boolean checkInput() {
        //UserName
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(etFullname.getText().toString())) {
            etFullname.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            etPhone.setError(REQUIRE);
            return false;
        }
        //Password
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(etRepassword.getText().toString())) {
            etRepassword.setError(REQUIRE);
            return false;
        }
        //valid
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showPopUp(View v) {
        HandleEvent.showPopUp(v, this);
    }

    public void login_logout(View view) {
        HandleEvent.onClickLogin_Logout(view, this);
    }
}