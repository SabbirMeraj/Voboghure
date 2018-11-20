package com.sabbirmeraj.voboghure;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RankedPostList extends ArrayAdapter<RankedPost> {

    Activity context;
    List<RankedPost> rankedPostList;
    String rate,person,u;

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


        TextView userName= listViewItem.findViewById(R.id.username);
        TextView totalRating=listViewItem.findViewById(R.id.totalRating);
        TextView totalPerson=listViewItem.findViewById(R.id.totalPerson);
        SharedPreferences prefs= getContext().getSharedPreferences(HomeActivity.MyPREFERENCES, MODE_PRIVATE);
        u=prefs.getString("USER", null);

        RankedPost rankedPost=rankedPostList.get(position);


        rate="Total rating: "+rankedPost.getTotalRating()+ " stars";
        person="Rated by: "+rankedPost.getTotalPerson()+ " persons";
        userName.setText(u);
        totalRating.setText(rate);
        totalPerson.setText(person);

        return listViewItem;
    }
}