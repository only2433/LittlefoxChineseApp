package com.littlefox.chinese.edu.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by 정재현 on 2015-07-07.
 */
public class Common
{
    public static final String PACKAGE_NAME             = "com.littlefox.chinese.edu";
    
	public static final String FILE_LOG_PATH 			= Environment.getExternalStorageDirectory() + File.separator + "chineseLittlefox"+ File.separator;
    public static final String PATH_APP_ROOT			= "/data/data/" + PACKAGE_NAME + "/files/";
    public static final String PATH_MP4_SAVE			= PATH_APP_ROOT +"mp4/";
    public static final String PATH_QUIZ_INFO			= PATH_APP_ROOT +"quiz/";
    public static final String FILE_MAIN_INFO			= "main_information.txt";
    public static final String LOG_FILE					= "chinese_littlefox_log.txt";

    public static final String BASE_DEVELOP_API 									= "http://api.chinese.smile45.littlefox.com/api_chinese/";
    public static final String BASE_PUBLIC_API										= "https://api.chinese.littlefox.com/api_chinese/";
    public static final String BASE_URI 											= BASE_PUBLIC_API;
    public static final String URI_INIT_APP											= BASE_URI + "init-app";
    public static final String URI_MAIN												= BASE_URI + "main";
    public static final String URI_CONTENT_LIST										= BASE_URI + "contents-list";
    public static final String URI_SERIES_INFO										= BASE_URI + "series-info";
    public static final String URI_QUIZ												= BASE_URI + "question";
    public static final String URI_USER_SIGN										= BASE_URI + "user-join";
    public static final String URI_ID_PASSWORD_SEARCH 								= BASE_URI + "user-id-find";
    public static final String URI_SEND_EMAIL_TO_CHANGE_PASSWORD 					= BASE_URI + "mail-to-password-reset";
    public static final String URI_USER_LOGIN										= BASE_URI + "user-login";
    public static final String URI_USER_INFO										= BASE_URI + "user-info";
    public static final String URI_USER_PASSWORD_CHANGE								= BASE_URI + "user-password-reset";
    public static final String URI_ADD_CHILD_ACCOUNT								= BASE_URI + "child-account";
    public static final String URI_CHANGE_USER										= BASE_URI + "change-user";
    public static final String URI_QUIZ_SAVE_RECORD									= BASE_URI + "save-quiz-record";
    public static final String URI_PLAY_SAVE_RECORD									= BASE_URI + "save-study-record";
    public static final String URI_SAVE_PAYMENT										= BASE_URI + "save-payment";
    public static final String URI_AUTH_CONTENT_PLAY								= BASE_URI + "auth-content-play";
    public static final String URI_CAPTION_INFORMATION								= BASE_URI + "caption_read";
    public static final String URI_SONG_CONTENT_LIST								= BASE_URI + "get_song_category_contents_list";
    
	/** 개발자 이메일 */
	public static final String DEVELOPER_EMAIL 		= "app.support@littlefox.com";
	
    public static final String WEBVIEW_URL_CHINESE_MAIN			= 	"https://chinese.littlefox.com";
    public static final String WEBVIEW_URL_CHINESE_MAIN_KO		= 	WEBVIEW_URL_CHINESE_MAIN +"/ko";
    public static final String WEBVIEW_URL_CHINESE_MAIN_EN		= 	WEBVIEW_URL_CHINESE_MAIN +"/en";
    
    public static final String WEBVIEW_URL_USER_SIGN_MAIN 				=	WEBVIEW_URL_CHINESE_MAIN_KO +"/app/accessterms";
    public static final String WEBVIEW_STUDY_DATA_URI 					= 	WEBVIEW_URL_CHINESE_MAIN_KO +"/app/org/";
    public static final String WEBVIEW_LITTLEFOX_CHINESE_GUIDE 			= 	WEBVIEW_URL_CHINESE_MAIN_KO +"/app/introduce_phone";
    public static final String WEBVIEW_LITTLEFOX_CHINESE_GUIDE_TABLET 	= 	WEBVIEW_URL_CHINESE_MAIN_KO +"/app/introduce_tablet";
    public static final String WEBVIEW_STUDY_GUIDE 						= 	WEBVIEW_URL_CHINESE_MAIN_KO +"/app/studyguide_phone";
    public static final String WEBVIEW_STUDY_GUIDE_TABLET 				= 	WEBVIEW_URL_CHINESE_MAIN_KO +"/app/studyguide_tablet";
    public static final String WEBVIEW_STUDY_RECORD 					= 	WEBVIEW_URL_CHINESE_MAIN_KO +"/app/studylog";
    public static final String WEBVIEW_CONTENT_PRESENT 					= 	WEBVIEW_URL_CHINESE_MAIN_KO +"/app/schedule/";
    
