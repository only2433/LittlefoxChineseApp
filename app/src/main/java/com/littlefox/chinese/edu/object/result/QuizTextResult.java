package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.result.base.QuizBaseObject;

/**
 * 서버로 부터 받은 TEXT 퀴즈 정보
 * @author 정재현
 *
 */
public class QuizTextResult extends QuizBaseObject
{
	private ArrayList<TextExample> question = new ArrayList<TextExample>();
	
	public ArrayList<TextExample> getQuestionList()
	{
		return question;
	}
	
	public class TextExample
	{
		private int no = -1;
		private String text = "";
		private int correct_no = -1;
		private ArrayList<TextExampleObject> example = new ArrayList<TextExampleObject>();
		
		public int getQuestionIndex()
		{
			return no;
		}
		
		public String getTitle()
		{
			return text;
		}
		
		/**
		 * 정답번호
		 * @return
		 */
		public int getCorrectIndex()
		{
			return correct_no;
		}
		
		public ArrayList<TextExampleObject> getExampleList()
		{
			return example;
		}
	}
	
	public class TextExampleObject 
	{
		private int no = -1;
		private String text = "";
		
		public int getExampleIndex()
		{
			return no;
		}
		
		public String getExampleText()
		{
			return text;
		}
	}
}
