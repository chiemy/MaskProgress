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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

public class HollowCircleProgress extends IMaskProgress{
	private int mMaxProgress;
	private int mMinProgress;
	public HollowCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public HollowCircleProgress(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HollowCircleProgress(Context context) {
		this(context, null);
	}
	
	private Paint bgPaint;
	private Paint proPaint;
	private Paint clearPaint;
	private GradientDrawable mProgressDrawable, mCompleteDrawable;
	private void init(Context context, AttributeSet attrs) {
		bgPaint = new Paint();
		bgPaint.setStyle(Style.FILL);
		bgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		//bgPaint.setXfermode(sModes[8]);
		
		proPaint = new Paint();
		proPaint.setStyle(Style.FILL);
		proPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		
		clearPaint = new Paint();
		
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
            bgPaint.setColor(colorProgress);
            proPaint.setColor(colorProgress);

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
	
	private RectF oval;
	private Bitmap bgBitmap;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int min = Math.min(w, h);
		float radius = min / 4;
		float arcRadius = radius - 10;
		float centerX = w / 2;
		float centerY = h / 2;
		
		oval = new RectF(centerX - arcRadius, centerY - arcRadius, centerX + arcRadius, centerY + arcRadius);
		bgBitmap =  Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvasBg = new Canvas(bgBitmap);
		canvasBg.drawRect(0, 0, w, h, bgPaint);
		
		Bitmap circleBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvasCircle = new Canvas(circleBitmap);
		canvasCircle.drawCircle(centerX, centerY, radius, proPaint);

		clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		Canvas canvas = new Canvas(bgBitmap);
	    canvas.drawBitmap(circleBitmap, 0, 0, clearPaint);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// progress
        if(mProgress >= mMinProgress && mProgress <= mMaxProgress) {
            drawProgress(canvas);
        }
        if(mProgress != mMaxProgress){
        	canvas.drawBitmap(bgBitmap, 0, 0, null);
        }
		super.onDraw(canvas);
	}
	
	private void drawProgress(Canvas canvas){
		float scale = 1 - (float) mProgress / (float)mMaxProgress;
        
        canvas.drawArc(oval, 0, 360*scale, true, proPaint);
	}

	private int mProgress;
	public void setProgress(int progress) {
		mProgress = progress;
        invalidate();
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