    public static final String WEBVIEW_AUTOBIOGRAPHY					=   WEBVIEW_URL_CHINESE_MAIN + "/static/app/use_report/list_mb.html?20181211";
    
    
    
    public static final String WEBVIEW_URL_USER_SIGN_MAIN_EN 				= WEBVIEW_URL_CHINESE_MAIN_EN+"/app/accessterms";
    public static final String WEBVIEW_STUDY_DATA_URI_EN  					= WEBVIEW_URL_CHINESE_MAIN_EN+"/app/org/";
    public static final String WEBVIEW_LITTLEFOX_CHINESE_GUIDE_EN  			= WEBVIEW_URL_CHINESE_MAIN_EN+"/app/introduce_phone";
    public static final String WEBVIEW_LITTLEFOX_CHINESE_GUIDE_EN_TABLET  	= WEBVIEW_URL_CHINESE_MAIN_EN+"/app/introduce_tablet";
    public static final String WEBVIEW_STUDY_GUIDE_EN 						= WEBVIEW_URL_CHINESE_MAIN_EN+"/app/studyguide_phone";
    public static final String WEBVIEW_STUDY_GUIDE_EN_TABLET 				= WEBVIEW_URL_CHINESE_MAIN_EN+"/app/studyguide_tablet";
    public static final String WEBVIEW_STUDY_RECORD_EN  					= WEBVIEW_URL_CHINESE_MAIN_EN+"/app/studylog";
    public static final String WEBVIEW_CONTENT_PRESENT_EN  					= WEBVIEW_URL_CHINESE_MAIN_EN+"/app/schedule/";
    
    public static final String URL_LITTLEFOX_CHANNEL = "https://www.youtube.com/channel/UCXRPWm72nrs5sK7fNTSXZrw";

    public static final int ICE_CREAM_SANDWICH 	= 14;
    public static final int JELLYBEAN_CODE 		= 16;
    public static final int JELLYBEAN_CODE_4_3 	= 18;
    public static final int KITKAT				= 19;
    public static final int LOLLIPOP			= 21;
    public static final int MALSHMALLOW			= 23;
    
    public static final String USER_TYPE_PAID	= "P";
    public static final String USER_TYPE_FREE	= "F";
    
    public static final long MAXIMUM_LOG_FILE_SIZE = 1024 * 1024 * 5;
    
    /**
     * 1024 x 768 사이즈 태블릿을 대응하기위한 용도 
     */
    public static final float MINIMUM_TABLET_DISPLAY_RADIO = 1.4f;
    
    /**
     * 저장공간 확인 :  앱을 사용하기 위한 최저치를 나타낸다. 250MB. 해당될때 동영상을 비디오가 다운될만큼 용량을 확보한다.
     */
    public static final int MIN_PLAYED_STORAGE_SIZE = 250;
    
    /**
     * 앱을 사용하기위한 사이즈. 동영상을 받기위해 1GB를 유지한다. 
     */
    public static final int VIDEO_DOWNLOAD_STORAGE_SIZE = 1000;

    public static final int TYPE_PARAMS_BOOLEAN 	= 0;
    public static final int TYPE_PARAMS_INTEGER 	= 1;
    public static final int TYPE_PARAMS_STRING		= 2;

    public static final String PACKAGE_KAKAO_TALK 	= "com.kakao.talk";
    public static final String PACKAGE_KAKAO_STORY 	= "com.kakao.story";
    
