package com.prekshashukla.firebaseapp.model;

import com.prekshashukla.firebaseapp.activities.FeedActivity;
import com.prekshashukla.firebaseapp.adapter.FeedAdapter;

/**
 * Model class to display list of feeds using {@link FeedAdapter}
 * * in {@link FeedActivity} page
 */

public class Feed {

    private String userImage;
    private String userName;
    private String status;
    private String feedImage;

    //empty constructor to be recognized by Firebase database
    public Feed() {
    }


    public Feed(String userImage, String userName, String status, String feedImage) {
        this.userImage = userImage;
        this.userName = userName;
        this.status = status;
        this.feedImage = feedImage;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeedImage() {
        return feedImage;
    }

    public void setFeedImage(String feedImage) {
        this.feedImage = feedImage;
    }
}
