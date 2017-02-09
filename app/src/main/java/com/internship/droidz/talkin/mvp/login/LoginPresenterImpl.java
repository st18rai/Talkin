package com.internship.droidz.talkin.mvp.login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.SessionWithAuthRequest;
import com.internship.droidz.talkin.data.web.requests.UserRequestModel;
import com.internship.droidz.talkin.data.web.response.file.CreateFileResponse;
import com.internship.droidz.talkin.data.web.response.file.UploadFileResponse;
import com.internship.droidz.talkin.repository.SessionRepository;
import com.internship.droidz.talkin.utils.ProcessTimerReceiver;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Joyker on 19.01.2017.
 */

public class LoginPresenterImpl implements LoginContract.LoginPresenter {

    LoginContract.LoginModel model;
    LoginContract.LoginView view;

    int TIME_TO_SEND_NOTIFICATION = 15 * 60;

    public LoginPresenterImpl(LoginContract.LoginModel model, LoginContract.LoginView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void checkAndStartTimer(Context context) {
        if (!App.getApp().getBackgroundChecker().isAppInForeground()) {
            Log.i("TAG", "checkAndStartTimer: ");
            AlarmManager processTimer = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            Intent intent = new Intent(context, ProcessTimerReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            processTimer.set(AlarmManager.RTC, System.currentTimeMillis() + TIME_TO_SEND_NOTIFICATION * 1000, pendingIntent);
        }
    }

    @Override
    public void stopTimer(Context context) {
        Log.i("TAG", "stopTimer: ");
        Intent intent = new Intent(context, ProcessTimerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void signIn(String email, String password) {
        SessionRepository repository = new SessionRepository(ApiRetrofit.getRetrofitApi());
        repository.signIn(email,password,view);
        }
    }
