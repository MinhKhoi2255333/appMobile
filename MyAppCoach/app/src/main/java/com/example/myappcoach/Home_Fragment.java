package com.example.myappcoach;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class Home_Fragment extends Fragment {

    public static final ArrayList<Ticket> listTicket = new ArrayList<>();
    private RecyclerView recyclerViewTicket;
    private TicketHomeAdapter ticketAdapter;
    
    private ImageButton imgTicket, imgMotobike;


    private ViewPager viewPager;
    private Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager.getCurrentItem() == getlistPhoto().size()-1){
                viewPager.setCurrentItem(0);
            }else{
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        createSlideShow(view);
        createListTicket(view);
        createBuyTicket(view);
        return view;
    }

    public void createBuyTicket(View view){
        imgTicket = (ImageButton) view.findViewById(R.id.img_ticket);
        imgMotobike = (ImageButton) view.findViewById(R.id.img_motobike);

        imgTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fragmentTransaction
                            .addToBackStack("buyTicket")
                            .setReorderingAllowed(true)
                            .replace(R.id.layoutFragment, new SearchTicket(), "ticket")
                            .commit();
                }
            }
        });
    }


    public ArrayList<Image_SlideShow> getlistPhoto(){
        ArrayList<Image_SlideShow> list = new ArrayList<>();
        list = new ArrayList<>();
        list.add(new Image_SlideShow(R.drawable.img_carousel_a));
        list.add(new Image_SlideShow(R.drawable.img_carousel_b));
        list.add(new Image_SlideShow(R.drawable.img_carousel_c));
        list.add(new Image_SlideShow(R.drawable.img_carousel_d));
        list.add(new Image_SlideShow(R.drawable.img_carousel_e));

        return list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createListTicket(View view){

        recyclerViewTicket = (RecyclerView) view.findViewById(R.id.rcvTicket);

        ticketAdapter = new TicketHomeAdapter(getActivity(), listTicket);
        recyclerViewTicket.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTicket.setHasFixedSize(true);
        recyclerViewTicket.setAdapter(ticketAdapter);

        getTicketAtDataBase();

    }

    private void getTicketAtDataBase() {
        String url = MainActivity.url + "/getTicket.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i< response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("describe");
                                int resID = getResources().getIdentifier(name , "drawable", getActivity().getPackageName());
                                listTicket.add(new Ticket(
                                        jsonObject.getInt("slot"),
                                        jsonObject.getInt("cost"),
                                        resID,
                                        jsonObject.getString("departure"),
                                        jsonObject.getString("destination"),
                                        jsonObject.getString("departureDate"),
                                        jsonObject.getString("departureTime")

                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ticketAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void createSlideShow(View view){
        ArrayList<Image_SlideShow> list = getlistPhoto();

        viewPager = view.findViewById(R.id.viewPagerID);
        CircleIndicator circleIndicator = view.findViewById(R.id.circle_indicator);

        SlideShowHomeAdapter slideShowAdapter = new SlideShowHomeAdapter(getActivity(), list);
        viewPager.setAdapter(slideShowAdapter);

        circleIndicator.setViewPager(viewPager);

        handler.postDelayed(runnable, 2000);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        slideShowAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }


    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
    }
}
