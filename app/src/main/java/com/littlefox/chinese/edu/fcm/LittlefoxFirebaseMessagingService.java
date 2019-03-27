package com.littlefox.chinese.edu.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.littlefox.chinese.edu.IntroLoadingActivity;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.Common;
import com.littlefox.logmonitor.Log;



public class LittlefoxFirebaseMessagingService extends FirebaseMessagingService
{
    final String TAG = "LittlefoxFirebaseMessagingService";
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.i(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0)
        {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());

            showNotification(remoteMessage.getData().get("msg"));
        }
        else
        {
            Log.f("Data Empty.");
        }
    }

    private void showNotification(String message)
    {

        final String CHANNEL_ID = "littlefox_chinese_channel_id";
        final String CHANNEL_NAME = "littlefox_chinese_channel_name";

        Log.f("message : "+message);
		Intent intent = new Intent(this, IntroLoadingActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder notificationBuilder = null;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
					NotificationManager.IMPORTANCE_DEFAULT);
			notificationChannel.enableLights(true);
			notificationChannel.setLightColor(Color.BLUE);
			notificationManager.createNotificationChannel(notificationChannel);

			notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
		}
		else
		{
			notificationBuilder = new NotificationCompat.Builder(this);
		}

		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Notification notification = notificationBuilder
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle(getResources().getString(R.string.app_name))
				.setContentText(message)
				.setAutoCancel(true)
				.setSound(soundUri)
				.setContentIntent(pendingIntent)
				.build();

		notificationManager.notify(0, notification);
	}

	@Override
	public void onNewToken(String token)
	{
		super.onNewToken(token);
		// Get updated InstanceID token.
		String refreshedToken = FirebaseInstanceId.getInstance().getToken();
		sendRegistrationToServer(refreshedToken);
	}

	/**
	 * Persist token to third-party servers.
	 *
	 * Modify this method to associate the user's FCM InstanceID token with any server-side account
	 * maintained by your application.
	 *
	 * @param token The new token.
	 */
	private void sendRegistrationToServer(String token)
	{
		Log.f("Push Address Token : "+ token);
		final Intent intent = new Intent(Common.BROADCAST_FIREBASE_TOKEN);
		intent.putExtra(Common.INTENT_FIREBASE_PUSH, token);
		final LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
		localBroadcastManager.sendBroadcast(intent);
	}

}