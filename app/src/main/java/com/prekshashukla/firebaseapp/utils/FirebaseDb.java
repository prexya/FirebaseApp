package com.prekshashukla.firebaseapp.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by preksha.shukla on 7/3/2017.
 */

public class FirebaseDb {
    public static DatabaseReference getFeedsDbRef() {
        return FirebaseDatabase.getInstance().getReference().child("feeds");
    }
}
