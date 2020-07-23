package com.littlefox.chinese.edu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.coroutines.SeriesIntroductionRequestCoroutine;
import com.littlefox.chinese.edu.object.result.SeriesInfoResult;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.view.text.SeparateTextView;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SeriesIntroductionInfoDialog extends Dialog
{
	@BindView(R.id.progress_wheel_layout)
	ScalableLayout _ProgressLayout;
	
	@BindView(R.id.info_item_base_layout)
	ScalableLayout _ItemBaseLayout;
	
	@BindView(R.id.info_item_people_layout)
	ScalableLayout _ItemPeopleLayout;
	
	@BindView(R.id.info_title_text)
	SeparateTextView _TitleSeparateView;
	
	@BindView(R.id.info_series_level)
	TextView _SeriesLevelTextView;
	
	@BindView(R.id.info_series_introduction)
	TextView _IntroductionTextView;
	
	@BindView(R.id.horizontal_scroll_base)
	HorizontalScrollView _HorizontalScrollView;
	
	@BindView(R.id.add_info_people_laytout)
	LinearLayout _AddPeopleLayout;
	
	Handler mRequestHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_REQUEST_SERISE_INFO:
				requestSeriesInformation();
				break;
			case MESSAGE_SETTING_SERISE_INFO:
				settingInformation();
				break;
			}
		}	
	};
	
	private static final int MESSAGE_REQUEST_SERISE_INFO 	= 0;
	private static final int MESSAGE_SETTING_SERISE_INFO 	= 1;
	private static final int DUTATION_REQUEST_INFO = 700;

	private Context mContext;
	private String mFeatureSeriesId = "";
	private SeriesInfoResult mSeriesInfoResult;
	
	public SeriesIntroductionInfoDialog(Context context, String featureSeriesId)
	{
		super(context, R.style.BackgroundDialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.story_content_info_dialog);
		ButterKnife.bind(this);
		mFeatureSeriesId = featureSeriesId;
		init(context);
		initFont();
	}
	
	private void init(Context context)
	{
		mContext = context;
		showLoadedLayout(false);
		
		final int BASE_WIDTH = Feature.IS_TABLET ? 680 : 940;

		
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.copyFrom(getWindow().getAttributes());
		params.width = CommonUtils.getInstance(mContext).getPixel(BASE_WIDTH);
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		
		getWindow().setAttributes(params);
		mRequestHandler.sendEmptyMessageDelayed(MESSAGE_REQUEST_SERISE_INFO, DUTATION_REQUEST_INFO);
	}
	
	private void initFont()
	{
		_TitleSeparateView.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_SeriesLevelTextView.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_IntroductionTextView.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	

	
	private void requestSeriesInformation()
	{
		//SeriesIntroductionRequestAsync async = new SeriesIntroductionRequestAsync(mContext, mFeatureSeriesId, mRequestListener);
		//async.execute();
		SeriesIntroductionRequestCoroutine coroutine = new SeriesIntroductionRequestCoroutine(mContext, mRequestListener);
		coroutine.setData(mFeatureSeriesId);
		coroutine.execute();
	}
	
	private void showLoadedLayout(boolean isLoadComplete)
	{
		if(isLoadComplete)
		{
			_ProgressLayout.setVisibility(View.GONE);
			_ItemBaseLayout.setVisibility(View.VISIBLE);
			_ItemPeopleLayout.setVisibility(View.VISIBLE);
		}
		else
		{
			_ProgressLayout.setVisibility(View.VISIBLE);
			_ItemBaseLayout.setVisibility(View.GONE);
			_ItemPeopleLayout.setVisibility(View.GONE);
		}
	}
	
	private void settingInformation()
	{
		_SeriesLevelTextView.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_step)+" "+mSeriesInfoResult.getLevel()+" | "+ mSeriesInfoResult.getTotalCountText());
		_IntroductionTextView.setText(mSeriesInfoResult.getDescription());
		_IntroductionTextView.setMovementMethod(new ScrollingMovementMethod());
		initSeparateText();
		makeInfoPeopleList();
	}
	
	private void initSeparateText()
	{
		String seriesName = Feature.IS_LANGUAGE_ENG == true ? mSeriesInfoResult.getSeriesNameEn() : mSeriesInfoResult.getSeriesNameKo();
		if(Feature.IS_TABLET)
		{
			_TitleSeparateView
			.setSeparateText(mSeriesInfoResult.getSeriesName(), "  "+seriesName)
			.setSeparateColor(mContext.getResources().getColor(R.color.color_000000), mContext.getResources().getColor(R.color.color_333333))
			.setSeparateTextSize(CommonUtils.getInstance(mContext).getPixel(40), CommonUtils.getInstance(mContext).getPixel(26))
			.showView();
		}
		else
		{
			_TitleSeparateView
			.setSeparateText(mSeriesInfoResult.getSeriesName(), "  "+seriesName)
			.setSeparateColor(mContext.getResources().getColor(R.color.color_000000), mContext.getResources().getColor(R.color.color_333333))
			.setSeparateTextSize(CommonUtils.getInstance(mContext).getPixel(56), CommonUtils.getInstance(mContext).getPixel(36))
			.showView();
		}
		
		
	}
	
	private void makeInfoPeopleList()
	{
		final int MARGIN_TERM = CommonUtils.getInstance(mContext).getPixel(40);
		final int ITEM_SIZE = mSeriesInfoResult.getPeopleInfoList().size();
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout.LayoutParams itemParams;
		for(int i = 0 ; i < ITEM_SIZE ; i++)
		{
			View itemBase = inflater.inflate(R.layout.story_content_info_people_item, null);
			ImageView peopleImage 	= itemBase.findViewById(R.id.info_base_item_image);
			TextView peopelName		= itemBase.findViewById(R.id.info_base_item_text);
			peopelName.setTypeface(Font.getInstance(mContext).getRobotoMedium());

			Glide.with(mContext)
					.load(mSeriesInfoResult.getPeopleInfoList().get(i).getImageUrl())
					.transition(withCrossFade())
					.into(peopleImage);

			
			peopelName.setText(mSeriesInfoResult.getPeopleInfoList().get(i).getName());
			itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			
			if(i == 0)
			{
				itemParams.leftMargin = MARGIN_TERM;
			}
			else if(i == ITEM_SIZE -1)
			{
				itemParams.leftMargin = MARGIN_TERM;
				itemParams.rightMargin = MARGIN_TERM;
			}
			else
			{
				itemParams.leftMargin = MARGIN_TERM;
			}
			
			itemBase.setLayoutParams(itemParams);
			
			_AddPeopleLayout.addView(itemBase);
		}

	}
	
	private AsyncListener mRequestListener = new AsyncListener()
	{
		@Override
		public void onRunningStart(String code) { }

		@Override
		public void onRunningCanceled(String code) { }

		@Override
		public void onRunningProgress(String code, Integer progress) { }

		@Override
		public void onRunningAdvanceInformation(String code, Object object) { }

		@Override
		public void onErrorListener(String code, String message){}
		
		@Override
		public void onRunningEnd(String code, Object mObject)
		{
			showLoadedLayout(true);
			mSeriesInfoResult = (SeriesInfoResult) mObject;
			mRequestHandler.sendEmptyMessage(MESSAGE_SETTING_SERISE_INFO);
		}
	

	};

}
