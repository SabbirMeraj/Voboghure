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

public class RankedPostList extends ArrayAdapter<RankedPost> {

    Activity context;
    List<RankedPost> rankedPostList;

    public RankedPostList(Activity context, List<RankedPost> rankedPostList){
        super(context,R.layout.activity_rankedpostlist, rankedPostList);
        this.context=context;
        this.rankedPostList=rankedPostList;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.activity_rankedpostlist,parent,false);


        TextView userName= listViewItem.findViewById(R.id.userName);
        TextView totalRating=listViewItem.findViewById(R.id.totalRating);
        TextView totalPerson=listViewItem.findViewById(R.id.totalPerson);


        RankedPost rankedPost=rankedPostList.get(position);


        userName.setText(rankedPost.getUserID());
        totalRating.setText(rankedPost.getTotalRating());
        totalPerson.setText(rankedPost.getTotalPerson());

        return listViewItem;
    }
}