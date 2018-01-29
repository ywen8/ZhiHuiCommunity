package com.rain.zhihui_community.ui.activity.group_chat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.rain.zhihui_community.MessageReceiver;
import com.rain.zhihui_community.MessageSender;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseActivity;
import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.db.ChatDbManager;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.GroupChatDB;
import com.rain.zhihui_community.entity.Neighbor;
import com.rain.zhihui_community.services.MessageService;
import com.rain.zhihui_community.ui.adapter.ChatGroupAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class GroupChatActivity extends BaseActivity<ChatContract.IChatView, ChatPresenter> implements ChatContract.IChatView {

    private Neighbor neighbor;
    private ChatGroupAdapter adapter;
    @BindView(R.id.et_sendmessage)
    EditText et_sendmessage;
    @BindView(R.id.btn_send)
    Button btn_send;
    @BindView(R.id.listview)
    ListView listview;

    //所有数据集合
    private List<GroupChatDB> allList = new ArrayList<>();

    //当前群组的聊天记录
    private List<GroupChatDB> list = new ArrayList<>();

    //当前用户信息
    private GroupChatDB userGroupChat;

    //获取最后一条数据的时间
    private GroupChatDB fastGroupChat = new GroupChatDB();

    private String mTextMessage;
    private ChatDbManager mChatDbManager;

    private MessageSender messageSender;
    private Handler handler = new Handler();

    @OnClick(R.id.btn_send)
    void send() {
        mTextMessage = et_sendmessage.getText().toString();
        userGroupChat.setCreatetime(getDate());
        userGroupChat.setMessage(mTextMessage);
        list.add(userGroupChat);
        adapter.notifyDataSetChanged();
        et_sendmessage.setText("");
        listview.setSelection(listview.getCount() - 1);
        judgeListView();
        presenter.send();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_group_chat);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        AppManager.getAppManager().addActivity(this);
        persons = GloData.getPersons().getUser();
        neighbor = (Neighbor) getIntent().getSerializableExtra("neighbor");
        TitleUtls.TitleItemView(this, neighbor.getNname() + "(" + neighbor.getTotalnumber() + ")人");
        userGroupChat = new GroupChatDB(persons.getUsername(), persons.getHeadimg(), persons.getId(), neighbor.getNid());
        mChatDbManager = new ChatDbManager();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        initData();
        initLitener();
        setupService();
    }

    private void setupService() {
        Intent intent = new Intent(this, MessageService.class);
        Map<String, String> receiveMap = new HashMap<>();
        receiveMap.put("nid", neighbor.getNid());
        receiveMap.put("time", fastGroupChat.getCreatetime());
        intent.putExtra("map", (Serializable) receiveMap);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        startService(intent);

    }

    /**
     * Binder可能会意外死忙（比如Service Crash），Client监听到Binder死忙后可以进行重连服务等操作
     */
    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (messageSender != null) {
                messageSender.asBinder().unlinkToDeath(this, 0);
                messageSender = null;
            }
            setupService();
        }
    };
    //消息监听回调接口
    private MessageReceiver messageReceiver = new MessageReceiver.Stub() {

        @Override
        public void onMessageReceived(GroupChatDB groupChatDB) throws RemoteException {
            Log.i(TAG, "onMessageReceived: " + groupChatDB.toString());
            mChatDbManager.insert(groupChatDB);
            new Thread() {
                public void run() {
                    handler.post(udpUIRunnable); //向Handler post runnable对象
                }
            }.start();

        }
    };
    // 构建Runnable对象，并在runnable中更新UI
    Runnable udpUIRunnable = new Runnable() {
        @Override
        public void run() {
            list = getData();
            judgeListView();
//            Log.i(TAG, "initData:33333333 " + list.size() + ":" + list.toString());
            adapter.setList(list);
        }
    };

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            //使用asInterface方法取得AIDL对应的操作接口
            messageSender = MessageSender.Stub.asInterface(service);
            GroupChatDB groupChatDB = new GroupChatDB();
            groupChatDB.setMessage("client user id");
            groupChatDB.setUsername("receiver user id");
            groupChatDB.setCreatetime("This is message content");
            try {
                //设置Binder死亡监听
                messageSender.asBinder().linkToDeath(deathRecipient, 0);
                //把接收消息的回调接口注册到服务端
                messageSender.registerReceiveListener(messageReceiver);
                //调用远程Service的sendMessage方法，并传递消息实体对象
                messageSender.sendMessage(groupChatDB);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected ChatPresenter initPresenter() {
        return new ChatPresenter();
    }

    @Override
    protected BaseModule initModule() {
        return new ChatModule(this);
    }

    private void initLitener() {
        et_sendmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    btn_send.setEnabled(true);
                } else {
                    btn_send.setEnabled(false);
                }
            }
        });
    }

    private void initData() {
        list = getData();
//        Log.i(TAG, "initData:1111111111 " + list.size() + ":" + list.toString());
        if (list != null && list.size() == 0) {
            userGroupChat.setCreatetime(getDate());
            mTextMessage = "大家好,我是" + persons.getUsername();
            userGroupChat.setMessage(mTextMessage);
            mChatDbManager.insert(userGroupChat);
            allList = mChatDbManager.loadAll();
            list = getData();
//            Log.i(TAG, "initData:222222222 " + list.size() + ":" + list.toString());
            presenter.send();
        }
        judgeListView();
        fastGroupChat = list.get(list.size() - 1);

        adapter = new ChatGroupAdapter(this, list);
        listview.setAdapter(adapter);
    }

    void judgeListView() {
        if (list.size() > 8) {
            listview.setStackFromBottom(true);
            listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        } else {
            listview.setStackFromBottom(false);
            listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        }
    }

    /**
     * 根据nid分割总表数据 返回集合
     *
     * @return
     */
    List<GroupChatDB> getData() {
        List<GroupChatDB> data = new ArrayList<>();
        allList = mChatDbManager.loadAll();
        String nid = neighbor.getNid();
        for (GroupChatDB groupData :
                allList) {
            if (groupData.getSex().equals(nid)) {
//                Log.i(TAG, "getData: " + neighbor.getNid() + ":::::::::::" + groupData.getSex());
                data.add(groupData);
            }
        }
        return data;
    }

    //获取日期
    private String getDate() {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return myDateFormat.format(curDate);
    }

    @Override
    public Map<String, String> getSendmap() {
        Map<String, String> sendMap = new HashMap<>();
        sendMap.put("uid", persons.getId());
        sendMap.put("nid", neighbor.getNid());
        sendMap.put("time", getDate());
        sendMap.put("messages", mTextMessage);
        return sendMap;
    }

    @Override
    public void onSendResult(ResponseBody responseBody) {
        try {
            String response = responseBody.string();
            JSONObject json = new JSONObject(response);
            int state = json.getInt("code");
            if (state == 1000) {
//                showToast("发送成功");
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<String, String> getReceivemap() {
        Map<String, String> receiveMap = new HashMap<>();
        receiveMap.put("nid", neighbor.getNid());
        receiveMap.put("time", fastGroupChat.getCreatetime());
        return receiveMap;
    }

    @Override
    public void onReceiveResult(List<GroupChatDB> chatList) {
        Log.i(TAG, "onReceiveResult: " + chatList.toString());

    }

    /**
     * 1.unregisterListener
     * 2.unbindService
     */
    @Override
    protected void onDestroy() {
        //解除消息监听接口
        mChatDbManager.clearDaoSession();
        handler.removeCallbacks(udpUIRunnable);
        if (messageSender != null && messageSender.asBinder().isBinderAlive()) {
            try {
                messageSender.unregisterReceiveListener(messageReceiver);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
