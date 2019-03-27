package com.littlefox.chinese.edu.object;

import com.littlefox.chinese.edu.common.Common;
import com.littlefox.logmonitor.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 다시보지 않기를 한 IAC 아이템을 특정 조건이 되었을 때 다시 보이게 하기 위해 만든 오브젝트
 * @author 정재현
 *
 */
public class IACController
{
	private NeverWatchIACInformation mSaveIACInformation = null;
	private boolean isIACAwake = false;
	private boolean isPositiveButtonClick = false;
	public IACController()
	{
		mSaveIACInformation = null;
	}
	
	/**
	 * 현재 서버의 IAC 정보를 넘겨주고 로컬 IAC와 비교를 통해 정보를 전달한다. 로컬 정보가 없을땐 서버정보의 IAC로 세팅한다.
	 * @param serverIACInformation
	 * @return
	 */
	public boolean isAwake(NeverWatchIACInformation serverIACInformation)
	{
		if(mSaveIACInformation == null)
		{
			isPositiveButtonClick = false;
			isIACAwake = true;
			mSaveIACInformation = serverIACInformation;
		}
		else if(mSaveIACInformation.iacCode.equals(serverIACInformation.iacCode) == false)
		{
			isPositiveButtonClick = false;
			isIACAwake = true;
			mSaveIACInformation = serverIACInformation;
		}
		else
		{
			awakeIACItem();
		}
		
		return isIACVisible();
	}
	
	/**
	 * 링크이동을 클릭했을 경우 IAC는 어떠한 상황이든 노출이 안된다.
	 */
	public void setPositiveButtonClick()
	{
		isPositiveButtonClick = true;
	}
	
	/**
	 * 닫기나 보지 않기를 클릭했을 때 세팅
	 */
	public void setNeverWatch()
	{
		isIACAwake = false;
	}
	
	/**
	 * IAC를 보여줘야하는 지에 대한 구분을 하는 메소드
	 * @return
	 */
	public boolean isIACVisible()
	{
		return isIACAwake;
	}
	
	/**
	 * 특정 상황이 되었을 때, IAC를 보여줘야하는 지에 대한 부분을 체크하는 메소드
	 */
	private void awakeIACItem()
	{
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date;
		Date saveDate;

		if(isPositiveButtonClick == true)
		{
			isIACAwake = false;
			return;
		}
		
		if(mSaveIACInformation.iacType.equals(Common.IAC_AWAKE_CODE_ALWAYS_VISIBLE))
		{
			isIACAwake = true;
		}
		else if (mSaveIACInformation.iacType.equals(Common.IAC_AWAKE_CODE_SPECIAL_DATE_VISIBLE))
		{
			Log.i("");
			date = new Date(currentTime);
			saveDate = new Date(mSaveIACInformation.iacNeverWatchTime);
			String currentDateFormat = CurDateFormat.format(date);
			String savedDateFormat = CurDateFormat.format(saveDate);
			
			if (Integer.valueOf(currentDateFormat) - Integer.valueOf(savedDateFormat) >= Integer.valueOf(mSaveIACInformation.latingDate))
			{
				isIACAwake = true;
			}
		}
		else if (mSaveIACInformation.iacType.equals(Common.IAC_AWAKE_CODE_ONCE_VISIBLE))
		{
			isIACAwake = false;
		}
	}
	
	/**
	 * 서버정보와 비교하여 다시 보지 않기를 클릭한 되야하는 IAC 정보
	 * @author 정재현
	 *
	 */
	public class NeverWatchIACInformation
	{
		/** IAC 각각의 고유 식별자 */
		public String iacCode ="";
		
		/** IAC를 다시 보지않기를 눌렀을 때의 시간 */
		public long iacNeverWatchTime = -1;
		
		/**
		 * IAC 두번째 버튼 타입
		 */
		public String iacType = "";
		
		/**
		 * 특정 날짜가 되엇을 때 노출 시키기 위한 Date
		 */
		public String latingDate = "";
		
		public NeverWatchIACInformation(String iacCode, long iacSleepTime, String iacType, String latingDate)
		{
			this.iacCode 					= iacCode;
			this.iacNeverWatchTime 	= iacSleepTime;
			this.iacType 					= iacType;
			this.latingDate				= latingDate;
		}
		
		public NeverWatchIACInformation(String iacCode, long iacSleepTime, String iacType)
		{
			this.iacCode 					= iacCode;
			this.iacNeverWatchTime 	= iacSleepTime;
			this.iacType 					= iacType;
			this.latingDate				= "";
		}
	}
	
}
