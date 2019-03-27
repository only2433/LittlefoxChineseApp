package com.littlefox.chinese.edu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.CommonUtils;

public class PaddingDrawableEditText extends android.support.v7.widget.AppCompatEditText
{
	
	private int mPaddingLeft = -1;
	private int mDrawableResource = -1;
	private int mDrawablePadding = -1;
	private int mDrawableWidth = -1;
	private int mDrawableHeight = -1;
	
	private Context mContext;
	public PaddingDrawableEditText(Context context)
	{
		super(context);
		mContext = context;
	}
	
	public PaddingDrawableEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
		TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.PaddingDrawableEditText);
		setTypeArray(array);
		settingLayout();
	}
	
	public PaddingDrawableEditText(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		mContext = context;
		TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.PaddingDrawableEditText, defStyleAttr, 0);	
		setTypeArray(array);
		settingLayout();
	}
	
	private void setTypeArray(TypedArray array)
	{
		mPaddingLeft 			= array.getInt(R.styleable.PaddingDrawableEditText_pd_padding_left, 0);
		mDrawableResource 		= array.getResourceId(R.styleable.PaddingDrawableEditText_pd_add_image, 0);
		mDrawablePadding 		= array.getInt(R.styleable.PaddingDrawableEditText_pd_drawable_padding, 0);
		mDrawableWidth			= array.getInt(R.styleable.PaddingDrawableEditText_pd_image_width, 0);
		mDrawableHeight			= array.getInt(R.styleable.PaddingDrawableEditText_pd_image_height, 0);
	}
	
	private void settingLayout()
	{
		Drawable drawable = mContext.getResources().getDrawable(mDrawableResource);
		Bitmap bitmap = CommonUtils.getInstance(mContext).getBitmapFromDrawable(
				drawable, 
				CommonUtils.getInstance(mContext).getPixel(mDrawableWidth), 
				CommonUtils.getInstance(mContext).getPixel(mDrawableHeight));
		drawable = CommonUtils.getInstance(mContext).getDrawableFromBitmap(bitmap);
		this.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
		this.setPadding(CommonUtils.getInstance(mContext).getPixel(mPaddingLeft), 0, 0, 0);
		this.setCompoundDrawablePadding(CommonUtils.getInstance(mContext).getPixel(mDrawablePadding));
	}
	
	public void initDrawableImage()
	{
		this.setPadding(0, 0, 0, 0);
		this.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		this.setCompoundDrawablePadding(0);
		requestLayout();
	}
	
	public void setImage(int resource)
	{
		Drawable drawable = mContext.getResources().getDrawable(resource);
		Bitmap bitmap = CommonUtils.getInstance(mContext).getBitmapFromDrawable(
				drawable, 
				CommonUtils.getInstance(mContext).getPixel(mDrawableWidth), 
				CommonUtils.getInstance(mContext).getHeightPixel(mDrawableHeight));
		drawable = CommonUtils.getInstance(mContext).getDrawableFromBitmap(bitmap);
		this.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
		this.setPadding(CommonUtils.getInstance(mContext).getPixel(mPaddingLeft), 0, 0, 0);
		this.setCompoundDrawablePadding(CommonUtils.getInstance(mContext).getPixel(mDrawablePadding));
		requestLayout();
	}
	
	public void setImage( int width, int height, int resource)
	{
		mDrawableWidth 	= width;
		mDrawableHeight 	= height;
		setImage(resource);
	}

}
