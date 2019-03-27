package com.littlefox.chinese.edu.object;

/**
 * 퀴즈의 정보 및 사용자 선택 정보
 * @author 정재현
 *
 */
public class QuizUserInteractionObject
{
	/**
	 * 서버에서 받은 문제의 번호
	 */
	private int question_no = -1;
	
	/**
	 * 사용자가 선택한 번호
	 */
	private int chosen_no = -1;
	
	/**
	 * 정답인 번호
	 */
	private int correct_no = -1;
	
	public QuizUserInteractionObject(int questionNumber , int correctNumber ,int chosenNumber)
	{
		question_no 		= questionNumber;
		correct_no		= correctNumber;
		chosen_no		= chosenNumber;
		
	}

	public int getQuestionNumber()
	{
		return question_no;
	}

	public int getChosenNumber()
	{
		return chosen_no;
	}

	public int getCorrectNumber()
	{
		return correct_no;
	}
}
