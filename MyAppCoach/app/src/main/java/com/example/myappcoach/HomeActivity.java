package com.example.myappcoach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadFragment("Home", "homeBackStack", "HomeFragment", new Home_Fragment());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeID:
                    loadFragment("Home", "homeBackStack", "HomeFragment", new Home_Fragment());
                    return true;

                case R.id.receiptID:
                    loadFragment("Receipt", "ReceiptBackStack", "ReceiptFragment", new Receipt_Fragment());
                    return true;
                case R.id.messageID:
                    loadFragment("Message", "MessageBackStack", "MessageFragment", new Message_Fragment());
                    return true;

                case R.id.accountID:
                    loadFragment("Account", "AccountBackStack", "AccountFragment", new Account_Fragment());
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(String title, String BackStack, String nameFragment ,Fragment fragment) {
        // load fragment
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fragmentTransaction
                    .addToBackStack(BackStack)
                    .setReorderingAllowed(true)
                    .replace(R.id.layoutFragment, fragment, nameFragment)
                    .commit();
        }

    }
}