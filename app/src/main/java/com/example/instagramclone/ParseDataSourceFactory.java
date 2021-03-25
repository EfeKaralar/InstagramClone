package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.instagramclone.models.Post;

public class ParseDataSourceFactory extends DataSource.Factory<Integer, Post> {
    @NonNull
    @Override
    public DataSource<Integer, Post> create() {
        ParsePositionalDataSource source = new ParsePositionalDataSource();
        return source;
    }
}
