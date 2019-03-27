package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.result.base.BaseResult;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 메인 화면의 정보를 서버에서 Reqest 하여 가지고 있는 클래스
 * @author 정재현
 *
 */
public class MainInformationResult extends BaseResult
{
	private HomeDataResult home;
	private ArrayList<StoryCardResult> story = new ArrayList<StoryCardResult>();
	private SongCategoryResult song;
	private StudyDataResult basic;
	
	public HomeDataResult getHomeDataResult()
	{
		return home;
	}
	
	public ArrayList<StoryCardResult> getStoryCardResultList()
	{
		return story;
	}
	
	public SongCategoryResult getSongCardResult()
	{
		return song;
	}
	
	public StudyDataResult getStudyDataResult()
	{
		return basic;
	}
	
}
