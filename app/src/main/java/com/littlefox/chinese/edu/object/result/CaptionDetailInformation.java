package com.littlefox.chinese.edu.object.result;

public class CaptionDetailInformation
{
	String start_time = "";
	String text = "";
	String end_time = "";
	
	public float getStartTime()
	{
		return Float.valueOf(start_time);
	}
	
	public float getEndTime()
	{
		return Float.valueOf(end_time);
	}
	
	public String getText()
	{
		return text;
	}
}