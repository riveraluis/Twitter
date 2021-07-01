package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailsActivity extends AppCompatActivity {
    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvScreenName;
    TextView tvRelativeTimeAgo;
    ImageView ivTweetImage;
    ImageView ivReply;
    Tweet tweet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvBody = findViewById(R.id.tvBody);
        tvRelativeTimeAgo = findViewById(R.id.tvRelativeTimeAgo);
        ivTweetImage = findViewById(R.id.ivTweetImage);
        ivReply = findViewById(R.id.ivReply);

        // Unwrap tweet passed by intent
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("currentTweet"));

        // Set tweet attributes (text and images)
        tvBody.setText(tweet.body);
        tvScreenName.setText(tweet.user.screenName);
        tvRelativeTimeAgo.setText(tweet.createdAt);
        ivReply.setImageResource(R.drawable.reply_arrow);
        Glide.with(getApplicationContext()).load(tweet.user.profileImageUrl).into(ivProfileImage);
        if (tweet.tweetImageURL != null)
            Glide.with(getApplicationContext()).load(tweet.tweetImageURL).into(ivTweetImage);

        //  Implement reply to tweet functionality
        ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComposeActivity.class);
                intent.putExtra("username", tvScreenName.getText().toString());
                intent.putExtra("hasReplied", true);
                getApplicationContext().startActivity(intent);
            }
        });
    }
}
