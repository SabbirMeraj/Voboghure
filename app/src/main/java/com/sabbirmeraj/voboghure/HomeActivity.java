package com.sabbirmeraj.voboghure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeActivity extends NavigationDrawerActivity {

    String[] optionArray = {"Share Your Experience","Timeline","Search","Contribute", "Suggestion",
            "Hall of Fame"};
    private DrawerLayout mDrawerLayout;
    FirebaseAuth auth;
    DatabaseReference rootReference;
    String loggedUser;
    public static final String MyPREFERENCES="MyPrefs";
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout=(FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_home, contentFrameLayout);
        sharedPref= getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        userManagement();
    }

    private void userManagement(){
        String user;
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser().getUid();
        rootReference= FirebaseDatabase.getInstance().getReference("users");
        rootReference.child(user).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loggedUser=dataSnapshot.getValue().toString();
                SharedPreferences.Editor editor= sharedPref.edit();
                editor.putString("USER", loggedUser);
                editor.commit();
                Toast.makeText(getApplicationContext(),loggedUser,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
