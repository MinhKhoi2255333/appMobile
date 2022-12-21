package com.example.myappcoach;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchTicketAdapter extends RecyclerView.Adapter<SearchTicketAdapter.ViewHolderTicket>{

    private ArrayList<Ticket> listTicket;
    private Context context;

    public SearchTicketAdapter(Context context, ArrayList<Ticket> listTicket) {
        this.listTicket = listTicket;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderTicket onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_search_view_result, parent, false);
        return new ViewHolderTicket(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTicket holder, int position) {
        Ticket ticket = listTicket.get(position);
        if(ticket != null){
            holder.depSearch.setText(ticket.getDeparture());
            holder.desSearch.setText(ticket.getDestination());
            holder.daSearch.setText("Date:" + ticket.getDepartureDate());
            holder.tiSearch.setText("Time:" + ticket.getDepartureTime());
            holder.coSearch.setText("Cost:"+ticket.getCost());
            holder.sloSearch.setText("Slot:" + ticket.getSlot());
        }

        holder.ticketSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BuyTicketActivity.class);
                intent.putExtra("departure", ticket.getDeparture());
                intent.putExtra("destination", ticket.getDestination());
                intent.putExtra("date", ticket.getDepartureDate());
                intent.putExtra("time", ticket.getDepartureTime());
                intent.putExtra("cost", ticket.getCost());
                intent.putExtra("slot", ticket.getSlot());

                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return listTicket.size();
    }

    public static class ViewHolderTicket extends RecyclerView.ViewHolder {

        private TextView depSearch, desSearch, daSearch, tiSearch, coSearch, sloSearch;
        private ConstraintLayout ticketSearchLayout;

        public ViewHolderTicket(@NonNull View itemView) {
            super(itemView);

            depSearch = (TextView) itemView.findViewById(R.id.departureSearch);
            desSearch = (TextView) itemView.findViewById(R.id.destinationSearch);
            daSearch = (TextView) itemView.findViewById(R.id.dateSearch);
            tiSearch = (TextView) itemView.findViewById(R.id.timeSearch);
            sloSearch = (TextView) itemView.findViewById(R.id.slotSearch);
            coSearch = (TextView) itemView.findViewById(R.id.costSearch);
            ticketSearchLayout = (ConstraintLayout) itemView.findViewById(R.id.ticketLayoutSearch);

        }
    }
}
