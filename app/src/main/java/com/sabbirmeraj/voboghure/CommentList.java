package com.sabbirmeraj.voboghure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentList extends ArrayAdapter<Comments> {
    Activity context;
    List<Comments> commentList;


    String name;

    public CommentList(Activity context, List<Comments> commentList){
        super(context,R.layout.activity_commentlist, commentList);
        this.context=context;
        this.commentList=commentList;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_commentlist, parent, false);

        TextView user = listViewItem.findViewById(R.id.userName);
        TextView userComment = listViewItem.findViewById(R.id.comment);

        Comments comment = (Comments) commentList.get(position);


        user.setText(getUserName(comment.getUserID()));
        //Toast.makeText(context, getUserName(comment.getUserID()),Toast.LENGTH_SHORT).show();
        userComment.setText(comment.getComment());

        return listViewItem;

    }

    private String getUserName(String userID){
        DatabaseReference rootReference= FirebaseDatabase.getInstance().getReference("users");
        ;
        rootReference.child(userID).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return name;
    }
}