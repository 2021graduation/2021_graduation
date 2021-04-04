package org.techtown.sqliteexample2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.CustomerViewHolder>{

    private ArrayList<Data> mlist;
    OnDataClickListener listener;


    public DataAdapter(ArrayList<Data> Data) {
        this.mlist = Data;
    }

    @NonNull
    @Override
    public DataAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_list, parent, false);
        CustomerViewHolder holder = new CustomerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.CustomerViewHolder holder, int position) {
       holder.idnum.setText(mlist.get(position).getId());
       holder.sData.setText(mlist.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        protected TextView idnum;
        protected TextView sData;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.idnum = (TextView) itemView.findViewById(R.id.idnum);
            this.sData = (TextView) itemView.findViewById(R.id.sData);
        }
    }

    // search result refresh
    public void Clearmlist() {
        int size = this.mlist.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mlist.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }
}
