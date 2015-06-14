package br.com.sevenbeats.presentation.player.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadManager;
import com.github.snowdream.android.app.DownloadTask;
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

import br.com.sevenbeats.utils.notification.DownloadNotification;
import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;

public class DownloadService extends Service {

    SharedPreferences preferences;

    private static final String DOCUMENT_VIEW_STATE_PREFERENCES = "DjvuDocumentViewState";

    private String downloadDirectoryName = "SevenBeatsDownloads";
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private NotificationManager mNM;
    String downloadUrl;
    public static boolean serviceState = false;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            downloadFile();
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        serviceState = true;
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        HandlerThread thread = new HandlerThread("ServiceStartArguments", 1);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVICE-ONCOMMAND", "onStartCommand");

        Bundle extra = intent.getExtras();
        if (extra != null) {
            String downloadUrl = extra.getString("downloadUrl");
            Log.d("URL", downloadUrl);

            this.downloadUrl = downloadUrl;
        }

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("SERVICE-DESTROY", "DESTORY");
        serviceState = false;
        //Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    public void downloadFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadFIle("http://69.28.84.155/public/musicas/kendrick_lamar_the_art_of_peer_pressure.mp3", "kendrick_lamar.mp3");
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
            OutputStream out = new BufferedOutputStream(new FileOutputStream(directory + fileName));
            InputStream inputStream = httpURLConnection.getInputStream();
            int fileLenght = httpURLConnection.getContentLength();
            byte[] buffer = new byte[2048 * 1024];
            long total = 0;
            int progress = 0;
            int lastProgress = 0;
            int count;

            DownloadRunnable downloadRunnable = new DownloadRunnable();

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


    public void executeDownload(){
        DownloadManager downloadManager = new DownloadManager(this);

        DownloadTask task = new DownloadTask(this);
        task.setUrl("http://69.28.84.155/public/musicas/kendrick_lamar_the_art_of_peer_pressure.mp3");
        task.setPath(getDownloadsDirectory());
        task.setSize(10240);
        downloadManager.add(task, listener); //Add the task
        downloadManager.start(task, listener); //Start the task
        downloadManager.stop(task, listener); //Stop the task if you exit your APP.
    }


    public class DownloadRunnable implements Runnable {
        int total;
        int progress;

        private DownloadRunnable(){
        }

        public void update(int total, int progress){
            this.total = total;
            this.progress = progress;

            try {
                Thread.sleep(5000);
                run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            DownloadNotification.setNotificationProgress(1, total, progress, false);
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


    public String getDownloadsDirectory(){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + downloadDirectoryName;
    }


    private DownloadListener listener = new DownloadListener<Integer, DownloadTask>() {
        /**
         * The download task has been added to the sqlite.
         * <p/>
         * operation of UI allowed.
         *
         * @param downloadTask the download task which has been added to the sqlite.
         */

        private long progressNotificationDelay;

        @Override
        public void onAdd(DownloadTask downloadTask) {
            super.onAdd(downloadTask);
        }

        /**
         * The download task has been delete from the sqlite
         * <p/>
         * operation of UI allowed.
         *
         * @param downloadTask the download task which has been deleted to the sqlite.
         */
        @Override
        public void onDelete(DownloadTask downloadTask) {
            super.onDelete(downloadTask);
        }

        /**
         * The download task is stop
         * <p/>
         * operation of UI allowed.
         *
         * @param downloadTask the download task which has been stopped.
         */
        @Override
        public void onStop(DownloadTask downloadTask) {
            super.onStop(downloadTask);
        }

        /**
         * Runs on the UI thread before doInBackground(Params...).
         */
        @Override
        public void onStart() {
            super.onStart();
            handler = new Handler();
            DownloadNotification.build(DownloadService.this);
        }

        /**
         * Runs on the UI thread after publishProgress(Progress...) is invoked. The
         * specified values are the values passed to publishProgress(Progress...).
         *
         * @param values The values indicating progress.
         */

        private Handler handler;

        @Override
        public void onProgressUpdate(final Integer... values) {
            super.onProgressUpdate(values);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DownloadNotification.setNotificationProgress(1, 100, values[0], false);
                }
            }, 2000);
        }

        /**
         * Runs on the UI thread after doInBackground(Params...). The specified
         * result is the value returned by doInBackground(Params...). This method
         * won't be invoked if the task was cancelled.
         *
         * @param downloadTask The result of the operation computed by
         *                     doInBackground(Params...).
         */
        @Override
        public void onSuccess(DownloadTask downloadTask) {
            super.onSuccess(downloadTask);
            DownloadNotification.setNotificationFinished(1, "finished");
        }

        /**
         * Applications should preferably override onCancelled(Object). This method
         * is invoked by the default implementation of onCancelled(Object). Runs on
         * the UI thread after cancel(boolean) is invoked and
         * doInBackground(Object[]) has finished.
         */
        @Override
        public void onCancelled() {
            super.onCancelled();
        }

        @Override
        public void onError(Throwable thr) {
            super.onError(thr);
        }

        /**
         * Runs on the UI thread after doInBackground(Params...) when the task is
         * finished or cancelled.
         */
        @Override
        public void onFinish() {
            super.onFinish();

        }
    };

}