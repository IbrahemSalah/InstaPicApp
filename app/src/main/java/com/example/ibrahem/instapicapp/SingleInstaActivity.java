package com.example.ibrahem.instapicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleInstaActivity extends AppCompatActivity {

    private String post_key= null;
    private DatabaseReference mDatabaseReference;
    private ImageView singlePostImage;
    private TextView singlePostTitle;
    private TextView singlePostDesc;
    private Button deleteButton;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_insta);

        mFirebaseAuth = FirebaseAuth.getInstance();
        post_key = getIntent().getExtras().getString("Postid");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("InstaApp");
        singlePostDesc =(TextView)findViewById(R.id.singleDesc);
        singlePostTitle = (TextView)findViewById(R.id.singleTitle);
        singlePostImage = (ImageView)findViewById(R.id.singleImageView);
        deleteButton = (Button)findViewById(R.id.singleDeleteButton);
        deleteButton.setVisibility(View.INVISIBLE);

        mDatabaseReference.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String postTitle = (String) dataSnapshot.child("title").getValue();
                String postDesc = (String) dataSnapshot.child("desc").getValue();
                String postImage = (String) dataSnapshot.child("image").getValue();
                String postUid = (String) dataSnapshot.child("uid").getValue();

                singlePostTitle.setText(postTitle);
                singlePostDesc.setText(postDesc);
                Picasso.with(SingleInstaActivity.this).load(postImage).into(singlePostImage);

                if(mFirebaseAuth.getCurrentUser().getUid().equals(postUid)){
                    deleteButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteButtonClicked(View view){
        mDatabaseReference.child(post_key).removeValue();
        Intent mainActivityIntent = new Intent(SingleInstaActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
