package com.example.instagramclone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.models.Post;
import com.parse.ParseFile;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    Context context;
    List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }
    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }
    
    // Add a list of items -- change to type used
    public void addAll(PagedList<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvUsername;
        TextView tvDate;
        ImageView ivImage;
        TextView tvDescription;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            container = itemView.findViewById(R.id.rlItemContainer);
        }

        public void bind(Post post) {
            // bind the post data to the view elements
            tvUsername.setText("@" + post.getUser().getUsername());
            tvDescription.setText(post.getDescription());
            tvDate.setText(post.getPostCreationDate());
            ParseFile image = post.getImage();

            if(image!=null){
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }

            // 1. Register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*

                    // 2. Navigate to a new activity
                    Intent i =new Intent(context, DetailActivity.class);
                    i.putExtra("post", Parcels.wrap(post));
                    Pair<View, String> p1 = Pair.create((View) tvUsername, "username");
                    Pair<View, String> p2 = Pair.create((View) ivImage, "image");
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity) context, p1, p2);
                    context.startActivity(i, options.toBundle());

                     */
                }
            });
        }
    }
}
