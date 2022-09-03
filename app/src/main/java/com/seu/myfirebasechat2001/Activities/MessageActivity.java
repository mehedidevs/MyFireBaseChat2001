package com.seu.myfirebasechat2001.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seu.myfirebasechat2001.Adapters.Chat_Adapter;
import com.seu.myfirebasechat2001.Model.Chat;
import com.seu.myfirebasechat2001.Model.User;
import com.seu.myfirebasechat2001.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profileImg;
    TextView profileName;

    RecyclerView recyclerView;
    EditText message;
    ImageButton send;


    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Intent intent;

    String userId, myId;

    List<Chat> chatList;
    Chat_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        profileImg = findViewById(R.id.profile_image);
        profileName = findViewById(R.id.profileName);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        databaseReference = FirebaseDatabase.getInstance().getReference("MyUsers");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        intent = getIntent();
        userId = intent.getStringExtra("userId");
        myId = firebaseUser.getUid();

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), FullActivity.class);
                intent.putExtra("userid", userId);
                startActivity(intent);

            }
        });

        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                profileName.setText(user.getUsername());

                if (user.getProfile_img().equals("noimg")) {
                    profileImg.setImageResource(R.mipmap.ic_launcher);

                } else {

                    Glide.with(getApplicationContext()).load(user.getProfile_img()).into(profileImg);

                }

                ReadMessage(myId, userId, user.getProfile_img());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        send = findViewById(R.id.send);
        message = findViewById(R.id.message);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message_str = message.getText().toString();

                SendMessage(myId, userId, message_str);
                message.setText(" ");


            }
        });


    }

    private void ReadMessage(final String myID, final String userID, final String imgURL) {


        chatList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chatList.clear();


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Chat chat = snapshot.getValue(Chat.class);


                    if (chat.getReceiver().equals(myID) && chat.getSender().equals(userID) ||
                            chat.getReceiver().equals(userID) && chat.getSender().equals(myID)
                    ) {
                        chatList.add(chat);

                    }


                }

                adapter = new Chat_Adapter(MessageActivity.this, chatList, imgURL);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void SendMessage(String sendor, String reciver, String message) {

        DatabaseReference chatreferanse = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> chatMap = new HashMap<>();


        chatMap.put("sender", sendor);
        chatMap.put("receiver", reciver);
        chatMap.put("message", message);
        chatreferanse.child("Chats").push().setValue(chatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(MessageActivity.this, "Message Sended", Toast.LENGTH_SHORT).show();

                }


            }
        });


        final DatabaseReference chatListRefferance = FirebaseDatabase.getInstance().getReference("myChatList")
                .child(firebaseUser.getUid())
                .child(userId);
        chatListRefferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {

                    chatListRefferance.child("id").setValue(userId);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
