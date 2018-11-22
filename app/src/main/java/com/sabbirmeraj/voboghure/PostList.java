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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.List;

public class PostList extends ArrayAdapter<Post> {
    Activity context;
    List<Post> postList;
    RatingBar rb;
    float rate;
    String name;
    String pID, uid;

    FirebaseAuth auth=FirebaseAuth.getInstance();

    float f,t;
    int p;

    public PostList(Activity context, List<Post> postList){
        super(context,R.layout.activity_postlist, postList);
        this.context=context;
        this.postList=postList;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.activity_postlist,parent,false);

        final TextView postID=listViewItem.findViewById(R.id.postID);
        final TextView userID=listViewItem.findViewById(R.id.userID);
        TextView placeName=listViewItem.findViewById(R.id.placeName);
        TextView budget=listViewItem.findViewById(R.id.budget);
        TextView duration=listViewItem.findViewById(R.id.duration);
        TextView description=listViewItem.findViewById(R.id.place);
        TextView user=listViewItem.findViewById(R.id.username);

        Button button=listViewItem.findViewById(R.id.rate);
        Button commentBtn=listViewItem.findViewById(R.id.commment);
        RatingBar ratingBar=listViewItem.findViewById(R.id.ratingBar);
        Post post=postList.get(position);
        uid=auth.getCurrentUser().getUid();

        f=Float.parseFloat(post.getTotalRating());
        p=Integer.parseInt(post.getTotalPerson());
        t=f/p;
        ratingBar.setRating(t);


        user.setText(post.getUname());
        postID.setText(post.getId());
        userID.setText(post.getUserID());

        placeName.setText(post.getPlace());
        budget.setText("Budget: "+ post.getBudget()+"/=");
        duration.setText("Duration: "+ post.getDuration()+"day(s)");
        description.setText(post.getDescription());



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder popup= new AlertDialog.Builder(context);
                LayoutInflater inflater=context.getLayoutInflater();
                View rootView=inflater.inflate(R.layout.rating_layout, parent, false);
                rb=rootView.findViewById(R.id.ratingBar2);
                popup.setView(rootView);
                popup.setIcon(android.R.drawable.btn_star_big_on);
                popup.setTitle("Rate!");
                pID=postID.getText().toString();

                popup.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addRating(postID.getText().toString(),uid,rb);
                    }
                });

                popup.create();
                popup.show();
            }
        });
        /*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               showDialog();
                LinearLayout row= (LinearLayout) view.getParent();
                RatingBar ratingbar=row.findViewById(R.id.ratingBar);
                Toast.makeText(getContext(),ratingbar.getRating()+"",Toast.LENGTH_SHORT).show();
            }
        });
    */
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(context, CommentActivity.class);
              intent.putExtra("POSTID", postID.getText().toString());
              intent.putExtra("USERID",userID.getText().toString());
              context.startActivity(intent);
            }
        });

        return listViewItem;
    }

    private void addRating(String postID,String userID, RatingBar rb){
        final DatabaseReference ref;
        DatabaseReference rootReference= FirebaseDatabase.getInstance().getReference("Rating");
        String id=rootReference.push().getKey();
        rate=rb.getRating();
        Rating ratingToBeAdded=new Rating(id,postID,userID,rate);
        rootReference.child(id).setValue(ratingToBeAdded)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       Toast.makeText(context,"Thanks for your rating",Toast.LENGTH_SHORT).show();
                    }
                });



        ref=FirebaseDatabase.getInstance().getReference();
        System.out.println("YOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO: "+postID);
        ref.child("posts").child(postID).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            float rating=Float.parseFloat(dataSnapshot.child("totalRating").getValue().toString());
            int person=Integer.parseInt(dataSnapshot.child("totalPerson").getValue().toString());
            person=person+1;
            String input2=person+"";
            float total=rating+rate;
            String input=total+"";
            ref.child("posts").child(pID).child("totalRating").setValue(input)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
            ref.child("posts").child(pID).child("totalPerson").setValue(input2)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent=new Intent(context, TimelineActivity.class);
                            intent.putExtra("POSTID", pID.toString());
                            //   intent.putExtra("USERID",userID.getText().toString());
                            context.startActivity(intent);
                        }
                    });
        }



        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

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
