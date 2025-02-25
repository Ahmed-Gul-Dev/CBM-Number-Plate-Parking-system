package com.example.cbm_anpr;
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6;
    ProgressDialog progressDialog;
    long node_id = 0;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference node;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1 = (EditText) findViewById(R.id.t1); // student name
        e2 = (EditText) findViewById(R.id.t2); //department
        e3 = (EditText) findViewById(R.id.t3); // phone no
        e4 = (EditText) findViewById(R.id.t4); // current status (graduated or student)
        e5 = (EditText) findViewById(R.id.t5); // vehicle no
        e6 = (EditText) findViewById(R.id.t6); // roll no

        node = db.getReference("Students");
        node.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    node_id = (dataSnapshot.getChildrenCount());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Register.this,"Connection Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void register(View v)
    {
        String name = e1.getText().toString().trim();
        String depart = e2.getText().toString().trim();
        String Pno = e3.getText().toString().trim();
        String Cstatus = e4.getText().toString().trim();
        String Vno = e5.getText().toString().trim().toUpperCase();
        String id = e6.getText().toString().trim();

        if(Vno.isEmpty())
        {
            e5.setError("Vehicle number can not be empty");
            e5.requestFocus();
            return;
        }
        if(id.isEmpty())
        {
            e6.setError("Roll number can not be empty");
            e6.requestFocus();
            return;
        }
        if(name.isEmpty())
        {
            e1.setError("Please enter your name");
            e2.requestFocus();
            return;
        }
        if(Pno.isEmpty())
        {
            e3.setError("Please enter your phone no");
            e3.requestFocus();
            return;
        }
        if(depart.isEmpty())
        {
            e2.setError("Program not be empty");
            e2.requestFocus();
            return;
        }
        if(Cstatus.isEmpty())
        {
            e4.setError("Active can not be empty");
            e4.requestFocus();
            return;
        }

        Vehicles obj = new Vehicles(name,depart,Pno,Cstatus,Vno,id);

        // Writing data to Firebase
        node.child(String.valueOf(node_id + 1)).setValue(obj);

        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");
        e5.setText("");
        e6.setText("");

        Toast.makeText(getApplicationContext(),"Saved Successfully.",Toast.LENGTH_LONG).show();
    }

}

