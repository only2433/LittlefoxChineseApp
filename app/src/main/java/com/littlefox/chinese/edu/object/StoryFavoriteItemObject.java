package com.littlefox.chinese.edu.object;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.result.StoryCardResult;

/**
 * Story 에서 즐겨찾기 하는 아이템만 따로 Preference로 저장하기 위해 
 * @author 정재현
 *
 */
public class StoryFavoriteItemObject
{
	private ArrayList<StoryCardResult> favoriteList = new ArrayList<StoryCardResult>();
	
	public StoryFavoriteItemObject(ArrayList<StoryCardResult> favoriteList)
	{
		this.favoriteList = favoriteList;
	}
	
	public ArrayList<StoryCardResult> getFavoriteItemList()
	{
		return favoriteList;
	}
	
	
}
