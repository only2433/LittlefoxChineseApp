package com.littlefox.chinese.edu.coroutines;

import android.content.Context;

import com.littlefox.chinese.edu.common.Common;
import com.littlefox.chinese.edu.common.NetworkUtil;
import com.littlefox.library.system.async.listener.AsyncListener;
import com.littlefox.library.system.coroutine.BaseCoroutine;
import com.littlefox.logmonitor.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FileDownloadCoroutine extends BaseCoroutine
{
    private ArrayList<String> mFileDownloadUrlList = new ArrayList<String>();
    private ArrayList<String> mFileSavePathList = new ArrayList<String>();

    public FileDownloadCoroutine(@NotNull Context context, AsyncListener asyncListener)
    {
        super(context, Common.ASYNC_CODE_FILE_DOWNLOAD);
        setAsyncListener(asyncListener);
    }

    @NotNull
    @Override
    public Object doInBackground()
    {

        if(isRunning())
        {
            return false;
        }
        boolean result = false;
        synchronized (mSync)
        {
            setRunning(true);

            for(int i = 0 ; i < mFileDownloadUrlList.size() ; i++)
            {
                Log.i("fileUrl : "+mFileDownloadUrlList.get(i)+", path : "+mFileSavePathList.get(i) );
                result = NetworkUtil.downloadFile(mFileDownloadUrlList.get(i), mFileSavePathList.get(i));

                if(result == false)
                {
                    getAsyncListener().onErrorListener("-1", "Network Error");
                }
            }
        }
        return true;
    }

    @Override
    public void setData(@NotNull Object... objects)
    {
        mFileDownloadUrlList = (ArrayList<String>)objects[0];
        mFileSavePathList = (ArrayList<String>)objects[1];

    }
}
