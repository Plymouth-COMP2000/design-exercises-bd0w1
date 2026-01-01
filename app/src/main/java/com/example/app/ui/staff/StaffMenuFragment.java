package com.example.app.ui.staff;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.data.local.AppDbHelper;
import com.example.app.data.model.MenuItem;

import java.util.List;

public class StaffMenuFragment extends Fragment {

    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_staff_menu, container, false);

        rv = view.findViewById(R.id.rvStaffMenu);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        View addBtn = view.findViewById(R.id.btnAddMenuItem);
        addBtn.setOnClickListener(v -> {
            android.widget.Toast.makeText(requireContext(), "Add clicked", android.widget.Toast.LENGTH_SHORT).show();
            showAddDialog();
        });

        AppDbHelper db = new AppDbHelper(requireContext());
        if (db.getAllMenuItems().isEmpty()) {
            db.insertMenuItem(new MenuItem("Burger", "Beef burger", 9.99, true));
            db.insertMenuItem(new MenuItem("Salad", "Mixed salad", 6.50, true));
        }

        loadMenu();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMenu();
    }

    private void loadMenu() {
        AppDbHelper db = new AppDbHelper(requireContext());
        List<MenuItem> items = db.getAllMenuItems();

        rv.setAdapter(new StaffMenuAdapter(items, item -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete menu item?")
                    .setMessage("Delete " + item.getName() + "?")
                    .setPositiveButton("Delete", (d, which) -> {
                        db.deleteMenuItem(item.getId());
                        loadMenu();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }));
    }

    private void showAddDialog() {
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_add_menu_item, null);

        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtDesc = dialogView.findViewById(R.id.edtDesc);
        EditText edtPrice = dialogView.findViewById(R.id.edtPrice);

        new AlertDialog.Builder(requireContext())
                .setTitle("Add menu item")
                .setView(dialogView)
                .setPositiveButton("Add", (d, which) -> {
                    String name = edtName.getText().toString().trim();
                    String desc = edtDesc.getText().toString().trim();
                    String priceStr = edtPrice.getText().toString().trim();

                    if (name.isEmpty() || priceStr.isEmpty()) return;

                    double price = Double.parseDouble(priceStr);

                    AppDbHelper db = new AppDbHelper(requireContext());
                    db.insertMenuItem(new MenuItem(name, desc, price, true));
                    loadMenu();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
