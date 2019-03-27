package com.littlefox.chinese.edu.object;

/**
 * 활용후기 관련 정보 오브젝트
 * 
 * @author 정재현
 *
 */
public class AutobiographyObject
{
	public String title 		= "";
	public String subtitle 		= "";
	public String target_url 	= "";
	public String image_url 	= "";
	
	/**
	 * 활용수기 생성자
	 * @param title 활용수기의 타이틀
	 * @param subtitle 활용수기 작성자
	 * @param target_url 활용수기 링크 url
	 * @param image_url 활용수기 thumbnail
	 */
	public AutobiographyObject(String title, String subtitle, String target_url, String image_url)
	{
		this.title 		= title;
		this.subtitle 		= subtitle;
		this.target_url 	= target_url;
		this.image_url 	= image_url;
	}
}
