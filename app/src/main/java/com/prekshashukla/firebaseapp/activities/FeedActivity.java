package com.prekshashukla.firebaseapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prekshashukla.firebaseapp.model.Feed;
import com.prekshashukla.firebaseapp.adapter.FeedAdapter;
import com.prekshashukla.firebaseapp.R;
import com.prekshashukla.firebaseapp.utils.FirebaseDb;
import com.prekshashukla.firebaseapp.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity class that displays feeds
 * fetched from database table "feed" of Firebase Realtime database
 * uses {@link FeedAdapter} to display list of {@link Feed}
 * also navigates to {@link ProfileActivity} user Profile page on button click
 */

public class FeedActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FeedAdapter feedAdapter;

    private Button buttonViewProfile;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //creating instance of Firebase Database and adding reference to database
        databaseReference = FirebaseDb.getFeedsDbRef();

        //initializing views
        buttonViewProfile = (Button) findViewById(R.id.activity_feed_view_profile_button);
        recyclerView = (RecyclerView) findViewById(R.id.activity_feed_recycler_view);

        //setting layout property of recyclerview
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        //on click listener for view profile button
        buttonViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to Profile page of recent user
                startActivity(new Intent(FeedActivity.this, ProfileActivity.class));
            }
        });

        //fetching feeds from Firebase database
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Feed> feedList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Feed feed = postSnapshot.getValue(Feed.class);
                    feedList.add(feed);
                }

                feedAdapter = new FeedAdapter(FeedActivity.this, feedList);
                recyclerView.setAdapter(feedAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ToastUtils.showToast(getApplicationContext(), getResources().getString(R.string.feed_load_fail) , false);
            }
        };
        databaseReference.addValueEventListener(postListener);
    }

}
