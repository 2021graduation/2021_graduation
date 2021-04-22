package org.techtown.graduation_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DisasterAdapter extends RecyclerView.Adapter<DisasterAdapter.CustomerViewHolder> {

    private ArrayList<DisasterRowData> rowlist;

    public DisasterAdapter(ArrayList<DisasterRowData> rowData) {
        this.rowlist = rowData;
    }

    @NonNull
    @Override
    public DisasterAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disaster_list, parent, false);
        CustomerViewHolder holder = new CustomerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        holder.create_data.setText(rowlist.get(position).getCreate_date());
        holder.location_name.setText(rowlist.get(position).getLocation_name());
        holder.msg.setText(rowlist.get(position).getMsg());

    }

    @Override
    public int getItemCount() {
        return rowlist.size();
    }

    public void addItem(DisasterRowData item) {
        rowlist.add(item);
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        protected TextView create_data;
        protected TextView location_name;
        protected TextView msg;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            this.create_data = itemView.findViewById(R.id.create_data);
            this.location_name = itemView.findViewById(R.id.loaction_name);
            this.msg = itemView.findViewById(R.id.msg);
        }
    }

    // search result refresh
    public void Clear() {
        int size = this.rowlist.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                rowlist.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}
