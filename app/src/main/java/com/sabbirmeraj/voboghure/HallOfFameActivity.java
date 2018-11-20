package com.sabbirmeraj.voboghure;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HallOfFameActivity extends NavigationDrawerActivity {


    DatabaseReference rootReference;
    ListView listView;
    List<RankedPost> rankedPostList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout=(FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_halloffame , contentFrameLayout);


        rootReference=FirebaseDatabase.getInstance().getReference("posts");

        listView=findViewById(R.id.postList);
        rankedPostList=new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();


        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rankedPostList.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    RankedPost post=postSnapshot.getValue(RankedPost.class);


                    rankedPostList.add(post);
                }
                Collections.sort(rankedPostList);
                RankedPostList adapter= new RankedPostList(HallOfFameActivity.this, rankedPostList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}

class RankedPost implements Comparable<RankedPost>{

    String postID, userID, totalPerson, totalRating;

    public RankedPost(String postID, String userID, String totalPerson, String totalRating) {
        this.postID = postID;
        this.userID = userID;
        this.totalPerson = totalPerson;
        this.totalRating = totalRating;
    }



    public RankedPost() {
    }

    public RankedPost(String totalPerson, String totalRating) {
        this.totalPerson = totalPerson;
        this.totalRating = totalRating;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(String totalPerson) {
        this.totalPerson = totalPerson;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }


    @Override
    public int compareTo(@NonNull RankedPost o) {
        float a=Float.parseFloat(this.getTotalRating());
        float b=Float.parseFloat(o.getTotalRating());
        return Float.compare(b,a);
    }
}