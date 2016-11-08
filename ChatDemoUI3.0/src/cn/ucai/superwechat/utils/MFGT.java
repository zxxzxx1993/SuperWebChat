package cn.ucai.superwechat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.hyphenate.easeui.domain.User;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.ui.AddContactActivity;
import cn.ucai.superwechat.ui.ChatActivity;
import cn.ucai.superwechat.ui.LoginActivity;
import cn.ucai.superwechat.ui.MainActivity;
import cn.ucai.superwechat.ui.ProfileFriendActivity;
import cn.ucai.superwechat.ui.RegisterActivity;
import cn.ucai.superwechat.ui.SendToaddFriendActivity;
import cn.ucai.superwechat.ui.SettingsActivity;
import cn.ucai.superwechat.ui.UserProfileActivity;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
public static void gotoLogin(Activity context){
    startActivity(context, LoginActivity.class);
}
    public static void gotoRegister(Activity context){
        startActivity(context, RegisterActivity.class);
    }

    public static void gotosetting(Activity context) {
        startActivity(context, SettingsActivity.class);
    }

    public static void gotoProfile(Activity activity) {
        startActivity(activity, UserProfileActivity.class);
    }

    public static void gotoAddFriend(Activity activity) {
        startActivity(activity, AddContactActivity.class);
    }

    public static void gotoProfileFriend(Context context, User user) {
        Intent intent = new Intent();
        intent.setClass(context, ProfileFriendActivity.class);
        intent.putExtra(I.User.USER_NAME,user);
        context.startActivity(intent);
    }

    public static void gototheAddFriend(ProfileFriendActivity profileFriendActivity, String mUserName) {
        Intent intent = new Intent();
        intent.setClass(profileFriendActivity, SendToaddFriendActivity.class);
        intent.putExtra(I.User.USER_NAME,mUserName);
        profileFriendActivity.startActivity(intent);
    }
    public static void gotoChat(Context context, String username) {
        Intent intent = new Intent();
        intent.setClass(context, ChatActivity.class);
        intent.putExtra("userId",username);
        context.startActivity(intent);
    }
}
