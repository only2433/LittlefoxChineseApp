package com.littlefox.chinese.edu.object.result;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.littlefox.chinese.edu.object.FeaturePlayObject;

/**
 * Today 정보 
 * @author 정재현
 *
 */
public class NewReleaseResult extends FeaturePlayObject
{

	private static final long serialVersionUID = 1L;
	/**
	 * 오픈 날짜를 의미 형식은  예 :) 12/14/2015
	 */
	private String open_date = "";
	
	public String getOpenDate()
	{
		return open_date;
	}
	
	
	/**
	 * 오늘 정보가 맞는 지 확인
	 * @return
	 */
	public boolean isTodayMatch()
	{
		Date todayDate = new Date();
		String todayString  = new SimpleDateFormat("MM/dd/yyyy").format(todayDate);
        return getOpenDate().equals(todayString);

    }
}