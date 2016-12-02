package org.androidtown.termi02;

import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gangho on 2016-12-02.
 */

public class FragmentInfo {
    private ImageView mImageView;
    private TextView mTextView;
    private Fragment mFragment;

    public FragmentInfo(ImageView img, TextView txt, Fragment fragment){
        this.mImageView = img;
        this.mTextView = txt;
        this.mFragment = fragment;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(TextView textView) {
        mTextView = textView;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }
}
