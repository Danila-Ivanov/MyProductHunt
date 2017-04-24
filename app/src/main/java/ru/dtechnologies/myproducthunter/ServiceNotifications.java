package ru.dtechnologies.myproducthunter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import ru.dtechnologies.myproducthunter.dataLayer.Core;
import ru.dtechnologies.myproducthunter.dataLayer.models.Post;
import ru.dtechnologies.myproducthunter.ui.views.PostActivity;

public class ServiceNotifications extends Service {

    /**
     * @param bitmap image thumbnail in format bitmap
     */
    private Bitmap bitmap;

    /**
     * @param screenshot image screenshot for PostActivity in format bitmap
     */
    private Bitmap screenshot;

    /**
     * @param NOTIFY_ID Id Notification
     */
    private static final int NOTIFY_ID = 101;

    public ServiceNotifications() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // запускаем задачу
        new CheckNotifications().execute();
        return START_REDELIVER_INTENT;
    }

    /**
     * AsyncTask for get notification from server and create notification in device
     */
    private class CheckNotifications extends AsyncTask<Void, Void, ArrayList<Post>>{

        @Override
        protected ArrayList<Post> doInBackground(Void... voids) {
            // получаем данные с сервера
            ArrayList<Post> posts = new Core().getNotification();
            Log.d("MyService", "posts = "+ posts);

            if (posts != null && posts.size() == 1){

                // загружаем изображения
                String urldisplay = posts.get(0).getThumbnail_image();
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                urldisplay = posts.get(0).getScreenshot_urls();
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    screenshot = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
            return posts;
        }

        @Override
        protected void onPostExecute(ArrayList<Post> posts) {
            super.onPostExecute(posts);
            if (posts != null && posts.size() != 0){

                // создаём уведомление на устройстве
                Intent notificationIntent = new Intent(ServiceNotifications.this, PostActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(ServiceNotifications.this,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                Notification.Builder builder = new Notification.Builder(ServiceNotifications.this);

                if (posts.size() == 1) {
                    Post post = posts.get(0);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    screenshot.compress(Bitmap.CompressFormat.PNG, 0, stream);
                    byte[] byteArray = stream.toByteArray();

                    notificationIntent.putExtra("Name", post.getName());
                    notificationIntent.putExtra("Description", post.getTagline());
                    notificationIntent.putExtra("Votes", String.valueOf(post.getVotes_count()));
                    notificationIntent.putExtra("Screenshot", byteArray);
                    notificationIntent.putExtra("url", post.getUrl());

                    builder.setContentIntent(contentIntent)
                            .setSmallIcon(R.drawable.style_btn_get_it)
                            .setLargeIcon(bitmap)
                            .setWhen(System.currentTimeMillis())
                            .setAutoCancel(true)
                            .setContentTitle(post.getName())
                            .setContentText(post.getTagline());
                } else {
                    builder.setContentIntent(contentIntent)
                            .setSmallIcon(R.drawable.style_btn_get_it)
                            .setLargeIcon(BitmapFactory.decodeResource(ServiceNotifications.this.getResources(), R.drawable.style_btn_get_it))
                            .setWhen(System.currentTimeMillis())
                            .setAutoCancel(true)
                            .setContentTitle("New posts")
                            .setContentText(posts.size() + " new posts");
                }


                Notification notification = builder.build();
                notification.defaults = Notification.DEFAULT_LIGHTS |
                        Notification.DEFAULT_VIBRATE;

                NotificationManager notificationManager = (NotificationManager) ServiceNotifications.this
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFY_ID, notification);
            }

            // перезапускаем задачу
            new CheckNotifications().execute();
        }
    }
}
