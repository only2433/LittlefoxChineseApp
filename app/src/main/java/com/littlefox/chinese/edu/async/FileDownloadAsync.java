package com.littlefox.chinese.edu.async;

import android.content.Context;

import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.library.system.async.BaseAsync;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.logmonitor.Log;

import java.util.ArrayList;

public class FileDownloadAsync extends BaseAsync
{
	private ArrayList<String> mFileDownloadUrlList = new ArrayList<String>();
	private ArrayList<String> mFileSavePathList = new ArrayList<String>();
	
	public FileDownloadAsync(Context context, ArrayList<String> fileDownloadUrlList, ArrayList<String> fileSavePathList, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_FILE_DOWNLOAD);
		
		Log.i("");
		mFileDownloadUrlList 	= fileDownloadUrlList;
		mFileSavePathList		= fileSavePathList;
		setAsyncListener(asyncListener);
	}
	
	public FileDownloadAsync(Context context, String fileDownloadUrl, String fileSavePath, AsyncListener asyncListener)
	{
		super(context, Common.ASYNC_CODE_FILE_DOWNLOAD);
		mFileDownloadUrlList.add(fileDownloadUrl);
		mFileSavePathList.add(fileSavePath);
		setAsyncListener(asyncListener);
	}

	@Override
	protected Object doInBackground(Void... params)
	{
		Log.i("isRunning : "+isRunning);
		if(isRunning == true)
		{
			return false;
		}
		boolean result = false;
		synchronized (mSync)
		{
			isRunning = true;
			
			for(int i = 0 ; i < mFileDownloadUrlList.size() ; i++)
			{
				Log.i("fileUrl : "+mFileDownloadUrlList.get(i)+", path : "+mFileSavePathList.get(i) );
				result = NetworkUtil.downloadFile(mFileDownloadUrlList.get(i), mFileSavePathList.get(i));
				
				if(result == false)
				{
					mAsyncListener.onErrorListener("-1", "Network Error");
				}
			}
		}
		return true;
	}

	@Override
	public void setData(Object... objects) {

	}
}
