package com.sabbirmeraj.voboghure;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PostList extends ArrayAdapter<Post> {
    Activity context;
    List<Post> postList;

    public PostList(Activity context, List<Post> postList){
        super(context,R.layout.activity_postlist, postList);
        this.context=context;
        this.postList=postList;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.activity_postlist,parent,false);


        TextView placeName=listViewItem.findViewById(R.id.placeName);
        TextView budget=listViewItem.findViewById(R.id.budget);
        TextView duration=listViewItem.findViewById(R.id.duration);
        TextView description=listViewItem.findViewById(R.id.description);

        Post post=postList.get(position);


        placeName.setText(post.getPlace());
        budget.setText(post.getBudget()+"");
        duration.setText(post.getDuration()+"");
        description.setText(post.getDescription());
        return listViewItem;
    }
}
