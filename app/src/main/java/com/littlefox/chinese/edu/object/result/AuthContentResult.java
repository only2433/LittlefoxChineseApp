package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.result.base.BaseResult;

public class AuthContentResult extends BaseResult
{
	private String video_url = "";
	
	private ArrayList<CaptionDetailInformation> caption_list = new ArrayList<CaptionDetailInformation>();
	
	public ArrayList<CaptionDetailInformation> getCaptionDetailInformationList()
	{
		return caption_list;
	}
	
	public String getVideoUrl()
	{
		return video_url;
	}
}
