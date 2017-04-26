package com.maksg.mobilebutler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.TextView;
import scheduler.SettingsChangeTask;
import utils.TextUtils;

public class ScheduleEventActivity extends AppCompatActivity {
    private SettingsChangeTask settingsChangeTask;
    private TextView dateTimeTextView, settingsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event);

        settingsChangeTask = getIntent().getParcelableExtra("Task");

        dateTimeTextView = (TextView) findViewById(R.id.dateTimeTextView);
        dateTimeTextView.setText(String.format("Выбранное дата и время:\n%s", DateUtils.formatDateTime(this,
                settingsChangeTask.getStartMoment().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_WEEKDAY |
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR)));

        String wifiState = settingsChangeTask.isWifiEnabled() ? "Включить" : "Выключить";
        String bluetoothState = settingsChangeTask.isBluetoothEnabled() ? "Включить" : "Выключить";
        String text = "Выбранные настройки:\n" +
                "Громкость предупреждений: " + Integer.toString(settingsChangeTask.getAlarmVolume()) + "\n" +
                "Громкость музыки: " + Integer.toString(settingsChangeTask.getMusicVolume()) + "\n" +
                "Громкость оповещений: " + Integer.toString(settingsChangeTask.getNotificationVolume()) + "\n" +
                "Громкость мелодии звонка: " + Integer.toString(settingsChangeTask.getRingtoneVolume()) + "\n" +
                "Громкость системных звуков: " + Integer.toString(settingsChangeTask.getSystemVolume()) + "\n" +
                "Состояние Wi-Fi: " + wifiState + "\n" +
                "Состояние Bluetooth: " + bluetoothState;

        settingsTextView = (TextView) findViewById(R.id.settingsTextView);
        settingsTextView.setText(TextUtils.makeSectionOfTextBold(text, "Громкость предупреждений:",
                "Громкость музыки:", "Громкость оповещений:", "Громкость оповещений:", "Громкость мелодии звонка:",
                "Громкость системных звуков:", "Состояние Wi-Fi:", "Состояние Bluetooth:"));
    }
}