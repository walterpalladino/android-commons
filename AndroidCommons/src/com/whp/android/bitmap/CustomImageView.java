package com.whp.android.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView {

    public CustomImageView(Context context) {
        super(context);
    }

    
    public CustomImageView(Context context, AttributeSet attrs) {
    	super(context, attrs);
    }
    
   	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
   		super(context, attrs, defStyle);
   	}
    
    OnImageChangeListiner onImageChangeListiner;

    public void setImageChangeListiner(
            OnImageChangeListiner onImageChangeListiner) {
        this.onImageChangeListiner = onImageChangeListiner;
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        if (onImageChangeListiner != null)
            onImageChangeListiner.imageChangedinView();
    }


    @SuppressWarnings("deprecation")
	@Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (onImageChangeListiner != null)
            onImageChangeListiner.imageChangedinView();
    }

    @Override
    public void setImageBitmap (Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        
        if (onImageChangeListiner != null)
            onImageChangeListiner.imageChangedinView();
    }

    public static interface OnImageChangeListiner {
        public void imageChangedinView();
    }
}