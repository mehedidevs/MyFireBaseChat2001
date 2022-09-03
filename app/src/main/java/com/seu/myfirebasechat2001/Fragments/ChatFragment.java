package com.seu.myfirebasechat2001.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seu.myfirebasechat2001.Adapters.User_Adapter;
import com.seu.myfirebasechat2001.Model.Chat;
import com.seu.myfirebasechat2001.Model.ChatList;
import com.seu.myfirebasechat2001.Model.User;
import com.seu.myfirebasechat2001.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    public ChatFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    FirebaseUser firebaseUser;

    String userId;

    List<User> mUsers;

    List<ChatList> userList;

    User_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userId = firebaseUser.getUid();


        userList = new ArrayList<>();
        mUsers = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        databaseReference = FirebaseDatabase.getInstance().getReference("myChatList").child(userId);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ChatList chatList = snapshot.getValue(ChatList.class);

                    userList.add(chatList);


                }

                myChatList();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void myChatList() {
        databaseReference = FirebaseDatabase.getInstance().getReference("MyUsers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                    User user = snapshot.getValue(User.class);

                    for (ChatList chatList : userList) {


                        if (user.getId().equals(chatList.getId())) {

                            mUsers.add(user);


                        }


                    }


                }

                adapter = new User_Adapter(mUsers, getContext());
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
