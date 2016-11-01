package cn.ucai.superwechat.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.utils.MFGT;

public class GuideActivity extends AppCompatActivity {
   GuideActivity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_guide);
    }
    @OnClick
    public void onclick(View view){
        switch (view.getId()){
            case  R.id.img_login:
                MFGT.gotoLogin(context);
                break;
            case R.id.img_register:
                MFGT.gotoRegister(context);
                break;
        }
    }
}
