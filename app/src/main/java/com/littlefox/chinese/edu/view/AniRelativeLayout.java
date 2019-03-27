package com.littlefox.chinese.edu.view;

    import android.content.Context;
    import android.util.AttributeSet;
    import android.view.ViewTreeObserver;
    import android.widget.RelativeLayout;

    /**
     * Created by paveld on 4/13/14.
     */
    public class AniRelativeLayout extends RelativeLayout {

        private float yFraction = 0;
        private float xFraction = 0;

        public AniRelativeLayout(Context context) {
            super(context);
        }

        public AniRelativeLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public AniRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        private ViewTreeObserver.OnPreDrawListener preDrawListener = null;

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
        
        
        public void setXFraction(float fraction) {

            this.xFraction = fraction;

            if (getHeight() == 0) {
                if (preDrawListener == null) {
                    preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
                            setXFraction(xFraction);
                            return true;
                        }
                    };
                    getViewTreeObserver().addOnPreDrawListener(preDrawListener);
                }
                return;
            }

            float translationX = getWidth() * fraction;
            setTranslationX(translationX);
        }

        public float getXFraction() {
            return this.xFraction;
        }
    }
