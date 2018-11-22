package com.sabbirmeraj.voboghure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity extends AppCompatActivity {


    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        auth=FirebaseAuth.getInstance();
        auth.signOut();
        SharedPreferences pref=getApplicationContext().getSharedPreferences(HomeActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        pref.edit().clear().apply();
        Intent i= new Intent(LogoutActivity.this, MainActivity.class);
        startActivity(i);
    }
}
