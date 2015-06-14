package br.com.sevenbeats.utils.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import br.com.sevenbeats.R;

/**
 * Created by diogojayme on 6/13/15.
 */
public class DownloadNotification {

    static NotificationCompat.Builder builder;
    static NotificationManager notificationManager;
    static int notificationId = 1;

    public static void build(Context context){
        notificationManager  = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Downloading file")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.ic_av_playlist_add);
        notificationManager.notify(notificationId, builder.build());
    }

    public static void isIndetermined(boolean indetermined){
        builder.setProgress(0, 0, indetermined);
    }

    public static void setNotificationProgress(int notificationId, int max, int progress, boolean indetermined){
        builder.setProgress(max, progress, indetermined);
        notificationManager.notify(notificationId, builder.build());
    }

    public static void setNotificationFinished(int notificationId, String messageFinish){
        builder.setTicker(messageFinish);
        notificationManager.notify(notificationId, builder.build());
    }

}
