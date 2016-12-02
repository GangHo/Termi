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

public class InBoxRvAdapter extends RecyclerView.Adapter<InBoxRvHolder> {
    private ArrayList<RvItem> inBoxRvItemArrayList;
    private Context context;

    public InBoxRvAdapter(ArrayList<RvItem> inBoxRvItemArrayList){
        this.inBoxRvItemArrayList = inBoxRvItemArrayList;
    }

    @Override
    public InBoxRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_rv,parent,false);

        return new InBoxRvHolder(view);
    }

    @Override
    public void onBindViewHolder(InBoxRvHolder holder, int position) {
        RvItem item = inBoxRvItemArrayList.get(position);

        holder.contentTxv.setText(item.mText);
        holder.imageView.setImageResource(item.mImage);
    }

    @Override
    public int getItemCount() {
        return inBoxRvItemArrayList.size();
    }
}
