package org.techtown.graduation_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.graduation_project.R;
import org.techtown.graduation_project.Table;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.CustomerViewHolder>{

    private ArrayList<Table> mlist;
    private OnItemClickListener mListener = null ;
    private OnItemLongClickListener mLongListener = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, TextView sData);
    }

    public interface OnItemLongClickListener
    {
        void onItemLongClick(View v, int pos, TextView sData);
    }


    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.mLongListener = listener;
    }

    public TableAdapter(ArrayList<Table> Table) {
        this.mlist = Table;
    }

    @NonNull
    @Override
    public TableAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list, parent, false);
        CustomerViewHolder holder = new CustomerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.CustomerViewHolder holder, int position) {
        holder.table.setText(mlist.get(position).getDate());
        holder.MyLatLng.setText(mlist.get(position).getLatlng_count());
        holder.disasterLatLng.setText(mlist.get(position).getDisaster_count());
    }

    @Override
    public int getItemCount() {

        return mlist.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        protected TextView table;
        protected TextView MyLatLng;
        protected TextView disasterLatLng;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.table = (TextView) itemView.findViewById(R.id.table_name);
            this.MyLatLng = (TextView) itemView.findViewById(R.id.latlng_count);
            this.disasterLatLng = (TextView) itemView.findViewById(R.id.disaster_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        if(mListener !=null){
                            mListener.onItemClick(v,position, table);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        if(mLongListener !=null){
                            mLongListener.onItemLongClick(v,position,table);
                        }
                    }
                    return true;
                }
            });
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
