package org.techtown.sqliteexample2;

import android.view.View;

public interface OnDataClickListener {
    public void onItemClick(DataAdapter.CustomerViewHolder holder, DataAdapter.CustomerViewHolder customerViewHolder, View view, int position);
}
