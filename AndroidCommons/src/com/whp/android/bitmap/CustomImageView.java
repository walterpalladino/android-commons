/*
 * Copyright 2013 Walter Hugo Palladino
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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