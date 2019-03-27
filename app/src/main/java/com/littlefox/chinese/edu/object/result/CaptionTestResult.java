package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.result.base.BaseResult;

/**
 * CAPTION_TEST
 */
public class CaptionTestResult extends BaseResult
{
	private String play_url = "";
	private CaptionInformationResult caption_data = null;
	
	public String getVideoUrl()
	{
		return play_url;
	}
	
	public ArrayList<CaptionDetailInformation> getCaptionInformationResult()
	{
		return caption_data.getCaptionDetailInformationList();
	}
}
