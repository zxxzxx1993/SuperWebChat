package cn.ucai.superwechat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.redpacketui.utils.RedPacketUtil;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.Constant;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.utils.MFGT;

/**
 * Created by Administrator on 2016/11/5.
 */
public class ProfileFragment extends Fragment {
    @Bind(R.id.profile_avatar)
    ImageView profileAvatar;
    @Bind(R.id.profile_nick)
    TextView profileNick;
    @Bind(R.id.profile_weixin)
    TextView profileWeixin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
       setUserInfo();
    }

    private void setUserInfo() {
        EaseUserUtils.setCurentAppUserAvatar(getActivity(),profileAvatar);
        EaseUserUtils.setCurentAppUserNick(profileNick);
        EaseUserUtils.setCurentAppUserName(profileWeixin);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.layout_profile, R.id.profile_money, R.id.profile_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_profile:
                MFGT.gotoProfile(getActivity());
                break;
            //red packet code : 进入零钱页面
            case R.id.profile_money:
                RedPacketUtil.startChangeActivity(getActivity());
                break;
            //end of red packet code

            case R.id.profile_setting:
                MFGT.gotosetting(getActivity());
                break;
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(((MainActivity)getActivity()).isConflict){
            outState.putBoolean("isConflict", true);
        }else if(((MainActivity)getActivity()).getCurrentAccountRemoved()){
            outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }
    }
}
