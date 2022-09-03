package com.seu.myfirebasechat2001.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.seu.myfirebasechat2001.Model.User;
import com.seu.myfirebasechat2001.R;

public class FullActivity extends AppCompatActivity {

    ImageView cover, profile;

    TextView useName, usrMail;

    DatabaseReference databaseReference;

    Intent intent;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        intent = getIntent();
        userId = intent.getStringExtra("userid");

        cover = findViewById(R.id.codverImage);
        profile = findViewById(R.id.profile_image);
        useName = findViewById(R.id.profiileName);
        usrMail = findViewById(R.id.profileEmail);



        databaseReference = FirebaseDatabase.getInstance().getReference("MyUsers").child(userId);


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


                    Glide.with(FullActivity.this).load(user.getProfile_img()).into(cover);

                    Glide.with(FullActivity.this).load(user.getProfile_img()).into(profile);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
