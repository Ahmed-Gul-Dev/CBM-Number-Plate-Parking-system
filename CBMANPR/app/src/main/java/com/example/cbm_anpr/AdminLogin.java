package com.example.cbm_anpr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {
    EditText etun,etpw;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        etun = (EditText) findViewById(R.id.editTextUN);
        etpw = (EditText) findViewById(R.id.editTextPW);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


    }
    public void btn_login_click(View v)
    {
        String email = etun.getText().toString();
        String password = etpw.getText().toString();

        if(email.isEmpty())
        {
            etun.setError("Username can't be empty");
            etun.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            etpw.setError("Please enter your password");
            etpw.requestFocus();
            return;
        }
        progressDialog.setMessage("Logging in");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminLogin.this,"Success !!",Toast.LENGTH_LONG).show();
                    Intent myintent = new Intent(AdminLogin.this,HomeActivity.class);
                    startActivity(myintent);
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
