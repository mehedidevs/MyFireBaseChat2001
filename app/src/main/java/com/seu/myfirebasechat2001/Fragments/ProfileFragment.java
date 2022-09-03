package com.seu.myfirebasechat2001.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.seu.myfirebasechat2001.Model.User;
import com.seu.myfirebasechat2001.R;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    ImageView cover, profile;

    TextView useName, usrMail;

    DatabaseReference databaseReference;


    FirebaseUser firebaseUser;

    public static final int MY_REQUEST_CODE = 1;

    StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        cover = view.findViewById(R.id.codverImage);
        profile = view.findViewById(R.id.profile_image);
        useName = view.findViewById(R.id.profiileName);
        usrMail = view.findViewById(R.id.profileEmail);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("MyUsers").child(firebaseUser.getUid());
        storageReference = FirebaseStorage.getInstance().getReference("Upload");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                useName.setText(user.getUsername());
                usrMail.setText(user.getMail());

                if (user.getProfile_img().equals("noimg")) {
                    cover.setImageResource(R.drawable.cover);
                    profile.setImageResource(R.drawable.cover);


                } else {


                    Glide.with(getContext()).load(user.getProfile_img()).into(cover);

                    Glide.with(getContext()).load(user.getProfile_img()).into(profile);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocalImage();

            }
        });


        return view;
    }

    private void getLocalImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");

        startActivityForResult(intent, MY_REQUEST_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                if (data != null) {
                    Uri uri = data.getData();

                    final StorageReference myupload = storageReference.child(useName.getText().toString() + System.currentTimeMillis());

                    myupload.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_LONG).show();

                            myupload.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri myUri = task.getResult();

                                    String myURL = myUri.toString();

                                    HashMap<String, Object> img_map = new HashMap<>();

                                    img_map.put("profile_img", myURL);

                                    databaseReference.updateChildren(img_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(getContext(), "Profile Image Updated!", Toast.LENGTH_SHORT).show();


                                        }
                                    });


                                }
                            });


                        }
                    });


                }


            }


        }


    }
}
