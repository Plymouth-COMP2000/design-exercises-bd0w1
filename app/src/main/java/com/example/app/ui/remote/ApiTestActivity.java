package com.example.app.ui.remote;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.R;

public class ApiTestActivity extends AppCompatActivity {

    private TextView txtResult;

    private static final String STUDENT_ID = "10864465";
    private static final String BASE_URL = "http://10.240.72.69/comp2000/coursework/";
    private static final String READ_ALL_USERS = BASE_URL + "read_all_users/" + STUDENT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_test);

        txtResult = findViewById(R.id.txtResult);

        findViewById(R.id.btnTestApi).setOnClickListener(v -> fetchUsers());
    }

    private void fetchUsers() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                READ_ALL_USERS,
                response -> {
                    // Success - update UI (Volley callback runs on main thread)
                    txtResult.setText("SUCCESS:\n" + response);
                },
                error -> {
                    String status = "unknown";
                    String body = "no body";

                    if (error.networkResponse != null) {
                        status = String.valueOf(error.networkResponse.statusCode);
                        try {
                            body = new String(error.networkResponse.data, "UTF-8");
                        } catch (Exception e) {
                            body = "could not decode body";
                        }
                    }

                    txtResult.setText("ERROR:\nStatus=" + status + "\nBody=\n" + body);
                }
        );

        queue.add(request);
    }
}
