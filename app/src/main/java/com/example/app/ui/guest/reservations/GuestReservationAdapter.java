package com.example.app.ui.guest.reservations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.data.model.Reservation;

import java.util.List;

public class GuestReservationAdapter extends RecyclerView.Adapter<GuestReservationAdapter.VH> {

    private final List<Reservation> items;

    public GuestReservationAdapter(List<Reservation> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation_guest, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Reservation r = items.get(position);
        h.txtWhen.setText(r.getDate() + " " + r.getTime());
        h.txtParty.setText("Party: " + r.getPartySize());
        h.txtStatus.setText("Status: " + r.getStatus());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtWhen, txtParty, txtStatus;
        VH(@NonNull View itemView) {
            super(itemView);
            txtWhen = itemView.findViewById(R.id.txtWhen);
            txtParty = itemView.findViewById(R.id.txtParty);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }
}
