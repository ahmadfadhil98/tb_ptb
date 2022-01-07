package com.example.aplikasiptb;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class AppNotificationService extends FirebaseMessagingService {

    Intent intent;
    String bookingId,homestayId;
    Integer idBooking,idHomestay,status;
    PendingIntent pendingIntent;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        bookingId = remoteMessage.getData().get("booking_id");
        idBooking = Integer.valueOf(bookingId);
        homestayId = remoteMessage.getData().get("homestay_id");
        idHomestay = Integer.valueOf(homestayId);
        status = Integer.valueOf(remoteMessage.getData().get("status"));


        //tampilkan nnotifikasi
        displayNotification(title,message);

    }

    private void displayNotification(String title, String message){
//        Panggil Manager Notifikasi
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//        Buat channel dan tambahkan ke notification manager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "com.example.aplikasiptb.CH02",
                    "Channel Notifikasi Booking",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        if(status==1){
            intent = new Intent(getApplicationContext(),InfoPembayaranActivity.class);
            intent.putExtra("homestayId",idHomestay);
            intent.putExtra("idBooking",idBooking);
        }else if(status==2){
            intent = new Intent(getApplicationContext(),ArahActivity.class);
        }


        pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                1231,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

//        Bikin Notifikasi
        Notification notification = new NotificationCompat.Builder(getApplicationContext(),"com.example.aplikasiptb.CH01")
                .setSmallIcon(R.drawable.info)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .addAction(
                        R.drawable.info,
                        "CLICK ME",
                        pendingIntent
                )
                .build();

//        Random random =
        notificationManager.notify(123,notification);

    }
}