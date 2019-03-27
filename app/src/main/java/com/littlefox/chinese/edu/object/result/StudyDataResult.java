package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.common.Common;

/**
 * 학습 자료실 정보를 담고 있는 클래스
 * @author 정재현
 *
 */
public class StudyDataResult
{
	private ArrayList<StudyDataItemResult> basic_shengdiao = new ArrayList<StudyDataItemResult>();
	private ArrayList<StudyDataItemResult> basic_yunmu = new ArrayList<StudyDataItemResult>();
	private ArrayList<StudyDataItemResult> basic_shengmu = new ArrayList<StudyDataItemResult>();
	
	/**
	 * 원하는 타입에 맞는 학습자료리스트를 리턴한다. 
	 * @param type 학습 자료 타입
	 * @return
	 */
	public ArrayList<StudyDataItemResult> getStudyDataList(int type)
	{
		switch(type)
		{
		case Common.STUDY_TYPE_INTONATION:
			return basic_shengdiao;
		case Common.STUDY_TYPE_SYLLABLE_BACK:
			return basic_yunmu;
		case Common.STUDY_TYPE_SYLLABLE_FRONT:
			return basic_shengmu;
		}
		
		return null;
	}
}
