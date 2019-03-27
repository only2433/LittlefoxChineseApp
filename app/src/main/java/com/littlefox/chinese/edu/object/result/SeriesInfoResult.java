package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.result.base.BaseResult;

/**
 * 시리즈의 정보를 담고 있는 객체
 * @author 정재현
 *
 */
public class SeriesInfoResult extends BaseResult
{
	private String series_name = "";
	private String series_name_ko = "";
	private String series_name_en = "";
	private String level = "";
	private String total_cnt ="";
	private String fs_desc = "";
	private ArrayList<SeriesInfoPeopleObject> list = new ArrayList<SeriesInfoPeopleObject>();
	
	public String getSeriesName()
	{
		return series_name;
	}
	
	public String getSeriesNameKo()
	{
		return series_name_ko;
	}
	
	public String getSeriesNameEn()
	{
		return series_name_en;
	}
	
	public String getLevel()
	{
		return level;
	}
	
	public String getTotalCountText()
	{
		return total_cnt;
	}
	
	public String getDescription()
	{
		return fs_desc;
	}
	
	public ArrayList<SeriesInfoPeopleObject> getPeopleInfoList()
	{
		return list;
	}
	
	/**
	 * 시리즈 정보의 등장인물정보를 담고있는 객체
	 * @author 정재현
	 *
	 */
	public class SeriesInfoPeopleObject
	{
		private String name 			= "";
		private String image_name 	= "";
		
		public String getName()
		{
			return name;
		}
		
		public String getImageUrl()
		{
			return image_name;
		}
	}
}
