package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlefox.chinese.edu.AddChildInformationModificationActivity.AddChildHolder;
import com.littlefox.chinese.edu.AddChildInformationModificationActivity.OnAddChildListener;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.CheckUserInput;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.object.UserBaseInformationObject;
import com.littlefox.chinese.edu.view.PaddingDrawableEditText;
import com.littlefox.library.view.dialog.MaterialLoadingDialog;
import com.littlefox.library.view.text.SeparateTextView;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddChildInformationConvertFragment extends Fragment implements AddChildHolder
{
	@Nullable	
	@BindView(R.id.top_margin_layout)
	ScalableLayout _RadioTopMarginLayout;
	
	@BindView(R.id.addchild_convert_child_titile)
	TextView _TitleText;
	
	@BindView(R.id.addchild_convert_warning_nickname_text)
	SeparateTextView _WarningNicknameText;
	
	@BindView(R.id.addchild_convert_nickname_edit)
	PaddingDrawableEditText _NicknameEdit;
	
	@BindView(R.id.addchild_convert_warning_name_text)
	TextView _WarningNameText;
	
	@BindView(R.id.addchild_convert_name_edit)
	PaddingDrawableEditText _NameEdit;
	
	@BindView(R.id.addchild_convert_calendar_edit)
	PaddingDrawableEditText _CalendarEdit;
	

	
	@BindView(R.id.addchild_convert_cancel_button)
	TextView _CancelButton;
	
	@BindView(R.id.addchild_convert_save_button)
	TextView _SaveButton;
	
	private Context mContext;
	private OnAddChildListener mOnAddChildListener;
	private UserBaseInformationObject mAddChildInformation = null;
	private String mCalendarData = "";
	
	private MaterialLoadingDialog mLoadingDialog = null;
	private String[] mCalendarYearList;
	private int mCalendarYearPosition = -1;
	
	public static AddChildInformationConvertFragment getInstance()
	{
		return new AddChildInformationConvertFragment();
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
			view = inflater.inflate(R.layout.addchild_manage_convert_fragment_tablet, container, false);
		}
		else
		{
			view = inflater.inflate(R.layout.addchild_manage_convert_fragment, container, false);
		}
		
		ButterKnife.bind(this, view);
		initView();
		initFont();
		initText();
		return view;
	}
	
	private void initView()
	{
		if(Feature.IS_TABLET && Feature.IS_MINIMUM_SUPPORT_TABLET_RADIO_DISPLAY)
		{
			_RadioTopMarginLayout.setVisibility(View.VISIBLE);
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
	
	private void initSetting()
	{
		if(mAddChildInformation != null)
		{
			Log.i("mCalendarData : "+ mCalendarData);
			
			_NicknameEdit.setText(mAddChildInformation.getNickname());
			_NameEdit.setText(mAddChildInformation.getName());
			if(mAddChildInformation.getBirthday().equals("") == false)
			{
				mCalendarData = mAddChildInformation.getBirthday().substring(0, 4);
			}
			
			setCalendarData(mCalendarData);
		}
		else
		{
			_NicknameEdit.setText("");
			_NameEdit.setText("");
			mCalendarData = "";
			_CalendarEdit.setText(mCalendarData);
		}
	}
	
	private void initFont()
	{
		_TitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_WarningNicknameText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_NicknameEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_WarningNameText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_NameEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_CalendarEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());

		_CancelButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_SaveButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	private void initText()
	{
		_TitleText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_add_user));
		_NicknameEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_nickname));
		_WarningNameText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_name_example));
		_NameEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_name));
		_CalendarEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_birthday));

		_SaveButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_save));
		_CancelButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_cancel));
		
		_WarningNicknameText.setSeparateText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_nickname_example), " "+CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_require))
		.setSeparateColor(getResources().getColor(R.color.color_b1b1b1), getResources().getColor(R.color.color_d8232a))
		.showView();
	}
	
	private void setCalendarData(String date)
	{
		mCalendarData = date;
		_CalendarEdit.setText(mCalendarData);
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
	
	@OnClick({R.id.addchild_convert_calendar_edit, R.id.addchild_convert_cancel_button, R.id.addchild_convert_save_button})
	public void OnSelectClick(View view)
	{
		switch(view.getId())
		{

		case R.id.addchild_convert_calendar_edit:
			showCalenderDialog();
			break;
		case R.id.addchild_convert_cancel_button:
			mOnAddChildListener.cancelModification();
			break;
		case R.id.addchild_convert_save_button:
			checkAddChildInformationResult();
			break;
		}
	}
	
	private void checkAddChildInformationResult()
	{
		int inputCheckType = CheckUserInput.INPUT_SUCCESS;
		
		inputCheckType = CheckUserInput.getInstance(mContext)
				.checkNicknameData(_NicknameEdit.getText().toString())
				.checkNameData(_NameEdit.getText().toString())
				.checkCalendarData(mCalendarData)
				.getResultValue();
		if (inputCheckType == CheckUserInput.INPUT_SUCCESS)
		{
			showLoadingDialog();
			
			mAddChildInformation = new  UserBaseInformationObject(_NicknameEdit.getText().toString(), _NameEdit.getText().toString(), mCalendarData);
			mOnAddChildListener.requestServer(mAddChildInformation);
		}
		else
		{
			mOnAddChildListener.showMessage(CheckUserInput.getInstance(mContext).getErrorMessage(inputCheckType), mContext.getResources().getColor(R.color.color_d8232a));
		}
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
	

	
	/**
	 * 유저가 없다는 것을 가리키며, 화면에 값을 초기화 해서 보여준다.
	 */
	public void addChildUser()
	{
		mAddChildInformation = null;
		initSetting();
	}
	
	/**
	 * 사용자 정보 변경시 사용하며, 현재 수정해야할 사용자 정보를 전달한다.
	 * @param object
	 */
	public void convertCurrentUser(UserBaseInformationObject object)
	{
		mAddChildInformation = object;
		initSetting();
	}
	
	public void setModificationComplete()
	{
		hideLoadingDialog();
	}
	
	@Override
	public void setOnAddChildListener(OnAddChildListener onAddChildListener)
	{
		mOnAddChildListener = onAddChildListener;
	}
	

}
