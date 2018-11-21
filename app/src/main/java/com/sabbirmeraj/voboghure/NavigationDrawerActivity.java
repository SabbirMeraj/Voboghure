package com.sabbirmeraj.voboghure;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NavigationDrawerActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = this.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);



        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                                                             @Override
                                                             public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                                 int id=menuItem.getItemId();
                                                                 switch(id){
                                                                     case R.id.postInfo:
                                                                         Intent newActivity0 = new Intent(NavigationDrawerActivity.this,PostActivity.class);
                                                                         startActivity(newActivity0);
                                                                         break;


                                                                     case R.id.timeline:
                                                                         Intent newActivity1 = new Intent(NavigationDrawerActivity.this,TimelineActivity.class);
                                                                         startActivity(newActivity1);
                                                                         break;
                                                                     case R.id.search:
                                                                         Intent newActivity2 = new Intent(NavigationDrawerActivity.this, SearchActivity.class);
                                                                         startActivity(newActivity2);
                                                                         break;
                                                                     case R.id.contribution:
                                                                         Intent newActivity3 = new Intent(NavigationDrawerActivity.this,ContributeActivity.class);
                                                                         startActivity(newActivity3);
                                                                         break;

                                                                     case R.id.suggestion:
                                                                         Intent newActivity4 = new Intent(NavigationDrawerActivity.this,SuggestionActivity.class);
                                                                         startActivity(newActivity4);
                                                                         break;
                                                                     case R.id.hallOfFame:
                                                                         Intent newActivity5 = new Intent(NavigationDrawerActivity.this,HallOfFameActivity.class);
                                                                         startActivity(newActivity5);
                                                                         break;


                                                                 }

                                                                 return false;
                                                             }
                                                         }

        );
        /*

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
*/
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    /*
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == R.id.postInfo){
                Intent newActivity0 = new Intent(HomeActivity.this,PostActivity.class);
                startActivity(newActivity0);

            }
            return super.onOptionsItemSelected(item);
        }

    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    public void showMenu(View view){

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.activity_popupwindow, null);

        // Creating the PopupWindow
        changeStatusPopUp = new PopupWindow(context);
        changeStatusPopUp.setContentView(layout);
        changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setFocusable(true);



        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = -20;
        int OFFSET_Y = 50;

        //Clear the default translucent background
        changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeStatusPopUp.showAtLocation(layout, Gravity.NO_GRAVITY,   OFFSET_X,OFFSET_Y);

    }
  */  /*



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
                        Intent newActivity2 = new Intent(HomeActivity.this, SearchActivity.class);
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
