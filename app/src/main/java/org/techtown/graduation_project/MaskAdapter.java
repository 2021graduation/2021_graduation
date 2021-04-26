package org.techtown.graduation_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MaskAdapter extends RecyclerView.Adapter<MaskAdapter.CustomerViewHolder> {

    private ArrayList<MaskData> masklist;

    public MaskAdapter(ArrayList<MaskData> maskData) {
        this.masklist = maskData;
    }

    @NonNull
    @Override
    public MaskAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mask_list, parent, false);
        CustomerViewHolder holder = new CustomerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MaskAdapter.CustomerViewHolder holder, int position) {

        holder.ITEM_NAME.setText(masklist.get(position).getITEM_NAME());
        holder.ENTP_NAME.setText(masklist.get(position).getENTP_NAME());
        holder.ITEM_PERMIT_DATE.setText(masklist.get(position).getITEM_PERMIT_DATE());

    }

    @Override
    public int getItemCount() {
        return masklist.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        protected TextView ITEM_NAME;
        protected TextView ENTP_NAME;
        protected TextView ITEM_PERMIT_DATE;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ITEM_NAME = (TextView) itemView.findViewById(R.id.ITEM_NAME);
            this.ENTP_NAME = (TextView) itemView.findViewById(R.id.ENTP_NAME);
            this.ITEM_PERMIT_DATE = (TextView) itemView.findViewById(R.id.ITEM_PERMIT_DATE);
        }
    }

    // search result refresh
    public void ClearMaskList() {
        int size = this.masklist.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                masklist.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}
