package com.internship.droidz.talkin.presentation.presenter.login;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.model.LoginModel;
import com.internship.droidz.talkin.presentation.view.login.LoginView;
import com.internship.droidz.talkin.repository.SessionRepository;
import com.internship.droidz.talkin.utils.ProcessTimerReceiver;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    LoginModel model;
    LoginView view = getViewState();

    int TIME_TO_SEND_NOTIFICATION = 15 * 60;

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    public void checkAndStartTimer(Context context) {
        if (!App.getApp().getBackgroundChecker().isAppInForeground()) {
            Log.i("TAG", "checkAndStartTimer: ");
            AlarmManager processTimer = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            Intent intent = new Intent(context, ProcessTimerReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            processTimer.set(AlarmManager.RTC, System.currentTimeMillis() + TIME_TO_SEND_NOTIFICATION * 1000, pendingIntent);
        }
    }

    public void stopTimer(Context context) {
        Log.i("TAG", "stopTimer: ");
        Intent intent = new Intent(context, ProcessTimerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public void signIn(String email, String password) {
        SessionRepository repository = new SessionRepository(ApiRetrofit.getRetrofitApi());
        repository.signIn(email, password, view);
    }
}
