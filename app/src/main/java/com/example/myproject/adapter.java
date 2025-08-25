package com.example.myproject;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class adapter extends FragmentStateAdapter {

    public adapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new All();
            case 1: return new Available();
            case 2: return new Sold();
            default: return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // total tabs
    }


}
