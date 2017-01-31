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
import com.internship.droidz.talkin.utils.ProcessTimerReceiver;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Joyker on 19.01.2017.
 */

public class LoginPresenterImpl implements LoginContract.LoginPresenter {

    LoginContract.LoginModel model;
    LoginContract.LoginView view;

    int TIME_TO_SEND_GCM = 15 * 60;

    public LoginPresenterImpl (LoginContract.LoginModel model, LoginContract.LoginView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void checkAndStartTimer(Context context) {
        if ( !App.getApp().getBackgroundChecker().isAppInForeground()) {
            Log.i("TAG", "checkAndStartTimer: ");
            AlarmManager processTimer = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            Intent intent = new Intent(context, ProcessTimerReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            processTimer.set(AlarmManager.RTC, System.currentTimeMillis() + TIME_TO_SEND_GCM * 1000, pendingIntent);
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

        int nonce= WebUtils.getNonce();
        long timestamp = System.currentTimeMillis()/1000l;
        ApiRetrofit.getRetrofitApi().getUserService()
                .getSession(new SessionRequest(ApiRetrofit.APP_ID,ApiRetrofit.APP_AUTH_KEY,
                        String.valueOf(nonce),String.valueOf(timestamp),WebUtils.calcSignature(nonce,timestamp)))

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //  .subscribe(sessionModel -> {})
                .subscribe(new Subscriber<SessionModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("error","message: "+e.getMessage());
                        if (e instanceof HttpException) {
                            try
                            {
                                Log.i("retrofit error,",((HttpException) e).response().errorBody().string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onNext(SessionModel sessionModel) {
                        int nonce= WebUtils.getNonce();
                        long timestamp = System.currentTimeMillis()/1000l;
                        SessionWithAuthRequest request=  new SessionWithAuthRequest(
                                new UserRequestModel(email, password),
                                ApiRetrofit.APP_ID,
                                ApiRetrofit.APP_AUTH_KEY,
                                String.valueOf(nonce),
                                String.valueOf(timestamp),
                                WebUtils.calcSignature(nonce, timestamp,
                                        email,
                                        password));

                        ApiRetrofit.getRetrofitApi().getUserService()
                                .getSessionWithAuth(request,sessionModel.getSession().getToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<SessionModel>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.i("debug","logged in");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        if (e instanceof HttpException) {
                                            try
                                            {
                                                Log.i("retrofit error,",((HttpException) e).response().errorBody().string());
                                                Toast.makeText((LoginScreen)view,"Wrong login or password",Toast.LENGTH_LONG).show();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                        else
                                        {
                                            Log.i("error","some error");
                                        }

                                    }

                                    @Override
                                    public void onNext(SessionModel sessionModel) {
                                        Log.i("user",String.valueOf(sessionModel.getSession().getUser_id()));
                                        Log.i("token",String.valueOf(sessionModel.getSession().getToken()));
                                        view.navigationToMainScreen();
                                    }
                                });
                    }
                });
    }
}
