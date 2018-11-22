package com.sabbirmeraj.voboghure;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SuggestionActivity extends NavigationDrawerActivity {

    FirebaseAuth auth;
    DatabaseReference rootReference;
    ListView listView;
    List<Post> list1,list2,postList;
    String uid, stringValue;
    Set<String> placeSet;
    List<String> totalPost, totalUser;
    List<Rating> ratedPost;
    float [][] matrix;
    float [] [] normalized;
    float [] avg;
    float [] similarities;
    float [] rating;
    ArrayList<Integer> selectedPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout=(FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_timeline , contentFrameLayout);

        auth=FirebaseAuth.getInstance();
        rootReference=FirebaseDatabase.getInstance().getReference("posts");

        listView=findViewById(R.id.postList);
        postList=new ArrayList<>();
        placeSet=new HashSet<>();
        totalPost=new ArrayList<>();
        totalUser=new ArrayList<>();
        ratedPost=new ArrayList<>();
        selectedPost=new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //contentBasedFiltering();
        collaborativeFiltering();    
    }

    private void collaborativeFiltering() {
       readData(new FirebaseCallback() {
           @Override
           public void onCallback(List<String> list) {
               utilityMatrix();
               System.out.println("Users" + totalUser);
               System.out.println("Posts" + totalPost);
           }
       });



    }

    private void utilityMatrix() {
        matrix= new float[totalUser.size()][totalPost.size()];

        readData2(new FirebaseCallback2() {
            @Override
            public void onCallback2(List<Rating> list) {
                String a, b;
                int row, column;
                int userRow, postColumn;
                userRow=totalUser.size();
                postColumn=totalPost.size();
                System.out.println("Rated Post Size: " +ratedPost.size());
                for (int i=0;i<ratedPost.size();i++){
                    a=ratedPost.get(i).getPostyID();
                    b=ratedPost.get(i).getUserID();
                    row=totalUser.indexOf(b);
                    column=totalPost.indexOf(a);

                    System.out.println("Row: "+ row +" Column: " +column);
                    if(row>=0 && column >=0)
                        matrix[row][column]=ratedPost.get(i).getRating();



                }


                System.out.println("Utility Matrix:   ");
                for(int i=0;i<totalUser.size();i++){
                    for(int j=0;j<totalPost.size();j++){
                        System.out.print(matrix[i][j] +"         ");
                    }
                    System.out.println();
                }







                normalizedMatrix(userRow,postColumn);

                findCorrelation(userRow, postColumn);
                selectSimilarUsersAndRating(userRow, postColumn);






            }


        });
    }

    private void selectSimilarUsersAndRating(int userRow,int postColumn) {
        float max1,max2,max3;
        int i1=0,i2=0,i3=0;
        max1=Integer.MIN_VALUE;
        max2=Integer.MIN_VALUE;
        max3=Integer.MIN_VALUE;
        String uid=auth.getCurrentUser().getUid();
        int userIndex=totalUser.indexOf(uid);


        for(int i=0;i<similarities.length;i++){

            if(i!=userIndex){

                if(similarities[i]>max1){
                    max3=max2;
                    max2=max1;
                    max1=similarities[i];
                    i3=i2;
                    i2=i1;
                    i1=i;
                }

                else if(similarities[i]>max2){
                    max3=max2;
                    max2=similarities[i];
                    i3=i2;
                    i2=i;
                }


                else if(similarities[i]>max1){
                    max1=similarities[i];
                    i1=i;

                }
            }
        }

        ArrayList<Integer> priorityIndex=new ArrayList<>();
        float rate;
        priorityIndex.add(i1);
        priorityIndex.add(i2);
        priorityIndex.add(i3);

        float sum1=0, sum2=0;

        System.out.println("Size: "+ priorityIndex.size());
        System.out.println(i1+ " "+ i2+ " "+i3+"  "+postColumn);
        for(int i=0;i<postColumn;i++){
            for(int j=0;j<priorityIndex.size();j++){
                System.out.println("Hello");
                sum1=sum1+matrix[priorityIndex.get(j)][i]*similarities[priorityIndex.get(j)];
                sum2=sum2+similarities[priorityIndex.get(j)];

                System.out.print("i: "+ i +" j: "+j+" Value: "+ matrix[priorityIndex.get(j)][i]+ "   "+ similarities[priorityIndex.get(j)]);
            }

            rate=sum1/sum2;
            sum1=0;
            sum2=0;
            if( matrix[userIndex][i]!=0 && rate > 2.5 ){
                selectedPost.add(i);
            }

        }


        showPost(selectedPost);


        System.out.println("Slected Post Size:" + selectedPost.size());
        for(int i=0;i< selectedPost.size();i++){
            System.out.println("Post ID: "  + totalPost.get(i));
        }







    }

    private void showPost(final ArrayList<Integer> selectedPost) {
        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference();

        if(selectedPost.size()!=0){
            ref.child("posts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    postList.clear();
                    String pid;
                    for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                        Post post=postSnapshot.getValue(Post.class);

                        for(int i=0;i<selectedPost.size();i++){
                            pid=totalPost.get(selectedPost.get(i));
                            if(pid.equals(postSnapshot.getKey())){
                                postList.add(post);
                            }
                        }



                    }
                    PostList adapter= new PostList(SuggestionActivity.this, postList);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        

    }

    private void findCorrelation(int userRow, int postColumn) {
        String uid=auth.getCurrentUser().getUid();
        int userIndex=totalUser.indexOf(uid);
        float userR;
        float sum=0, sum2=0;
        similarities=new float[userRow];




        for(int i=0;i<postColumn;i++){
            sum=sum+(normalized[userIndex][i]*normalized[userIndex][i]);
        }

        userR= (float) Math.sqrt(sum);
        sum=0;
        for(int i=0;i<userRow;i++){
            for(int j=0;j<postColumn;j++){
              sum=sum+ (normalized[i][j]*normalized[i][j]);
              sum2=sum2+ (normalized[i][j]*normalized[userIndex][j]);
            }

            sum= (float) Math.sqrt(sum);
            float x=sum*userR;
            if(x!=0)
                similarities[i]= sum2 / (sum*userR);
            else{
                similarities[i]=-1;
            }
            sum=0;
            sum2=0;

        }

        similarities[userIndex]=(float) 1.0;

        System.out.println("Similarities Matrix:   ");
        for(int i=0;i<totalUser.size();i++){
           System.out.println(similarities[i]);
        }


















    }

    private void normalizedMatrix(int userRow, int postColumn) {

       float sum=0;
       int count=0;
       normalized=new float[userRow][postColumn];
       avg=new float[userRow];
        for(int i=0;i<userRow;i++){
            for(int j=0;j<postColumn;j++){
                sum=sum+matrix[i][j];
                if(matrix[i][j]!=0) count++;
            }
            avg[i]=sum/count;
            sum=0;
            count=0;
        }

        for(int i=0;i<userRow;i++){
            for(int j=0;j<postColumn;j++){
                if(matrix[i][j]!=0){
                    normalized[i][j]=matrix[i][j]-avg[i];
                }
            }
        }


        System.out.println("Normalized Matrix:   ");
        for(int i=0;i<totalUser.size();i++){
            for(int j=0;j<totalPost.size();j++){
                System.out.print(normalized[i][j] +"         ");
            }
            System.out.println();
        }

    }


    private void readData2(final FirebaseCallback2 firebaseCallback2){

        DatabaseReference rootReference=FirebaseDatabase.getInstance().getReference();

        rootReference.child("Rating").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              ratedPost.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Rating r=postSnapshot.getValue(Rating.class);
                    ratedPost.add(r);
                }
                firebaseCallback2.onCallback2(ratedPost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readData(final FirebaseCallback firebaseCallback){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        uid = auth.getCurrentUser().getUid();

        reference.child("posts").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalPost.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    totalPost.add(postSnapshot.getKey());
                    // System.out.println(postSnapshot.getKey()+"      "+ dataSnapshot);

                }

                //firebaseCallback.onCallback(totalPost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalUser.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    totalUser.add(postSnapshot.getKey());

                }

                firebaseCallback.onCallback(totalUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private interface FirebaseCallback{
        void onCallback(List <String> list);
    }

    private interface FirebaseCallback2{
        void onCallback2(List<Rating>list);
    }










































































































































    private void contentBasedFiltering() {
        rootReference=FirebaseDatabase.getInstance().getReference("userprofile");
        uid=auth.getCurrentUser().getUid();

        rootReference.orderByChild("userID").equalTo(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot upSnapshot: dataSnapshot.getChildren()){

                    if(dataSnapshot.child("criteria").getValue().toString().equals("Place")){
                        placeSet.add(dataSnapshot.child("value").getValue().toString());
                    }


                }

                findSimilarCriteria();
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




    }


    void findSimilarCriteria(){
        String place;
        Iterator itr=placeSet.iterator();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("posts");

        while(itr.hasNext()){
            place= (String) itr.next();
            System.out.println(place);
            ref.orderByChild("place").equalTo(place).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    searchByDuration(dataSnapshot.child("duration").getValue().toString());
                    searchByBudget(dataSnapshot.child("budget").getValue().toString());
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
        }
    }


    private void searchByDuration(String value) {
       DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference("posts");
       stringValue=value;
       rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    int input = Integer.parseInt(stringValue);
                    if (post.getDuration() == input)
                        postList.add(post);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void searchByBudget(String value) {
        DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference("posts");
        stringValue=value;
        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    int input = Integer.parseInt(stringValue);
                    int  end;
                    end=input+700;

                    if (post.getBudget() <= end)
                        postList.add(post);
                }


                PostList adapter = new PostList(SuggestionActivity.this, postList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
