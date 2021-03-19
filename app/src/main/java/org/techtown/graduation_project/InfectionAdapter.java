package org.techtown.graduation_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InfectionAdapter extends RecyclerView.Adapter<InfectionAdapter.CustomViewHolder> {

    private ArrayList<InfectionData> infectIonList;

    public InfectionAdapter(ArrayList<InfectionData> infectionData) {
        this.infectIonList = infectionData;
    }
    @NonNull
    @Override
    public InfectionAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.infection_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InfectionAdapter.CustomViewHolder holder, int position) {
        holder.gubnu.setText(infectIonList.get(position).getGubnu());
        holder.defCnt.setText(infectIonList.get(position).getDefCnt());
        holder.incDec.setText(infectIonList.get(position).getIncDec());
        holder.localOccCnt.setText(infectIonList.get(position).getLocalOccCnt());
        holder.deathCnt.setText(infectIonList.get(position).getDeathCnt());
        holder.isolClearCnt.setText(infectIonList.get(position).getIsolClearCnt());
    }

    @Override
    public int getItemCount() {
        return (null != infectIonList ? infectIonList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView gubnu;
        protected TextView defCnt;
        protected TextView incDec;
        protected TextView localOccCnt;
        protected TextView deathCnt;
        protected TextView isolClearCnt;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.gubnu = (TextView) itemView.findViewById(R.id.gubun);
            this.defCnt = (TextView) itemView.findViewById(R.id.defCnt);
            this.incDec = (TextView) itemView.findViewById(R.id.incDec);
            this.localOccCnt = (TextView) itemView.findViewById(R.id.localOccCnt);
            this.deathCnt = (TextView) itemView.findViewById(R.id.deathCnt);
            this.isolClearCnt = (TextView) itemView.findViewById(R.id.isolClearCnt);

        }
    }
}
