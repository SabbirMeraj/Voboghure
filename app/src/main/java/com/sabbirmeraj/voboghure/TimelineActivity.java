package com.sabbirmeraj.voboghure;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends NavigationDrawerActivity {


    DatabaseReference rootReference;
    ListView listView;
    List<Post> postList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout=(FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_timeline , contentFrameLayout);
      //  recyclerView= findViewById(R.id.postList);
    //    recyclerView.setHasFixedSize(true);
      //  recyclerView.setLayoutManager(new LinearLayoutManager(this));



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

/*
    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(rootReference, Post.class)
                        .build();
        FirebaseRecyclerAdapter<Post,PostViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Post,PostViewHolder>(
              options

        ) {


            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {
                    holder.setPlaceName(model.getPlace());
                    holder.setBudget(model.getBudget()+"");
                    holder.setDuraion(model.getDuration()+"");
                    holder.setDescription(model.getDescription());

            }

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public PostViewHolder(View itemView){
            super(itemView);
            mView=itemView;
        }

        public void setPlaceName(String placeName){
            TextView place=mView.findViewById(R.id.placeName);
            place.setText(placeName);
        }

        public void setBudget(String budget){
            TextView budgetView=mView.findViewById(R.id.budget);
            budgetView.setText(budget);
        }
        public void setDuraion(String duration){
            TextView durationView=mView.findViewById(R.id.duration);
            durationView.setText(duration);
        }


        public void setDescription(String description){
            TextView descriptionView=mView.findViewById(R.id.description);
            descriptionView.setText(description);
        }

    }*/
}