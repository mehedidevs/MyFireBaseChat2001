package com.seu.myfirebasechat2001.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.seu.myfirebasechat2001.Activities.MessageActivity;
import com.seu.myfirebasechat2001.Model.User;
import com.seu.myfirebasechat2001.R;
import com.seu.myfirebasechat2001.ViewHolders.Users_ViewHolder;

import java.util.List;

public class User_Adapter extends RecyclerView.Adapter<Users_ViewHolder> {


    List<User> userList;
    Context context;


    public User_Adapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }


    @NonNull
    @Override
    public Users_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);


        return new Users_ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull Users_ViewHolder holder, int position) {

        final User user = userList.get(position);


        holder.userName.setText(user.getUsername());
        holder.userMail.setText(user.getMail());


        if (user.getProfile_img().equals("noimg")) {
            holder.pro_img.setImageResource(R.drawable.ic_love_birds);

        } else {

            Glide.with(context).load(user.getProfile_img()).into(holder.pro_img);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userId", user.getId());

                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
