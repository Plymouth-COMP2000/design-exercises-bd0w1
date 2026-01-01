package com.example.app.ui.guest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GuestReservationsFragment extends Fragment {

    private RecyclerView rvGuestReservations;
    private String selectedDate = "";
    private String selectedTime = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guest_reservations, container, false);

        rvGuestReservations = view.findViewById(R.id.rvGuestReservations);
        rvGuestReservations.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadGuestReservations();

        TextInputEditText edtName = view.findViewById(R.id.edtName);
        TextInputEditText edtEmail = view.findViewById(R.id.edtEmail);
        TextInputEditText edtPartySize = view.findViewById(R.id.edtPartySize);
        Button btnSelectDate = view.findViewById(R.id.btnSelectDate);
        Button btnSelectTime = view.findViewById(R.id.btnSelectTime);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSelectDate.setOnClickListener(v -> showDatePicker(btnSelectDate));
        btnSelectTime.setOnClickListener(v -> showTimePicker(btnSelectTime));

        btnSubmit.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            int partySize = Integer.parseInt(edtPartySize.getText().toString());

            Reservation r = new Reservation(
                    name,
                    email,
                    selectedDate,
                    selectedTime,
                    partySize,
                    "",
                    "PENDING"
            );

            AppDbHelper db = new AppDbHelper(requireContext());
            db.insertReservation(r);

            Toast.makeText(requireContext(),
                    "Reservation submitted",
                    Toast.LENGTH_SHORT).show();

            edtName.setText("");
            edtEmail.setText("");
            edtPartySize.setText("");
            btnSelectDate.setText("Select Date");
            btnSelectTime.setText("Select Time");
            selectedDate = "";
            selectedTime = "";

            loadGuestReservations();
        });

        return view;
    }

    private void showDatePicker(Button btn) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(requireContext(), (view, year1, monthOfYear, dayOfMonth) -> {
            selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth);
            btn.setText(selectedDate);
        }, year, month, day).show();
    }

    private void showTimePicker(Button btn) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        new TimePickerDialog(requireContext(), (view, hourOfDay, minuteOfHour) -> {
            selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfHour);
            btn.setText(selectedTime);
        }, hour, minute, false).show();
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
