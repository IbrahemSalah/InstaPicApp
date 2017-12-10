package com.example.ibrahem.instapicapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SetupActivity extends AppCompatActivity {

    private static final int GALLERY_REQ = 1;
    private EditText editDisplayName;
    private ImageButton displayImage;
    private Uri mImageUri = null;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        editDisplayName = (EditText) findViewById(R.id.displayName);
        displayImage = (ImageButton) findViewById(R.id.setupImageButton);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference().child("profile_image");
    }

    public void profileImageButtonClicked(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("Result code :", String.valueOf(resultCode));
        if (requestCode == GALLERY_REQ && resultCode == Activity.RESULT_OK) {
            Toast.makeText(SetupActivity.this, "Condition True", Toast.LENGTH_SHORT).show();
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                displayImage.setImageURI(mImageUri);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void doneButtonClick(View view){
        final String name = editDisplayName.getText().toString().trim();
        final String userID = mFirebaseAuth.getCurrentUser().getUid();
        if(!TextUtils.isEmpty(name) && mImageUri !=null){
            StorageReference filePath = mStorageReference.child(mImageUri.getLastPathSegment());
            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    mDatabaseReference.child(userID).child("name").setValue(name);
                    mDatabaseReference.child(userID).child("image").setValue(downloadUrl);
                    // my <code></code>
                    Intent mainActivityIntent = new Intent(SetupActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                }
            });

        }
    }
}

