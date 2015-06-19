package br.com.sevenbeats.presentation.player.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import br.com.sevenbeats.presentation.player.PlayerConstants;
import br.com.sevenbeats.utils.notification.DownloadNotification;
import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;

public class DownloadService extends Service {
    private String fileName;
    private String downloadUrl;
    private ServiceHandler mServiceHandler;
    public static boolean serviceState = false;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            downloadFile(downloadUrl, fileName);
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        serviceState = true;
        HandlerThread thread = new HandlerThread("ServiceStartArguments", 1);
        thread.start();
        Looper mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extra = intent.getExtras();
        if (extra != null) {
            this.downloadUrl = extra.getString(PlayerConstants.extraUrl);
            this.fileName = extra.getString(PlayerConstants.extraFileName);
        }

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        serviceState = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void downloadFile(final String downloadUrl, final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadFIle(downloadUrl, fileName);
            }
        }).start();
    }

    //Service downloader - http://stackoverflow.com/questions/3028306/download-a-file-with-android-and-showing-the-progress-in-a-progressdialog
    public void downloadFIle(String fileUrl, String fileName) {
        DownloadNotification.build(this);
        try {
            URL url = new URL(fileUrl);

            OkUrlFactory okUrlFactory = new RetrofitHttpClient().generateDefaultOkUrlFactory();
            HttpURLConnection httpURLConnection = okUrlFactory.open(url);
            File directory = this.getExternalCacheDir(); //Usado pra telefones com root
            OutputStream out = new BufferedOutputStream(new FileOutputStream(directory + "/" + fileName + ".mp3"));
            InputStream inputStream = httpURLConnection.getInputStream();
            int fileLenght = httpURLConnection.getContentLength();
            byte[] buffer = new byte[1024];
            long total = 0;
            int progress = 0;
            int count;

            while ((count = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, count);
                total += count;
                progress = (int)((total * 100) / fileLenght);
                DownloadNotification.setNotificationProgress(1, 100, progress, false);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class RetrofitHttpClient extends UrlConnectionClient {

        private static final int CONNECT_TIMEOUT_MILLIS = 60 * 1000; // 30s
        private static final int READ_TIMEOUT_MILLIS = 85 * 1000; // 45s

        private OkUrlFactory generateDefaultOkUrlFactory() {
            OkHttpClient client = new com.squareup.okhttp.OkHttpClient();
            client.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            client.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            return new OkUrlFactory(client);
        }

        private final OkUrlFactory factory;

        public RetrofitHttpClient() {
            factory = generateDefaultOkUrlFactory();
        }

        @Override protected HttpURLConnection openConnection(Request request) throws IOException {
            return factory.open(new URL(request.getUrl()));
        }
    }

}