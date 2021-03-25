package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class ParseDataSourceFactory extends DataSource.Factory<Integer, Post> {
    @NonNull
    @Override
    public DataSource<Integer, Post> create() {
        ParsePositionalDataSource source = new ParsePositionalDataSource();
        return source;
    }
}
