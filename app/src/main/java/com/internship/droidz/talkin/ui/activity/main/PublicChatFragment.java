package com.internship.droidz.talkin.ui.activity.main;

import android.content.Context;
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

import java.util.ArrayList;

public class PublicChatFragment extends Fragment {

    // Temporary solution
    Boolean chatsExist = true;
    Boolean connectionExist = true;

    public PublicChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_public_chat, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentPublicChat);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        final TextView chatsListEmpty = (TextView) view.findViewById(R.id.textViewFragmentPublicChatEmpty);
        final TextView connectionProblemTV = (TextView) view.findViewById(R.id.textViewFragmentConnection);

        // Temporary solution
        ArrayList<String> chatName = new ArrayList<>();
        ArrayList<String> userName = new ArrayList<>();
        ArrayList<String> lastMessage = new ArrayList<>();
        ArrayList<String> lastTime = new ArrayList<>();
        ArrayList<Integer> imageIds = new ArrayList<>();

        chatName.add("Chat 1");
        chatName.add("Chat 2");
        chatName.add("Chat 3");
        chatName.add("Chat 4");
        chatName.add("Chat 5");
        chatName.add("Chat 6");
        chatName.add("Chat 7");

        userName.add("Bob");
        userName.add("Karl");
        userName.add("John");
        userName.add("Bill");
        userName.add("Sara");
        userName.add("Mary");
        userName.add("Jack");

        lastMessage.add("Message 1");
        lastMessage.add("Message 2");
        lastMessage.add("Message 3");
        lastMessage.add("Message 4");
        lastMessage.add("Message 5");
        lastMessage.add("Message 6");
        lastMessage.add("Message 7");


        lastTime.add("12:54");
        lastTime.add("11:59");
        lastTime.add("15:37");
        lastTime.add("12:12");
        lastTime.add("17:24");
        lastTime.add("10:32");
        lastTime.add("07:44");

        imageIds.add(R.drawable.userpic);
        imageIds.add(R.drawable.userpic);
        imageIds.add(R.drawable.userpic);
        imageIds.add(R.drawable.userpic);
        imageIds.add(R.drawable.userpic);
        imageIds.add(R.drawable.userpic);
        imageIds.add(R.drawable.userpic);

        if (chatsExist) {
            PublicChatAdapter adapter = new PublicChatAdapter(chatName, userName, lastMessage, lastTime, imageIds);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        } else {
            chatsListEmpty.setVisibility(View.VISIBLE);
        }

        if (connectionExist) {
            connectionProblemTV.setVisibility(View.GONE);
        } else {
            connectionProblemTV.setVisibility((View.VISIBLE));
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.setRefreshing(false);
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
