package com.sabbirmeraj.voboghure;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends NavigationDrawerActivity {


    DatabaseReference rootReference;
    ListView listView;
    List<Comments> commentList;
    EditText uComment;
    String userID,postID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout=(FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_commnet , contentFrameLayout);
        Intent i=getIntent();

        uComment=findViewById(R.id.usrComment);
       userID=i.getStringExtra("USERID");
       postID=i.getStringExtra("POSTID");


        rootReference= FirebaseDatabase.getInstance().getReference("Comments");

        listView=findViewById(R.id.commentList);
        commentList=new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();


        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();

                for(DataSnapshot commentSnapshot: dataSnapshot.getChildren()){
                    Comments comment=commentSnapshot.getValue(Comments.class);

                    if(comment.getPostID().equals(postID))
                    commentList.add(comment);
                }
                CommentList adapter= new CommentList(CommentActivity.this, commentList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void submitComment(View view){
        final String userComment=uComment.getText().toString();
        String cid=rootReference.push().getKey();
        Comments commentToBeAdded= new Comments(cid, postID,userID,userComment);

        rootReference.child(cid).setValue(commentToBeAdded)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Comment has been added", Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(getApplicationContext(), CommentActivity.class);
                            i.putExtra("POSTID",postID);
                            i.putExtra("USERID",userID);
                            startActivity(i);
                        }
                    }
                });
    }

}
