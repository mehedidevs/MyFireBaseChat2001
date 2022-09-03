package com.seu.myfirebasechat2001.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.seu.myfirebasechat2001.Fragments.AllUserFragment;
import com.seu.myfirebasechat2001.Fragments.ChatFragment;
import com.seu.myfirebasechat2001.Fragments.ProfileFragment;

public class Frag_Adapter extends FragmentPagerAdapter {

    String[] name = {"Chats", "User", "Profile"};


    public Frag_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new ChatFragment();

            case 1:

                return new AllUserFragment();

            case 2:
                return new ProfileFragment();


        }

        return null;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }
}
