package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.AutobiographyObject;
import com.littlefox.chinese.edu.object.FeaturePlayObject;


/**
 * 홈 화면 정보를 담고 있는 리스트 
 * @author 정재현
 *
 */
public class HomeDataResult
{
	private ArrayList<NewReleaseResult> new_release_list 	= new ArrayList<NewReleaseResult>();
	private ArrayList<BasicSeriesResult> basic_seriese_list	= new ArrayList<BasicSeriesResult>();
	private ArrayList<FeatureResult> feature_list			= new ArrayList<FeatureResult>();
	private ArrayList<BannerLinkResult> banner_list			= new ArrayList<BannerLinkResult>();
	private ArrayList<FeaturePlayObject> best_story			= new ArrayList<FeaturePlayObject>();
	private ArrayList<FeaturePlayObject> best_song			= new ArrayList<FeaturePlayObject>();
	private ArrayList<AutobiographyObject> recommend_list	= new ArrayList<AutobiographyObject>();
	
	public ArrayList<NewReleaseResult> getNewReleaseList()
	{
		return new_release_list;
	}
	
	public ArrayList<BasicSeriesResult> getBasicSeriesList()
	{
		return basic_seriese_list;
	}
	
	public ArrayList<FeatureResult> getFeatureList()
	{
		return feature_list;
	}
	
	public ArrayList<BannerLinkResult> getBannerList()
	{
		return banner_list;
	}
	
	public ArrayList<FeaturePlayObject> getBestStoryList()
	{
		return best_story;
	}
	
	public ArrayList<FeaturePlayObject> getBestSongList()
	{
		return best_song;
	}
	
	public ArrayList<AutobiographyObject> getRecommendList()
	{
		return recommend_list;
	}
}
