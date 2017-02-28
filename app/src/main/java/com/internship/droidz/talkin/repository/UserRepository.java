package com.internship.droidz.talkin.repository;

import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.CacheSharedPreference;
import com.internship.droidz.talkin.data.db.model.DbUserModel;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.requests.chat.CreatePublicDialogRequest;
import com.internship.droidz.talkin.data.web.requests.user.UserSearchRequest;
import com.internship.droidz.talkin.data.web.response.chat.DialogModel;
import com.internship.droidz.talkin.data.web.response.user.UserSearchResponse;
import com.internship.droidz.talkin.data.web.service.UserService;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import rx.Observable;

/**
 * Created by Novak Alexandr on 28.02.2017.
 */

public class UserRepository {

    private UserService userService;
    private CacheSharedPreference cache;

    public UserRepository(ApiRetrofit retrofit)
    {
        this.userService=retrofit.getUserService();
        this.cache = CacheSharedPreference.getInstance(App.getApp().getApplicationContext());
    }


    public Observable<UserSearchResponse> searchUser(String full_name)
    {
        UserSearchRequest request = new UserSearchRequest(full_name);
        return userService.searchUser(full_name,cache.getToken());
        //.flatMap(this::storeToDb);
    }

    public void storeToDb(UserSearchResponse.User user)
    {
        DbUserModel model = new DbUserModel(user);
        model.save();
    }

    public List<DbUserModel> getAllUsers()
    {
       return DbUserModel.listAll(DbUserModel.class);
    }

    public DbUserModel getUserById(Integer id)
    {
       return Select.from(DbUserModel.class)
                .where(Condition.prop("user_id").eq(id)).first();
    }

}
