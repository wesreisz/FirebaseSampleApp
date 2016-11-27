package com.wesleyreisz.samplefirebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView mCondition;
    Button mSunny;
    Button mFoggy;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionReference = mRootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCondition = (ImageView)findViewById(R.id.ivCondition);
        mSunny = (Button)findViewById(R.id.btnSunny);
        mSunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConditionReference.setValue("Sunny");
            }
        });
        mFoggy = (Button)findViewById(R.id.btnFoggy);
        mFoggy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConditionReference.setValue("Foggy");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mConditionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                if (data.equalsIgnoreCase("sunny")){
                    mCondition.setImageDrawable(null); // <--- added to force redraw of ImageView
                    mCondition.setImageResource(R.drawable.sunny);
                }else{
                    mCondition.setImageDrawable(null); // <--- added to force redraw of ImageView
                    mCondition.setImageResource(R.drawable.foggy);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
