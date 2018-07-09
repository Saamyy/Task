package com.example.mahmoudsamy.task.view.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.mahmoudsamy.task.R;
import com.example.mahmoudsamy.task.view.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {
    MainFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflateFragment();
    }

    private void inflateFragment() {
        fragment = new MainFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.lists_fragments, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

}
