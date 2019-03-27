package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.object.ContentListTitleObject;

/**
 * 스터디 화면을 담고 있는 정보 클래스
 * @author 정재현
 *
 */
public class StoryCardResult extends ContentListTitleObject
{
	/**
	 * 각각의 아이템에 맞게 HeaderTitle이 들어갈수도 잇어서
	 */
	public static final int TYPE_LIST_DEFAULT = 0;
	public static final int TYPE_TITLE_FAVORITE = 1;
	public static final int TYPE_TITLE_STORY = 2;


	/**
	 * 시리즈의 타이틀
	 */
	private String cont_name = "";

	/**
	 * 시리즈의 레벨
	 */
	private String level;
	private String image_url = "";
	
	/**
	 * 현재 INDEX
	 */
	private int mCurrentIndex = 0;



	private boolean isFavoriteCheck = false;

	private int mCurrentCardType = TYPE_LIST_DEFAULT;
	

	public StoryCardResult(int cardType)
	{
		super("", "",ContentListTitleObject.SERIES_END,"", "", "","");
		mCurrentCardType = cardType;
		mCurrentIndex = 0;
	}
	
	public StoryCardResult(int index, String contentId, String contentIdEN, String ingStatus, String title, String imageUrl, String level , String title_back_rgb, String status_back_rgb, String title_text_image_url, String title_image_url, boolean isFavoriteCheck)
	{
		super(contentId, contentIdEN, ingStatus,title_back_rgb, status_back_rgb,title_text_image_url, title_image_url);
		mCurrentIndex = index;
		cont_name = title;
		image_url = imageUrl;
		this.level = level;
		this.isFavoriteCheck = isFavoriteCheck;
	}

	
	public void setCurrentIndex(int index)
	{
		mCurrentIndex = index;
	}

	public void setFavoriteItem(boolean isFavorite)
	{
		isFavoriteCheck = isFavorite;
	}

	public boolean isFavoriteItem()
	{
		return isFavoriteCheck;
	}
	
	public int getCurrentIndex()
	{
		return mCurrentIndex;
	}
	
	public int getCurrentCardType()
	{
		return mCurrentCardType;
	}

	public String getTitle()
	{
		return cont_name;
	}
	
	public void setTitle(String contName)
	{
		cont_name = contName;
	}
	
	public void setStoryCardImageUrl(String image_url)
	{
		this.image_url = image_url;
	}

	public String getStoryCardImageUrl()
	{
		return image_url;
	}

	public String getCurrentLevel()
	{
		return level;
	}

}
