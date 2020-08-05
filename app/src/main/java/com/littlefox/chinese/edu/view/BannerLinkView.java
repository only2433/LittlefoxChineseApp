package com.littlefox.chinese.edu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.bumptech.glide.Glide;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.object.result.BannerLinkResult;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 *  가로 스크롤로 페이지 이동을 하며 클릭했을때 해당 링크로 보내주는 커스텀 뷰
 * @author 정재현
 *
 */
public class BannerLinkView extends RelativeLayout 
{
	
	public interface OnBannerClickListener
	{
		void OnBannerItemClick(String url);
	}
	
	private static final int DEFAULT_VIEW_PAGER_WIDTH 			= 940;
	private static final int DEFAULT_VIEW_PAGER_HEIGHT 			= 400;
	private static final int DEFAULT_INDICATOR_SIZE				= 30;
	private static final int DEFAULT_INDICATOR_TOP 				= 370;
	private static int DIFF_LAYOUT_IMAGE						= 0;
	
	private static final int AVAILABLE_TAP_UP_SIZE				= 15;
	
	private static final String TAG_INDICATOR = "indicator";
	
	private ScalableLayout _BaseScaleLayout;

	private ViewPager _ViewPager;
	
	private Context mContext;
	
	/**
	 * View의 각각의 정보
	 */
	private int mBaseWidth				= 0;
	private int mBaseHeight 			= 0;
	private int mImageWidth 			= 0;
	private int mImageHeight 			= 0;
	private int mIndicatorSize			= 0;
	private int mIndicatorTop			= 0;
	
	private boolean isIndicatorHave = false;
	private List<BannerLinkResult> mBannerLinkList;
	private LayoutInflater mLayoutInflater;
	private ImagePagerAdapter mImagePagerAdapter;
	private OnBannerClickListener mOnBannerClickListener;
	private int mCurrentPosition = 0;
	private int mDownPointX = 0;

	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	
	public BannerLinkView(Context context)
	{
		super(context);
		Log.i("");
		initView(context);
	}
	
