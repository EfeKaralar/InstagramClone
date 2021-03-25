package com.example.instagramclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.EndlessRecyclerViewScrollListener;
import com.example.instagramclone.ParseDataSourceFactory;
import com.example.instagramclone.Post;
import com.example.instagramclone.PostsAdapter;
import com.example.instagramclone.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    public static final String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;

   // LiveData<PagedList<Post>> posts;
   // Store a member variable for the listener
   EndlessRecyclerViewScrollListener scrollListener;

    public PostsFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        allPosts = new ArrayList<>();
        Log.d(TAG, "onViewCreated: ");

        // Steps to use the Recycler View
        // 0. create layout for one row in the list
        // 1. create the adapter
        adapter = new PostsAdapter(getContext(), allPosts);
        // 2. create the data source
        // 3. set the adapter on recycler view
        rvPosts.setAdapter(adapter);
        // 4. set the layout manager on recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(layoutManager);
        queryPosts();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                allPosts = new ArrayList<>();
                adapter = new PostsAdapter(getContext(), allPosts);
                rvPosts.setAdapter(adapter);
                rvPosts.setLayoutManager(layoutManager);
                // Make sure you call swipeContainer.setRefreshing(false)
                swipeContainer.setRefreshing(false);
                // once the network request has completed successfully.
                queryPosts();
            }
        });
        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        /*
        // initial page size to fetch can be configured here
        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder().setEnablePlaceholders(true)
                        .setPrefetchDistance(10)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(10).build();

        ParseDataSourceFactory sourceFactory = new ParseDataSourceFactory();

        posts = new LivePagedListBuilder<>(sourceFactory, pagedListConfig).build();

        posts.observe(this, new Observer<PagedList<Post>>() {
            @Override
            public void onChanged(@Nullable PagedList<Post> tweets) {
                adapter.addAll(tweets);
            }
        });

         */

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i(TAG, "onLoadMore: " + page);
                int lastPostPos = page*20;
                loadMoreData(lastPostPos);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);
    }

    private void loadMoreData(int lastPostPos) {
        // 1. Send an API request to retrieve appropriate paginated data
        loadNextPage(lastPostPos);
        // 2. Deserialize and construct new model objects from the API response
        // 3. Append the new data objects to the existing set of items inside the array of items
        // 4. Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    private void loadNextPage(int lastPostPos) {
        int limit = lastPostPos+20;
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(limit);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Issue with getting posts", e);
                    e.printStackTrace();
                    return;
                }
                for(Post post: posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                allPosts.clear();
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }


    public void queryPosts() {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Issue with getting posts", e);
                    e.printStackTrace();
                    return;
                }
                for(Post post: posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}