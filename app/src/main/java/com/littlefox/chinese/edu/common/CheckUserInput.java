package com.littlefox.chinese.edu.common;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.object.UserLoginObject;
import com.littlefox.logmonitor.Log;

/**
 * 사용자의 입력 정보를 정확한 정보로 입력했는지 확인하는 Singleton Builder Class. 빌더패턴으로 구현해 간결하게 사용하게 변경
 * @author 정재현
 *
 */
public class CheckUserInput
{
	// 글자내용을 체그하기 위한 패턴
	private static final String TEXT_ENGLISH_KOREAN_NUMBER 			= "^[a-zA-Z0-9가-힣]*$";
	private static final String TEXT_EMAIL							= "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
	private static final String TEXT_ENGLISH_NUMBER 				= "^[a-zA-Z0-9]*$";
	private static final String TEXT_ENGLISH_KOREAN 				= "^[a-zA-Z가-힣]*$";
	
	public static final int INPUT_SUCCESS									= 100;
	public static final int WARNING_ID_NOT_INPUT 							= 0;
	public static final int WARNING_ID_WRONG_INPUT							= 1;
	public static final int WARNING_PASSWORD_NOT_INPUT 						= 2;
	public static final int WARNING_PASSWORD_NOT_INPUT_CONFIRM				= 3;
	public static final int WARNING_PASSWORD_NOT_INPUT_NEW					= 4;
	public static final int WARNING_PASSWORD_WRONG_INPUT 					= 5;
	public static final int WARNING_PASSWORD_NOT_EQUAL_CONFIRM				= 6;
	public static final int WARNING_PASSWORD_EQUAL_ORIGIN					= 7;
	public static final int WARNING_PASSWORD_NOT_CORRECT_ORIGIN 			= 8;
	public static final int WARNING_NICK_NAME_NOT_INPUT 					= 9;
	public static final int WARNING_NICK_NAME_WRONG_INPUT 					= 10;
	public static final int WARNING_NAME_NOT_INPUT 							= 11;
	public static final int WARNING_NAME_WRONG_INPUT			 			= 12;
	public static final int WARNING_BIRTHDAY_NOT_INPUT 						= 13;
	public static final int WARNING_SEX_NOT_INPUT							= 14;
	public static final int WARNING_PHONE_NOT_INPUT							= 15;
	public static final int WARNING_PHONE_LIMIT_INPUT						= 16;
	
	public static final String SEX_TYPE_MALE 		= "M";
	public static final String SEX_TYPE_FEMALE 		= "F";

	
	private static CheckUserInput sCheckUserInput = null;
	private static Context sContext = null;
	private static int sResultValue = INPUT_SUCCESS;
	public static CheckUserInput getInstance(Context context)
	{
		if(sCheckUserInput == null)
		{
			sCheckUserInput = new CheckUserInput();
		}
		sContext = context;
		sResultValue = INPUT_SUCCESS;
		return sCheckUserInput;
	}
	
	/**
	 * 해당 패턴말고 다른 글자가 들어있는 지 체크
	 * @param pattenText 패턴
	 * @param text 해당 텍스트
	 * @return TRUE : 패턴안에 들어있다. FALSE : 패턴안에 없는 글자가 있다.
	 */
	private boolean isExceptTextHave(String pattenText, String text)
	{
		if(Pattern.matches(pattenText, text))
		{
			Log.f("Match");
			return true;
		}
		else 
		{
			Log.f("Not Match");
			return false;
		}
	}
	
