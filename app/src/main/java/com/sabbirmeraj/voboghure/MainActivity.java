package com.sabbirmeraj.voboghure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.sabbirmeraj.voboghure.R.id.link;

public class MainActivity extends AppCompatActivity {
    static String message;
    EditText email,password;

    FirebaseAuth auth;
    TextView link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email= (EditText) findViewById(R.id.email);
        password=findViewById(R.id.password);
        auth= FirebaseAuth.getInstance();
        //link=  (TextView) findViewById(R.id.link);
    }

    public void login(View view){
        String e=email.getText().toString();
        String p=password.getText().toString();

        if(e.equals("") && p.equals(" ")){
            Toast.makeText(getApplicationContext(),"Fill up both email and password", Toast.LENGTH_SHORT).show();
        }

        else{
            auth.signInWithEmailAndPassword(e,p)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){



                                Intent i=new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(i);
                            }

                            else{
                                Toast.makeText(getApplicationContext(),"Invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }



    public void sendMessage(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        String msg= email.getText().toString();
        intent.putExtra(message, msg);
        startActivity(intent);
    }




    public void onClick(View view){
        Intent intent= new Intent(this, RegistrationActivity.class);
        String msg= email.getText().toString();
        intent.putExtra(message, msg);
        startActivity(intent);
    }


}
