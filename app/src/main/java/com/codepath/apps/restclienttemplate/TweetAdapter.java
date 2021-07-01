package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;

    // Pass in the context and list of tweets
    public TweetAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // Inflate a layout for each row
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder (view);
    }

    // Bind values based on position of element
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);
        // Bind the tweet with ViewHolder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    // Define a ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvRelativeTimeAgo;
        ImageView ivTweetImage;
        ImageView ivReply;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvRelativeTimeAgo = itemView.findViewById(R.id.tvRelativeTimeAgo);
            ivTweetImage = itemView.findViewById(R.id.ivTweetImage);
            ivReply = itemView.findViewById(R.id.ivReply);

            ivReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ComposeActivity.class);
                    intent.putExtra("username", tvScreenName.getText().toString());
                    intent.putExtra("hasReplied", true);
                    context.startActivity(intent);
                }
            });
            itemView.setOnClickListener(this);
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvRelativeTimeAgo.setText(tweet.createdAt);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);

            if (tweet.tweetImageURL != null)
                Glide.with(context).load(tweet.tweetImageURL).into(ivTweetImage);

            ivReply.setImageResource(R.drawable.reply_arrow);
        }

        // Click on a row in Recycle view to view tweet details
        @Override
        public void onClick(View v) {
            // Get position of item
            int position = getAdapterPosition();
            // Check if position is valid
            if (position != RecyclerView.NO_POSITION) {
                // Get tweet at position
                Tweet tweet = tweets.get(position);
                // Create intent for new activity
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                // Serialize tweet using parceler
                intent.putExtra("currentTweet", Parcels.wrap(tweet));
                // Show activity
                context.startActivity(intent);
            }
        }
    }
}
