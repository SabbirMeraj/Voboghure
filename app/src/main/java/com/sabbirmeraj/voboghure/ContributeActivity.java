package com.sabbirmeraj.voboghure;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContributeActivity extends NavigationDrawerActivity {


    Spinner dropdown;
    Button button;
    String option;
    EditText etName,etPlace,etAddress,etPhoneNumber,etReview;
    DatabaseReference rootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout=(FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_contribute, contentFrameLayout);
        //button=findViewById(R.id.button);
        dropdown=findViewById(R.id.contributionSpinner);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                option=adapterView.getItemAtPosition(i).toString();
                showForm(option);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void showForm(String option){
        if(option.equals("Hotel")){
            Fragment fragment=new HotelContribution();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame,fragment,fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        }
        else if(option.equals("Guide")){
            Fragment fragment=new GuideContribution();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame,fragment,fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        }

        else if(option.equals("--")){

        }
    }

    public void postHotel(View view){

        String name, place, address, phoneNumber, review,id;

        etName=findViewById(R.id.hotelName);
        etPlace=findViewById(R.id.hotelPlace);
        etAddress=findViewById(R.id.hotelAddress);
        etPhoneNumber=findViewById(R.id.hotelPhoneNumber);
        etReview=findViewById(R.id.hotelReview);
        rootReference= FirebaseDatabase.getInstance().getReference("Hotels");

        name=etName.getText().toString();
        place=etPlace.getText().toString();
        address=etAddress.getText().toString();
        phoneNumber=etPhoneNumber.getText().toString();
        review=etReview.getText().toString();
        id=rootReference.push().getKey();

        if(name!=null && place!=null &&address!=null &&phoneNumber!=null){
            Hotel hotelToBeAdded=new Hotel(name,place,address,phoneNumber,review);
            rootReference.child(id).setValue(hotelToBeAdded)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Hotel has been added", Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(getApplicationContext(), ContributeActivity.class);
                                startActivity(i);
                            }
                        }
                    });
        }




    }


    public void postGuide(View view) {

        String name, place, phoneNumber, review, id;

        etName = findViewById(R.id.guideName);
        etPlace = findViewById(R.id.guidePlace);

        etPhoneNumber = findViewById(R.id.guidePhoneNumber);
        etReview = findViewById(R.id.guideReview);
        rootReference = FirebaseDatabase.getInstance().getReference("Guides");

        name = etName.getText().toString();
        place = etPlace.getText().toString();

        phoneNumber = etPhoneNumber.getText().toString();
        review = etReview.getText().toString();
        id = rootReference.push().getKey();

        if (name != null && place != null && phoneNumber != null) {
            Guide guideToBeAdded = new Guide(name, place, phoneNumber, review);
            rootReference.child(id).setValue(guideToBeAdded)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Guide Info has been added", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), ContributeActivity.class);
                                startActivity(i);
                            }
                        }
                    });
        }
    }

}
