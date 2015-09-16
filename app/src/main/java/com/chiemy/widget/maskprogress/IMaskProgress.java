package com.chiemy.widget.maskprogress;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public abstract class IMaskProgress extends View{
	
	public IMaskProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public IMaskProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public IMaskProgress(Context context) {
		super(context);
	}

	public abstract void setProgress(int progress);
	
	public abstract int getMaxProgress();

	public abstract void setMaxProgress(int maxProgress);

	public abstract int getProgress();
}
