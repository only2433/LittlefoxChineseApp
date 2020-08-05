package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.UserInformationModificationActivity.OnUserInformationListener;
import com.littlefox.chinese.edu.UserInformationModificationActivity.UserInformationHolder;
import com.littlefox.chinese.edu.common.CheckUserInput;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.object.UserBaseInformationObject;
import com.littlefox.chinese.edu.object.UserTotalInformationObject;
import com.littlefox.chinese.edu.view.PaddingDrawableEditText;
import com.littlefox.library.view.dialog.MaterialLoadingDialog;
import com.littlefox.library.view.text.SeparateTextView;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInformationConvertFragment extends Fragment implements UserInformationHolder
{
	@Nullable	
	@BindView(R.id.top_margin_layout)
	ScalableLayout _RadioTopMarginLayout;
	
	@BindView(R.id.myinfo_modification_info_base_layout)
	LinearLayout _InformationModificationLayout;
	
	@BindView(R.id.myinfo_modification_id_edit)
	PaddingDrawableEditText _InformationIDEdit;
	
	@BindView(R.id.myinfo_modification_warning_nickname_text)
	SeparateTextView _WarningNicknameText;
	
	@BindView(R.id.myinfo_modification_nickname_edit)
	PaddingDrawableEditText _InformationNicknameEdit;
	
	@BindView(R.id.myinfo_modification_warning_name_text)
	TextView _WarningNameText;
	
	@BindView(R.id.myinfo_modification_name_edit)
	PaddingDrawableEditText _InformationNameEdit;
	
	@BindView(R.id.myinfo_modification_calendar_text)
	PaddingDrawableEditText _InformationCalenderEdit;
	
	@BindView(R.id.myinfo_modification_phone_text)
	PaddingDrawableEditText _InformationPhoneEdit;
	
	@BindView(R.id.myinfo_modification_info_cancel_button)
	TextView _InformationCancelButton;
	
	@BindView(R.id.myinfo_modification_info_confirm_button)
	TextView _InformationConfirmButton;
	
	@BindView(R.id.myinfo_modification_password_base_layout)
	LinearLayout _PasswordModificationLayout;
	
	@BindView(R.id.myinfo_modification_warning_password_text)
	TextView _WarningPasswordText;
	
	@BindView(R.id.myinfo_modification_origin_password_edit)
	PaddingDrawableEditText _PasswordOriginEdit;
	
	@BindView(R.id.myinfo_modification_convert_password_edit)
	PaddingDrawableEditText _PasswordConvertEdit;
	
	@BindView(R.id.myinfo_modification_confirm_password_edit)
	PaddingDrawableEditText _PasswordConvertConfirmEdit;
	
	@BindView(R.id.myinfo_modification_password_cancel_button)
	TextView _PasswordCancelButton;
	
	@BindView(R.id.myinfo_modification_password_confirm_button)
	TextView _PasswordConfirmButton;
	
	private Context mContext;
	private int mCurrentConvertType = UserInformationHolder.ACTION_INFORMATION_CHANGE;
	private UserBaseInformationObject mUserModificationObject = null;
	private OnUserInformationListener mOnUserInformationListener;
	private String mBirthdayText = "";
	private int mCalendarYearPosition = -1;
	private MaterialLoadingDialog mLoadingDialog = null;
	private String[] mCalendarYearList;
	
	public static UserInformationConvertFragment getInstance()
	{
		return new UserInformationConvertFragment();
	}
	
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState)
	{
		Log.f("");
		View view;
		if(Feature.IS_TABLET)
		{
			view = inflater.inflate(R.layout.myinfo_convert_fragment_tablet, container, false);
		}
		else
		{
			view = inflater.inflate(R.layout.myinfo_convert_fragment, container, false);
		}
		 
		ButterKnife.bind(this, view);
		init();
		initFont();
		initText();
		return view;
	}
	
	private void init()
	{
		if(Feature.IS_TABLET && Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY)
		{
			_RadioTopMarginLayout.setVisibility(View.VISIBLE);
		}
		
		if(mCurrentConvertType == UserInformationHolder.ACTION_INFORMATION_CHANGE)
		{
			UserTotalInformationObject totalUserInformationObject = (UserTotalInformationObject) CommonUtils.getInstance(mContext).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
			mUserModificationObject = totalUserInformationObject.getBaseUserInformation();
			_InformationModificationLayout.setVisibility(View.VISIBLE);
			_PasswordModificationLayout.setVisibility(View.GONE);
			settingInformation();
		}
		else if(mCurrentConvertType == UserInformationHolder.ACTION_PASSWORD_CHANGE)
		{
			_InformationModificationLayout.setVisibility(View.GONE);
			_PasswordModificationLayout.setVisibility(View.VISIBLE);
		}
		hideLoadingDialog();
	}
	
	private void settingInformation()
	{
		_InformationIDEdit.setText(mUserModificationObject.getEmail());
		_InformationIDEdit.setEnabled(false);
		_InformationNicknameEdit.setText(mUserModificationObject.getNickname());
		_InformationNameEdit.setText(mUserModificationObject.getName());
		mBirthdayText = mUserModificationObject.getBirthday();
		setCalendarData(mBirthdayText);
		_InformationPhoneEdit.setText(mUserModificationObject.getPhoneNumber());
	}

	private void initFont()
	{
		_InformationIDEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_WarningNicknameText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_InformationNicknameEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_WarningNameText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_InformationNameEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_InformationCalenderEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());

		_InformationPhoneEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_InformationCancelButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_InformationConfirmButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());

		_WarningPasswordText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_PasswordOriginEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_PasswordConvertEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_PasswordConvertConfirmEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_PasswordCancelButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_PasswordConfirmButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	private void initText()
	{
		_InformationIDEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_email));
		
		_InformationNicknameEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_nickname));
		_WarningNameText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_name_example));
		_InformationNameEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_name));
		_InformationCalenderEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_birthday));
		
		_InformationPhoneEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_phone));
		_InformationCancelButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_cancel));
		_InformationConfirmButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_save));

		_WarningPasswordText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_password_example));
		_PasswordOriginEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_origin_password));
		_PasswordConvertEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_modification_password));
		_PasswordConvertConfirmEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_confirm_password));
		_PasswordCancelButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_cancel));
		_PasswordConfirmButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_save));
		
		
		_WarningNicknameText.setSeparateText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_nickname_example), " "+CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_require))
		.setSeparateColor(getResources().getColor(R.color.color_b1b1b1), getResources().getColor(R.color.color_d8232a))
		.showView();
	}
	

	
	private void setCalendarData(String text)
	{
		try
		{
			_InformationCalenderEdit.setText(text.substring(0, 4));
		}catch(StringIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}
	
	private void showLoadingDialog()
	{
		if(mLoadingDialog == null)
		{
			mLoadingDialog = new MaterialLoadingDialog(mContext, CommonUtils.getInstance(mContext).getPixel(150), getResources().getColor(R.color.color_d8232a));
		}
		mLoadingDialog.show();
	}
	
	private void hideLoadingDialog()
	{
		if(mLoadingDialog != null)
		{
			mLoadingDialog.dismiss();
			mLoadingDialog = null;
		}
	}


	
	/**
	 * 사용자 입력을 체크 한 후 , 에러적인 상황이면 메세지를 보내고 아니면 서버로 보냄
	 */
	private void checkUserInformationResult()
	{
		int inputCheckType = CheckUserInput.INPUT_SUCCESS;
		
		inputCheckType = CheckUserInput.getInstance(mContext)
					.checkNicknameData(_InformationNicknameEdit.getText().toString())
					.checkNameData(_InformationNameEdit.getText().toString())
					.checkCalendarData(_InformationCalenderEdit.getText().toString())
					.checkPhoneData(_InformationPhoneEdit.getText().toString())
					.getResultValue();
		
		if (inputCheckType == CheckUserInput.INPUT_SUCCESS)
		{
			showLoadingDialog();
			makeUserInformation();
			mOnUserInformationListener.requestModification(UserInformationHolder.ACTION_INFORMATION_CHANGE, mUserModificationObject);
		}
		else
		{
			mOnUserInformationListener.showMessage(CheckUserInput.getInstance(mContext).getErrorMessage(inputCheckType), mContext.getResources().getColor(R.color.color_d8232a));
		}

	}
	
	/**
	 * 사용자 입력을 체크 한 후 , 에러적인 상황이면 메세지를 보내고 아니면 서버로 보냄
	 * @return
	 */
	private void checkUserPasswordResult()
	{
		int inputCheckType = CheckUserInput.INPUT_SUCCESS;
		
		inputCheckType = CheckUserInput.getInstance(mContext)
				.checkPasswordData(_PasswordOriginEdit.getText().toString(), _PasswordConvertEdit.getText().toString(), _PasswordConvertConfirmEdit.getText().toString())
				.getResultValue();
		
		if(inputCheckType == CheckUserInput.INPUT_SUCCESS)
		{
			showLoadingDialog();
			mOnUserInformationListener.requestModification(UserInformationHolder.ACTION_PASSWORD_CHANGE, _PasswordConvertEdit.getText().toString());
		}
		else
		{
			mOnUserInformationListener.showMessage(CheckUserInput.getInstance(mContext).getErrorMessage(inputCheckType), mContext.getResources().getColor(R.color.color_d8232a));
		}

	}
	
	private void makeUserInformation()
	{
		mUserModificationObject.setName(_InformationNameEdit.getText().toString());
		mUserModificationObject.setNickname(_InformationNicknameEdit.getText().toString());
		mUserModificationObject.setBirthday(_InformationCalenderEdit.getText().toString());
		mUserModificationObject.setPhoneNumber(_InformationPhoneEdit.getText().toString());
	}
	
	private void showCalenderDialog()
	{
		
		mCalendarYearList = CommonUtils.getInstance(mContext).getAvailableSelectYears();
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_birthday));
		builder.setSingleChoiceItems(mCalendarYearList, mCalendarYearList.length - 1, new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Log.i("value : " + which);
				mCalendarYearPosition = which;
			}
		});
		builder.setPositiveButton(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_confirm),new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				setCalendarData(mCalendarYearList[mCalendarYearPosition]);
				Log.i("value : " + which);
			}
		});
		
		builder.setNegativeButton(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_cancel),new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				mCalendarYearPosition = -1;
			}
		});
		builder.create();
		builder.show();
	}

	public void setConvertType(int type)
	{
		mCurrentConvertType = type;
		init();
	}
	
	public void setModificationComplete()
	{
		hideLoadingDialog();
	}
	
	@OnClick({R.id.myinfo_modification_info_cancel_button, R.id.myinfo_modification_info_confirm_button, R.id.myinfo_modification_password_cancel_button, R.id.myinfo_modification_password_confirm_button, R.id.myinfo_modification_calendar_text})
	public void OnSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.myinfo_modification_info_cancel_button:
		case R.id.myinfo_modification_password_cancel_button:	
			mOnUserInformationListener.cancelModification();
			break;
		case R.id.myinfo_modification_info_confirm_button:
			checkUserInformationResult();
			break;
		case R.id.myinfo_modification_password_confirm_button:
			checkUserPasswordResult();
			break;
		case R.id.myinfo_modification_calendar_text:
			showCalenderDialog();
			break;
		}
	}

	
	@Override
	public void setOnUserInformationListener(OnUserInformationListener onUserInformationListener)
	{
		mOnUserInformationListener = onUserInformationListener;
	}
	

}
