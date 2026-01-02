package com.example.app.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.R;
import com.example.app.ui.staff.StaffMainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class StaffLoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        txtResult = findViewById(R.id.txtResult);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> doLogin());
    }

    private void doLogin() {
        String u = edtUsername.getText().toString().trim();
        String p = edtPassword.getText().toString().trim();

        if (u.isEmpty() || p.isEmpty()) {
            txtResult.setText("Enter username and password.");
            return;
        }

        String url = "http://10.240.72.69/comp2000/coursework/read_all_users/10864465";

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray users = response.getJSONArray("users");
                        boolean ok = false;

                        for (int i = 0; i < users.length(); i++) {
                            JSONObject user = users.getJSONObject(i);

                            String apiUsername = user.optString("username");
                            String apiPassword = user.optString("password");

                            if (apiUsername.equals(u) && apiPassword.equals(p)) {
                                ok = true;
                                break;
                            }
                        }

                        if (ok) {
                            txtResult.setText("Login success.");
                            startActivity(new Intent(this, StaffMainActivity.class));
                            finish();
                        } else {
                            txtResult.setText("Invalid credentials.");
                        }

                    } catch (Exception e) {
                        txtResult.setText("Parse error: " + e.getMessage());
                    }
                },
                error -> {
                    txtResult.setText("Network error: " + error.toString());
                }
        );

        Volley.newRequestQueue(this).add(req);
    }
}
