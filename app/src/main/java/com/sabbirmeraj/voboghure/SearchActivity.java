package com.sabbirmeraj.voboghure;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends NavigationDrawerActivity {


    Spinner dropdown;
    EditText searchCriteria;
    String option,value;
    DatabaseReference rootReference;
    ListView listView;
    List<Post> postList;
    List<Hotel> hotelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout=(FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_search , contentFrameLayout);
        dropdown=findViewById(R.id.spinner1);
        searchCriteria=findViewById(R.id.criteria);


        listView=findViewById(R.id.postList);
        postList=new ArrayList<>();
        hotelList=new ArrayList<>();









        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.criteria, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                option=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }




    public void search(View view){

        value=searchCriteria.getText().toString();
        addToUserProfile(option,value);
        if(option.equals("Place")){
            searchByPlace();
        }
        else if(option.equals("Budget")){
            searchByBudget();
        }

        else if(option.equals("Duration")){
            searchByDuration();
        }

        else if(option.equals("Hotel")){
            searchHotel();
        }
    }



    private void searchHotel() {
        rootReference = FirebaseDatabase.getInstance().getReference("Hotels");
        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();

                for (DataSnapshot hotelSnapshot : dataSnapshot.getChildren()) {
                    Hotel hotel = hotelSnapshot.getValue(Hotel.class);


                    if (hotel.getName().equals(value))
                        hotelList.add(hotel);
                }
                HotelList adapter = new HotelList(SearchActivity.this, hotelList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchByBudget() {
        rootReference = FirebaseDatabase.getInstance().getReference("posts");
        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    int input = Integer.parseInt(value);
                    int  end;
                    end=input+700;

                    if (post.getBudget() <= end)
                        postList.add(post);
                }
                PostList adapter = new PostList(SearchActivity.this, postList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void searchByDuration() {
        rootReference = FirebaseDatabase.getInstance().getReference("posts");
        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    int input = Integer.parseInt(value);
                    if (post.getDuration() == input)
                        postList.add(post);
                }
                PostList adapter = new PostList(SearchActivity.this, postList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void searchByPlace(){

        rootReference= FirebaseDatabase.getInstance().getReference("posts");
        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Post post=postSnapshot.getValue(Post.class);

                    if(post.getPlace().equals(value))
                        postList.add(post);
                }
                PostList adapter= new PostList(SearchActivity.this, postList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*
        rootReference.child("posts").orderByChild("place").equalTo(value).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {




               dataSnapshot.getKey();
                String sa=dataSnapshot.child("description").getValue().toString();
                Toast.makeText(getApplicationContext(), sa + "yooo", Toast.LENGTH_SHORT).show();



                postList.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Post post=postSnapshot.getValue(Post.class);


                    postList.add(post);
                }
                PostList adapter= new PostList(SearchActivity.this, postList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    */

}


    private void addToUserProfile(String option, String value) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        DatabaseReference ref;
        String uid=auth.getCurrentUser().getUid();
        String id;
        ref=FirebaseDatabase.getInstance().getReference("userprofile");
        id=ref.push().getKey();
        UserProfile up= new UserProfile(id, uid, option, value);

        ref.child(id).setValue(up).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });


    }
}


