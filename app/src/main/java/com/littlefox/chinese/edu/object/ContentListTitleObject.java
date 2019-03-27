package com.littlefox.chinese.edu.object;

import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.logmonitor.Log;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 컨텐트리스트를 불러오기 위한 정보를 담고 있다. 타이틀 이미지 나 URL 정보는 미리 가지고 있어서 화면에 노출 시켜 사용자에게 로딩되는 느낌을 없게한다.
 * @author 정재현
 *
 */
public class ContentListTitleObject implements Parcelable
{
	public static final String SERIES_END	= "0"; // 시리즈 연재 끝
	public static final String SERIES_ING 	= "1"; // 시리즈 연재 중
	/**
	 * 각각의 시리즈의 고유 ID : 컨텐트 리스트 정보를 가져오기 위해 해당 동화리스트의 키를 가지고 있어야한다.
	 */
	public String sm_id = "";
	/**
	 * 각각의 시리즈의 고유 ID 글로벌 : 컨텐트 리스트 정보를 가져오기 위해 해당 동화리스트의 키를 가지고 있어야한다.
	 */
	public 	String sm_id_en = "";
	protected String ing_status	= SERIES_END;
	protected String title_back_rgb = "";
	protected String title_text_image_url = "";
	protected String title_image_url = "";
	protected String status_back_rgb = "";
	
	public ContentListTitleObject(String sm_id, String sm_id_en, String ing_status ,String title_back_rgb, String status_back_rgb, String title_text_image_url, String title_image_url)
	{
		this.sm_id 					= sm_id;
		this.sm_id_en				= sm_id_en;
		this.ing_status				= ing_status;
		this.title_back_rgb 		= title_back_rgb;
		this.status_back_rgb   		= status_back_rgb;
		this.title_text_image_url	= title_text_image_url;
		this.title_image_url 		= title_image_url;
	}
	
	public ContentListTitleObject(Parcel in)
	{
		sm_id 					= in.readString();
		sm_id_en 				= in.readString();
		ing_status 				= in.readString();
		title_back_rgb 			= in.readString();
		status_back_rgb 		= in.readString();
		title_text_image_url 	= in.readString();
		title_image_url 		= in.readString();
		
	}
	
	public String getStoryKeyId()
	{
		if(Feature.IS_LANGUAGE_ENG)
			return sm_id_en;
		else
			return sm_id;
	}
	
	public String getTitleBackground()
	{
		return title_back_rgb;
	}
	
	public String getStatusBarBackground()
	{
		return status_back_rgb;
	}
	
	public String getTitleTextImageUrl()
	{
		return title_text_image_url;
	}
	
	public String getTitleThumbnail()
	{
		return title_image_url;
	}
	
	public void setTitleTextImageUrl(String title_text_image_url)
	{
		this.title_text_image_url = title_text_image_url;
	}
	
	public void setTitleThumbnail(String title_image_url)
	{
		this.title_image_url = title_image_url;
	}
	
	/**
	 * 시리즈가 연재 되는 지, 끝났는 지  전달
	 * @return
	 */
	public String getSeriesStatus()
	{
		return ing_status;
	}

	@Override
	public int describeContents()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(sm_id);
		dest.writeString(sm_id_en);
		dest.writeString(ing_status);
		dest.writeString(title_back_rgb);
		dest.writeString(status_back_rgb);
		dest.writeString(title_text_image_url);
		dest.writeString(title_image_url);
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel source)
		{
			// TODO Auto-generated method stub
			return new ContentListTitleObject(source);
		}

		@Override
		public Object[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new ContentListTitleObject[size];
		}
		
	};
}
