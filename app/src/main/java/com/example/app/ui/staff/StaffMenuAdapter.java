package com.example.app.ui.staff;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.data.model.MenuItem;

import java.util.List;

public class StaffMenuAdapter extends RecyclerView.Adapter<StaffMenuAdapter.VH> {

    public interface OnMenuLongClick {
        void onLongClick(MenuItem item);
    }

    private final List<MenuItem> items;
    private final OnMenuLongClick onLongClick;

    public StaffMenuAdapter(List<MenuItem> items, OnMenuLongClick onLongClick) {
        this.items = items;
        this.onLongClick = onLongClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_staff, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        MenuItem item = items.get(position);
        h.txtName.setText(item.getName());
        h.txtDesc.setText(item.getDescription());
        h.txtPrice.setText("£" + String.format("%.2f", item.getPrice()));

        h.itemView.setOnLongClickListener(v -> {
            onLongClick.onLongClick(item);
            return true;
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtName, txtDesc, txtPrice;

        VH(@NonNull View v) {
            super(v);
            txtName = v.findViewById(R.id.txtName);
            txtDesc = v.findViewById(R.id.txtDesc);
            txtPrice = v.findViewById(R.id.txtPrice);
        }
    }
}
