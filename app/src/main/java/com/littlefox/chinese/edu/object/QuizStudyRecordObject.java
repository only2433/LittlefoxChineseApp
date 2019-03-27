package com.littlefox.chinese.edu.object;

import java.util.ArrayList;

/**
 * 퀴즈 정보를 서버에게 전달하는 총 정보를 담고 있는 객체
 * @author 정재현
 *
 */
public class QuizStudyRecordObject
{
	private String mContentId = "";
	private ArrayList<QuizUserInteractionObject> mQuizResultInformationList = new ArrayList<QuizUserInteractionObject>();
	private String mQuizId = "";
	private String mCorrectCount = "";
	private String mQuizCount= "";
	
	public QuizStudyRecordObject(String contentId, String quizId, String quizCount, String correctCount, ArrayList<QuizUserInteractionObject> quizResultInformation)
	{
		mContentId = contentId;
		mQuizId = quizId;
		mQuizCount = quizCount;
		mCorrectCount = correctCount;
		mQuizResultInformationList = quizResultInformation;
	}

	public String getContentId()
	{
		return mContentId;
	}

	public ArrayList<QuizUserInteractionObject> getQuizResultInformationList()
	{
		return mQuizResultInformationList;
	}

	public String getQuizId()
	{
		return mQuizId;
	}

	public String getCorrectCount()
	{
		return mCorrectCount;
	}

	public String getQuizCount()
	{
		return mQuizCount;
	}
	
	
}
