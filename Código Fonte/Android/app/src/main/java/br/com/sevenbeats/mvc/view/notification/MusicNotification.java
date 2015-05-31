package br.com.sevenbeats.mvc.view.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import br.com.sevenbeats.mvc.view.PlayerActivity;


/**
 * Created by diogojayme on 5/13/15.
 */
public class MusicNotification extends Notification {

    private Context context;
    private Notification notification;

    public MusicNotification(Context context){
        this.context = context;
    }

    public MusicNotification create(long id, String tag){
        PendingIntent pi = PendingIntent.getActivity(context, 0,
                new Intent(context, PlayerActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new Notification();
        notification.tickerText = "Player";
        notification.flags |= Notification.FLAG_ONGOING_EVENT;

        notification.setLatestEventInfo(context, "MusicPlayerSample",
                "Playing: " + tag, pi);

        return this;
    }

    public Notification getNotification(){
        return notification;
    }
}
