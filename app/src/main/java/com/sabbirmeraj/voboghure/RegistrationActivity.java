package com.sabbirmeraj.voboghure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    EditText email,password,name;

    DatabaseReference rootReference;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        name=findViewById(R.id.name);

        auth=FirebaseAuth.getInstance();
        rootReference=FirebaseDatabase.getInstance().getReference("users");


        dialog=new ProgressDialog(this);
    }

    public void registerUser(View v){
        dialog.setMessage("Registering...Please wait!");
        dialog.show();

        if(email.toString().equals("") && password.toString().equals("")){
            dialog.dismiss();
            Toast.makeText(getApplicationContext(),"Users could not be created",Toast.LENGTH_SHORT).show();
        }

        else{
            String e=email.getText().toString();
            String p=password.getText().toString();

            auth.createUserWithEmailAndPassword(e,p)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                       public void onComplete(Task <AuthResult> task){
                           if(task.isSuccessful()){
                               currentUser=auth.getCurrentUser();
                               User user=new User(name.getText().toString(), email.getText().toString(),password.getText().toString());
                               rootReference.child(currentUser.getUid()).setValue(user)
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if(task.isSuccessful()){
                                                   dialog.dismiss();
                                                   Toast.makeText(getApplicationContext(),"Users registered succesfully",Toast.LENGTH_SHORT).show();
                                                   Intent i=new Intent(getApplicationContext(), HomeActivity.class);
                                                   startActivity(i);
                                               }
                                           }
                                       });

                           }
                           else{
                               dialog.dismiss();
                               Toast.makeText(getApplicationContext(),"Users could not be registered"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                           }

                       }
                    });



        }
    }

}
