package org.techtown.graduation_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    // 아이템을 담을 배열리스트
    private ArrayList<MainData> arrayList;

    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    // 처음으로 생성될때 생명주기를 뜻함
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    // 실제 추가될때에 대한 생명주기
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.tv_sgguNm.setText(arrayList.get(position).getSgguNm());
        holder.tv_sidoNm.setText(arrayList.get(position).getSidoNm());
        holder.tv_yadmNm.setText(arrayList.get(position).getYadmNm());
        holder.tv_telno.setText(arrayList.get(position).getTelno());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.tv_sgguNm.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_profile;
        protected TextView tv_sgguNm;
        protected TextView tv_sidoNm;
        protected TextView tv_yadmNm;
        protected TextView tv_telno;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.profile);
            this.tv_sgguNm = (TextView) itemView.findViewById(R.id.tv_sgguNm);
            this.tv_sidoNm = (TextView) itemView.findViewById(R.id.tv_sidoNm);
            this.tv_yadmNm = (TextView) itemView.findViewById(R.id.tv_yadmNm);
            this.tv_telno = (TextView) itemView.findViewById(R.id.tv_telno);
        }
    }
}
