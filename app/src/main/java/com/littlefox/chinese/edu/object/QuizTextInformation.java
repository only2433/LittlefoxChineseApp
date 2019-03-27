package com.littlefox.chinese.edu.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.littlefox.chinese.edu.object.result.QuizTextResult.TextExample;

/**
 * 텍스트 문제 관련 객체. 해당 정보를 가지고 각 문제를 보여준다.
 * @author 정재현
 *
 */
public class QuizTextInformation
{
	/**
	 * 화면에 뿌려주기 위한 퀴즈의 인덱스
	 */
	private int mQuizIndex = -1;
	
	/**
	 * 서버에서 받은 퀴즈순서의 인덱스
	 */
	private int mRecordQuizIndex = -1;
	
	/**
	 * 서버에서 받은 퀴즈 정답의 인덱스
	 */
	private int mRequestCorrentIndex = -1;
	
	private String mTitle = "";
	private ArrayList<ExamplePictureInformation> mExampleList  = new ArrayList<ExamplePictureInformation>();
	
	public QuizTextInformation(int quizIndex, int requestQuizIndex, TextExample textExample)
	{
		mQuizIndex = quizIndex;
		mRecordQuizIndex = requestQuizIndex;
		mTitle				 = textExample.getTitle();
		init(textExample);
	}
	
	private void init(TextExample textExample)
	{
		mRequestCorrentIndex =  textExample.getCorrectIndex();
		
		for(int i = 0 ; i < textExample.getExampleList().size() ; i++)
		{
			if(mRequestCorrentIndex == textExample.getExampleList().get(i).getExampleIndex())
			{
				mExampleList.add(new ExamplePictureInformation(textExample.getExampleList().get(i).getExampleIndex(), textExample.getExampleList().get(i).getExampleText(), true));
			}
			else
			{
				mExampleList.add(new ExamplePictureInformation(textExample.getExampleList().get(i).getExampleIndex(), textExample.getExampleList().get(i).getExampleText(), false));
			}
		}
	}
	
	public int getRecordQuizIndex()
	{
		return mRecordQuizIndex;
	}
	
	public int getRecordCorrectIndex()
	{
		return mRequestCorrentIndex;
	}
	
	public int getQuizIndex()
	{
		return mQuizIndex + 1;
	}
	
	public String getTitle()
	{
		return mTitle;
	}
	
	public ArrayList<ExamplePictureInformation> getExampleList()
	{
		return mExampleList;
	}
	
	public void shuffle()
	{
		Collections.shuffle(mExampleList, new Random(System.nanoTime()));
	}
	public class ExamplePictureInformation
	{
		/**
		 * 서버에서 받은 Example 순서의 index
		 */
		private int mExampleIndex = -1;
		private String mText = "";
		private boolean isAnswer = false;
		public ExamplePictureInformation(int index, String text , boolean isAnswer)
		{
			mExampleIndex = index;
			mText = text;
			this.isAnswer = isAnswer;
		}
		
		public int getExampleIndex()
		{
			return mExampleIndex;
		}
		
		public String getExampleText()
		{
			return mText;
		}
		
		public boolean isAnswer()
		{
			return isAnswer;
		}
	}
}
