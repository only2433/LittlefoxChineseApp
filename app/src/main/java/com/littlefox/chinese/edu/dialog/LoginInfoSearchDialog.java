package com.littlefox.chinese.edu.dialog;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.CheckUserInput;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.object.result.InfoSearchResult;
import com.littlefox.chinese.edu.view.PaddingDrawableEditText;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginInfoSearchDialog extends Dialog
{
	@BindView(R.id.login_title_base_layout)
	ScalableLayout _TitleBaseLayout;
	
	@BindView(R.id.login_title_main_layout)
	ScalableLayout _TitleLoginLayout;
	
	@BindView(R.id.login_main_title_text)
	TextView _TitleLoginText;
	
	@BindView(R.id.login_title_search_layout)
	ScalableLayout _TitleSearchLayout;
	
	@BindView(R.id.login_search_id_text)
	TextView _TitleSearchIDText;
	
	@BindView(R.id.login_search_pw_text)
	TextView _TitleSearchPasswordText;
	
	@BindView(R.id.login_search_index_arrow)
	ImageView _TitleSearchPointArrowImage;

	/**
	 * 로그인 다이얼로그 Search 및 결과 세가지 화면이 있다. 두번째 UI 타입
	 */
	@BindView(R.id.login_type_search_type1_layout)
	ScalableLayout _LoginBodyType1Layout;
	
	@BindView(R.id.login_search_type1_text)
	TextView _LoginBodyType1MessageText;
	
	
	@BindView(R.id.login_search_type1_edit)
	PaddingDrawableEditText _LoginBodyType1EditText;
	
	@BindView(R.id.login_search_type1_ok_button)
	TextView _LoginBodyType1OkButton;
	
	@BindView(R.id.login_search_type1_cancel_button)
	TextView _LoginBodyType1CancelButton;
	
	/**
	 * 로그인 다이얼로그 Search 및 결과 두가지 화면이 있다. 세번째 UI 타입
	 */
	@BindView(R.id.login_type_search_type2_layout)
	ScalableLayout _LoginBodyType2Layout;
	
	@BindView(R.id.login_search_type2_text)
	TextView _LoginBodyType2MessageText;
	
	@BindView(R.id.login_search_type2_edit1)
	PaddingDrawableEditText _LoginBodyType2FirstEditText;
	
	@BindView(R.id.login_search_type2_edit2)
	PaddingDrawableEditText _LoginBodyType2SecondEditText;
	
	@BindView(R.id.login_search_type2_ok_button)
	TextView _LoginBodyType2OkButton;
	
	/**
	 * 로그인 다이얼로그 상태값에 따라 메인 Activity에게 정보 전달
	 * @author 정재현
	 *
	 */
	public interface OnLoginListener
	{
		/**
		 * ID 찾기를  시도
		 */
        void executeSearchID(String email);
		/**
		 * 비밀번호 찾기를 시도
		 * @param id 사용자의 ID
		 */
        void executeSearchPassword(String id);
		/**
		 * 이메일을 전송한다.
		 * @param email 전송할 이메일
		 */
        void sendEmail(String email);
	}

    public static final int LOGIN_SEARCH_ID 										= 0;
	public static final int LOGIN_SEARCH_PASSWORD									= 1;
	public static final int LOGIN_SEARCH_RESULT_ID_SUCCESS 							= 2;
	public static final int LOGIN_SEARCH_RESULT_PASSWORD_SUCCESS 					= 3;
	public static final int LOGIN_SEARCH_RESULT_ID_FAIL								= 4;
	public static final int LOGIN_SEARCH_RESULT_PASSWORD_FAIL						= 5;
	
	private static final int TABLET_ICON_WIDTH = 54;
	private static final int TABLET_ICON_HEIGHT= 45;

	private int BASE_WIDTH = 826;
	private Context mContext;
	private int mCurrentLoginType = LOGIN_SEARCH_ID;
	private OnLoginListener mOnLoginListener;
	private boolean isAutoLogin = false;
	private InfoSearchResult mInfoSearchResult;
	
	public LoginInfoSearchDialog(Context context, int type)
	{
		super(context, R.style.BackgroundDialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_search_dialog);
		ButterKnife.bind(this);
		mContext = context;
		mCurrentLoginType = type;
		Log.i("type : "+type);
		init();
	}
	
	public LoginInfoSearchDialog(Context context, int type, InfoSearchResult result)
	{
		super(context, R.style.BackgroundDialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_search_dialog);
		ButterKnife.bind(this);
		mContext = context;
		mCurrentLoginType = type;
		init();
		mInfoSearchResult = result;
		
		Log.i("type : "+type);
		setResultInformation();
		
	}

	private void init()
	{
		BASE_WIDTH = Feature.IS_TABLET ? 620 : 826;
		isAutoLogin = false;
		initText();
		initFont();
		settingBaseLayout();
		settingDetailLayout();
		
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.copyFrom(getWindow().getAttributes());
		params.width = CommonUtils.getInstance(mContext).getPixel(BASE_WIDTH);
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		
		getWindow().setAttributes(params);
	}
	
	private void initFont()
	{
		_TitleLoginText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_TitleSearchIDText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_TitleSearchPasswordText.setTypeface(Font.getInstance(mContext).getRobotoMedium());

		_LoginBodyType1MessageText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_LoginBodyType1EditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_LoginBodyType1OkButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_LoginBodyType1CancelButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_LoginBodyType2MessageText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_LoginBodyType2FirstEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_LoginBodyType2SecondEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_LoginBodyType2OkButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	private void initText()
	{
		_TitleLoginText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_login));
		_TitleSearchIDText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_id_search));
		_TitleSearchPasswordText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_pw_search));
		
		_LoginBodyType1MessageText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_search_id));
		_LoginBodyType1EditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_email));
		_LoginBodyType1OkButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_search));
		_LoginBodyType1CancelButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_cancel));
		
		_LoginBodyType2MessageText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_search_result_id));
		_LoginBodyType2OkButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_login_doing));
	}
	
	/**
	 *  Base UI 를 상태에 따라 보여줌
	 */
	private void settingBaseLayout()
	{
		Log.i("mCurrentLoginType : "+mCurrentLoginType);
		switch(mCurrentLoginType)
		{

		case LOGIN_SEARCH_ID:
		case LOGIN_SEARCH_PASSWORD:
			_TitleLoginLayout.setVisibility(View.GONE);
			_TitleSearchLayout.setVisibility(View.VISIBLE);
			_LoginBodyType1Layout.setVisibility(View.VISIBLE);
			_LoginBodyType2Layout.setVisibility(View.GONE);
			break;

		case LOGIN_SEARCH_RESULT_ID_SUCCESS:
		case LOGIN_SEARCH_RESULT_PASSWORD_SUCCESS:
			_TitleLoginLayout.setVisibility(View.VISIBLE);
			_TitleSearchLayout.setVisibility(View.GONE);
			_LoginBodyType1Layout.setVisibility(View.GONE);
			_LoginBodyType2Layout.setVisibility(View.VISIBLE);
			break;
			
		case LOGIN_SEARCH_RESULT_ID_FAIL:
		case LOGIN_SEARCH_RESULT_PASSWORD_FAIL:
			_TitleLoginLayout.setVisibility(View.VISIBLE);
			_TitleSearchLayout.setVisibility(View.GONE);
			_LoginBodyType1Layout.setVisibility(View.VISIBLE);
			_LoginBodyType2Layout.setVisibility(View.GONE);
			break;
		}
		
		
	}
	
	/**
	 * 세부적인 UI 를 Setting 해주는 레이아웃
	 */
	private void settingDetailLayout()
	{
		switch(mCurrentLoginType)
		{
		case LOGIN_SEARCH_ID:
			_TitleSearchIDText.setTextColor(mContext.getResources().getColor(R.color.color_ffffff));
			_TitleSearchPasswordText.setTextColor(mContext.getResources().getColor(R.color.color_ecaaab));
			_LoginBodyType1MessageText.setTextColor(mContext.getResources().getColor(R.color.color_888888));
			_LoginBodyType1MessageText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_search_id));
			_LoginBodyType1EditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_email));
			if(Feature.IS_TABLET)
			{
				_LoginBodyType1Layout.setScale_TextSize(_LoginBodyType1EditText, 32);
				_LoginBodyType1EditText.setImage(TABLET_ICON_WIDTH, TABLET_ICON_HEIGHT, R.drawable.icon_mail);
			}
			else
			{
				_LoginBodyType1EditText.setImage(R.drawable.icon_mail);
			}
			
			_LoginBodyType1OkButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_search));
			break;
			
		case LOGIN_SEARCH_PASSWORD:
			_TitleSearchIDText.setTextColor(mContext.getResources().getColor(R.color.color_ecaaab));
			_TitleSearchPasswordText.setTextColor(mContext.getResources().getColor(R.color.color_ffffff));
			_LoginBodyType1MessageText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_search_password));
			_LoginBodyType1MessageText.setTextColor(mContext.getResources().getColor(R.color.color_888888));
			_LoginBodyType1EditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_id));
			if(Feature.IS_TABLET)
			{
				_LoginBodyType1Layout.setScale_TextSize(_LoginBodyType1EditText, 32);
				_LoginBodyType1EditText.setImage(54, 45, R.drawable.icon_id);
			}
			else
			{
				_LoginBodyType1EditText.setImage(R.drawable.icon_id);
			}
			
			_LoginBodyType1OkButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_search));
			break;
			
		case LOGIN_SEARCH_RESULT_ID_SUCCESS:
			_TitleLoginText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_result_id));
			_LoginBodyType2MessageText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_search_result_id));
			_LoginBodyType2FirstEditText.setBackgroundResource(R.drawable.input_gray);
			_LoginBodyType2FirstEditText.setInputType(0);
			
			if(Feature.IS_TABLET)
			{
				_LoginBodyType2Layout.setScale_TextSize(_LoginBodyType2FirstEditText, 32);
				_LoginBodyType2FirstEditText.setImage(TABLET_ICON_WIDTH, TABLET_ICON_HEIGHT, R.drawable.icon_id);
			}
			else
			{
				_LoginBodyType2FirstEditText.setImage(R.drawable.icon_id);
			}
			
			_LoginBodyType2SecondEditText.setBackgroundResource(R.drawable.input_gray);
			_LoginBodyType2SecondEditText.setInputType(0);
			
			if(Feature.IS_TABLET)
			{
				_LoginBodyType2Layout.setScale_TextSize(_LoginBodyType2SecondEditText, CommonUtils.getInstance(mContext).getPixel(32));
				_LoginBodyType2SecondEditText.setImage(TABLET_ICON_WIDTH, TABLET_ICON_HEIGHT, R.drawable.icon_calendar);
			}
			else
			{
				_LoginBodyType2SecondEditText.setImage(R.drawable.icon_calendar);
			}

			_LoginBodyType2OkButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_login_doing));
			break;
			
		case LOGIN_SEARCH_RESULT_PASSWORD_SUCCESS:
			_TitleLoginText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_result_password));
			_LoginBodyType2MessageText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_search_result_password));
			_LoginBodyType2FirstEditText.setBackgroundColor(mContext.getResources().getColor(R.color.color_cdefeb));
			_LoginBodyType2FirstEditText.setInputType(0);
			_LoginBodyType2FirstEditText.setGravity(Gravity.CENTER);
			_LoginBodyType2FirstEditText.initDrawableImage();
			if(Feature.IS_TABLET)
			{
				_LoginBodyType2Layout.setScale_TextSize(_LoginBodyType2FirstEditText, 32);
			}

			_LoginBodyType2SecondEditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_email));
			_LoginBodyType2SecondEditText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			_LoginBodyType2SecondEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
			
			if(Feature.IS_TABLET)
			{
				_LoginBodyType2Layout.setScale_TextSize(_LoginBodyType2SecondEditText, 32);
				_LoginBodyType2SecondEditText.setImage(TABLET_ICON_WIDTH, TABLET_ICON_HEIGHT, R.drawable.icon_mail);
			}
			else
			{
				_LoginBodyType2SecondEditText.setImage(R.drawable.icon_mail);
			}

			_LoginBodyType2OkButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_email_send));
			break;
			
		case LOGIN_SEARCH_RESULT_ID_FAIL:
			_TitleLoginText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_result_id));
			_LoginBodyType1MessageText.setText(mInfoSearchResult.getMessage());
			_LoginBodyType1MessageText.setTextColor(mContext.getResources().getColor(R.color.color_d8232a));
			if(Feature.IS_TABLET)
			{
				_LoginBodyType1Layout.setScale_TextSize(_LoginBodyType1EditText, 32);
				_LoginBodyType1EditText.setImage(TABLET_ICON_WIDTH, TABLET_ICON_HEIGHT, R.drawable.icon_mail);
			}
			else
			{
				_LoginBodyType1EditText.setImage(R.drawable.icon_mail);
			}

			_LoginBodyType1EditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_email));
			_LoginBodyType1OkButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_research));
			break;
			
		case LOGIN_SEARCH_RESULT_PASSWORD_FAIL:
			_TitleLoginText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_result_password));
			_LoginBodyType1MessageText.setText(mInfoSearchResult.getMessage());
			_LoginBodyType1MessageText.setTextColor(mContext.getResources().getColor(R.color.color_d8232a));
			
			if(Feature.IS_TABLET)
			{
				_LoginBodyType1Layout.setScale_TextSize(_LoginBodyType1EditText, CommonUtils.getInstance(mContext).getPixel(32));
				_LoginBodyType1EditText.setImage(TABLET_ICON_WIDTH, TABLET_ICON_HEIGHT, R.drawable.icon_id);
			}
			else
			{
				_LoginBodyType1EditText.setImage(R.drawable.icon_id);
			}

			_LoginBodyType1EditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_id));
			_LoginBodyType1OkButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_research));
			break;
		}
	}
	
	private void changeSearchTab(int type)
	{
		if(mCurrentLoginType == type)
		{
			return;
		}
		
		mCurrentLoginType = type;
		ObjectAnimator transObjectAnimator = null;
		
		
		if(type == LOGIN_SEARCH_ID)
		{
			transObjectAnimator = ObjectAnimator.ofFloat(_TitleSearchPointArrowImage, "x", (_TitleSearchIDText.getWidth() - _TitleSearchPointArrowImage.getWidth())/2);
		}
		else if(type == LOGIN_SEARCH_PASSWORD)
		{
			transObjectAnimator = ObjectAnimator.ofFloat(_TitleSearchPointArrowImage, "x", (_TitleSearchIDText.getWidth() - _TitleSearchPointArrowImage.getWidth())/2 + _TitleSearchPasswordText.getWidth());
			
		}
		transObjectAnimator.setDuration(250);
		transObjectAnimator.setInterpolator(new LinearInterpolator());
		transObjectAnimator.start();
		settingDetailLayout();
	}
	
	public void setOnLoginListener(OnLoginListener onLoginListener)
	{
		mOnLoginListener = onLoginListener;
	}
	

	/**
	 * 결과를 화면에 보여줘야할때 사용
	 */
	private void setResultInformation()
	{
		if(mCurrentLoginType == LOGIN_SEARCH_RESULT_ID_SUCCESS)
		{
			//_LoginBodyType2FirstEditText.setPadding();
			_LoginBodyType2FirstEditText.setText(mInfoSearchResult.getLoginID());
			//_LoginBodyType2SecondEditText.setPadding();
			_LoginBodyType2SecondEditText.setText(mInfoSearchResult.getRegisterDate());
		}
		else if(mCurrentLoginType == LOGIN_SEARCH_RESULT_PASSWORD_SUCCESS)
		{
			//_LoginBodyType2FirstEditText.setPadding();
			_LoginBodyType2FirstEditText.setText(mInfoSearchResult.getBlindEmail());
		}
	}
	
	@OnClick({R.id.login_search_type1_ok_button, R.id.login_search_type1_cancel_button, R.id.login_search_type2_ok_button
		, R.id.login_search_id_text, R.id.login_search_pw_text})
	public void selectClick(View view)
	{
		int inputChecktype = CheckUserInput.INPUT_SUCCESS;
		switch(view.getId())
		{

			
		case R.id.login_search_type1_ok_button:
			
			inputChecktype = CheckUserInput.getInstance(mContext).checkIDData(_LoginBodyType1EditText.getText().toString()).getResultValue();
			
			if(inputChecktype != CheckUserInput.INPUT_SUCCESS)
			{
				Toast.makeText(mContext, CheckUserInput.getInstance(mContext).getErrorMessage(inputChecktype), Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(mCurrentLoginType == LOGIN_SEARCH_ID || mCurrentLoginType == LOGIN_SEARCH_RESULT_ID_FAIL)
			{
				
				mOnLoginListener.executeSearchID(_LoginBodyType1EditText.getText().toString());
			}
			else if(mCurrentLoginType == LOGIN_SEARCH_PASSWORD ||mCurrentLoginType == LOGIN_SEARCH_RESULT_PASSWORD_FAIL)
			{
				mOnLoginListener.executeSearchPassword(_LoginBodyType1EditText.getText().toString());
			}
			
			dismiss();
			break;
		case R.id.login_search_type1_cancel_button:
			dismiss();
			break;
		case R.id.login_search_type2_ok_button:
			if(mCurrentLoginType == LOGIN_SEARCH_RESULT_ID_SUCCESS)
			{
				dismiss();
			}
			else if(mCurrentLoginType == LOGIN_SEARCH_RESULT_PASSWORD_SUCCESS)
			{
				inputChecktype = CheckUserInput.getInstance(mContext).checkIDData(_LoginBodyType2SecondEditText.getText().toString()).getResultValue();
				
				if(inputChecktype != CheckUserInput.INPUT_SUCCESS)
				{
					Toast.makeText(mContext, CheckUserInput.getInstance(mContext).getErrorMessage(inputChecktype), Toast.LENGTH_SHORT).show();
					return;
				}

				
				mOnLoginListener.sendEmail(_LoginBodyType2SecondEditText.getText().toString());
				dismiss();
			}
			break;
			
		case R.id.login_search_id_text:
			changeSearchTab(LOGIN_SEARCH_ID);
			break;
		case R.id.login_search_pw_text:
			changeSearchTab(LOGIN_SEARCH_PASSWORD);
			break;
		}
	}
}
