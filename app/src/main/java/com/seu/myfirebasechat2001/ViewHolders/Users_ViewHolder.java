package com.seu.myfirebasechat2001.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seu.myfirebasechat2001.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Users_ViewHolder extends RecyclerView.ViewHolder {

  public   CircleImageView pro_img;
    public TextView userName, userMail;

    public Users_ViewHolder(@NonNull View itemView) {
        super(itemView);
        pro_img = itemView.findViewById(R.id.userPof_img);
        userName = itemView.findViewById(R.id.userName);
        userMail = itemView.findViewById(R.id.usermail);


    }
}
