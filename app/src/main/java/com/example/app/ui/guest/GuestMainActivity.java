package com.example.app.ui.guest;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.app.R;

public class GuestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);

        BottomNavigationView nav = findViewById(R.id.guestBottomNav);

        // default
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.guestFragmentContainer, new GuestMenuFragment())
                .commit();

        nav.setOnItemSelectedListener(item -> {
            Fragment f;
            int id = item.getItemId();

            if (id == R.id.guest_menu) f = new GuestMenuFragment();
            else if (id == R.id.guest_reservations) f = new GuestReservationsFragment();
            else f = new GuestSettingsFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.guestFragmentContainer, f)
                    .commit();

            return true;
        });
    }
}
