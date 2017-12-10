package com.example.ibrahem.instapicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameFiled;
    private EditText emailFiled;
    private EditText passFiled;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameFiled = (EditText) findViewById(R.id.nameFiled);
        emailFiled = (EditText) findViewById(R.id.emailFiled);
        passFiled = (EditText) findViewById(R.id.passFiled);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void registerButtonClicked(View view) {

        final String name = nameFiled.getText().toString().trim();
        final String email = emailFiled.getText().toString().trim();
        final String password = passFiled.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String userId = mFirebaseAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDb = mDatabaseReference.child(userId);
                        currentUserDb.child("Name").setValue(name);
                        currentUserDb.child("Image").setValue("Default");
                        Toast.makeText(RegisterActivity.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(RegisterActivity.this, SetupActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    } else if (!task.isSuccessful()) {
                        Log.i("Failed Registration: ", task.getException().getMessage());
                    }
                }
            });
        }
    }
}
