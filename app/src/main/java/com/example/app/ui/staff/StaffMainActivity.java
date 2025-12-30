package com.example.app.ui.staff;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.app.R;

public class StaffMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);

        BottomNavigationView nav = findViewById(R.id.staffBottomNav);

        // default
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.staffFragmentContainer, new StaffMenuFragment())
                .commit();

        nav.setOnItemSelectedListener(item -> {
            Fragment f;
            int id = item.getItemId();

            if (id == R.id.staff_menu) f = new StaffMenuFragment();
            else if (id == R.id.staff_reservations) f = new StaffReservationsFragment();
            else f = new StaffSettingsFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.staffFragmentContainer, f)
                    .commit();

            return true;
        });
    }
}
