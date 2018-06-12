package app.com.dunkeydelivery.NotificationUtils;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.MainActivity;
import app.com.dunkeydelivery.activities.SplashActivity;

/**
 * Created by Developer on 2/12/2018.
 */

public class GenerateNotification {

    private static Integer notificationId = 0;

    public static void sendNotificationSimpleText(Context context, String messageTitle, String messageBody, String type, String jsonData) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("EntityId", messageBody);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
        notificationBuilder.setContentTitle("Order Status")
                .setContentText(messageTitle)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setChannelId(context.getString(R.string.google_app_id))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.app_name);
            String description = context.getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(context.getString(R.string.google_app_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
        ++notificationId;
    }
}
