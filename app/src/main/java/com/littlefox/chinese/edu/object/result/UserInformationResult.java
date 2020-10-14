package com.littlefox.chinese.edu.object.result;

import com.littlefox.chinese.edu.object.UserBaseInformationObject;
import com.littlefox.chinese.edu.object.UserPaymentStatus;
import com.littlefox.chinese.edu.object.result.base.BaseResult;

import java.util.ArrayList;

/**
 * 유저의 정보 및 추가사용자 정보를 가져오는 Result 정보
 * @author 정재현
 *
 */
public class UserInformationResult extends BaseResult
{
	private UserBaseInformationObject user_info;
	private ArrayList<UserBaseInformationObject> list = new ArrayList<UserBaseInformationObject>();
	private UserPaymentStatus payment_status = null;

	public UserBaseInformationObject getUserInformation()
	{
		return user_info;
	}
	
	public ArrayList<UserBaseInformationObject> getChildInformationList()
	{
		return list;
	}

	public UserPaymentStatus getPaymentStatus()
	{
		return payment_status;
	}
}
