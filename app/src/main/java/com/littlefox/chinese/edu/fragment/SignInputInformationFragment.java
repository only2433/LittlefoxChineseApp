package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.UserSignActivity.OnSignListener;
import com.littlefox.chinese.edu.UserSignActivity.UserSignCallback;
import com.littlefox.chinese.edu.common.CheckUserInput;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.object.UserSignObject;
import com.littlefox.chinese.edu.view.PaddingDrawableEditText;
import com.littlefox.library.view.dialog.MaterialLoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInputInformationFragment extends Fragment implements UserSignCallback
{
	@BindView(R.id.sign_warning_id_text)
	TextView _InputIDWarningText;
	
	@BindView(R.id.sign_id_edit)
	PaddingDrawableEditText _InputIDEditText;
	
	@BindView(R.id.sign_warning_pw_text)
	TextView _InputPasswordWarningText;
	
	@BindView(R.id.sign_pw_edit)
	PaddingDrawableEditText _InputPasswordEditText;
	
	@BindView(R.id.sign_pw_confirm_edit)
	PaddingDrawableEditText _InputPasswordConfirmEditText;
	
	@BindView(R.id.sign_warning_nickname_text)
	TextView _InputNickNameWarningText;

	@BindView(R.id.sign_nickname_edit)
	PaddingDrawableEditText _InputNicknameEditText;
	
	@BindView(R.id.sign_confirm_button)
	TextView _SignCheckButton;
	
	/*
	 * 회원가입 관련 오류 인덱스
	 */
	
	private Context mContext;
	private OnSignListener mOnSignListener;
	private int mCurrentSignInput = CheckUserInput.INPUT_SUCCESS;
	private MaterialLoadingDialog mLoadingDialog = null;
	
	public static Fragment getInstance()
	{
		return new SignInputInformationFragment();
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
			view = inflater.inflate(R.layout.user_sign_input_information_fragment_tablet, container, false);
		}
		else
		{
			view = inflater.inflate(R.layout.user_sign_input_information_fragment, container, false);
		}
		
		ButterKnife.bind(this, view);
		init();
		initFont();
		initText();
		return view;
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
	}

	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();

	}
	
	private void init()
	{
		mCurrentSignInput = CheckUserInput.INPUT_SUCCESS;
	}

	private void initFont()
	{
		_InputIDWarningText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_InputIDEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		
		_InputPasswordWarningText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_InputPasswordEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_InputPasswordConfirmEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		
		_InputNickNameWarningText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_InputNicknameEditText.setTypeface(Font.getInstance(mContext).getRobotoMedium());

		_SignCheckButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	private void initText()
	{
		_InputIDWarningText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_input_use_email));
		_InputIDEditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_email));
		_InputPasswordWarningText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_password_example));
		_InputPasswordEditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_pw));
		_InputPasswordConfirmEditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_pw_confirm));
		_InputNickNameWarningText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_nickname_example));
		_InputNicknameEditText.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_nickname));

		_SignCheckButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_sign));
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
	private void setResult()
	{
		int inputCheckType = CheckUserInput.INPUT_SUCCESS;
		
		inputCheckType = CheckUserInput.getInstance(mContext)
				.checkIDData(_InputIDEditText.getText().toString())
				.checkPasswordData(_InputPasswordEditText.getText().toString(), _InputPasswordConfirmEditText.getText().toString())
				.checkNicknameData(_InputNicknameEditText.getText().toString())
				.getResultValue();
		
		if(inputCheckType == CheckUserInput.INPUT_SUCCESS)
		{
			showLoadingDialog();
			UserSignObject result = new UserSignObject(
					_InputIDEditText.getText().toString(), 
					_InputPasswordEditText.getText().toString(), 
					_InputNicknameEditText.getText().toString());	
			mOnSignListener.CheckUserSign(result);		
		}
		else
		{
			mOnSignListener.ShowMessage(CheckUserInput.getInstance(mContext).getErrorMessage(inputCheckType), mContext.getResources().getColor(R.color.color_d8232a));
		}
	}


	@OnClick(R.id.sign_confirm_button)
	public void selectClick(View view)
	{
		switch(view.getId())
		{
			case R.id.sign_confirm_button:
				setResult();
				break;

		}
	}

	@Override
	public void setOnSignListener(OnSignListener onSignListener)
	{
		mOnSignListener = onSignListener;
	}
	

	@Override
	public void onUserSignCheckEnd()
	{
		hideLoadingDialog();
	}
	
}