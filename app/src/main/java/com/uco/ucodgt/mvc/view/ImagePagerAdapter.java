package com.uco.ucodgt.mvc.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


public class ImagePagerAdapter extends PagerAdapter {

    private Context context;
    private int[] images;
    private OnImageClickListener onImageClickListener;

    private Handler handler;

    public ImagePagerAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public interface OnImageClickListener {
        void onImageClick(int position);
    }
    public void setOnImageClickListener(OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(com.uco.ucodgt.R.layout.item_image, container, false);
        ImageView imageView = view.findViewById(com.uco.ucodgt.R.id.imageView);
        imageView.setImageResource(images[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageClickListener != null) {
                    onImageClickListener.onImageClick(position);
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
