package com.example.myappcoach;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Confirm_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);

//        String departure = BuyTicketActivity.bundle.getString("departure");
//        Toast.makeText(getActivity(), departure, Toast.LENGTH_SHORT).show();

//        String departure = getArguments().getString("departure");
//        String destination = getArguments().getString("destination");
//        String date = getArguments().getString("date");
//        String time = getArguments().getString("time");
//        int cost = getArguments().getInt("cost");
//        int slot = getArguments().getInt("slot");
//        ArrayList<CharSequence> listSeat = getArguments().getCharSequenceArrayList("seats");
//
//        Toast.makeText(getActivity(), departure, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(), destination, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(), cost+"", Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(), slot+"", Toast.LENGTH_SHORT).show();

        return view;
    }
}