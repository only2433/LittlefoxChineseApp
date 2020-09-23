package com.littlefox.chinese.edu.object.result.base;

import java.io.Serializable;

/**
 * 플레이 영상에 관련되어 있는 기본 오브젝트
 * @author 정재현
 *
 */
public class PlayObject implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int position				= 0;
	public String fc_id					= "";
	public String image_url 			= "";
	public String cont_name				= "";
	public String play_time				= "";
	
	public String getTitle()
	{
		return cont_name;
	}
	
	public String getContentId()
	{
		return fc_id;
	}


	public String getThumbnalUrl()
	{
		return image_url;
	}
	
	public String getPlayTime()
	{
		return play_time;
	}


	public void setPosition(int position)
	{
		this.position = position;
	}

	public String getPosition()
	{
		if(position == 0)
		{
			return "";
		}

		if(position < 10)
		{
			return "0" + String.valueOf(position);
		}
		else
		{
			return String.valueOf(position);
		}
	}
	
}
