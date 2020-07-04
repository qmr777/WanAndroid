package com.qmr.base.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, Data>
        extends RecyclerView.Adapter<VH> {

    public static final int ITEM_TYPE_EMPTY = 4396;

    protected OnItemClickListener<Data> listener;
    protected OnItemLongClickListener<Data> longClickListener;
    protected ArrayList<Data> datalist = new ArrayList<>();


    public void setOnItemClickListener(OnItemClickListener<Data> l) {
        listener = l;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<Data> l) {
        longClickListener = l;
    }

    public void resetData(List<Data> data) {
        datalist.clear();
        datalist.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        datalist.clear();
        notifyDataSetChanged();
    }

    public ArrayList<Data> getData() {
        return datalist;
    }

    public void addData(List<Data> data) {
        if (data == null)
            return;
        int prelength = datalist.size();
        datalist.addAll(data);
        notifyItemRangeInserted(prelength, data.size());
    }

    protected Data getDataInPosition(int position) {
        if (datalist.size() > position)
            return datalist.get(position);
        return null;
    }

    public boolean removeItem(Data item) {
        //boolean flag = false;
        int position = datalist.indexOf(item);
        if (position != -1) {
            datalist.remove(item);
            notifyItemRemoved(position);
        }
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        if (listener != null)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(holder.getAdapterPosition(), datalist.get(holder.getAdapterPosition()));
                }
            });
        if (longClickListener != null)
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return longClickListener.OnItemLongClick(holder.getAdapterPosition(), datalist.get(holder.getAdapterPosition()));
                }
            });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}
