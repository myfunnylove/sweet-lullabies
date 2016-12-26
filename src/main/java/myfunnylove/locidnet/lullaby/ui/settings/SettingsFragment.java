package myfunnylove.locidnet.lullaby.ui.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import myfunnylove.locidnet.lullaby.R;
import myfunnylove.locidnet.lullaby.player.Player;
import myfunnylove.locidnet.lullaby.ui.base.BaseFragment;
import myfunnylove.locidnet.lullaby.ui.base.Const;
import myfunnylove.locidnet.lullaby.ui.base.Preferences;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/1/16
 * Time: 9:59 PM
 * Desc: SettingsFragment
 */
public class SettingsFragment extends BaseFragment {


    @BindViews({R.id.alarmOff,R.id.alarmQuarter,R.id.alarmHalf, R.id.alarmHour, R.id.alarmHalfHour,R.id.alarm2Hour})
    List<AppCompatRadioButton> radioButtons;


    @BindView(R.id.setAlarm)
    Button setAlarmButton;


    private int id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        radioButtons.get(Preferences.getInstance().getTimerId()).setChecked(true);
    }

    @OnCheckedChanged({R.id.alarmOff,R.id.alarmQuarter,R.id.alarmHalf, R.id.alarmHour, R.id.alarmHalfHour,R.id.alarm2Hour})
    public void OnCheckedChanged(AppCompatRadioButton button, boolean isChecked ) {
        if (isChecked) {
            setAlarmButton.setBackgroundColor(getResources().getColor(R.color.mp_theme_dark_blue_colorPrimaryDark));


            onItemChecked(radioButtons.indexOf(button));
        }
    }



    AlarmManager am;


    @OnClick(R.id.setAlarm)
    public void onSetTime(View view){


        Preferences.getInstance().setTimerId(id);

        if (Player.isPaused == 0){


            setAlarmButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        am = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(),AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        am.cancel(pendingIntent);
        long time = System.currentTimeMillis() + Const.timer.get(id);
        am.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
        }

//        am = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(getActivity(),AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//
//        am.cancel(pendingIntent);
//        long time = System.currentTimeMillis() + 5000;
//        am.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);


    }


    private void onItemChecked(int position) {

        id = position;

    }
}
