package com.prekshashukla.firebaseapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.prekshashukla.firebaseapp.R;
import com.prekshashukla.firebaseapp.utils.ToastUtils;

/**
 * Activity class for user signin
 * Main page of application
 * signs in existing user of Firebase database
 * on successful login, navigates to {@link FeedActivity}
 * Also for new user, user can navigate to {@link SignupActivity}
 * to create new user in Firebase
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private TextInputEditText etEmail, etPassword;
    private Button buttonLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //initializing views
        etEmail = (TextInputEditText) findViewById(R.id.activity_main_username);
        etPassword = (TextInputEditText) findViewById(R.id.activity_main_password);
        buttonLogin = (Button) findViewById(R.id.activity_main_login);
        tvRegister = (TextView) findViewById(R.id.activity_main_register);

        //on click listener of views
        buttonLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_login:
                //user login
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(MainActivity.this, FeedActivity.class));
                                    finish();
                                } else {
                                    ToastUtils.showToast(getApplicationContext(), getResources().getString(R.string.sign_in_failed), false);
                                }
                            }
                        });

                break;

            case R.id.activity_main_register:
                //navigate to user signup page for new user
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
                break;
        }
    }

}
