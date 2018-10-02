package com.sabbirmeraj.voboghure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    static String message;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email= (EditText) findViewById(R.id.email);
    }


    public void sendMessage(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        String msg= email.getText().toString();
        intent.putExtra(message, msg);
        startActivity(intent);
    }
}
