package com.littlefox.chinese.edu.object;

import java.util.ArrayList;

import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.object.result.base.PlayObject;

public class StudyDataInformation extends PlayObject
{
	public static final int TYPE_SECTION_TITLE = 0;
	public static final int TYPE_DEFAULT_LIST = 1;
	/**
	 * 리스트의 Section Title
	 */
	private String mSectionTitle = "";

	private int mCurrentType = TYPE_DEFAULT_LIST;
	
	public StudyDataInformation(String title)
	{
		mSectionTitle = title;
		mCurrentType = TYPE_SECTION_TITLE;
	}
	
	public StudyDataInformation(PlayObject playObject)
	{
		this.fc_id 					= playObject.fc_id;
		this.cont_name 				= playObject.cont_name;
		this.image_url   			= playObject.image_url;
		this.play_time				= playObject.play_time;
		mCurrentType = TYPE_DEFAULT_LIST;
	}

	public String getSectionTitle()
	{
		return mSectionTitle;
	}


	public int getCurrentType()
	{
		return mCurrentType;
	}
}