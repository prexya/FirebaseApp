package com.prekshashukla.firebaseapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.prekshashukla.firebaseapp.R;
import com.prekshashukla.firebaseapp.model.Feed;
import com.prekshashukla.firebaseapp.activities.FeedActivity;

import java.util.List;

/**
 * Adapter class to display list of {@link Feed}
 * in {@link FeedActivity} page
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<Feed> feedList;
    private Context context;
    private LayoutInflater layoutInflater;

    public FeedAdapter(Context context, List<Feed> feeds) {
        this.context = context;
        this.feedList = feeds;
        layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserName, tvStatus;
        PorterShapeImageView ivUserPhoto;
        ImageView ivFeedImage;


        public ViewHolder(View view) {
            super(view);

            ivUserPhoto = (PorterShapeImageView) view.findViewById(R.id.item_feed_user_image);
            tvUserName = (TextView) view.findViewById(R.id.item_feed_name);
            tvStatus = (TextView) view.findViewById(R.id.item_feed_status);
            ivFeedImage = (ImageView) view.findViewById(R.id.item_feed_image);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Feed feed = feedList.get(position);
        Glide.with(context)
                .load(feed.getUserImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivUserPhoto);
        holder.tvUserName.setText(feed.getUserName());
        holder.tvStatus.setText(feed.getStatus());
        Glide.with(context)
                .load(feed.getFeedImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .fitCenter()
                .into(holder.ivFeedImage);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }
}
