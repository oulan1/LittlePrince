package com.example.littleprince;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView imgDay, imgEvening, imgNight;
    private ConstraintLayout layoutMorning;

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "PRINCE_CHANNEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgDay = findViewById(R.id.imageDay);
        imgEvening = findViewById(R.id.imageEvening);
        imgNight = findViewById(R.id.imageNight);
        layoutMorning = findViewById(R.id.layoutMorning);

        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        createChannelIfNeeded(notificationManager);
    }

    public void btnMorningClick(View view) {
        layoutMorning.setVisibility(View.VISIBLE);
        imgDay.setVisibility(View.INVISIBLE);
        imgEvening.setVisibility(View.INVISIBLE);
        imgNight.setVisibility(View.INVISIBLE);

        sendNotification("Утро", "Привести в порядок свою планету", R.drawable.cleaning);
    }

    public void btnDayClick(View view) {
        layoutMorning.setVisibility(View.INVISIBLE);
        imgDay.setVisibility(View.VISIBLE);
        imgEvening.setVisibility(View.INVISIBLE);
        imgNight.setVisibility(View.INVISIBLE);

        sendNotification("День", "Полить розу", R.drawable.rose);
    }

    public void btnEveningClick(View view) {
        layoutMorning.setVisibility(View.INVISIBLE);
        imgDay.setVisibility(View.INVISIBLE);
        imgEvening.setVisibility(View.VISIBLE);
        imgNight.setVisibility(View.INVISIBLE);

        sendNotification("Вечер", "Закрыть розу ширмой", R.drawable.home);
    }

    public void btnNightClick(View view) {
        layoutMorning.setVisibility(View.INVISIBLE);
        imgDay.setVisibility(View.INVISIBLE);
        imgEvening.setVisibility(View.INVISIBLE);
        imgNight.setVisibility(View.VISIBLE);

        sendNotification("Ночь", "Полюбоваться звездами", R.drawable.sunset);
    }

    private void sendNotification(String title, String message, int iconResId) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0)
        );

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(true)
                        .setSmallIcon(iconResId)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "LittlePrinceChannel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public void onPrinceClick(View view) {
        showToast("Маленький принц");
    }

    public void onPlanetClick(View view) {
        showToast("Астероид Б-612, планета на которой живет Маленький принц");
    }

    public void onVolcanoClick(View view) {
        showToast("Потухший вулкан. О нем нужно заботиться");
    }

    public void onRoseClick(View view) {
        showToast("Её нужно поливать, а на ночь укрывать ширмой и колпаком");
    }

    public void onBreakfastClick(View view) {
        showToast("Действующий вулкан, на нем удобно разогревать завтрак");
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.show();
    }
}