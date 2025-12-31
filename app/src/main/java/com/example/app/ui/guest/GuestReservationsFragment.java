package com.example.app.ui.guest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.data.local.AppDbHelper;
import com.example.app.data.model.Reservation;
import com.example.app.ui.guest.reservations.GuestReservationAdapter;

import java.util.List;

public class GuestReservationsFragment extends Fragment {

    private RecyclerView rvGuestReservations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guest_reservations, container, false);

        rvGuestReservations = view.findViewById(R.id.rvGuestReservations);
        rvGuestReservations.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadGuestReservations();

        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtEmail = view.findViewById(R.id.edtEmail);
        EditText edtDate = view.findViewById(R.id.edtDate);
        EditText edtTime = view.findViewById(R.id.edtTime);
        EditText edtPartySize = view.findViewById(R.id.edtPartySize);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String date = edtDate.getText().toString();
            String time = edtTime.getText().toString();
            int partySize = Integer.parseInt(edtPartySize.getText().toString());

            Reservation r = new Reservation(
                    name,
                    email,
                    date,
                    time,
                    partySize,
                    "",        // no notes anymore
                    "PENDING"
            );

            AppDbHelper db = new AppDbHelper(requireContext());
            db.insertReservation(r);

            Toast.makeText(requireContext(),
                    "Reservation submitted",
                    Toast.LENGTH_SHORT).show();

            edtName.setText("");
            edtEmail.setText("");
            edtDate.setText("");
            edtTime.setText("");
            edtPartySize.setText("");

            loadGuestReservations();
        });

        return view;
    }

    private void loadGuestReservations() {
        AppDbHelper db = new AppDbHelper(requireContext());
        List<Reservation> res = db.getAllReservations();
        rvGuestReservations.setAdapter(new GuestReservationAdapter(res));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadGuestReservations();
    }
}
