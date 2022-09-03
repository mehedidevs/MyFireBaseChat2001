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
import com.seu.myfirebasechat2001.Model.User;
import com.seu.myfirebasechat2001.R;

import java.util.ArrayList;
import java.util.List;


public class AllUserFragment extends Fragment {


    public AllUserFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    List<User> userList;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    String userId;
    User_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_all_user, container, false);


        recyclerView = view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        userList = new ArrayList<>();
        getAllUsers();


        return view;
    }

    private void getAllUsers() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("MyUsers");
        userId = firebaseUser.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userList.clear();


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {



                    User user = snapshot.getValue(User.class);

                    if (!user.getId().equals(userId)){

                        userList.add(user);

                    }




                }
                adapter = new User_Adapter(userList, getContext());

                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
