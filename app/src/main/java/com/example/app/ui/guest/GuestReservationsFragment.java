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

import com.example.app.R;
import com.example.app.data.local.AppDbHelper;
import com.example.app.data.model.Reservation;

public class GuestReservationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guest_reservations, container, false);

        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtEmail = view.findViewById(R.id.edtEmail);
        EditText edtDate = view.findViewById(R.id.edtDate);
        EditText edtTime = view.findViewById(R.id.edtTime);
        EditText edtPartySize = view.findViewById(R.id.edtPartySize);
        EditText edtNotes = view.findViewById(R.id.edtNotes);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String date = edtDate.getText().toString();
            String time = edtTime.getText().toString();
            int partySize = Integer.parseInt(edtPartySize.getText().toString());
            String notes = edtNotes.getText().toString();

            Reservation r = new Reservation(
                    name,
                    email,
                    date,
                    time,
                    partySize,
                    notes,
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
            edtNotes.setText("");
        });

        return view;
    }
}
