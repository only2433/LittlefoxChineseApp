package com.littlefox.chinese.edu;

import android.app.Application;
import android.util.DisplayMetrics;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.littlefox.chinese.edu.analytics.GoogleAnalyticsHelper;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.Feature;
import com.littlefox.chinese.edu.object.UserLoginObject;
import com.littlefox.logmonitor.Log;

/**
 * Created by 정재현 on 2015-07-07.
 */
public class MainApplication extends Application
{
    /** 1920 pixel 을 기준으로 각 pixel 에 곱해야 하는 factor */
    public static float sDisplayFactor = 0.0f;

    /** 1080 height pixel 을 기준으로 각 pixel 에 곱해야 하는 factor */
    public static float sDisplayHeightFactor = 0.0f;

    /** 해당 변수는 static 변수도 어느순간 값을 회수되기 때문에 값을 가지고 있어야 하기 때문 */
    public static DisplayMetrics sDisPlayMetrics = null;

 

	@Override
    public void onCreate()
    {
        super.onCreate();
        
    }
	
}
