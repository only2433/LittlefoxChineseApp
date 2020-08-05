package com.littlefox.chinese.edu.view.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.State;
import android.view.View;

/**
 * RecyclerView 는 Divider 가 없어서 그려줘야한다.  Divider 를 그려주는 Decoration
 * @author 정재현
 *
 */
public class LineDividerItemDecoration extends RecyclerView.ItemDecoration
{
	protected int mLineSize = -1;
	protected Context mContext;
	protected Paint mPaint;
	protected int DEFAULT_LIST_ITEM_SIZE = 0;
	
	public LineDividerItemDecoration(Context context , int color, int lineSize)
	{
		mContext 	= context;
		mPaint		= new Paint();
		mPaint.setColor(color);
		mPaint.setAntiAlias(true);
		mLineSize = lineSize;
	}
	
	public LineDividerItemDecoration(Context context , int color, int lineSize, int defaultLimitItemSize)
	{
		mContext 	= context;
		mPaint		= new Paint();
		mPaint.setColor(color);
		mPaint.setAntiAlias(true);
		mLineSize = lineSize;
		DEFAULT_LIST_ITEM_SIZE = defaultLimitItemSize;
	}

	@Override
	public void onDrawOver(Canvas canvas, RecyclerView parent, State state)
	{
		int childCount = parent.getChildCount();
		
		for(int i = 0; i < childCount; i++)
		{
			View childview = parent.getChildAt(i);
			RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)childview.getLayoutParams();
			
			if(DEFAULT_LIST_ITEM_SIZE != 0 && childview.getHeight() < DEFAULT_LIST_ITEM_SIZE)
			{
				continue;
			}
			
			if(i == childCount -1)
			{
				continue;
			}
			
			int top = childview.getBottom() + params.bottomMargin;
			int bottom = top + mLineSize;
			
			canvas.drawRect(0, top, parent.getWidth(), bottom, mPaint);
		}
	}

	
}