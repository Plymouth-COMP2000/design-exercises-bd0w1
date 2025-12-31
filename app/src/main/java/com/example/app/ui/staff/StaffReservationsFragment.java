package com.example.app.ui.staff;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.data.local.AppDbHelper;
import com.example.app.data.model.Reservation;

import java.util.List;

public class StaffReservationsFragment extends Fragment {

    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_reservations, container, false);

        rv = view.findViewById(R.id.rvStaffReservations);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadReservations();
    }

    private void loadReservations() {
        AppDbHelper db = new AppDbHelper(requireContext());
        List<Reservation> res = db.getAllReservations();

        rv.setAdapter(new StaffReservationAdapter(res, this::onReservationLongClick));
    }

    private void onReservationLongClick(Reservation r) {
        AppDbHelper db = new AppDbHelper(requireContext());
        new AlertDialog.Builder(requireContext())
            .setTitle("Manage reservation")
            .setItems(new CharSequence[]{"Approve", "Cancel", "Delete"}, (d, which) -> {
                if (which == 0) {
                    db.updateReservationStatus(r.getId(), "APPROVED");
                } else if (which == 1) {
                    db.updateReservationStatus(r.getId(), "CANCELLED");
                } else {
                    db.deleteReservation(r.getId());
                }
                loadReservations();
            })
            .show();
    }
}
