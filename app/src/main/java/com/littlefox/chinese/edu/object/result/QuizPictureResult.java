package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.result.base.QuizBaseObject;

/**
 * 그림 퀴즈 관련 정보 데이터 객체
 * @author 정재현
 *
 */
public class QuizPictureResult extends QuizBaseObject
{
	private String image_url = "";
	private String inc_image_url = "";
	
	private ArrayList<PictureQuizInformation> question = new ArrayList<PictureQuizInformation>();
	
	public String getCorrectImageUrl()
	{
		return image_url;
	}
	
	public String getInCorrectImageUrl()
	{
		return inc_image_url;
	}
	
	public String getCorrectImageFileName()
	{
		return getFCId() + "_quiz_merge.png";
	}
	
	public String getInCorrectImageFileName()
	{
		return getFCId() + "_incorrect_merge.png";
	}
	
	public ArrayList<PictureQuizInformation> getQuestionList()
	{
		return question;
	}
	
	
	public class PictureQuizInformation 
	{
		private int no 				= -1;
		private String text 			= "";
		private String sound_url 	= "";
		private String inc_no 		= "";
		
		public int getQuestionIndex()
		{
			return no;
		}
		
		public String getTitle()
		{
			return text;
		}
		
		/**
		 * 현재 이미지 리스트에서 정답인 이미지 조각의 Index
		 * @return
		 */
		public int getCorrectIndex()
		{
			return no -1;
		}
		
		public String getSoundUrl()
		{
			return sound_url;
		}
		
		public int getInCorrectIndex()
		{
			if(inc_no.equals(""))
			{
				return -1;
			}
			
			return Integer.valueOf(inc_no) -1;
		}
	}
}
