package com.internship.droidz.talkin.model;

import com.internship.droidz.talkin.data.db.model.DbDialogModel;
import com.internship.droidz.talkin.repository.ChatRepository;

import java.util.List;

/**
 * Created by Koroqe on 19-Mar-17.
 */
public class ChatsModel {

    ChatRepository mChatRepository;

    public List<DbDialogModel> updateChatsList() {

        return mChatRepository.getAllDialogs();
    }


}
