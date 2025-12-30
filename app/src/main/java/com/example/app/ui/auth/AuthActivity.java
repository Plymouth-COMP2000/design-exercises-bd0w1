package com.example.app.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.ui.guest.GuestMainActivity;
import com.example.app.ui.remote.ApiTestActivity;
import com.example.app.ui.staff.StaffMainActivity;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        findViewById(R.id.btnStaff).setOnClickListener(v -> {
            startActivity(new Intent(this, StaffMainActivity.class));
        });

        findViewById(R.id.btnGuest).setOnClickListener(v -> {
            startActivity(new Intent(this, GuestMainActivity.class));
        });

        findViewById(R.id.btnApiTest).setOnClickListener(v -> {
            startActivity(new Intent(this, ApiTestActivity.class));
        });
    }
}
