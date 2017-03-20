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

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.chats.ChatsPresenter;
import com.internship.droidz.talkin.presentation.view.chats.ChatsView;


public class PrivateChatFragment extends Fragment implements ChatsView, View.OnClickListener {

    @InjectPresenter
    ChatsPresenter mPresenter;



    Context context;
    Boolean chatsExist = false;

    private TextView textViewFragmentConnection;
    private RecyclerView mRecyclerView;

    public PrivateChatFragment() {
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
        View view = inflater.inflate(R.layout.fragment_private_chat, container, false);

        context = container.getContext();

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentPrivateChat);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        final TextView chatsListEmpty = (TextView) view.findViewById(R.id.textViewFragmentPrivateChatEmpty);

        textViewFragmentConnection = (TextView) view.findViewById(R.id.textViewFragmentConnection);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentPrivateChat);

        if (chatsExist) {
            PrivateChatAdapter adapter = new PrivateChatAdapter();
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

    public void showConnectionProblemTitle() {

        textViewFragmentConnection.animate()
                .translationY( 1 * textViewFragmentConnection.getHeight())
                .alpha(1.0f)
                .setDuration(500);
        mRecyclerView.animate()
                .translationY( 1 * textViewFragmentConnection.getHeight())
                .setDuration(1000);
    }

    public void hideConnectionProblemTitle() {

        textViewFragmentConnection.animate()
                .translationY( -1 * textViewFragmentConnection.getHeight())
                .alpha(0.0f)
                .setDuration(500);
        mRecyclerView.animate()
                .translationY( -1 * textViewFragmentConnection.getHeight())
                .setDuration(1000);

    }

    @Override
    public void onClick(View v) {

    }
}
