/*
package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.models.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TextView tvUsername;
    TextView tvDate;
    ImageView ivImage;
    TextView tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvUsername = findViewById(R.id.tvUsernameDET);
        tvDate = findViewById(R.id.tvDateDET);
        ivImage = findViewById(R.id.ivImageDET);
        tvDesc = findViewById(R.id.tvDescriptionDET);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvUsername.setText(post.getUser().getUsername());
        tvDate.setText(post.getPostCreationDate());
        Glide.with(DetailActivity.this).load(post.getImage().getUrl()).into(ivImage);
        tvDesc.setText(post.getDescription());

    }
}


 */