    public static final String INTENT_PLAYER_PARAMS 					="player_object_params";
    public static final String INTENT_QUIZ_PARAMS						= "quiz_params";
    public static final String INTENT_STORY_CONTENT_PARAMS 				= "story_content_params";
    public static final String INTENT_SONG_CONTENT_PARAMS				= "song_content_params";
    public static final String INTENT_STUDY_DATA_PARAMS					= "study_data_params";
    public static final String INTENT_INIT_APP_INFO						= "init_app_into";
    public static final String INTENT_PREVIOUS_MODE						= "previous_mode";
    public static final String INTENT_LOGIN_COMPLETE					= "login_complete";
    public static final String INTENT_AUTOBIOGRAPHY						= "autobiography_params";
    /**
     * 첫 앱 시작시 로그인 정보가 없을 시에 호출 할때 사용
     */
    public static final String INTENT_LOGIN_INIT_APP					= "login_init_app";

    /**
     * 파이어 베이스 푸쉬 인텐트
     */
    public static final String INTENT_FIREBASE_PUSH                 = "firebase_push";
    
    
    public static final String PARAMS_DISPLAY_METRICS					= "display_metrics";
    public static final String PARAMS_APP_USER_PK						= "app_user_pk";
    public static final String PARAMS_STUDY_DATA					  	= "study_data";
    public static final String PARAMS_STORY_CARD_INFORMATION 			= "story_card_information";
    public static final String PARAMS_REGISTER_APP_VERSION 				= "current_app_version";
    public static final String PARAMS_USER_LOGIN						= "user_login";
    public static final String PARAMS_IS_AUTO_LOGIN						= "is_auto_login";
    public static final String PARAMS_INAPP_INFO						= "inapp_info";
    public static final String PARAMS_USER_TOTAL_INFO					= "user_total_info";
    public static final String PARAMS_ACCESS_TOKEN						= "access_token";
    public static final String PARAMS_IAC_AWAKE_INFO					= "iac_awake_info";
    public static final String PARAMS_CLIENT_KEY						= "client_key";
    public static final String PARAMS_SERVER_KEY						= "server_key";
    public static final String PARAMS_FIREBASE_PUSH_TOKEN               = "firebase_push_token";
    public static final String PARAMS_PLAYER_SPEED_INDEX                = "player_speed_index";
    
	/** 앱 별점 주기 링크 */
	public static final String APP_LINK = "https://play.google.com/store/apps/details?id=com.littlefox.chinese.edu";
   
    public static  String HTTP_HEADER_APP_NAME 	= "LFCN_App_Android_Phone";
    public static  String HTTP_HEADER_APP_NAME_TABLET 	= "LFCN_App_Android_Tablet";
    public static  String HTTP_HEADER 	= HTTP_HEADER_APP_NAME;
    
    public static final String HTTP_HEADER_ANDROID	= "Android";
    
    public static final String ASYNC_CODE_GCM_REGISTER 								= "async_gcm_register";
    public static final String ASYNC_CODE_INIT_APP_INFO_REQUEST 					= "async_init_app";
    public static final String ASYNC_CODE_MAIN_INFO_REQUEST 						= "main_info";
    public static final String ASYNC_CODE_CONTENT_LIST_REQUEST 						= "content_list";
    public static final String ASYNC_CODE_SERIES_INFO								= "series_info";
    public static final String ASYNC_CODE_QUIZ_INFORMATION							= "quiiz_information";
    public static final String ASYNC_CODE_FILE_DOWNLOAD								= "file_download";
    public static final String ASYNC_CODE_USER_SIGN									= "user_sign";
    public static final String ASYNC_CODE_USER_LOGIN								= "user_login";
    public static final String ASYNC_CODE_SEARCH_ID									= "search_id";
    public static final String ASYNC_CODE_SEARCH_PASSWORD							= "search_password";
    public static final String ASYNC_CODE_SEND_EMAIL_TO_SEARCH_PASSWORD 			= "send_email_to_search_password";
    public static final String ASYNC_CODE_USER_INFORMATION_REQUEST					= "user_information_request";
    public static final String ASYNC_CODE_USER_INFORMATION_SET						= "user_information_set";
    public static final String ASYNC_CODE_USER_PASSWORD_CHANGE						= "user_password_change";
    public static final String ASYNC_CODE_CHILD_ACCOUNT_ADD							= "child_account_add";
    public static final String ASYNC_CODE_CHILD_ACCOUNT_MODIFICATION				= "child_account_modification";
    public static final String ASYNC_CODE_CHILD_ACCOUNT_DELETE						= "child_account_delete";
    public static final String ASYNC_CODE_CHANGE_USER								= "change_user";
    public static final String ASYNC_CODE_QUIZ_SAVE_RECORD							= "save_record_quiz";
    public static final String ASYNC_CODE_PLAY_SAVE_RECORD							= "save_record_play";
    public static final String ASYNC_CODE_SAVE_PAYMENT								= "save_payment";
    public static final String ASYNC_CODE_AUTH_CONTENT_PLAY							= "auth_content_play";
    public static final String ASYNC_CODE_CAPTION_INFORMATION						= "caption_information";
    public static final String ASYNC_CODE_SONG_CATEGORY_CONTENT_LIST				= "song_category_content_list";

