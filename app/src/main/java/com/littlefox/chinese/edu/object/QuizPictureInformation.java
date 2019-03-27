package com.littlefox.chinese.edu.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.graphics.Bitmap;

import com.littlefox.chinese.edu.common.Common;

/**
 * 그림 문제 관련 객체. 해당 정보를 가지고 각 문제를 보여준다.
 * @author 정재현
 *
 */
public class QuizPictureInformation
{
	private int mCurrentQuizIndex = -1;
	private ArrayList<ExamplePictureInformation> mQuizImageList = new ArrayList<ExamplePictureInformation>();
	private String mTitleText;
	
	/**
	 * 퀴즈의 정답 및 문제 번호를 의미 : 서버에서 받은 정보, 퀴즈의 순서가 곧 정답의 index 이다.
	 */
	private int mRecordQuizCorrectIndex = -1;
	
	/**
	 * 퀴즈에서 보여지는 오답 문제번호를 의미 
	 */
	private int mRecordQuizInCorrectIndex = -1;
	
	
	public QuizPictureInformation (int quizIndex, String title, Bitmap firstExampleBitmap, Bitmap secondExampleBitmap)
	{
		mCurrentQuizIndex 	= quizIndex;
		mTitleText			= title;
		mQuizImageList.add(new ExamplePictureInformation(firstExampleBitmap));
		mQuizImageList.add(new ExamplePictureInformation(secondExampleBitmap));
		
		mQuizImageList.get(0).setAnswer(true);
	}
	
	/**
	 * 현재 화면에 보여질 문제의 번호와 잘못된 문제의 번호 : +1을 하는 이유는 서버에서 1,2,3,4,5 순으로 정보를 주기 때문이다.  
	 * @param correctIndex 정답의 인덱스
	 * @param inCorrectIndex 잘못된 정보의 인덱스
	 */
	public void setRecordQuizValue(int correctIndex, int inCorrectIndex)
	{
		mRecordQuizCorrectIndex = correctIndex + 1;
		mRecordQuizInCorrectIndex = inCorrectIndex + 1;
	}
	
	public int getRecordQuizCorrectIndex()
	{
		return mRecordQuizCorrectIndex;
	}
	
	public int getRecordQuizInCorrectIndex()
	{
		return mRecordQuizInCorrectIndex;
	}
	
	/**
	 * 이미지객체를 섞는다
	 */
	public void shuffle()
	{
		Collections.shuffle(mQuizImageList, new Random(System.nanoTime()));
	}
	
	public int getQuizIndex()
	{
		return mCurrentQuizIndex+1;
	}
	
	public ArrayList<ExamplePictureInformation> getImageInformationList()
	{
		return mQuizImageList;
	}
	
	public String getTitle()
	{
		return mTitleText;
	}

	/**
	 * 각각의 이미지 정보를 담은 객체
	 * @author 정재현
	 *
	 */
	public class ExamplePictureInformation
	{
		private Bitmap mImage = null;
		private boolean isAnswer = false;
		
		public ExamplePictureInformation(Bitmap image)
		{
			mImage = image;
		}
		
		public Bitmap getImage()
		{
			return mImage;
		}
		
		public void setAnswer(boolean isAnswer)
		{
			this.isAnswer = isAnswer;
		}
		
		public boolean isAnswer()
		{
			return isAnswer;
		}
	}
	
}
