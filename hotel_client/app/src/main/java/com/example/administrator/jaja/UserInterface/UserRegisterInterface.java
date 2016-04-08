package com.example.administrator.jaja.UserInterface;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;
import com.example.administrator.jaja.R;
import com.example.administrator.jaja.RecvThread.RegisterThread;
import com.example.administrator.jaja.UI.CustomProgressDialog;
import com.example.administrator.jaja.messageType;

/**
 * Created by Administrator on 2016/3/23.
 */
public class UserRegisterInterface  extends Activity implements View.OnClickListener{
    private Button sendRegisterButton;
    private EditText passwordEditText;
    private Handler mHandler;

    private CustomProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_register_layout);
        dialog =new CustomProgressDialog(this, "正在注册中",R.anim.frame);
        findViewByIdAndSetListener();
        initHandler();
    }

    public void findViewByIdAndSetListener(){
        sendRegisterButton=(Button)findViewById(R.id.send_register_button);
        sendRegisterButton.setOnClickListener(this);

        passwordEditText=(EditText)findViewById(R.id.user_password);
    }

    public void initHandler(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == messageType.USER_REGISTER.ordinal()){
                    if(msg.arg1 == messageType.REGISTER_SUCCESS.ordinal())
                    {
                        Toast.makeText(getApplicationContext(),"注册成功,注册账号为:"+msg.obj,Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                    if(msg.arg1 == messageType.REGISTER_FAILED.ordinal())
                    {
                        Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.send_register_button:
                send_register_data();
                break;
        }
    }

    public void send_register_data(){
        RegisterThread registerThread=new RegisterThread(passwordEditText.getText().toString(),
                messageType.USER_REGISTER,mHandler);
        registerThread.start();
        dialog.show();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
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

    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
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
