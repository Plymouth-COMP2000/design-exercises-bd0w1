package com.example.app.ui.guest.reservations;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.data.model.Reservation;
import com.google.android.material.chip.Chip;

import java.util.List;

public class GuestReservationAdapter extends RecyclerView.Adapter<GuestReservationAdapter.VH> {

    public interface OnReservationLongClickListener {
        void onReservationLongClick(Reservation reservation);
    }

    private final List<Reservation> items;
    private final OnReservationLongClickListener longClickListener;

    public GuestReservationAdapter(List<Reservation> items, OnReservationLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
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
        h.chipStatus.setText(r.getStatus());

        int statusColor = R.color.status_pending;
        switch (r.getStatus()) {
            case "APPROVED":
                statusColor = R.color.status_approved;
                break;
            case "CANCELLED":
                statusColor = R.color.status_cancelled;
                break;
        }
        h.chipStatus.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(h.itemView.getContext(), statusColor)));

        h.itemView.setOnLongClickListener(v -> {
            longClickListener.onReservationLongClick(r);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtWhen, txtParty;
        Chip chipStatus;
        VH(@NonNull View itemView) {
            super(itemView);
            txtWhen = itemView.findViewById(R.id.txtWhen);
            txtParty = itemView.findViewById(R.id.txtParty);
            chipStatus = itemView.findViewById(R.id.chipStatus);
        }
    }
}
