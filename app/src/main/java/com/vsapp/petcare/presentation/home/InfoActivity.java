package com.vsapp.petcare.presentation.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vsapp.petcare.R;
import com.vsapp.petcare.datamodels.DogList;

import java.util.Map;

public class InfoActivity extends AppCompatActivity {

    DogList dbj;
    int position;
    DatabaseReference databaseReference;
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        desc=findViewById(R.id.desc);
        dbj = (DogList) getIntent().getExtras().getSerializable("object");
        position = dbj.getPosition();
        Log.d("verify", "dog" + position);
        if(position<10) {
             databaseReference = FirebaseDatabase.getInstance().getReference().child("dogs").child("dog" + position);
        }
        else{
            databaseReference = FirebaseDatabase.getInstance().getReference().child("dogs").child("dog_" + position);

        }
        Log.d("data is", databaseReference.toString());
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
              String description=(String)dataSnapshot.child("description").getValue().toString();
               loadData(description);


           }
           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }

    private void loadData(String description) {
        Log.d("description","is"+description);
        desc.setText(description);

    }


}

