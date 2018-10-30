package com.prekshashukla.firebaseapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prekshashukla.firebaseapp.R;

/**
 * Activity class for user profile page
 * fetches user details from Firebase database
 * on signout button click, uses Firebase signout and ends session of recent user
 * and navigates to {@link MainActivity}
 */

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivUserPhoto;
    private TextView tvUserName, tvUserEmail;
    private Button buttonSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing views
        ivUserPhoto = (ImageView) findViewById(R.id.activity_profile_image);
        tvUserName = (TextView) findViewById(R.id.activity_profile_username);
        tvUserEmail = (TextView) findViewById(R.id.activity_profile_email);
        buttonSignOut = (Button) findViewById(R.id.activity_profile_signout);

        //on click listener for signout button
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //setting values of recent logged in user details from firebase database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
                String name = user.getDisplayName();
                String email = user.getEmail();
                Uri photoUri = user.getPhotoUrl();

            tvUserEmail.setText(email);
            tvUserName.setText(name);
                Glide.with(getApplicationContext())
                        .load(user.getPhotoUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivUserPhoto);
            ivUserPhoto.setImageURI(photoUri);

        }

    }

}
