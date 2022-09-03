package com.seu.myfirebasechat2001.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seu.myfirebasechat2001.Model.Chat;
import com.seu.myfirebasechat2001.R;
import com.seu.myfirebasechat2001.ViewHolders.Chat_ViewHolder;

import java.util.List;

public class Chat_Adapter extends RecyclerView.Adapter<Chat_ViewHolder> {

    public static final int MSG_LEFT = 0;
    public static final int MSG_RIGHT = 1;

    private Context context;
    private List<Chat> chatList;
    public String image_url;

    FirebaseUser firebaseUser;

    public Chat_Adapter(Context context, List<Chat> chatList, String image_url) {
        this.context = context;
        this.chatList = chatList;
        this.image_url = image_url;
    }

    public Chat_Adapter() {
    }


    @NonNull
    @Override
    public Chat_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.right_item, parent, false);

            return new Chat_ViewHolder(view);


        } else {

            View view = LayoutInflater.from(context).inflate(R.layout.left_item, parent, false);

            return new Chat_ViewHolder(view);

        }


    }

    @Override
    public void onBindViewHolder(@NonNull Chat_ViewHolder holder, int position) {

        Chat chat = chatList.get(position);
        holder.messageTxt.setText(chat.getMessage());
        if (image_url.equals("noimg")) {
            holder.profile_Img.setImageResource(R.drawable.ic_love_birds);

        } else {


            Glide.with(context).load(image_url).into(holder.profile_Img);
        }


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    @Override
    public int getItemViewType(int position) {


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (chatList.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_RIGHT;

        } else {

            return MSG_LEFT;
        }

    }
}
