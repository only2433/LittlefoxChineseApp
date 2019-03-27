package com.littlefox.chinese.edu.rootcheck;

import com.littlefox.logmonitor.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RootCheck
{
	public static final String ROOT_PATH = "";
	public static final String ROOTING_PATH_1 = "/system/bin/su";
	public static final String ROOTING_PATH_2 = "/system/xbin/su";
	public static final String ROOTING_PATH_3 = "/system/app/SuperUser.apk";
	public static final String ROOTING_PATH_4 = "/data/data/com.noshufou.android.su";

	public static String[] RootFilesPath = new String[] { 
		ROOT_PATH + ROOTING_PATH_1, 
		ROOT_PATH + ROOTING_PATH_2, 
		ROOT_PATH + ROOTING_PATH_3, 
		ROOT_PATH + ROOTING_PATH_4,};
	
	

	private static boolean checkRootingFiles(String[] filePaths)
	{
		boolean result = false;
		File file;

		for (String path : filePaths)
		{
			file = new File(path);
			Log.i("root file path : " + file.getAbsolutePath());
			if (file != null && file.exists() && file.isFile())
			{
				Log.i("Catch root file path : " + file.getAbsolutePath());
				result = true;
				break;
			}
			else
			{
				result = false;
			}
		}

		return result;
	}

	/**
	 * 루팅된 디바이스인지 체크.
	 * 
	 * @return TRUE : 루팅된 폰</p>FALSE : 루팅되지 않는 폰
	 */
	public static boolean checkRootingDevice()
	{
		boolean isRootingFlag = false;

		try
		{
			Process process = Runtime.getRuntime().exec("find / -name su");

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			if (reader.ready() == false)
			{
				return false;
			}

			String result = reader.readLine();
			if (result.contains("/su") == true)
			{
				isRootingFlag = true;
			}

		}
		catch (Exception e)
		{
			isRootingFlag = false;
		}

		if (!isRootingFlag)
		{
			isRootingFlag = checkRootingFiles(RootFilesPath);
		}

		return isRootingFlag;
	}


}
