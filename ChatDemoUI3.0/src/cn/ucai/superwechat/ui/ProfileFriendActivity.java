package cn.ucai.superwechat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.User;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatHelper;
import cn.ucai.superwechat.bean.Result;
import cn.ucai.superwechat.data.NetDao;
import cn.ucai.superwechat.data.OkHttpUtils;
import cn.ucai.superwechat.utils.MFGT;
import cn.ucai.superwechat.utils.ResultUtils;

/**
 * Created by Administrator on 2016/11/7.
 */
public class ProfileFriendActivity extends BaseActivity {
    @Bind(R.id.iv_friend)
    ImageView ivFriend;
    @Bind(R.id.friend_usernick)
    TextView friendUsernick;
    @Bind(R.id.friend_username)
    TextView friendUsername;
    User user = null;
    @Bind(R.id.friend_sendmessage)
    Button friendSendmessage;
    @Bind(R.id.friend_chat)
    Button friendChat;
    @Bind(R.id.friend_add)
    Button friendAdd;
    String username;
    boolean isfriend = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_profile);
        ButterKnife.bind(this);
        username =  getIntent().getStringExtra(I.User.USER_NAME);
        if (username == null) {
            MFGT.finish(this);
        }
        User user = SuperWeChatHelper.getInstance().getAppContactList().get(username);
        if (user == null){
           isfriend = false;
        }
        else {
            isfriend = true;
        }
        syncUserInfo();
    }

    private void syncUserInfo() {
        NetDao.searchUser(this, username, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s!=null){
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result!=null&&result.isRetMsg()){
                   User u = (User) result.getRetData();
                        if (u!=null){
                            if (isfriend){
                                SuperWeChatHelper.getInstance().saveAppContact(user);
                            }
                            user = u;
                            setUserInfo();

                        }else {
                            if (!isfriend){
                            MFGT.finish(ProfileFriendActivity.this);
                            return;}
                        }
                    }else {
                        if (!isfriend){
                            MFGT.finish(ProfileFriendActivity.this);
                            return;}
                    }
                }else {
                    if (!isfriend){
                        MFGT.finish(ProfileFriendActivity.this);
                        return;}
                }
            }

            @Override
            public void onError(String error) {
                if (!isfriend){
                    MFGT.finish(ProfileFriendActivity.this);
                    return;}
            }
        });
    }

    private void setUserInfo() {
        EaseUserUtils.setAppUserAvatar(ProfileFriendActivity.this, user.getMUserName(), ivFriend);
        EaseUserUtils.setAppUserNick(user.getMUserNick(), friendUsernick);
        EaseUserUtils.setAppUserNameEhino(user.getMUserName(), friendUsername);
    }

    private void isFriend(boolean isFriend) {
        if (isFriend) {
            friendSendmessage.setVisibility(View.VISIBLE);
            friendChat.setVisibility(View.VISIBLE);
            friendAdd.setVisibility(View.GONE);
        }
        else {
            friendSendmessage.setVisibility(View.GONE);
            friendChat.setVisibility(View.GONE);
            friendAdd.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.friend_back)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.friend_sendmessage, R.id.friend_chat, R.id.friend_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.friend_sendmessage:
                MFGT.gotoChat(this,user.getMUserName());
                break;
            case R.id.friend_chat:
                if (!EMClient.getInstance().isConnected())
                    Toast.makeText(this, R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
                else {
                    startActivity(new Intent(this, VideoCallActivity.class).putExtra("username", user.getMUserName())
                            .putExtra("isComingCall", false));
                    // videoCallBtn.setEnabled(false);
//                    inputMenu.hideExtendMenuContainer();
                }
                break;
            case R.id.friend_add:
                MFGT.gototheAddFriend(this,user.getMUserName());
                break;
        }
    }
}
