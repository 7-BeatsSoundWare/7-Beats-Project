package br.com.sevenbeats.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import br.com.sevenbeats.mvc.controller.PlayerManager;


public class PlayerRemoteReceiver extends BroadcastReceiver {

    private static PlayerManager sPlayer;

    public static void setPlayer(PlayerManager player) {
        sPlayer = player;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (sPlayer != null && sPlayer.getPlaylist() != null) {
            if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
                final KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                if (event.getAction() != KeyEvent.ACTION_DOWN) return;

                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_MEDIA_STOP:
                        sPlayer.pause();
                        break;
                    case KeyEvent.KEYCODE_HEADSETHOOK:
                    case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                        if (sPlayer.isPlaying()) {
                            sPlayer.pause();
                        } else if (!sPlayer.getPlayer().isPlaying()) {
                            sPlayer.play();
                        }
                        break;
                    case KeyEvent.KEYCODE_MEDIA_PLAY:
                        sPlayer.play();
                        break;
                    case KeyEvent.KEYCODE_MEDIA_PAUSE:
                        sPlayer.pause();
                        break;
                    case KeyEvent.KEYCODE_MEDIA_NEXT:
                        sPlayer.next();
                        break;
                    case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                        sPlayer.previous();
                        break;
                }
            }
        }
    }
}
