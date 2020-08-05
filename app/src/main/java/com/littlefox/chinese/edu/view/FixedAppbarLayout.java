package com.littlefox.chinese.edu.view;

import android.content.Context;
import com.google.android.material.appbar.AppBarLayout;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

public class FixedAppbarLayout extends AppBarLayout
{
	private float yFraction = 0;
	private ViewTreeObserver.OnPreDrawListener preDrawListener = null;
	
	public FixedAppbarLayout(Context context)
	{
		super(context);
	}
	
	public FixedAppbarLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	
	
	public void setYFraction(float fraction) {

        this.yFraction = fraction;

        if (getHeight() == 0) {
            if (preDrawListener == null) {
                preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
                        setYFraction(yFraction);
                        return true;
                    }
                };
                getViewTreeObserver().addOnPreDrawListener(preDrawListener);
            }
            return;
        }

        float translationY = getHeight() * fraction;
        setTranslationY(translationY);
    }

    public float getYFraction() {
        return this.yFraction;
    }


}
