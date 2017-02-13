package com.internship.droidz.talkin.repository;

import android.util.Log;

import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.CacheSharedPreference;
import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.model.UserModel;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;
import com.internship.droidz.talkin.data.web.requests.RegistrationRequest;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.SessionWithAuthRequest;
import com.internship.droidz.talkin.data.web.requests.UserRequestModel;
import com.internship.droidz.talkin.data.web.requests.UserSignUpRequest;
import com.internship.droidz.talkin.data.web.service.ContentService;
import com.internship.droidz.talkin.data.web.service.UserService;
import com.internship.droidz.talkin.presentation.view.login.LoginView;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Novak Alexandr on 09.02.2017.
 */

public class SessionRepository {

    private UserService userService;
    private ContentService contentService;
    private CacheSharedPreference cache;

    public SessionRepository(ApiRetrofit retrofit) {
        this.userService = retrofit.getUserService();
        this.contentService = retrofit.getContentService();
        cache = CacheSharedPreference.getInstance(App.getApp().getApplicationContext());
    }

    public Observable<SessionModel> createSession() {
        int nonce = WebUtils.getNonce();
        long timestamp = System.currentTimeMillis() / 1000l;
        SessionRequest request = new SessionRequest(ApiRetrofit.APP_ID, ApiRetrofit.APP_AUTH_KEY,
                String.valueOf(nonce), String.valueOf(timestamp), WebUtils.calcSignature(nonce, timestamp));
        return userService.getSession(request);
    }

    private Observable<SessionModel> createSessionWithAuth(String email, String password, String token) {
        int nonce = WebUtils.getNonce();
        long timestamp = System.currentTimeMillis() / 1000l;
        SessionWithAuthRequest request = new SessionWithAuthRequest(
                new UserRequestModel(email, password),
                ApiRetrofit.APP_ID,
                ApiRetrofit.APP_AUTH_KEY,
                String.valueOf(nonce),
                String.valueOf(timestamp),
                WebUtils.calcSignature(nonce, timestamp,
                        email,
                        password));
        return userService.getSessionWithAuth(request, token);

    }

    public void signIn(String email, String password, LoginView view) {
        createSession().flatMap(sessionModel ->
                createSessionWithAuth(email, password, sessionModel.getSession().getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SessionModel>() {
                    @Override
                    public void onCompleted() {
                        Log.i("RX", "Logged in");
                        view.navigationToMainScreen();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                Log.i("retrofit login,", ((HttpException) e).response().errorBody().string());
                                view.showLoginError();

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            Log.i("error_login_user", "error: " + e.getMessage());
                            view.showNetworkError();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onNext(SessionModel sessionModel) {
                        cache.putToken(sessionModel.getSession().getToken());
                    }
                });
    }

    public Observable<SessionModel> signUp(String email, String password,
                                           String fullName, String phone, String website) {
        return createSession()
                .flatMap(new Func1<SessionModel, Observable<UserModel>>() {
                    @Override
                    public Observable<UserModel> call(SessionModel sessionModel) {
                        UserSignUpRequest requestReg = new UserSignUpRequest(email,
                                password, fullName, phone, website);
                        RegistrationRequest request = new RegistrationRequest(requestReg);
                        return userService.requestSignUp(request, sessionModel.getSession().getToken());
                    }
                }).flatMap(new Func1<UserModel, Observable<SessionModel>>() {
                    @Override
                    public Observable<SessionModel> call(UserModel userModel) {
                        return createSession();
                    }
                }).flatMap(new Func1<SessionModel, Observable<SessionModel>>() {
                    @Override
                    public Observable<SessionModel> call(SessionModel sessionModel) {
                        cache.putToken(sessionModel.getSession().getToken());
                        return createSessionWithAuth(email, password, sessionModel.getSession().getToken());
                    }
                });

    }
}
