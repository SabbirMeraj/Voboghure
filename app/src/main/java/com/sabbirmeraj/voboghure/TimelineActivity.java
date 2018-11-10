package com.sabbirmeraj.voboghure;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {


    DatabaseReference rootReference;
    ListView listView;
    List<Post> postList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        rootReference=FirebaseDatabase.getInstance().getReference("posts");
        listView=findViewById(R.id.postList);
        postList=new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();


        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Post post=postSnapshot.getValue(Post.class);


                    postList.add(post);
                }
                PostList adapter= new PostList(TimelineActivity.this, postList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}