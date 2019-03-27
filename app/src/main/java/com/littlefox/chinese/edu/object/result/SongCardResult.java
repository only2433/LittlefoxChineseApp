package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.result.base.PlayObject;

/**
 * 송 화면 정보를 담고 있는 클래스
 * @author 정재현
 *
 */
public class SongCardResult extends PlayObject
{

	/**
	 * ID 가  -1일 경우는 Coming Soon 을 나타내는 부분. 실행은 되지않고 화면에만 보여준다.
	 */
	private int id 					= -1;
	private String cont_name_ko 	= "";
	private String cont_name_en 	= "";
	private String coming_soon_layer_img_url = "";
	private int mCurrentIndex 		= 0;
	private String open_date = "";
	
	/**
	 * 사용자가 선택한 리스트만 뽑아내기 위해 사용
	 */
	private boolean isSelected = false;
	
	public SongCardResult(int index , int id, String fc_id, String title_ko, String title_en, String title , String image_url)
	{
		this.mCurrentIndex 					= index;
		this.id 							= id;
		this.fc_id 							= fc_id;
		this.cont_name_ko					= title_ko;
		this.cont_name_en					= title_en;
		this.cont_name						= title;
		this.image_url						= image_url;
	}
	
	public void setSelected(boolean isSelect)
	{
		isSelected = isSelect;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getTitleKO()
	{
		return cont_name_ko;
	}
	
	public String getTitleEN()
	{
		return cont_name_en;
	}
	
	public int getCurrentIndex()
	{
		return mCurrentIndex;
	}
	
	public String getComingSoonImageUrl()
	{
		return coming_soon_layer_img_url;
	}
	
	public String getOpenDate()
	{
		return open_date;
	}
}
