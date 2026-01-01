package com.example.app.ui.guest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.data.local.AppDbHelper;
import com.example.app.data.model.MenuItem;
import com.example.app.ui.guest.menu.MenuAdapter;

import java.util.List;

public class GuestMenuFragment extends Fragment {

    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_menu, container, false);

        rv = view.findViewById(R.id.rvMenu);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        loadMenuFromDb();

        return view;
    }

    private void loadMenuFromDb() {
        AppDbHelper db = new AppDbHelper(requireContext());
        List<MenuItem> items = db.getAllMenuItems();
        rv.setAdapter(new MenuAdapter(items));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMenuFromDb();
    }
}
