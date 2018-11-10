package com.sabbirmeraj.voboghure;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {




    EditText placeName,budget, duration,description;

    FirebaseAuth auth;
    String user;
    DatabaseReference rootReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        auth=FirebaseAuth.getInstance();
        rootReference= FirebaseDatabase.getInstance().getReference("posts");

        placeName=findViewById(R.id.placeName);
        budget=findViewById(R.id.budget);
        duration=findViewById(R.id.duration);
        description=findViewById(R.id.description);


    }


    public void post(View view){
        String place, tourDescription, value, id;
        int tourBudget, tourDuration;


        user=auth.getCurrentUser().getUid();
        id=rootReference.push().getKey();


        place= placeName.getText().toString();
        tourDescription=description.getText().toString();
        value=budget.getText().toString();
        tourBudget=Integer.parseInt(value);
        value=duration.getText().toString();
        tourDuration=Integer.parseInt(value);


        Post postToBeAdded=new Post(id, user, place, tourBudget, tourDuration ,tourDescription);

        rootReference.child(id).setValue(postToBeAdded)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Post has been added", Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(getApplicationContext(), PostActivity.class);
                            startActivity(i);
                        }
                    }
                });
    }
}
