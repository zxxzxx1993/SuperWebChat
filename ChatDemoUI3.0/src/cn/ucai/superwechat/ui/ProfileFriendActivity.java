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
import cn.ucai.superwechat.utils.MFGT;

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
    User user;
    @Bind(R.id.friend_sendmessage)
    Button friendSendmessage;
    @Bind(R.id.friend_chat)
    Button friendChat;
    @Bind(R.id.friend_add)
    Button friendAdd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_profile);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra(I.User.USER_NAME);
        if (user == null) {
            MFGT.finish(this);
        }
        setUserInfo();
    }

    private void setUserInfo() {
        EaseUserUtils.setAppUserAvatar(ProfileFriendActivity.this, user.getMUserName(), ivFriend);
        EaseUserUtils.setAppUserNick(user.getMUserNick(), friendUsernick);
        EaseUserUtils.setAppUserNameEhino(user.getMUserName(), friendUsername);
        isFriend();
    }

    private void isFriend() {
        if (SuperWeChatHelper.getInstance().getAppContactList().containsKey(user.getMUserName())) {
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
