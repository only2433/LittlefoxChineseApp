package com.littlefox.chinese.edu.listener;


/**
 * Main에서 서브 Fragment에 각각의 콜백을 받기위한 이벤트 
 * @author 정재현
 *
 */
public interface MainHolder
{
	void setOnSubTabsEventListener(OnMainSubTabsEventListener onSubTabsEventListener);
}
