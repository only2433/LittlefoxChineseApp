package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.result.base.BaseResult;

public class SongContentListInformationResult extends BaseResult
{
	private ArrayList<SongCardResult> list = new ArrayList<SongCardResult>();
	
	public ArrayList<SongCardResult> getSongCardList()
	{
		return list;
	}
}
