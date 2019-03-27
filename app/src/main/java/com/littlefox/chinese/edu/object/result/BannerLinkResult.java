package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

/**
 * 배너 정보 리스트 
 * @author 정재현
 *
 */
public class BannerLinkResult
{
	private String target_url = "";
	private String title = "";
	private String image_url = "";
	
	public String getLinkUrl()
	{
		return target_url;
	}

	public String getImageUrl()
	{
		return image_url;
	}
	
	public String getTitle()
	{
		return title;
	}

}