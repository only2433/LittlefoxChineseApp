package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.result.base.BaseResult;
import com.littlefox.chinese.edu.object.result.base.PlayObject;

/**
 * 서버에서 컨텐트 리스트 정보를 불러온다.
 * @author 정재현
 *
 */
public class ContentListResult extends BaseResult
{	
	/**
	 * 시리즈 정보의 ID
	 */
	private String fs_id ="";
	private ArrayList<ContentItemObject> list = new ArrayList<ContentItemObject>();
	private String caption_yn = "";
	
	public String getSeriesID()
	{
		return fs_id;
	}

	public ArrayList<ContentItemObject> getContentList()
	{
		return list;
	}
	
	/**
	 * 자막 정보가 없는 지의 유무
	 * @return
	 */
	public boolean isCaptionEmpty()
	{
        return caption_yn.equals("N");

    }
	
	public class ContentItemObject extends PlayObject
	{
		private boolean isSelected = false;
		private String cont_name_ko = "";
		private String cont_name_en = "";
		private String quiz_use_yn = "";
		private String feature_use_yn = "";
		
		
		
		public String isQuizUse()
		{
			return quiz_use_yn;
		}

		public String isFeatureUse()
		{
			return feature_use_yn;
		}

		public String getSubTitleKo()
		{
			return cont_name_ko;
		}
		
		public String getSubTitleEn()
		{
			return cont_name_en;
		}
		
		public void setSelected(boolean isSelect)
		{
			isSelected = isSelect;
		}
		
		public boolean isSelected()
		{
			return isSelected;
		}
	}
}
