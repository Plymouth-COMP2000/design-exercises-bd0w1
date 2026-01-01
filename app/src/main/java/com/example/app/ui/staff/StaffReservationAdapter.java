package com.example.app.ui.staff;

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

public class StaffReservationAdapter
        extends RecyclerView.Adapter<StaffReservationAdapter.VH> {

    public interface OnReservationLongClick {
        void onLongClick(Reservation r);
    }

    private final List<Reservation> items;
    private final OnReservationLongClick onLongClick;

    public StaffReservationAdapter(List<Reservation> items, OnReservationLongClick onLongClick) {
        this.items = items;
        this.onLongClick = onLongClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation_staff, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Reservation r = items.get(pos);
        h.txtGuest.setText(r.getGuestName() + " (" + r.getGuestEmail() + ")");
        h.txtDetails.setText(
                r.getDate() + " " + r.getTime() +
                " | Party: " + r.getPartySize()
        );
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
            onLongClick.onLongClick(r);
            return true;
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtGuest, txtDetails;
        Chip chipStatus;

        VH(View v) {
            super(v);
            txtGuest = v.findViewById(R.id.txtGuest);
            txtDetails = v.findViewById(R.id.txtDetails);
            chipStatus = v.findViewById(R.id.chipStatus);
        }
    }
}
