package com.sabbirmeraj.voboghure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class HomeActivity extends AppCompatActivity {

    String[] optionArray = {"Share Your Experience","Timeline","Search","Contribute", "Suggestion",
            "Hall of Fame"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, R.id.label, optionArray);

        ListView listView = (ListView) findViewById(R.id.option_list);
        listView.setAdapter(adapter);







        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               // final TextView mTextView = (TextView)view;
                switch (position) {
                    case 0:
                        Intent newActivity0 = new Intent(HomeActivity.this,PostActivity.class);
                        startActivity(newActivity0);
                        break;

                    case 1:
                        Intent newActivity1 = new Intent(HomeActivity.this,TimelineActivity.class);
                        startActivity(newActivity1);
                        break;
                    case 2:
                        Intent newActivity2 = new Intent(HomeActivity.this,TimelineActivity.class);
                        startActivity(newActivity2);
                        break;
                    case 3:
                        Intent newActivity3 = new Intent(HomeActivity.this,TimelineActivity.class);
                        startActivity(newActivity3);
                        break;
                    case 4:
                        Intent newActivity4 = new Intent(HomeActivity.this,TimelineActivity.class);
                        startActivity(newActivity4);
                        break;
                    case 5:
                        Intent newActivity5 = new Intent(HomeActivity.this,TimelineActivity.class);
                        startActivity(newActivity5);
                        break;
                    case 6:
                        Intent newActivity6 = new Intent(HomeActivity.this,TimelineActivity.class);
                        startActivity(newActivity6);
                        break;
                        // Nothing do!
                }

            }
        });


    }
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.message);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

    }
    */
}
