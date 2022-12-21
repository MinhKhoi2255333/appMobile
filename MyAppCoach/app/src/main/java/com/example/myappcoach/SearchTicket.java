package com.example.myappcoach;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SearchTicket extends Fragment{

    private TextView dep, des, da;
    private EditText num;
    private Button btSearch;

    public int positoinClick;

    private ListView listPlace;

    private RecyclerView recyclerViewTicket;
    private SearchTicketAdapter searchTicketAdapter;
    private ArrayList<Ticket> listSearchTicket = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_ticket, container, false);

        dep = (TextView) view.findViewById(R.id.departureTicket);
        des = (TextView) view.findViewById(R.id.destinationTicket);
        da = (TextView) view.findViewById(R.id.dateTicket);
        num = (EditText) view.findViewById(R.id.numTicket);
        btSearch = (Button) view.findViewById(R.id.btsearch);

        createListTicket(view);

        dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeparture();
            }
        });
        des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDestination();
            }
        });
        da.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedYear = Calendar.getInstance().get(Calendar.YEAR);
                int selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
                int selectedDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        da.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

                datePickerDialog.show();
            }
        });
//        String departure, String destination, String departureDate, String departureTime, String slot
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listSearchTicket.clear();
                searchTicketAdapter.notifyDataSetChanged();
                searchTicket(dep.getText().toString(),des.getText().toString(),da.getText().toString(),num.getText().toString());
            }
        });

        return view;
    }

    private void setDeparture() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.list_place_search);

        int sizeListTiccket = Home_Fragment.listTicket.size();
        String[] place = new String[sizeListTiccket];
        for(int i=0 ; i<sizeListTiccket ; i++){
            place[i] = Home_Fragment.listTicket.get(i).getDeparture();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        listPlace = (ListView) dialog.findViewById(R.id.lvPlace);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, place);
        listPlace.setAdapter(arrayAdapter);

        listPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dep.setText(place[i]);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setDestination() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.list_place_search);

        int sizeListTiccket = Home_Fragment.listTicket.size();
        String[] place = new String[sizeListTiccket];
        for(int i=0 ; i<sizeListTiccket ; i++){
            place[i] = Home_Fragment.listTicket.get(i).getDestination();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        listPlace = (ListView) dialog.findViewById(R.id.lvPlace);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, place);
        listPlace.setAdapter(arrayAdapter);

        listPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                des.setText(place[i]);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void searchTicket(String departure, String destination, String departureDate, String slot){

        String url = MainActivity.url + "/checkTicket.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json = new JSONArray(response);

                            for(int i=0; i< json.length(); i++){

                                JSONObject jsonObject = json.getJSONObject(i);
                                String name = jsonObject.getString("describe");
                                int resID = getResources().getIdentifier(name , "drawable", getActivity().getPackageName());
                                listSearchTicket.add(new Ticket(
                                        jsonObject.getInt("slot"),
                                        jsonObject.getInt("cost"),
                                        resID,
                                        jsonObject.getString("departure"),
                                        jsonObject.getString("destination"),
                                        jsonObject.getString("departureDate"),
                                        jsonObject.getString("departureTime")
                                ));
                            }
                            searchTicketAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("Login error", error+"");
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if(!departure.isEmpty() && !destination.isEmpty() && !departureDate.isEmpty() && !slot.isEmpty() ){
                    params.put("departure_put", departure);
                    params.put("destination_put", destination);
                    params.put("departureDate_put", departureDate);
                    params.put("slot_put", slot);
                }

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }



    @SuppressLint("NotifyDataSetChanged")
    public void createListTicket(View view){

        Toast.makeText(getActivity(), "createListTicket", Toast.LENGTH_SHORT).show();

        recyclerViewTicket = (RecyclerView) view.findViewById(R.id.rcvSearch);

//        listSearchTicket.add(new Ticket(30, 150, R.drawable.kiengiang, "HCM", "AG", "22", "5"));
//        listSearchTicket.add(new Ticket(30, 150, R.drawable.kiengiang, "HCM", "AG", "22", "5"));
//        listSearchTicket.add(new Ticket(30, 150, R.drawable.kiengiang, "HCM", "AG", "22", "5"));
        searchTicketAdapter = new SearchTicketAdapter(getActivity(), listSearchTicket);
        recyclerViewTicket.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTicket.setHasFixedSize(true);
        recyclerViewTicket.setAdapter(searchTicketAdapter);

//        searchTicket(dep.getText().toString(),des.getText().toString(),da.getText().toString(),tim.getText().toString(),num.getText().toString());

    }
}
