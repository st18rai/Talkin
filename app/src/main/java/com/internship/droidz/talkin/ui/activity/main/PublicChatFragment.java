package com.internship.droidz.talkin.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.data.db.model.DbDialogModel;
import com.internship.droidz.talkin.data.web.service.SmackService;
import com.internship.droidz.talkin.ui.activity.conversation.ConversationActivity;

import java.util.ArrayList;


public class PublicChatFragment extends Fragment {

    Boolean chatsExist = true;

    public PublicChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //test
        Intent intent = new Intent(getContext(), SmackService.class);
        intent.putExtra("id", "1"); //TODO test
        getContext().startService(intent);
        Intent intent1 = new Intent(getContext(), ConversationActivity.class);
        intent1.putExtra("chatId", id); //TODO test
        getContext().startActivity(intent1);
        //test

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_public_chat, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentPublicChat);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        final TextView chatsListEmpty = (TextView) view.findViewById(R.id.textViewFragmentPublicChatEmpty);

        if (chatsExist) {
            PublicChatAdapter adapter = new PublicChatAdapter(new ArrayList<DbDialogModel>());
            recyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        } else {
            chatsListEmpty.setVisibility(View.VISIBLE);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
