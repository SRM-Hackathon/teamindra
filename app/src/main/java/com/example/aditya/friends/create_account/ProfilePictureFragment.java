package com.example.aditya.friends.create_account;

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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ProfilePictureFragment extends Fragment {

    private static final int TAKE_PICTURE = 2002;
    private static final int REQUEST_STORAGE_PERMISSION = 2003;

    private TextView mNextTextView;
    private ImageView mProfilePictureImageView;

    private Bitmap mProfileImage;
    private String mUrl;

    public interface ProfilePictureFragmentListener {
        void onProfilePictureUpload(String url);
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
        FirebaseApp.initializeApp(getContext());


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

            uploadToDataBase();
        }
    }


    private void uploadToDataBase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();
        final StorageReference profilePictureRef = storageRef.child(FriendsUtils.mOldPersonData.getUniqueId() + "_profile_pic.jpg");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mProfileImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataImage = baos.toByteArray();

        UploadTask[] uploadTask = {profilePictureRef.putBytes(dataImage)};
        uploadTask[0].addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "SHIT " + exception.toString(), Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mUrl = profilePictureRef.getDownloadUrl().toString();
                    mListener.onProfilePictureUpload(mUrl);
                }

            });
    }
}
