package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.UserInformationModificationActivity.OnUserInformationListener;
import com.littlefox.chinese.edu.UserInformationModificationActivity.UserInformationHolder;
import com.littlefox.chinese.edu.billing.InAppPurchase;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.object.InAppInformation;
import com.littlefox.chinese.edu.object.UserTotalInformationObject;
import com.littlefox.chinese.edu.view.PaddingDrawableEditText;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInformationShowFragment extends Fragment implements UserInformationHolder
{
	@Nullable
	@BindView(R.id.top_margin_layout)
	ScalableLayout _RadioTopMarginLayout;
	
	@BindView(R.id.myinfo_pay_complete_layout)
	ScalableLayout _PayCompleteLayout;
	
	@BindView(R.id.pay_complete_title_text)
	TextView _PayCompleteTitleText;
	
	@BindView(R.id.pay_complete_day_text)
	TextView _PayCompleteDayText;
	
	@BindView(R.id.myinfo_pay_not_yet_layout)
	ScalableLayout _PayNotYetLayout;
	
	@BindView(R.id.pay_not_yet_title_text)
	TextView _PayNotYetTitleText;
	
	@BindView(R.id.pay_enter_button)
	TextView _PayEnterButton;
	
	@BindView(R.id.myinfo_id_edit)
	PaddingDrawableEditText  _UserIDEditText;
	
	@BindView(R.id.myinfo_nickname_edit)
	PaddingDrawableEditText  _UserNicknameEditText;
	
	@BindView(R.id.myinfo_name_edit)
	PaddingDrawableEditText  _UserNameEditText;
	
	@BindView(R.id.myinfo_calendar_text)
	PaddingDrawableEditText  _UserCalendarEditText;
	
	@BindView(R.id.myinfo_phone_text)
	PaddingDrawableEditText  _PhoneEditText;
	
	@BindView(R.id.myinfo_change_password_button)
	TextView _ChangePasswordButton;
	
	@BindView(R.id.myinfo_info_modification_button)
	TextView _InfoModificationButton;
	
	private Context mContext;
	private InAppInformation mInAppInformation;
	private UserTotalInformationObject mUserInformation;
	private OnUserInformationListener mOnUserInformationListener;
	
	public static UserInformationShowFragment getInstance()
	{
		return new UserInformationShowFragment();
	}
	
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		mContext = context;
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view;
		if(Feature.IS_TABLET)
		{
			view = inflater.inflate(R.layout.myinfo_show_fragment_tablet, container, false);
		}
		else
		{
			view = inflater.inflate(R.layout.myinfo_show_fragment, container, false);
		}
		
		ButterKnife.bind(this, view);
		init();
		initView();
		initFont();
		initText();
		return view;
	}
	
	private void init()
	{
		mInAppInformation = (InAppInformation) CommonUtils.getInstance(mContext).getPreferenceObject(Common.PARAMS_INAPP_INFO, InAppInformation.class);
		if(mInAppInformation.getInAppType().equals(InAppPurchase.IN_APP_FREE_USER))
		{
			_PayCompleteLayout.setVisibility(View.GONE);
			_PayNotYetLayout.setVisibility(View.VISIBLE);
		}
		else
		{
			_PayCompleteLayout.setVisibility(View.VISIBLE);
			_PayNotYetLayout.setVisibility(View.GONE);

			_PayCompleteDayText.setText(CommonUtils.getInstance(mContext).getPayInformationDate(Feature.IS_LANGUAGE_ENG,mInAppInformation.getInAppStartDay())+" ~ "+ CommonUtils.getInstance(mContext).getPayInformationDate(Feature.IS_LANGUAGE_ENG,mInAppInformation.getInAppEndDay()));

			_PayCompleteTitleText.setText(mInAppInformation.getInAppTitle(mContext));
			
		}
	}
	private void initView()
	{
		
		if(Feature.IS_TABLET && Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY)
		{
			_RadioTopMarginLayout.setVisibility(View.VISIBLE);
		}
		
		_UserIDEditText.setEnabled(false);
		_UserNicknameEditText.setEnabled(false);
		_UserNameEditText.setEnabled(false);
		_UserCalendarEditText.setEnabled(false);
		_PhoneEditText.setEnabled(false);
		
		settingViewInformation();
	}
	
	private void initFont()
	{
		_PayCompleteTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_PayCompleteDayText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_PayNotYetTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_PayEnterButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_UserIDEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_UserNicknameEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_UserNameEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_UserCalendarEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_PhoneEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_ChangePasswordButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_InfoModificationButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	private void initText()
	{
		_PayNotYetTitleText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_pay_not_yet));
		_PayEnterButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_pay));
		_UserIDEditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_email));
		_UserNicknameEditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_nickname));
		_UserNameEditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_name));
		_UserCalendarEditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_birthday));
		_PhoneEditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_phone));
		_ChangePasswordButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_change_password));
		_InfoModificationButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_info_modification));
	}
	
	private void setCalendarData(String date)
	{
		try
		{
			Log.i("date : "+date);
			_UserCalendarEditText.setText(date.substring(0, 4));
		}catch(StringIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		Log.f("");
	}

	@Override
	public void onResume()
	{
		super.onResume();
		Log.f("");
	}

	@Override
	public void onStop()
	{
		super.onStop();
		Log.f("");
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		Log.f("");
	}
	
	@OnClick({R.id.myinfo_change_password_button, R.id.myinfo_info_modification_button, R.id.pay_enter_button})
	public void onSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.myinfo_change_password_button:
			Log.f("비밀번호 변경");
			mOnUserInformationListener.executeAction(UserInformationHolder.ACTION_PASSWORD_CHANGE);
			break;
		case R.id.myinfo_info_modification_button:
			Log.f("정보 변경");
			mOnUserInformationListener.executeAction(UserInformationHolder.ACTION_INFORMATION_CHANGE);
			break;
		case R.id.pay_enter_button:
			Log.f("결제 페이지 이동");
			mOnUserInformationListener.executeAction(UserInformationHolder.ACTION_PAYMENT);
			break;
		}
	}

	@Override
	public void setOnUserInformationListener(OnUserInformationListener onUserInformationListener)
	{
		mOnUserInformationListener = onUserInformationListener;
	}
	
	public void settingViewInformation()
	{
		mUserInformation	= (UserTotalInformationObject)CommonUtils.getInstance(mContext).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
		_UserIDEditText.setText(mUserInformation.getBaseUserInformation().getEmail());
		_UserNicknameEditText.setText(mUserInformation.getBaseUserInformation().getNickname());
		_UserNameEditText.setText(mUserInformation.getBaseUserInformation().getName());

		if(mUserInformation.getBaseUserInformation().getBirthday().equals("") == false)
		{
			setCalendarData(mUserInformation.getBaseUserInformation().getBirthday().substring(0, 4));
		}

		_PhoneEditText.setText(mUserInformation.getBaseUserInformation().getPhoneNumber());
		
	}

}
