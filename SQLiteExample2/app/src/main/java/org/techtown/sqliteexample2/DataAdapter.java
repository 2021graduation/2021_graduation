package org.techtown.sqliteexample2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.CustomerViewHolder>{

    private ArrayList<Data> mlist;
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
       holder.sData.setText(mlist.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return mlist.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        protected TextView sData;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.sData = (TextView) itemView.findViewById(R.id.sData);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        if(mListener !=null){
                            mListener.onItemClick(v,position, sData);
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
                            mLongListener.onItemLongClick(v,position,sData);
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
