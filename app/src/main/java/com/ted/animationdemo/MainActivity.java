package com.ted.animationdemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WeChatFragment mWeChat;
    private FriendsFragment mFriend;
    private SceneFragment mScene;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        findViewById(R.id.wechat_bt).setOnClickListener(this);
        findViewById(R.id.scene_bt).setOnClickListener(this);
        findViewById(R.id.friend_bt).setOnClickListener(this);

        setDefaultScene();
    }

    private void setDefaultScene() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mScene = new SceneFragment();
        transaction.replace(R.id.fra_container,mScene);
        transaction.commit();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        switch (id){
            case R.id.wechat_bt:
                if (mWeChat == null){
                    mWeChat = new WeChatFragment();
                }
                transaction.replace(R.id.fra_container,mWeChat);
                break;
            case R.id.scene_bt:
                if (mScene == null){
                    mScene = new SceneFragment();
                }
                transaction.replace(R.id.fra_container, mScene);
                break;
            case R.id.friend_bt:
                if (mFriend == null){
                    mFriend = new FriendsFragment();
                }
                transaction.replace(R.id.fra_container, mFriend);
                break;
            default:
                break;
        }

        transaction.commit();
    }
}
