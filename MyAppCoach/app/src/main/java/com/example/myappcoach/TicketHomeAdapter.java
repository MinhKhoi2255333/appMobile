package com.example.myappcoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TicketHomeAdapter extends RecyclerView.Adapter<TicketHomeAdapter.ViewHolderTicket> {


    private ArrayList<Ticket> listTicket;
    private Context context;

    public TicketHomeAdapter(Context context, ArrayList<Ticket> listTicket) {
        this.listTicket = listTicket;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderTicket onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_in_recycle_home, parent, false);
        return new ViewHolderTicket(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTicket holder, int position) {
        Ticket ticket = listTicket.get(position);
        if(ticket != null){
            holder.name.setText(String.format("%s-%s", ticket.getDeparture(), ticket.getDestination()));
            holder.cost.setText(ticket.getCost()+"");
            holder.imageView.setImageResource(ticket.getDescribe());
        }
        holder.ticketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToReceipt(ticket);
            }


        });
    }

    private void goToReceipt(Ticket ticket) {
        Toast.makeText(context, ticket.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return listTicket.size();
    }


    public class ViewHolderTicket extends RecyclerView.ViewHolder {

        private TextView name, cost;
        private ImageView imageView;
        private RelativeLayout ticketLayout;

        public ViewHolderTicket(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.nameTicket);
            cost = (TextView) itemView.findViewById(R.id.costTicket);
            imageView = (ImageView)  itemView.findViewById(R.id.imgPlace);
            ticketLayout = (RelativeLayout) itemView.findViewById(R.id.layoutTicket);

        }
    }
}
