package com.littlefox.chinese.edu.object;

/**
 * 태블릿 홈 리스트의 추천리스트에 사용할 목적으로 사용
 * @author 정재현
 *
 */
public class HomeContentPlayObject 
{
	/**
	 * 태블릿에서는 인기 동화 , 인기 동요 아이템이 보여진다.
	 * 2017.08.01 활용수기 추가
	 */
	public static final int CONTENT_DEFAULT 		= 0;
	public static final int CONTENT_BEST_STORY 		= 1;
	public static final int CONTENT_BEST_SONG		= 2;
	public static final int CONTENT_AUTOBIOGRAPHY 	= 3;
	
	private AutobiographyObject mAutobiographyObject = null;
	private ContentPlayObject mPlayObject = null;
	private String mSectionTitle = "";
	private int mCurrentContentType = CONTENT_DEFAULT;
	
	/**
	 * RecyclerView 에 Section 과 Thumbnail을 구분하기 위해 사용
	 */
	private int mThumbnailIndex = 0;
	/**
	 * Home Tablet 에서 사용하는 리스트의 생성자
	 * @param sectionTitle 해당 섹션 타이틀
	 * @param currentType 컨텐트 타입의 종류 ( 기본, 인기동화, 인기동요 ) - 태블릿만 인기동화 인기동요 사용
	 */
	public HomeContentPlayObject(String sectionTitle, int currentType)
	{
		mSectionTitle = sectionTitle;
		mPlayObject = null;
		mCurrentContentType= currentType;
	}
	
	/**
	 * Home Tablet 에서 사용하는 리스트의 생성자
	 * @param playObject 플레이 해야 하는 정보
	 */
	public HomeContentPlayObject(ContentPlayObject playObject)
	{
		mSectionTitle = "";
		mPlayObject = playObject;
	}
	
	/**
	 * Home Tablet 에서 사용하는 리스트의 생성자
	 * @param playObject 플레이 해야 하는 정보
	 * @param currentType 컨텐트 타입의 종류 ( 기본, 인기동화, 인기동요 , 활용수기) - 태블릿만 인기동화 인기동요 활용수기 사용
	 */
	public HomeContentPlayObject(ContentPlayObject playObject, int currentType)
	{
		mSectionTitle = "";
		mPlayObject = playObject;
		mCurrentContentType= currentType;
	}
	
	public HomeContentPlayObject(AutobiographyObject autobiographyObject, int type)
	{
		mSectionTitle = "";
		mAutobiographyObject = autobiographyObject;
		mCurrentContentType = type;
	}
	
	/**
	 * 컨텐트 타입의 종류 ( 기본, 인기동화, 인기동요 ) - 태블릿만 인기동화 인기동요 사용
	 * @return
	 */
	public int getCurrentContentType()
	{
		return mCurrentContentType;
	}
	
	public ContentPlayObject getContentPlayObject()
	{
		return mPlayObject;
	}
	
	public AutobiographyObject getAutobiographyObject()
	{
		return mAutobiographyObject;
	}
	
	public String getSectionTitle()
	{
		return mSectionTitle;
	}
	
	public void setThumbnailIndex(int index)
	{
		mThumbnailIndex = index;
	}
	
	public int getThumbnailIndex()
	{
		return mThumbnailIndex;
	}
}