    public static final String BROADCAST_FIREBASE_TOKEN = "broadcast_firebase_token";

    public static final String QUIZ_CODE_PICTURE 		= "N";
    public static final String QUIZ_CODE_SOUND_TEXT 	= "S";
    public static final String QUIZ_CODE_TEXT			= "T";
    
    public static final int REQUEST_CONTENT_TYPE_MOVIE 						= 1;
    public static final int REQUEST_CONTENT_TYPE_MOVIE_WITH_CAPTION 		= 2;
    public static final int REQUEST_CONTENT_TYPE_SONG						= 3;
    public static final int REQUEST_CONTENT_TYPE_SONG_WITH_CAPTION			= 4;
    
    public static final int RESULT_SWITCH_PAYUSER 	= 100;
    public static final int RESULT_SWITCH_FREEUSER 	= 101;
    
    public static final int PLAY_TYPE_SONG 				= 0;
    public static final int PLAY_TYPE_SERIES_STORY 		= 1; // 스토리 중 시리즈
    public static final int PLAY_TYPE_SHORT_STORY  		= 2; // 스토리 중 단편
    public static final int PLAY_TYPE_STUDY_DATA		= 3; //학습 자료실
    
    public static final String PLAY_TYPE_FEATURE_SERIES_STORY 		= "series";
    public static final String PLAY_TYPE_FEATURE_SHORT_STORY 		= "single";
    public static final String PLAY_TYPE_FEATURE_SONG				= "song";
    
    public static final int FAVORITE_TYPE_ADD 		= 0;
    public static final int FAVORITE_TYPE_DELETE 	= 1;
    
    public static final String PAYMENT_ID_30_DAYS 			= "PD00000001";
    public static final String PAYMENT_ID_SUBSCRIPTION 		= "PD00000002";
    public static final String PAYMENT_ID_365_DAYS			= "PD00000003";	
    
    public static final String UPDATE_CRITICAL 	= "C";
    public static final String UPDATE_NORMAL	= "N";
    
    /**
     * 언어선택
     */
    public static final int LANGUAGE_DEFAULT	= -1;
    public static final int LANGUAGE_KOREA 		= 0;
    public static final int LANGUAGE_ENGLISH	= 1;
	
    /**
	 * 성조
	 */
	public static final int STUDY_TYPE_INTONATION = 0; 
	
	/**
	 * 운모 : 음절의 뒷부분
	 */
	public static final int STUDY_TYPE_SYLLABLE_BACK = 1;
	
	/**
	 * 성모 : 음절의 앞부분
	 */
	public static final int STUDY_TYPE_SYLLABLE_FRONT = 2;
	
    
    /**
     * 하단에서 나오는 버튼 레이아웃의 타입 : 전체 재생 : 선택
     */
    public static final int CHOICE_LAYOUT_TYPE_DEFAULT = 0;
    
    /**
     * 하단에서 나오는 버튼 레이아웃의 타입 : 선택 재생 : 취소
     */
    public static final int CHOICE_LAYOUT_TYPE_SELECTED = 1;
    
    /**
     * 하단의 버튼 각각의 버튼 타입
     */
    public static final int CHOICE_ITEM_TYPE_ALL_PLAY 			= 0;
    public static final int CHOICE_ITEM_TYPE_SELECT 			= 1;
    public static final int CHOICE_ITEM_TYPE_SELECT_PLAY 		= 2;
    public static final int CHOICE_ITEM_TYPE_CANCLE 			= 3;
    
