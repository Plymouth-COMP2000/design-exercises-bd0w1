package com.example.app.ui.staff;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.app.R;

public class StaffMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);

        Toolbar toolbar = findViewById(R.id.staffToolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView nav = findViewById(R.id.staffBottomNav);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.staffFragmentContainer, new StaffMenuFragment())
                .commit();
        getSupportActionBar().setTitle("Staff Menu");

        nav.setOnItemSelectedListener(item -> {
            Fragment f = null;
            String title = "";
            int id = item.getItemId();

            if (id == R.id.staff_menu) {
                f = new StaffMenuFragment();
                title = "Staff Menu";
            } else if (id == R.id.staff_reservations) {
                f = new StaffReservationsFragment();
                title = "Manage Reservations";
            } else if (id == R.id.staff_settings) {
                f = new StaffSettingsFragment();
                title = "Settings";
            }

            if (f != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.staffFragmentContainer, f)
                        .commit();
                getSupportActionBar().setTitle(title);
            }

            return true;
        });
    }
}
