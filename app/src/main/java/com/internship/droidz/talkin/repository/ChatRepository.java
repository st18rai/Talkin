package com.internship.droidz.talkin.repository;

import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.CacheSharedPreference;
import com.internship.droidz.talkin.data.db.model.DbDialogModel;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.requests.chat.CreatePublicDialogRequest;
import com.internship.droidz.talkin.data.web.response.chat.DialogModel;
import com.internship.droidz.talkin.data.web.service.ChatService;

import java.util.List;

import rx.Observable;

/**
 * Created by Novak Alexandr on 20.02.2017.
 */

public class ChatRepository {

    private ChatService chatService;
    private ContentRepository contentRepository;
    private CacheSharedPreference cache;

    public ChatRepository(ApiRetrofit retrofitApi) {
        this.contentRepository = new ContentRepository(retrofitApi);
        this.chatService= retrofitApi.getChatService();
        this.cache = CacheSharedPreference.getInstance(App.getApp().getApplicationContext());

    }

    public Observable<DialogModel> createPublicDialog(String name,String token)
    {
        CreatePublicDialogRequest request = new CreatePublicDialogRequest(name);
        return chatService.createDialog(request,token)
                .flatMap(this::storeToDb);
    }
//
//    public Observable<DialogModel> createPublicDialog(String name, File photo, String token)
//    {
//       return contentRepository.uploadFile(AmazonConstants.CONTENT_TYPE_JPEG, photo, name + "_avatar")
//                .flatMap(new Func1<GetFileResponse, Observable<DialogModel>>() {
//                    @Override
//                    public Observable<DialogModel> call(GetFileResponse getFileResponse) {
//                        CreatePublicDialogRequest request = new CreatePublicDialogRequest(name,getFileResponse.getBlob().getId());
//
//                        return chatService.createDialog(request,cache.getToken());
//                    }
//                })
//               .flatMap(this::storeToDb);
//    }
//
//    public Observable<DialogModel> createPrivateDialog(List<Integer> occupants_ids)
//    {
//        CreatePrivateDialogRequest request = new CreatePrivateDialogRequest(occupants_ids);
//        return chatService.createDialog(request,cache.getToken())
//                .flatMap(this::storeToDb);
//    }
//
//
//
//    public Observable<DialogModel> createPrivateDialog(List<Integer> occupants_ids, File photo)
//    {
//        return contentRepository.uploadFile(
//                AmazonConstants.CONTENT_TYPE_JPEG,
//                photo,
//                occupants_ids.hashCode()+"_avatar")
//                .flatMap(new Func1<GetFileResponse, Observable<DialogModel>>() {
//                    @Override
//                    public Observable<DialogModel> call(GetFileResponse getFileResponse) {
//                        CreatePrivateDialogRequest request = new CreatePrivateDialogRequest(occupants_ids,getFileResponse.getBlob().getId());
//                        return chatService.createDialog(request,cache.getToken());
//                    }
//                })
//                .flatMap(this::storeToDb);
//    }
//
//    public Observable<DialogModel> createGroupDialog(List<Integer> occupants_ids,String name)
//    {
//        CreateGroupDialogRequest request = new CreateGroupDialogRequest(occupants_ids,name);
//        return chatService.createDialog(request,cache.getToken());//.flatMap(this::storeToDb);
//    }
//
//    public Observable<DialogModel> createGroupDialog(List<Integer> occupants_ids,String name, File photo)
//    {
//        return contentRepository.uploadFile(AmazonConstants.CONTENT_TYPE_JPEG,photo,name+"_avatar")
//                .flatMap(new Func1<GetFileResponse, Observable<DialogModel>>() {
//                    @Override
//                    public Observable<DialogModel> call(GetFileResponse getFileResponse) {
//                        CreateGroupDialogRequest request = new CreateGroupDialogRequest(occupants_ids,name,getFileResponse.getBlob().getId());
//                        return chatService.createDialog(request,cache.getToken());
//                    }
//                })
//                .flatMap(this::storeToDb);
//    }

    private Observable<DialogModel> storeToDb(DialogModel dialogModel)
    {
        DbDialogModel dialog = new DbDialogModel();
        dialog.setDialog(dialogModel);
        dialog.save();
        return Observable.just(dialogModel);
    }

    public List<DbDialogModel> getAllDialogs()
    {
        return DbDialogModel.listAll(DbDialogModel.class);
    }

}
