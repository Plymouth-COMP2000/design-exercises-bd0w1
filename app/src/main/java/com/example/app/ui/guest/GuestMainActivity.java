package com.example.app.ui.guest;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.app.R;

public class GuestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);

        Toolbar toolbar = findViewById(R.id.guestToolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView nav = findViewById(R.id.guestBottomNav);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.guestFragmentContainer, new GuestMenuFragment())
                .commit();
        getSupportActionBar().setTitle("Guest Menu");

        nav.setOnItemSelectedListener(item -> {
            Fragment f = null;
            String title = "";
            int id = item.getItemId();

            if (id == R.id.guest_menu) {
                f = new GuestMenuFragment();
                title = "Guest Menu";
            } else if (id == R.id.guest_reservations) {
                f = new GuestReservationsFragment();
                title = "Your Reservations";
            } else if (id == R.id.guest_settings) {
                f = new GuestSettingsFragment();
                title = "Settings";
            }

            if (f != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.guestFragmentContainer, f)
                        .commit();
                getSupportActionBar().setTitle(title);
            }

            return true;
        });
    }
}
