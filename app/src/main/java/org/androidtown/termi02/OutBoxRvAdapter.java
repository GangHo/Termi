package org.androidtown.termi02;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Gangho on 2016-11-20.
 */

public class OutBoxRvAdapter extends RecyclerView.Adapter<OutBoxRvHolder> {
    private ArrayList<RvItem> outBoxRvItemArrayList;
    private Context context;

    public OutBoxRvAdapter(ArrayList<RvItem> inBoxRvItemArrayList){
        this.outBoxRvItemArrayList = inBoxRvItemArrayList;
    }

    @Override
    public OutBoxRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.outbox_rv,parent,false);

        return new OutBoxRvHolder(view);
    }

    @Override
    public void onBindViewHolder(OutBoxRvHolder holder, int position) {
        RvItem item = outBoxRvItemArrayList.get(position);

        holder.contentTxv.setText(item.mText);
        holder.imageView.setImageResource(item.mImage);
    }

    @Override
    public int getItemCount() {
        return outBoxRvItemArrayList.size();
    }
}
