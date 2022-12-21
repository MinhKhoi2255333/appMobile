package com.example.myappcoach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BuyTicketActivity extends AppCompatActivity {

    public static Bundle bundle  = new Bundle();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);


        Intent intent = getIntent();


        bundle.putString("departure", intent.getStringExtra("departure"));
        bundle.putString("destination", intent.getStringExtra("destination"));
        bundle.putString("date", intent.getStringExtra("date"));
        bundle.putString("time", intent.getStringExtra("time"));
        bundle.putInt("cost", intent.getIntExtra("cost", -1));
        bundle.putInt("slot", intent.getIntExtra("slot", -1));

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Seat_Fragemt seat_fragemt = new Seat_Fragemt();
        seat_fragemt.setArguments(bundle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fragmentTransaction
                    .addToBackStack("BuyTicket")
                    .setReorderingAllowed(true)
                    .replace(R.id.layoutSeat, seat_fragemt, "buyTicket")
                    .commit();
        }

    }
}