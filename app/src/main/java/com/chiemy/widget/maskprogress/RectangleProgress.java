/*
 * The MIT License (MIT)
 * Copyright (c) [2015] [chiemy]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.chiemy.widget.maskprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

public class RectangleProgress extends IMaskProgress{
	private int mMaxProgress;
	private int mMinProgress;
	public RectangleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public RectangleProgress(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RectangleProgress(Context context) {
		this(context, null);
	}
	
	private GradientDrawable mProgressDrawable, mCompleteDrawable;
	private void init(Context context, AttributeSet attrs) {
        mMinProgress = 0;
        mMaxProgress = 100;

        mProgressDrawable = (GradientDrawable) getDrawable(R.drawable.rect_progress).mutate();

        mCompleteDrawable = (GradientDrawable) getDrawable(R.drawable.rect_complete).mutate();
        initAttributes(context, attrs);
    }
	
	private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.RectangleProgress);
        if (attr == null) {
            return;
        }
        try {
            int colorProgress = attr.getColor(R.styleable.RectangleProgress_colorProgress, Color.parseColor("#99000000"));
            mProgressDrawable.setColor(colorProgress);

            int colorComplete = attr.getColor(R.styleable.RectangleProgress_colorComplete, Color.parseColor("#99000000"));
            mCompleteDrawable.setColor(colorComplete);
        } finally {
            attr.recycle();
        }
    }
	
	protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }
	
	protected Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }
	
	@Override
	protected void onDraw(Canvas canvas) {
		// progress
        if(mProgress >= mMinProgress && mProgress <= mMaxProgress) {
            drawProgress(canvas);
        }

		super.onDraw(canvas);
	}
	
	private int mProgress;
	public void setProgress(int progress) {
		mProgress = progress;
        invalidate();
	}
	
	private void drawProgress(Canvas canvas){
		float scale = (float) mProgress / (float)mMaxProgress;
        float indicatorHeight = (float) getMeasuredHeight() * scale;

        mProgressDrawable.setBounds(0, (int) indicatorHeight, getMeasuredWidth(), getMeasuredHeight());
        mProgressDrawable.draw(canvas);
	}
	
	public int getMaxProgress() {
		return mMaxProgress;
	}

	public void setMaxProgress(int mMaxProgress) {
		this.mMaxProgress = mMaxProgress;
	}

	public int getProgress() {
		return mProgress;
	}

}
