package com.littlefox.chinese.edu.object.result.base;

public class QuizBaseObject extends BaseResult
{
	private String fc_id ="";
	
	private String locale = "";
	
	private String fc_name= "";
	
	private String fc_sub_name = "";
	
	private int fc_level = -1;
	
	private int time_limit = -1;
	
	private String type = "";
	
	private String quiz_id ="";

	public String getFCId()
	{
		return fc_id;
	}

	public String getName()
	{
		return fc_name;
	}
	
	public String getSubName()
	{
		return fc_sub_name;
	}

	public int getTimeLimit()
	{
		return time_limit;
	}

	public int getLevel()
	{
		return fc_level;
	}

	public String getType()
	{
		return type;
	}

	public String getQuizId()
	{
		return quiz_id;
	}
}