	public BannerLinkView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		Log.i("");
		initView(context);
		TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ViewPagerLinkView);
		setTypeArray(typedArray);
	}
	
	public BannerLinkView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		Log.i("");
		initView(context);
		TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ViewPagerLinkView, defStyleAttr, 0);
		setTypeArray(typedArray);
	}
	
	private void initView(Context context)
	{
		mContext = context;
		mLayoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = mLayoutInflater.inflate(R.layout.banner_view, this, false);
		addView(view);
		_BaseScaleLayout 	= (ScalableLayout)view.findViewById(R.id.base_scalable_layout);
		_ViewPager			= (ViewPager) view.findViewById(R.id.link_view_pager);
		_ViewPager.setOnTouchListener(onTouchPager);
		
	}
	

	
	/**
	 * XML 에서 지정해둔 정보를 가져온다.
	 * @param typedArray  XML 정보
	 */
	private void setTypeArray(TypedArray typedArray)
	{
		mBaseWidth				= typedArray.getInt(R.styleable.ViewPagerLinkView_link_base_width, 0);
		mBaseHeight				= typedArray.getInt(R.styleable.ViewPagerLinkView_link_base_height, 0);
		mImageWidth 			= typedArray.getInt(R.styleable.ViewPagerLinkView_link_image_width, DEFAULT_VIEW_PAGER_WIDTH);
		mImageHeight			= typedArray.getInt(R.styleable.ViewPagerLinkView_link_image_height, DEFAULT_VIEW_PAGER_HEIGHT);
		mIndicatorSize			= typedArray.getInt(R.styleable.ViewPagerLinkView_link_indicator_size, DEFAULT_INDICATOR_SIZE);
		mIndicatorTop			= typedArray.getInt(R.styleable.ViewPagerLinkView_link_indicator_top, DEFAULT_INDICATOR_TOP);

		typedArray.recycle();
		
		initBaseLayout();
	}
	
	private void initBaseLayout()
	{
		if(mBaseWidth != 0 && mBaseHeight != 0)
			_BaseScaleLayout.setScaleSize(mBaseWidth, mBaseHeight);
	}
	
	/**
	 * 배너 이미지 사이즈 중앙에 배너 인디케이터를 위치 시키는 메소드
	 */
	private void initLayoutParams()
	{
		DIFF_LAYOUT_IMAGE = (int) ((_BaseScaleLayout.getScaleWidth() - mImageWidth)/ 2);
		final int DEFAULT_INDICATOR_MARGIN_LEFT = CommonUtils.getInstance(mContext).getPixel(34);
		final int centerLocation = mBannerLinkList.size() > 1 ?  (mImageWidth - (mIndicatorSize * mBannerLinkList.size() + DEFAULT_INDICATOR_MARGIN_LEFT * (mBannerLinkList.size() - 1))) / 2 + DIFF_LAYOUT_IMAGE
																			:  (mImageWidth - mIndicatorSize)/2 + DIFF_LAYOUT_IMAGE;
		
		_BaseScaleLayout.moveChildView(_ViewPager, (_BaseScaleLayout.getScaleWidth() - mImageWidth)/2, (_BaseScaleLayout.getScaleHeight() - mImageHeight)/2 , mImageWidth, mImageHeight);
		
		if(isIndicatorHave)
		{
			for(int i = 0 ; i < mBannerLinkList.size() ; i++)
			{
				ImageView imageView = new ImageView(mContext);
				if(i == 0)
				{
					imageView.setImageResource(R.drawable.banner_ball_on);
				}
				else
				{
					imageView.setImageResource(R.drawable.banner_ball_off);
				}
				
				imageView.setTag(TAG_INDICATOR+i);
				imageView.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Log.i("getTag : "+ v.getTag().toString());
					}
				});
				_BaseScaleLayout.addView(imageView, centerLocation + (DEFAULT_INDICATOR_MARGIN_LEFT * i) + (mIndicatorSize * i) , mIndicatorTop, mIndicatorSize, mIndicatorSize);
			}
		}
		
	}
	
	/**
	 * 화면에 보이는 영역의 배너 포지션에 인디케이터를 표시하여 갱신한다.
	 * @param position 현재 보이는 index
	 */
	private void notifyIndicator(int position)
	{
		for(int i = 0 ; i < _BaseScaleLayout.getChildCount(); i++)
		{
			View view = _BaseScaleLayout.getChildAt(i);
			
			if(String.valueOf(view.getTag()).contains(TAG_INDICATOR))
			{
				if(view.getTag().equals(TAG_INDICATOR+position))
				{
					((ImageView)view).setImageResource(R.drawable.banner_ball_on);
				}
				else
				{
					((ImageView)view).setImageResource(R.drawable.banner_ball_off);
				}
			}
		}
	}
	
	private int getPositionBanner(String tag)
	{
		for(int i = 0 ; i < _BaseScaleLayout.getChildCount(); i++)
		{
			View view = _BaseScaleLayout.getChildAt(i);
			
			if(String.valueOf(view.getTag()).contains(TAG_INDICATOR))
			{
				if(view.getTag().equals(tag))
				{
					return i;
				}
			}
		}
		
		return 0;
			
	}
	
	public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener)
	{
		mOnBannerClickListener = onBannerClickListener;
	}
	
	private View.OnTouchListener onTouchPager = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
		try {
				
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					_ViewPager.requestDisallowInterceptTouchEvent(true);
					mDownPointX = (int) event.getX();
					Log.i("event down : "+ mDownPointX);
					return false;
				}
				if(event.getAction() == MotionEvent.ACTION_UP
						|| event.getAction() == MotionEvent.ACTION_CANCEL
						|| event.getAction() == MotionEvent.ACTION_OUTSIDE) 
				{
					Log.i("event up : "+ (int)event.getX());
					Log.i("Math.abs(downPointX - (int)event.getX() : "+ Math.abs(mDownPointX - (int)event.getX()));
					//Request parent to treat touch event.
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						if(Math.abs(mDownPointX - (int)event.getX()) <= AVAILABLE_TAP_UP_SIZE)
						{
							GoogleAnalyticsHelper.getInstance(mContext).sendCurrentEvent(Common.ANALYTICS_CATEGORY_MAIN, 
																					Common.ANALYTICS_ACTION_BANNER, 
																					(mCurrentPosition+1)+Common.ANALYTICS_LABEL_CLICK);
							mOnBannerClickListener.OnBannerItemClick(mBannerLinkList.get(mCurrentPosition).getLinkUrl());
						}
					}
					_ViewPager.requestDisallowInterceptTouchEvent(true);
					return false;
				}
			}
			catch(Throwable e) {
			
			}
			return false;
			
			
		}	
	};
	

	private void visibleViewPager()
	{
		Log.i("");
		mImagePagerAdapter = new ImagePagerAdapter();
		_ViewPager.setAdapter(mImagePagerAdapter);
		_ViewPager.addOnPageChangeListener(onPageChangeListener);
	}
	
	public void setBannerInfomation(List<BannerLinkResult> bannerList)
	{
		mBannerLinkList = bannerList;
		
		if(mBannerLinkList.size() != 0)
		{
			isIndicatorHave = true;
		}
		
		initLayoutParams();
		visibleViewPager();
	}

	/**
	 * 화면에 보이는 이미지 Adapter (추후 통신을 하여 이미지를 받아오는 부분 구현 추가해야함)
	 * @author 정재현
	 *
	 */
	private class ImagePagerAdapter extends PagerAdapter
	{
		public ImagePagerAdapter()
		{
			super();
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, final int position)
		{
			Log.i("position : "+position);
			ImageView imageView = null;
			
			imageView = new ImageView(mContext);
			imageView.setScaleType(ScaleType.FIT_XY);

			Glide.with(mContext)
					.load(mBannerLinkList.get(position).getImageUrl())
					.transition(withCrossFade())
					.into(imageView);

			
			container.addView(imageView, 0);
			
		
			
			return imageView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View)object);
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return mBannerLinkList.size();
		}

		@Override
		public boolean isViewFromObject(View pager, Object object)
		{
			return pager == object ;
		}
		
		@Override
		public void finishUpdate(ViewGroup container){}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader){}

		@Override
		public void startUpdate(ViewGroup container){}
		
	}
	

	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener()
	{
		
		@Override
		public void onPageSelected(int position)
		{
			mCurrentPosition = position;
			if(isIndicatorHave)
			{
				notifyIndicator(position);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2){}
		@Override
		public void onPageScrollStateChanged(int position){}
	};
}
