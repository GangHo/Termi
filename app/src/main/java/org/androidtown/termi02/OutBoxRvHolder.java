package org.androidtown.termi02;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gangho on 2016-11-20.
 */

public class OutBoxRvHolder extends RecyclerView.ViewHolder {

    TextView contentTxv;
    ImageView imageView;
    public OutBoxRvHolder(View itemView) {
        super(itemView);

        this.contentTxv = (TextView)itemView.findViewById(R.id.outbox_mail_text);
        this.imageView = (ImageView)itemView.findViewById(R.id.outbox_mail_img);
    }
}
