package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.SongCategoryObject;
import com.littlefox.chinese.edu.object.SongMainPlayObject;

public class SongCategoryResult
{
	private ArrayList<SongMainPlayObject> song_main_story_list = new ArrayList<SongMainPlayObject>();
	private ArrayList<SongCategoryObject> song_main_category_list = new ArrayList<SongCategoryObject>();
	
	public ArrayList<SongMainPlayObject> getSongMainList()
	{
		return song_main_story_list;
	}
	
	public ArrayList<SongCategoryObject> getSongCategoryList()
	{
		return song_main_category_list;
	}
}