    /**
     * 성적을 보여주는 화면구성의 INDEX
     */
    public static final int GRADE_POOL			= 0;
    public static final int GRADE_GOODS 		= 1;
    public static final int GRADE_VERYGOOD 		= 2;
    public static final int GRADE_EXCELLENT		= 3;
    
    public static final int TARGET_DISPLAY_TABLET_WIDTH = 1920;
  	public static final int TARGET_DISPLAY_WIDTH 		= 1080;

  	public static final String IAC_AWAKE_CODE_ALWAYS_VISIBLE 		="C";
	public static final String IAC_AWAKE_CODE_ONCE_VISIBLE 			= "E";
	public static final String IAC_AWAKE_CODE_SPECIAL_DATE_VISIBLE 	="F";
	
	public static final String CHINESE_STEP_1_CONTENT_ID = "DP000728";
	public static final String CHINESE_STEP_1_CONTENT_ID_EN = "DP000777";
	
	public static final String ANALYTICS_CATEGORY_PLAYER 				= "플레이어";
	public static final String ANALYTICS_CATEGORY_MAIN 					= "메인화면";
	public static final String ANALYTICS_CATEGORY_STORY 				= "동화메인";
	public static final String ANALYTICS_CATEGORY_CONTENTLIST 			= "컨텐트리스트";
	public static final String ANALYTICS_CATEGORY_QUIZ 					= "퀴즈";
	public static final String ANALYTICS_CATEGORY_SETTING 				= "설정";
	public static final String ANALYTICS_CATEGORY_PAYMENT 				= "결제";
	public static final String ANALYTICS_CATEGORY_USERSIGN 				= "회원가입";
	public static final String ANALYTICS_CATEGORY_LOGIN 				= "로그인";
	
	public static final String ANALYTICS_ACTION_STORY					= "동화";
	public static final String ANALYTICS_ACTION_SONG					= "동요";
	public static final String ANALYTICS_ACTION_STUDYDATA				= "학습자료실";
	public static final String ANALYTICS_ACTION_PREVIEW_STORY			= "동화 미리보기";
	public static final String ANALYTICS_ACTION_PREVIEW_SONG			= "동요 미리보기";
	public static final String ANALYTICS_ACTION_PREVIEW_STUDYDATA		= "학습자료실 미리보기";
	public static final String ANALYTICS_ACTION_PAYMENT					= "결제";
	public static final String ANALYTICS_ACTION_YOUTUBE					= "Youtube/Youku";
	public static final String ANALYTICS_ACTION_FAVORITE_STORY			= "인기동화";
	public static final String ANALYTICS_ACTION_FAVORITE_SONG			= "인기동요";
	public static final String ANALYTICS_ACTION_CONTENTPLAY				= "컨텐츠 재생";
	public static final String ANALYTICS_ACTION_TODAYSTORY				= "오늘의 동화";
	public static final String ANALYTICS_ACTION_BANNER					= "배너 클릭";
	public static final String ANALYTICS_ACTION_FAVORITE_ADD			= "즐겨찾기";
	public static final String ANALYTICS_ACTION_QUIZ					= "퀴즈";
	public static final String ANALYTICS_ACTION_CONTENT_PRESENT			= "컨텐츠 발표일정";
	public static final String ANALYTICS_ACTION_STUDY_RECORD			= "학습기록";
	public static final String ANALYTICS_ACTION_PAY_1MONTH				= "1month";
	public static final String ANALYTICS_ACTION_PAY_1MONTH_AUTO			= "1month_자동";
	public static final String ANALYTICS_ACTION_PAY_1YEAR				= "1year";
	public static final String ANALYTICS_ACTION_USER_SIGN				= "회원가입";
	public static final String ANALYTICS_ACTION_USER_LOGIN				= "로그인";
	public static final String ANALYTICS_ACTION_AUTOBIOGRAPHY			= "학습후기";
	
	public static final String ANALYTICS_LABEL_PLAY						= "_재생";
	public static final String ANALYTICS_LABEL_CLICK					= "_클릭";
	public static final String ANALYTICS_LABEL_AUTO_PLAY				= "자동_재생";
	
	/**
	 * 인증시 Appversion을 API 서버에서 받아오며, 자막 관련 정보를 받을 수 있는 API Version
	 */
	public static final String API_VERSION_1_0_3 = "1.0.3";

}
