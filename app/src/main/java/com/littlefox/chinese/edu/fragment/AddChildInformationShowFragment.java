package com.littlefox.chinese.edu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.littlefox.chinese.edu.AddChildInformationModificationActivity.AddChildHolder;
import com.littlefox.chinese.edu.AddChildInformationModificationActivity.OnAddChildListener;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.object.UserBaseInformationObject;
import com.littlefox.chinese.edu.object.UserTotalInformationObject;
import com.littlefox.chinese.edu.view.PaddingDrawableEditText;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddChildInformationShowFragment extends Fragment implements AddChildHolder
{
	@BindView(R.id.addchild_title_layout)
	ScalableLayout _TitleBaseLayout;
	
	@BindView(R.id.addchild_message_text)
	TextView _InformationTitleText;
	
	@BindView(R.id.addchild_scrollview)
	ScrollView _ScrollView;
	
	@BindView(R.id.addchild_base_layout)
	LinearLayout _AddChildBaseViewLayout;
	
	@BindView(R.id.addchild_add_button_base_layout)
	ScalableLayout _AddChildAddBaseLayout;
	
	@BindView(R.id.addchild_add_button)
	ImageView _AddChildAddButton;
	
	@BindView(R.id.addchild_add_text)
	TextView _AddTitleText;
	
	private static final int MAX_CHILD_SIZE = 3;
	
	private static final int INFORMATION_MANAGE 	= 0;
	private static final int INFORMATION_GUEST		= 1;
	
	private Context mContext;
	private UserTotalInformationObject mUserTotalInformationObject;
	private OnAddChildListener mOnAddChildListener;
	
	public static AddChildInformationShowFragment getInstance()
	{
		return new AddChildInformationShowFragment();
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
			view = inflater.inflate(R.layout.addchild_manage_show_fragment_tablet, container, false);
		}
		else
		{
			view = inflater.inflate(R.layout.addchild_manage_show_fragment, container, false);
		}
		
		ButterKnife.bind(this, view);
		_AddChildAddBaseLayout.setVisibility(View.VISIBLE);
		initAddUserLayout();
		initFont();
		initText();
		return view;
	}

	private void initFont()
	{
		_InformationTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_AddTitleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
	}
	
	private void initText()
	{
		_InformationTitleText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.message_add_user_manage));
		_AddTitleText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_add));
	}
	
	private void initAddUserLayout()
	{
		_AddChildBaseViewLayout.removeAllViews();
		mUserTotalInformationObject = (UserTotalInformationObject) CommonUtils.getInstance(mContext).getPreferenceObject(Common.PARAMS_USER_TOTAL_INFO, UserTotalInformationObject.class);
		
		Log.i("Child Size : "+mUserTotalInformationObject.getChildInformationList().size());
		if(mUserTotalInformationObject.isBaseUserUse())
		{
			for(int i = 0; i < mUserTotalInformationObject.getChildInformationList().size() ; i++)
			{
				makeAddUserUILayout(INFORMATION_MANAGE,i);
			}
			
			if(mUserTotalInformationObject.getChildInformationList().size() < MAX_CHILD_SIZE)
			{
				_AddChildBaseViewLayout.addView(_AddChildAddBaseLayout);
			}
		}
		else
		{
			_TitleBaseLayout.setVisibility(View.GONE);
			makeAddUserUILayout(INFORMATION_GUEST, mUserTotalInformationObject.getCurrentUserIndex());
			FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) _AddChildBaseViewLayout.getLayoutParams();
			params.setMargins(0, CommonUtils.getInstance(mContext).getPixel(30), 0, 0);
			_AddChildBaseViewLayout.setLayoutParams(params);
			
		}
	}
	
	private void makeAddUserUILayout(int type, int index)
	{
		View baseView;
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final int position = index;
		UserBaseInformationObject childInformation = mUserTotalInformationObject.getChildInformationList().get(index);
		
		Log.i("index : "+index + " , childInformation date : "+ childInformation.getBirthday());
		if(Feature.IS_TABLET)
		{
			baseView	= inflater.inflate(R.layout.addchild_manage_add_sub_tablet, null);
		}
		else
		{
			baseView	= inflater.inflate(R.layout.addchild_manage_add_sub, null);
		}
		
		
		TextView _titleText = ButterKnife.findById(baseView, R.id.addchild_show_child_titile);
		_titleText.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_titleText.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.title_add_user)+" "+String.valueOf(index+1));
		
		TextView _deleteButton = ButterKnife.findById(baseView, R.id.addchild_show_delete_button);
		_deleteButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_deleteButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_delete));
		if(type == INFORMATION_GUEST)
		{
			_deleteButton.setVisibility(View.GONE);
		}
		
		TextView _modificationButton = ButterKnife.findById(baseView, R.id.addchild_show_info_modification_button);
		_modificationButton.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_modificationButton.setText(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.button_edit));
		
		PaddingDrawableEditText _nicknameEdit = ButterKnife.findById(baseView, R.id.addchild_modification_nickname_edit);
		_nicknameEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_nicknameEdit.setEnabled(false);
		_nicknameEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_nickname));
		_nicknameEdit.setText(childInformation.getNickname());
		
		PaddingDrawableEditText _nameEdit = ButterKnife.findById(baseView, R.id.addchild_modification_name_edit);
		_nameEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_nameEdit.setEnabled(false);
		_nameEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_name));
		_nameEdit.setText(childInformation.getName());

		PaddingDrawableEditText _calendarEdit = ButterKnife.findById(baseView, R.id.addchild_modification_calendar_text);
		_calendarEdit.setTypeface(Font.getInstance(mContext).getRobotoMedium());
		_calendarEdit.setEnabled(false);
		_calendarEdit.setHint(CommonUtils.getInstance(mContext).getLanguageTypeString(R.array.login_birthday));
		
		try
		{
			if(childInformation.getBirthday().equals("") == false)
			{
				_calendarEdit.setText(childInformation.getBirthday().substring(0, 4));
			}
			
		}catch(StringIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}

	
		_deleteButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mOnAddChildListener.executeAction(AddChildHolder.ACTION_DELETE, position);
			}
		});
		
		_modificationButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mOnAddChildListener.executeAction(AddChildHolder.ACTION_MODIFICATION, position);
			}
		});
		
		_AddChildBaseViewLayout.addView(baseView);
	}
	
	public void nofityAddChildLayout()
	{
		initAddUserLayout();
	}
	
	@OnClick({R.id.addchild_add_button})
	public void OnSelectClick(View view)
	{
		switch(view.getId())
		{
		case R.id.addchild_add_button:
			mOnAddChildListener.executeAction(AddChildHolder.ACTION_ADD, -1);
			break;
		}
	}

	@Override
	public void setOnAddChildListener(OnAddChildListener onAddChildListener)
	{
		mOnAddChildListener = onAddChildListener;
	}
	
}
