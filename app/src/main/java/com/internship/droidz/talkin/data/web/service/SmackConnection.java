package com.internship.droidz.talkin.data.web.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.internship.droidz.talkin.data.CacheSharedPreference;
import com.internship.droidz.talkin.data.web.ApiRetrofit;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.DefaultExtensionElement;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.id.StanzaIdUtil;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.PacketParserUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Алексей on 28.03.2017.
 */

public class SmackConnection implements MessageListener, ConnectionListener {

    private static final String TAG = "SmackConnection";
    private final String mServer = "chat.quickblox.com";

    private XMPPTCPConnection mConnection;
    private final Context mApplicationContext;
    private MultiUserChat MUC;
    private Message mMessage;
    private String chatID;
    private MultiUserChatManager mManager;
    DefaultExtensionElement extensionElement;
    private BroadcastReceiver mMessageReceiver;
    private CacheSharedPreference mCachedSharedPreferences;


    public SmackConnection(CacheSharedPreference cachedSharedPreferences, Context context, String chatID) {

        mCachedSharedPreferences = cachedSharedPreferences;
        mApplicationContext = context;
        this.chatID = chatID;
    }

    public void establishConnection() throws IOException, SmackException, XMPPException {

        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(String.valueOf(mCachedSharedPreferences.getUserId()),
                        mCachedSharedPreferences.getCurrentPassword()) //TODO replace preferences with DB methods
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setServiceName(mServer)
                .setPort(5555)
                .build();
        mConnection = new XMPPTCPConnection(config);
        mConnection.addConnectionListener(this);
        mConnection.connect();
        mConnection.login();

        setupBroadcastMessageReceiver();

//        ChatManager.getInstanceFor(mConnection).addChatListener((chat, createdLocally) -> {
//            chat.addMessageListener(messageListener);
//        });

        mManager = MultiUserChatManager.getInstanceFor(mConnection);
        MUC = mManager.getMultiUserChat(ApiRetrofit.APP_ID + "_" + chatID + "@muc.chat.quickblox.com");
        MUC.join(mConnection.getUser());

        MUC.addMessageListener(this);
    }

    public void disconnect() {

        if (mConnection != null) {
            mConnection.disconnect();
        }
        mConnection = null;
        if (mMessageReceiver != null) {
            mApplicationContext.unregisterReceiver(mMessageReceiver);
            mMessageReceiver = null;
        }
    }

    private void sendMessage(String body, String to) {

        mMessage = new Message();
        long timestamp = System.currentTimeMillis() / 1000;
        extensionElement = new DefaultExtensionElement("extraParams", "jabber:client");
        extensionElement.setValue("save_to_history", "1");
        extensionElement.setValue("date_sent", timestamp + "");
        extensionElement.setValue("notification_type", timestamp + "");
        mMessage.setBody(body);
        Log.d("message : ", body);
        mMessage.setStanzaId(StanzaIdUtil.newStanzaId());
        mMessage.setType(Message.Type.groupchat);
        mMessage.setTo(to);
        mMessage.addExtension(extensionElement);

        try {
            MUC.sendMessage(mMessage);
            Log.d(TAG, "send message : " + mMessage.toXML().toString());
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processMessage(Message message) {

        String messageID = getMessageID(message);
        Intent intent = new Intent(SmackService.NEW_MESSAGE);
        intent.putExtra(SmackService.MESSAGE_ID, messageID);
        mApplicationContext.sendBroadcast(intent);
    }

    private String getMessageID(Message message) {

        ExtensionElement extensionElement = message.getExtension("extraParams", "jabber:client");
        String temp = extensionElement.toXML().toString();
        String messageID = null;

        try {
            XmlPullParser parser = PacketParserUtils.getParserFor(temp);
            CharSequence name = PacketParserUtils.parseContent(parser);
            XmlPullParser parser2 = PacketParserUtils.getParserFor(String.valueOf(name));
            messageID = PacketParserUtils.parseElementText(parser2);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageID;
    }

    private void setupBroadcastMessageReceiver() {

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(SmackService.SEND_MESSAGE)) {
                    sendMessage(intent.getStringExtra(SmackService.BUNDLE_MESSAGE_BODY), intent.getStringExtra(SmackService.BUNDLE_TO));
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(SmackService.SEND_MESSAGE);
        mApplicationContext.registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void connected(XMPPConnection xmppConnection) {

    }

    @Override
    public void authenticated(XMPPConnection xmppConnection, boolean b) {

    }

    @Override
    public void connectionClosed() {

    }

    @Override
    public void connectionClosedOnError(Exception e) {

    }

    @Override
    public void reconnectionSuccessful() {

    }

    @Override
    public void reconnectingIn(int i) {

    }

    @Override
    public void reconnectionFailed(Exception e) {

    }
}