	/**
	 * 특정 스트링의 바이트 사이즈가 start와 end 사이에 있는 지 확인
	 * @param text 해당 텍스트
	 * @param start 시작 인텍스 크기
	 * @param end 종료 인덱스 크기
	 * @return
	 */
	private boolean isByteSizeFit(String text, int start , int end)
	{
		try
		{
			Log.f("text : "+ text+", text.getBytes().length : "+text.getBytes("ms949").length);
			if(text.getBytes().length >= start && text.getBytes("ms949").length <= end)
			{
				return true;
			}
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	/**
	 * ID 를 체크 하는 메소드 
	 * @param idText
	 * @return
	 */
	public CheckUserInput checkIDData(String idText)
	{
		Log.f("idText : "+idText);
		
		if(sResultValue != INPUT_SUCCESS)
		{
			return this;
		}
		
		if(idText.equals(""))
		{
			sResultValue =  WARNING_ID_NOT_INPUT;
		}
		else if(isExceptTextHave(TEXT_EMAIL, idText) == false)
		{
			sResultValue = WARNING_ID_WRONG_INPUT;
		}
		
		return this;
	}
	
	/**
	 * 패스워드를 체크 하는 메소드
	 * @param originPasswordText 기존의 패스워드
	 * @param passwordText 새로 바뀌려는 패스워드
	 * @param confirmPasswordText 다시 입력한 새로 바뀌려는 패스워드
	 * @return
	 */
	public CheckUserInput checkPasswordData(String originPasswordText, String passwordText, String confirmPasswordText)
	{
		Log.f("originPasswordText : "+originPasswordText+", passwordText : "+passwordText+", confirmPasswordText : "+confirmPasswordText);
		UserLoginObject loginObject = (UserLoginObject) CommonUtils.getInstance(sContext).getPreferenceObject(Common.PARAMS_USER_LOGIN, UserLoginObject.class);
		
		if(sResultValue != INPUT_SUCCESS)
		{
			return this;
		}
		
		if(originPasswordText.equals(""))
		{
			sResultValue = WARNING_PASSWORD_NOT_INPUT;
		}
		else if(loginObject.getUserPassword().equals(originPasswordText) == false)
		{
			sResultValue = WARNING_PASSWORD_NOT_CORRECT_ORIGIN;
		}
		else if (passwordText.equals(""))
		{
			sResultValue = WARNING_PASSWORD_NOT_INPUT_NEW;
		}
		else if (isExceptTextHave(TEXT_ENGLISH_NUMBER, passwordText) == false || isByteSizeFit(passwordText, 4, 10) == false)
		{
			sResultValue = WARNING_PASSWORD_WRONG_INPUT;
		}
		else if (confirmPasswordText.equals(""))
		{
			sResultValue = WARNING_PASSWORD_NOT_INPUT_CONFIRM;
		}
		else if(originPasswordText.equals(passwordText))
		{
			sResultValue = WARNING_PASSWORD_EQUAL_ORIGIN;
		}
		else if (passwordText.equals(confirmPasswordText) == false)
		{
			sResultValue = WARNING_PASSWORD_NOT_EQUAL_CONFIRM;
		}
		
		return this;
	}
	
	public CheckUserInput checkPasswordData(String passwordText, String confirmPasswordText)
	{
		Log.f("passwordText : "+passwordText+", confirmPasswordText : "+confirmPasswordText);
		
		if(sResultValue != INPUT_SUCCESS)
		{
			return this;
		}
		
		if(passwordText.equals(""))
		{
			sResultValue = WARNING_PASSWORD_NOT_INPUT;
		}
		else if (isExceptTextHave(TEXT_ENGLISH_NUMBER, passwordText) == false || isByteSizeFit(passwordText, 4, 10) == false)
		{
			sResultValue = WARNING_PASSWORD_WRONG_INPUT;
		}
		else if(confirmPasswordText.equals(""))
		{
			sResultValue = WARNING_PASSWORD_NOT_INPUT_CONFIRM;
		}
		else if(passwordText.equals(confirmPasswordText) == false)
		{
			sResultValue = WARNING_PASSWORD_NOT_EQUAL_CONFIRM;
		}
		
		return this;
	}
	
	/**
	 * Nickname을 체크하는 메소드
	 * @param nickname
	 * @return
	 */
	public CheckUserInput checkNicknameData(String nickname)
	{
		Log.f("nickname : "+nickname);
		
		if(sResultValue != INPUT_SUCCESS)
		{
			return this;
		}
		
		if(nickname.equals(""))
		{
			sResultValue = WARNING_NICK_NAME_NOT_INPUT;
		}
		else if(isExceptTextHave(TEXT_ENGLISH_KOREAN_NUMBER, nickname) == false ||  isByteSizeFit(nickname, 4, 16) == false)
		{
			sResultValue = WARNING_NICK_NAME_WRONG_INPUT;
		}
		
		return this;
	}
	
	/**
	 * Name을 체크하는 메소드
	 * @param name
	 * @return
	 */
	public CheckUserInput checkNameData(String name)
	{
		Log.f("name : "+name);
		if(sResultValue != INPUT_SUCCESS)
		{
			return this;
		}
		
		if(name.equals(""))
		{
			sResultValue = INPUT_SUCCESS;
		}
		else if(isExceptTextHave(TEXT_ENGLISH_KOREAN, name) == false ||  isByteSizeFit(name, 4, 16) == false)
		{
			sResultValue = WARNING_NAME_WRONG_INPUT;
		}
		
		return this;
	}
	
	/**
	 * Caledar 정보를 체크
	 * @param calendar
	 * @return
	 */
	public CheckUserInput checkCalendarData(String calendar)
	{
		Log.f("calendar : "+calendar);
		if(sResultValue != INPUT_SUCCESS)
		{
			return this;
		}
		
		if(calendar.equals(""))
		{
			sResultValue = INPUT_SUCCESS;
		}
		
		return this;
	}
	
	/**
	 * Phone 정보를 체크
	 * @param phone
	 * @return
	 */
	public CheckUserInput checkPhoneData(String phone)
	{
		Log.f("phone : "+phone);
		if(sResultValue != INPUT_SUCCESS)
		{
			return this;
		}
		
		if(phone.equals(""))
		{
			sResultValue = INPUT_SUCCESS;
		}
		return this;
	}
	
	/**
	 * Sex 정보를 체크
	 * @param sexType 성별
	 * @return
	 */
	public CheckUserInput checkSexData(String sexType)
	{
		Log.f("sexType : "+sexType);
		if(sResultValue != INPUT_SUCCESS)
		{
			return this;
		}
		
		if(sexType.equals(""))
		{
			sResultValue = WARNING_SEX_NOT_INPUT;
		}
		return this;
	}
	
	/**
	 * 체크한 정보 값을 전달한다.
	 * @return
	 */
	public int getResultValue()
	{
		Log.f("sResultValue : "+sResultValue);
		return sResultValue;
	}
	
	/**
	 * 오류때의 메세지를 호출하는 메소드
	 * @param type
	 * @return
	 */
	public String getErrorMessage(int type)
	{
		switch(type)
		{
		case WARNING_ID_NOT_INPUT :
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_input_use_email);
		case WARNING_ID_WRONG_INPUT:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_input_error_email);
		case WARNING_PASSWORD_NOT_INPUT:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_password_not_input);
		case WARNING_PASSWORD_NOT_INPUT_CONFIRM:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_password_not_input_confirm);
		case WARNING_PASSWORD_NOT_INPUT_NEW:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_password_not_input_new);
		case WARNING_PASSWORD_NOT_CORRECT_ORIGIN:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_input_not_correct_origin);
		case WARNING_PASSWORD_EQUAL_ORIGIN:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_input_equal_origin);
		case WARNING_PASSWORD_WRONG_INPUT:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_password_example);
		case WARNING_PASSWORD_NOT_EQUAL_CONFIRM:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_password_not_equal);
		case WARNING_NICK_NAME_NOT_INPUT:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_nickname_not_input);
		case WARNING_NICK_NAME_WRONG_INPUT:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_nickname_example);
		case WARNING_NAME_NOT_INPUT:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_name_not_input);
		case WARNING_NAME_WRONG_INPUT:	
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_name_example);
		case WARNING_BIRTHDAY_NOT_INPUT:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_birthday_select);
		case WARNING_SEX_NOT_INPUT:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_sex_select);
		case WARNING_PHONE_NOT_INPUT:
			return CommonUtils.getInstance(sContext).getLanguageTypeString(R.array.message_phone_select);
		}
		return "";
	}
}
