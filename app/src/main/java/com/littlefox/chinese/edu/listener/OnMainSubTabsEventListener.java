package com.littlefox.chinese.edu.listener;

import com.littlefox.chinese.edu.object.ContentListTitleObject;
import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.SongCategoryObject;

public interface OnMainSubTabsEventListener
{
	/**
	 * 중국어 첫걸음 컨텐트 리스트를 보여준다.
	 */
    void onCreateChineseStep1Content();
	
	/**
	 * 학습자료실 탭으로 이동한다.
	 */
    void onMoveStudyDataStep1List();
	/**
	 * 리틀팍스 중국어 소개로 이동
	 */
    void onStartStepLittlefoxIntroduce();
	/**
	 * 학습 가이드로 이동
	 */
    void onStartStepStudyGuide();
	void onStartQuiz(String contentID);
	void onPlayContent(ContentPlayObject contentPlayObject);
	void onStartStudyData(String contentID);
	void onBannerClick(String moveUrl);
	void onCreteContentList(ContentListTitleObject titleObject);
	void onSetFavorite(int type, String addTitle);
	void onSnackMessage(String message, int color);
	void onCreateSongList(SongCategoryObject object);
	/**
	 * 학습후기로 이동
	 * @param url
	 */
    void onAutobiography(String url);
}
