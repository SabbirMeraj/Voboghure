package com.sabbirmeraj.voboghure;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SuggestionActivity extends NavigationDrawerActivity {

    FirebaseAuth auth;
    DatabaseReference rootReference;
    ListView listView;
    List<Post> list1,list2,postList;
    String uid, stringValue;
    Set<String> placeSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout=(FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_timeline , contentFrameLayout);

        auth=FirebaseAuth.getInstance();
        rootReference=FirebaseDatabase.getInstance().getReference("posts");

        listView=findViewById(R.id.postList);
        postList=new ArrayList<>();
        placeSet=new HashSet<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        contentBasedFiltering();

    }

    private void contentBasedFiltering() {
        rootReference=FirebaseDatabase.getInstance().getReference("userprofile");
        uid=auth.getCurrentUser().getUid();

        rootReference.orderByChild("userID").equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot upSnapshot: dataSnapshot.getChildren()){

                    if(dataSnapshot.child("criteria").getValue().toString().equals("Place")){
                        placeSet.add(dataSnapshot.child("value").getValue().toString());
                    }


                }

                findSimilarCriteria();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    void findSimilarCriteria(){
        String place;
        Iterator itr=placeSet.iterator();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("posts");

        while(itr.hasNext()){
            place= (String) itr.next();
            System.out.println(place);
            ref.orderByChild("place").equalTo(place).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    searchByDuration(dataSnapshot.child("duration").getValue().toString());
                    searchByBudget(dataSnapshot.child("budget").getValue().toString());
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    private void searchByDuration(String value) {
       DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference("posts");
       stringValue=value;
       rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    int input = Integer.parseInt(stringValue);
                    if (post.getDuration() == input)
                        postList.add(post);
                }

  
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void searchByBudget(String value) {
        DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference("posts");
        stringValue=value;
        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    int input = Integer.parseInt(stringValue);
                    int  end;
                    end=input+700;

                    if (post.getBudget() <= end)
                        postList.add(post);
                }


                PostList adapter = new PostList(SuggestionActivity.this, postList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}