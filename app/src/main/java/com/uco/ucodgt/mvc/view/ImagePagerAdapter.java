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


/**
 * A PagerAdapter implementation for displaying images in a ViewPager for the Admin/Client/Worker menu.
 * It will show a flow of images on menus
 * @author Alfonso de la torre
 */
public class ImagePagerAdapter extends PagerAdapter {

    private Context context;
    private int[] images;
    private OnImageClickListener onImageClickListener;

    private Handler handler;

    /**
     * Constructs an ImagePagerAdapter.
     *
     * @param context The context in which the adapter is being used.
     * @param images An array of image resource IDs to be displayed.
     */
    public ImagePagerAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
        this.handler = new Handler(Looper.getMainLooper());
    }

    /**
     * Returns the number of images in the adapter.
     *
     * @return The number of images.
     */
    @Override
    public int getCount() {
        return images.length;
    }


    /**
     * Determines whether a particular object represents a page view.
     *
     * @param view The view to check for association with a page.
     * @param object The object to check for association with the view.
     * @return True if the view is associated with the specified object.
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    /**
     * Interface definition for a callback to be invoked when an image is clicked.
     */
    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    /**
     * Sets the listener to be invoked when an image is clicked.
     *
     * @param listener The listener to set.
     */
    public void setOnImageClickListener(OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }


    /**
     * Creates the page for the given position.
     *
     * @param container The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page.
     */
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


    /**
     * Removes a page for the given position.
     *
     * @param container The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param object The Object representing the page to be removed.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
