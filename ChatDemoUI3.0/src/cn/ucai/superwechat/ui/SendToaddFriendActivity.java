package cn.ucai.superwechat.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.utils.MFGT;

/**
 * Created by Administrator on 2016/11/7.
 */
public class SendToaddFriendActivity extends BaseActivity {
    @Bind(R.id.send)
    Button send;
    @Bind(R.id.edit_note)
    EditText editNote;
    String username;
    String msg;
    @Bind(R.id.addfriend_back)
    ImageView addfriendBack;
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tosendmessage);
        ButterKnife.bind(this);
        inITView();
    }

    private void inITView() {
        username = getIntent().getStringExtra(I.User.USER_NAME);
        msg = getString(R.string.addcontact_send_msg_prefix) + EaseUserUtils.getCurrentAppUserInfo().getMUserNick();
        editNote.setText(msg);
             addfriendBack.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     MFGT.finish(SendToaddFriendActivity.this);
                 }
             });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSendMessage();
            }
        });
    }


    private void toSendMessage() {
        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.addcontact_adding);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(username, msg);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                            MFGT.finish(SendToaddFriendActivity.this);
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                            MFGT.finish(SendToaddFriendActivity.this);
                        }
                    });
                }
            }
        }).start();
    }


}
