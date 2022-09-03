package com.seu.myfirebasechat2001.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seu.myfirebasechat2001.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat_ViewHolder extends RecyclerView.ViewHolder {

   public CircleImageView profile_Img;
    public TextView messageTxt;

    public Chat_ViewHolder(@NonNull View itemView) {
        super(itemView);

        profile_Img = itemView.findViewById(R.id.profile_image);
        messageTxt = itemView.findViewById(R.id.show_message);
    }
}
