package com.example.cbm_anpr;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView read1,read2,mainread;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        read1 = (TextView) findViewById(R.id.ps1);
        read2 = (TextView) findViewById(R.id.ps2);
        mainread = (TextView) findViewById(R.id.FIREBASESTATUS);

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value1 = dataSnapshot.child("parkingslot1").getValue().toString();
                String value2 = dataSnapshot.child("parkingslot2").getValue().toString();


                read1.setText(value1);
                read2.setText(value2);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this,"Connection Error", Toast.LENGTH_LONG).show();
            }
        });

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    mainread.setText("FIREBASE CONNECTED ");
                } else {
                    mainread.setText("FIREBASE CONNECTION FAILED");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}