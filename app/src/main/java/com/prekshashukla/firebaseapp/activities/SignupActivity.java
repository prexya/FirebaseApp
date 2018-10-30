package com.prekshashukla.firebaseapp.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.prekshashukla.firebaseapp.R;
import com.prekshashukla.firebaseapp.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Activity class for user signup
 * creates new user using Firebase database
 * on successful registration, navigates to {@link MainActivity}
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText etEmail, etName, etPassword;
    private Button buttonRegister, buttonUplaodImage;
    private ImageView imageView;

    private FirebaseAuth auth;
    private String userEmail, userName, userPassword;
    private Uri imageEncoded;

    static final int REQUEST_CAMERA = 1;
    static final int SELECT_FILE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //creating instance of FirebaseAuth
        auth = FirebaseAuth.getInstance();

        //initializing views
        etEmail = (TextInputEditText) findViewById(R.id.activity_signup_email);
        etName = (TextInputEditText) findViewById(R.id.activity_signup_username);
        etPassword = (TextInputEditText) findViewById(R.id.activity_signup_password);

        buttonRegister = (Button) findViewById(R.id.activity_signup_button);
        buttonUplaodImage = (Button) findViewById(R.id.activity_signup_upload_button);

        imageView = (ImageView) findViewById(R.id.activity_signup_image);

        //on click listener of views
        buttonRegister.setOnClickListener(this);
        buttonUplaodImage.setOnClickListener(this);
    }

/*
 * dialog to select source of image, either using camera or from phone gallery
 * or cancel
 */
    private void selectImage() {
        final CharSequence[] items = { getResources().getString(R.string.take_photo),
                getResources().getString(R.string.choose_from_library),
                getResources().getString(R.string.cancel) };
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setTitle(getResources().getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getResources().getString(R.string.take_photo))) {
                    cameraIntent();
                } else if (items[item].equals(getResources().getString(R.string.choose_from_library))) {
                    galleryIntent();
                } else if (items[item].equals(getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /*
     * Opening camera intent, when user selects camera as photo source
     */
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    /*
    * Opening camera intent, when user selects existing photo gallery of phone as photo source
    */
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_file)), SELECT_FILE);
        }
    }

    /*
     * when user finally selects a photo from either sources
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    /*
     * setting value of imageView as selected image from gallery by converting into bitmap
     */
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri photoURI = Uri.parse(destination.toString());
        imageView.setImageBitmap(bm);
        imageEncoded = photoURI;
    }

    /*
     * setting value of imageView as clicked image from camera by converting into bitmap
     */
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri photoURI = Uri.parse(destination.toString());

        imageView.setImageBitmap(thumbnail);
        imageEncoded = photoURI;
    }

    /*
     * on click event of views
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_signup_button:
                //create user
                userName = etName.getText().toString();
                userEmail = etEmail.getText().toString();
                userPassword = etPassword.getText().toString();
                if (userName.length() == 0 && userPassword.length() == 0 && userEmail.length() == 0) {
                    ToastUtils.showToast(getApplicationContext(), getResources().getString(R.string.enter_all_details), false);
                } else if(userName.length() == 0){
                    etName.setError(getResources().getString(R.string.enter_name));
                } else if(userEmail.length() == 0){
                    etEmail.setError(getResources().getString(R.string.enter_email));
                } else if (userPassword.length() == 0){
                    etPassword.setError(getResources().getString(R.string.enter_password));
                }  else {
                    auth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        ToastUtils.showToast(getApplicationContext(), getResources().getString(R.string.registration_success), false);

                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        FirebaseUser user = auth.getCurrentUser();

                                        //updating name and photo of created user
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(userName)
                                                .setPhotoUri(imageEncoded)
                                                .build();

                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("Success", "User profile updated.");
                                                        }
                                                    }
                                                });
                                        startActivity(intent);
                                        finish();
                                    } else  {
                                        ToastUtils.showToast(getApplicationContext(), getResources().getString(R.string.registration_fail), false);
                                    }
                                }
                            });
                }


                break;

            case R.id.activity_signup_upload_button:
                //on upload button click, select sources of image
                selectImage();
                break;
        }
    }
}
