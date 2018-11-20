package com.sabbirmeraj.voboghure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class PostActivity extends NavigationDrawerActivity {




    static final int GALLERY_REQUEST=1;
    ImageButton selectImage;
    EditText placeName,budget, duration,description;

    FirebaseAuth auth;
    String user;
    DatabaseReference rootReference;
    StorageReference storage;
    private Uri imageUri;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_post, contentFrameLayout);

        auth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference("posts");
        /////   storage= FirebaseStorage.getInstance().getReference();
        // System.out.println(storage.toString());
        placeName = findViewById(R.id.placeName);
        budget = findViewById(R.id.budget);
        duration = findViewById(R.id.duration);
        description = findViewById(R.id.place);
    }
        /*
        selectImage= findViewById(R.id.img);
        progress=new ProgressDialog(this);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleyIntent= new Intent(Intent.ACTION_GET_CONTENT);
                galleyIntent.setType("image/*");
                startActivityForResult(galleyIntent, GALLERY_REQUEST);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode== GALLERY_REQUEST && resultCode==RESULT_OK){
            imageUri=data.getData();
            selectImage.setImageURI(imageUri);

        }
    }



    */








    public void post(View view){
       // progress.setMessage("Posting...");
      //  progress.show();
        String place, tourDescription, value, id;
        int tourBudget, tourDuration;
     //   StorageReference filePath=storage.child("Images").child(imageUri.getLastPathSegment());

        user=auth.getCurrentUser().getUid();
        id=rootReference.push().getKey();


        place= placeName.getText().toString();
        tourDescription=description.getText().toString();
        value=budget.getText().toString();
        tourBudget=Integer.parseInt(value);
        value=duration.getText().toString();
        tourDuration=Integer.parseInt(value);


        Post postToBeAdded=new Post(id, user, place, tourBudget, tourDuration ,tourDescription);
        /*
        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().getResult();
                Toast.makeText(getApplicationContext(), downloadUrl.toString(), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }



        });
*/
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
