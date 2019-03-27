package com.littlefox.chinese.edu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.object.UserSelectInformation;
import com.littlefox.chinese.edu.object.UserTotalInformationObject;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 사용자 설정을 변경하는 다이얼로그 
 * @author 정재현
 *
 */
public class UserSelectDialog extends Dialog
{
	@BindView(R.id.user_select_base_layout)
	ScalableLayout _BaseUserLayout;
	
	@BindView(R.id.user_select_title)
	TextView _TitleText;
	
	@BindView(R.id.add_list_layout)
	LinearLayout _AddListLayout;
	
	private Handler mDismissHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MESSAGE_DISMISS:
				dismiss();
				break;
			}
		}
	};
	
	public interface OnSelectDialogListener
	{
		/**
		 * -1일 경우, 사용자 , 0~ 추가사용자를 의미
		 * @param index
		 */
        void onSelectUserIndex(int index);
	}
	
	private static final int MESSAGE_DISMISS = 0;
	private static final int DURATION_DISMISS = 300;
	
	private  int BASE_WIDTH = 826;
	private  int BASE_TOP_HEIGHT= 144;
	private  int BASE_MARGIN_TERM = 14;
	
	private  int CONTENT_WIDTH = 798;
	private  int CONTENT_HEIGHT = 196;
	
	private Context mContext;
	private ArrayList<UserSelectInformation> mInformationList;
	private LayoutInflater mInflater = null;
	
	private OnSelectDialogListener mOnSelectDialogListener;

	public UserSelectDialog(Context context, ArrayList<UserSelectInformation> informationList)
	{
		super(context, R.style.BackgroundDialog);
		init(context, informationList);
	}
	
	private void init(Context context, ArrayList<UserSelectInformation> informationList)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		if(Feature.IS_TABLET)
		{
			setContentView(R.layout.user_select_information_base_tablet);
		}
		else
		{
			setContentView(R.layout.user_select_information_base);
		}

		ButterKnife.bind(this);
		mContext 				= context;
		mInformationList 	= informationList;
		initInformation();
		
		initFont();
		initText();
		initView();
		
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.copyFrom(getWindow().getAttributes());
		params.width = CommonUtils.getInstance(mContext).getPixel(BASE_WIDTH);
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		
		getWindow().setAttributes(params);
	}
	
	private void initFont()
	{
		_TitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	private void initText()
	{
		_TitleText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_user_select));
	}
	
	private void initInformation()
	{
		BASE_WIDTH 				= Feature.IS_TABLET ? 540 : 826;
		BASE_TOP_HEIGHT 		= Feature.IS_TABLET ? 94 : 144;
		BASE_MARGIN_TERM 		= Feature.IS_TABLET ? 10 : 14;
		CONTENT_WIDTH			= Feature.IS_TABLET ? 520 : 798;
		CONTENT_HEIGHT			= Feature.IS_TABLET ? 128 : 196;
	}
	
	private void initView()
	{
		UserTotalInformationObject userTotalInfo = (UserTotalInformationObject) CommonUtils.getInstance(mContext).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
		View subBase;
		int currentUserSelectIndex = -1;
		if(userTotalInfo.isBaseUserUse())
			currentUserSelectIndex = 0;
		else
		{
			currentUserSelectIndex = userTotalInfo.getCurrentUserIndex() + 1;
		}
		
		int height = BASE_TOP_HEIGHT +BASE_MARGIN_TERM + CONTENT_HEIGHT * mInformationList.size();
		Log.i("height : "+height);
		
		_BaseUserLayout.setScaleHeight( height);
		_BaseUserLayout.moveChildView(_AddListLayout, 0, BASE_TOP_HEIGHT, BASE_WIDTH, height);
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for(int i = 0 ; i < mInformationList.size(); i++)
		{
			if(Feature.IS_TABLET)
			{
				subBase = mInflater.inflate(R.layout.user_select_information_content_tablet, null);
			}
			else
			{
				subBase = mInflater.inflate(R.layout.user_select_information_content, null);
			}

			ScalableLayout subBaseLayout 	= subBase.findViewById(R.id.user_select_add_base_layout);
			
			subBaseLayout.setTag(i);
			subBaseLayout.setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					changeSelectUser((Integer) v.getTag());
					mDismissHandler.sendEmptyMessageDelayed(MESSAGE_DISMISS, DURATION_DISMISS);
				}
			});
			ImageView subAddBackground 			= subBase.findViewById(R.id.user_select_background);
			ImageView subAddChoiceIcon 			= subBase.findViewById(R.id.user_select_add_choice_icon);
			subAddChoiceIcon.setTag("icon");
			TextView subUserTitle				= subBase.findViewById(R.id.user_select_add_user_title);
			TextView subUserName				= subBase.findViewById(R.id.user_select_add_user_name);
			subUserTitle.setTypeface(Font.getInstance(mContext).getRobotoMedium());
			subUserName.setTypeface(Font.getInstance(mContext).getRobotoMedium());
			if( i == 0 )
			{
				subUserTitle.setText(mInformationList.get(i).getBaseUserName(mContext));
			}
			else
			{
				subUserTitle.setText(mInformationList.get(i).getAddUserName(mContext));
			}
			
			if(i == currentUserSelectIndex)
			{
				subAddChoiceIcon.setImageResource(R.drawable.check_on);
				subAddBackground.setBackgroundResource(R.color.color_main_navigation_drawer_bg);
			}
			subUserName.setText(mInformationList.get(i).getUserName());
			
			Log.i("left : "+BASE_MARGIN_TERM +", top : "+ (BASE_TOP_HEIGHT + (CONTENT_HEIGHT * i)));
			Log.i("CONTENT_WIDTH : "+CONTENT_WIDTH +", CONTENT_HEIGHT : "+ CONTENT_HEIGHT);
			_AddListLayout.addView(subBaseLayout);
		}
	}
	/**
	 * 사용자 를 변경을 하였을때 화면 갱신을 위해 사용
	 * @param position 변경 포지션
	 */
	private void changeSelectUser(int position)
	{
		Log.f("changeSelectUser position : " + position);
		if(position == 0)
		{
			mOnSelectDialogListener.onSelectUserIndex(-1);
		}
		else
		{
			int childListIndex = position - 1;
			mOnSelectDialogListener.onSelectUserIndex(childListIndex);
		}

		int checkSelectTagPostion = -1;
		for (int i = 0; i < _BaseUserLayout.getChildCount(); i++)
		{
			if(_BaseUserLayout.getChildAt(i).getTag() == null)
			{
				continue;
			}
			else
			{
				checkSelectTagPostion++;
			}
			
			if (_BaseUserLayout.getChildAt(i).getTag().equals(checkSelectTagPostion))
			{
				ScalableLayout subBaseLayout = (ScalableLayout) _BaseUserLayout.getChildAt(i);

				if (checkSelectTagPostion == position)
				{
					subBaseLayout.setBackgroundResource(R.color.color_main_navigation_drawer_bg);
				}
				else
				{
					subBaseLayout.setBackgroundResource(R.color.color_white);
				}
				for (int j = 0; j < subBaseLayout.getChildCount(); j++)
				{
					if(subBaseLayout.getChildAt(j).getTag() == null)
					{
						continue;
					}
					
					if (subBaseLayout.getChildAt(j).getTag().equals("icon"))
					{
						ImageView iconImage = (ImageView) subBaseLayout.getChildAt(j);

						if (checkSelectTagPostion == position)
						{
							iconImage.setImageResource(R.drawable.check_on);
						}
						else
						{
							iconImage.setImageResource(R.drawable.check_off);

						}
						break;
					}
				}
			}
		}
	}
	
	public void setOnSelectDialogListener(OnSelectDialogListener onSelectDialogListener)
	{
		mOnSelectDialogListener = onSelectDialogListener;
	}

}
