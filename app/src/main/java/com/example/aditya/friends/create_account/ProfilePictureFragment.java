package com.example.aditya.friends.create_account;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aditya.friends.R;
import com.example.aditya.friends.utils.FriendsUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ProfilePictureFragment extends Fragment {

    private static final int TAKE_PICTURE = 2002;
    private static final int REQUEST_STORAGE_PERMISSION = 2003;

    private TextView mNextTextView;
    private ImageView mProfilePictureImageView;

    private Bitmap mProfileImage;
    private Uri mImageUri;
    private Map response =  new HashMap();

    public interface ProfilePictureFragmentListener {
        void onProfilePictureUpload(String requestID, Map resultData);
    }

    private ProfilePictureFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfilePictureFragmentListener) {
            mListener = (ProfilePictureFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " Error creating listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_profile_picture, container, false);

        mNextTextView = (TextView) view.findViewById(R.id.create_account_profile_picture_next);
        mProfilePictureImageView = (ImageView) view.findViewById(R.id.create_account_profile_picture_imageView);

        mNextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdentityVerificationFragment identityVerificationFragment = new IdentityVerificationFragment();
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                        .replace(R.id.create_account_frameLayout, identityVerificationFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mProfilePictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        return view;
    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mProfileImage = (Bitmap) extras.get("data");
            mProfilePictureImageView.setImageBitmap(mProfileImage);

            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();
            StorageReference mountainsRef = storageRef.child("mountains.jpg");
            StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mProfileImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataImage = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(dataImage);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

        }
    }
}
