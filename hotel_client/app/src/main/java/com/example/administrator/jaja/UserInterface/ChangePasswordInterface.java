package com.example.administrator.jaja.UserInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jaja.R;
import com.example.administrator.jaja.RecvThread.CancelReservationThread;
import com.example.administrator.jaja.RecvThread.ChangePasswordThread;
import com.example.administrator.jaja.UI.CustomProgressDialog;
import com.example.administrator.jaja.messageType;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ChangePasswordInterface extends Activity implements View.OnClickListener {
    private Handler mHandler;
    private Button changePasswordButton;
    private EditText originPasswordEditText;
    private EditText newPasswordEditText;
    private TextView accountTextView;

    private CustomProgressDialog dialog;
    private int account;//用户账户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_password_layout);
        dialog =new CustomProgressDialog(this, "密码修改中",R.anim.frame);
        account = (int) getIntent().getSerializableExtra("UserAccount");
        findViewByIdAndSetListener();
        initHandler();
        // loadDataToListView();
        //loadRoomFromServer();//从服务器获取可用的房间数据，然后显示到列表上
    }

    public void findViewByIdAndSetListener() {
        changePasswordButton = (Button) findViewById(R.id.change_password_button);
        changePasswordButton.setOnClickListener(this);

        accountTextView = (TextView) findViewById(R.id.account_text);

        accountTextView.setText(Integer.toString(account));

        originPasswordEditText=(EditText)findViewById(R.id.origin_password_edit);

        newPasswordEditText=(EditText)findViewById(R.id.new_password_edit);

    }

    public void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == messageType.CHANGE_SUCCESS.ordinal()) {
                    Toast.makeText(getApplicationContext(), "密码修改成功", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                if (msg.arg1 == messageType.ORIGIN_PASSWD_ERROR.ordinal()) {
                    Toast.makeText(getApplicationContext(), "原始密码错误", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_password_button:
                ChangePasswordThread changePasswordThread = new ChangePasswordThread(messageType.CHANGE_PASSWORD,
                        originPasswordEditText.getText().toString(),
                        newPasswordEditText.getText().toString(),account, mHandler);
                changePasswordThread.start();
                dialog.show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();
        }
        return false;
    }

    /**
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };
}
