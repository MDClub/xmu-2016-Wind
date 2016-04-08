package com.example.administrator.jaja;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.administrator.jaja.RecvClass.Login;
import com.example.administrator.jaja.RecvThread.loginThread;
import com.example.administrator.jaja.UI.CustomProgressDialog;
import com.example.administrator.jaja.UserInterface.UserManagementInterface;
import com.example.administrator.jaja.UserInterface.UserRegisterInterface;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button LoginButton;
    private Button RegisterButton;
    private EditText AccountEditText;
    private EditText PasswordEditText;
    private Handler mHandler;

    private CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_login);
        dialog =new CustomProgressDialog(this, "正在登录中",R.anim.frame);
        findViewByIdAndSetListener();
        initHandler();
    }

    public void findViewByIdAndSetListener(){
        AccountEditText=(EditText)findViewById(R.id.login_edtId);
        PasswordEditText=(EditText)findViewById(R.id.login_edtPwd);

        LoginButton=(Button)findViewById(R.id.login_btn);
        LoginButton.setOnClickListener(this);

        RegisterButton=(Button)findViewById(R.id.register_btn);
        RegisterButton.setOnClickListener(this);
    }

    public void initHandler(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                    if(msg.what == messageType.LOGIN.ordinal()) //messageType.LOGIN
                    {
                        if(msg.arg1 == messageType.LOGIN_SUCCESS.ordinal() )//验证成功则跳转
                        {
                            startUserManagementActivity((Login) msg.obj);
                            dialog.dismiss();
                        } else
                            Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_LONG).show();
                    }
                    if(msg.what == messageType.USER_REGISTER.ordinal())
                    {
                        Toast.makeText(getApplicationContext(), msg.arg1 + "ok=" + msg.obj, Toast.LENGTH_SHORT).show();
                    }
                }
        };
    }

    public void startUserManagementActivity(Login login){
        Intent startUserManagement=new Intent(this,UserManagementInterface.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("user_info",login);
        startUserManagement.putExtras(mBundle);
        this.startActivity(startUserManagement);
    }

    public void UserRegisterActivity(){
        Intent UserRegister=new Intent(this,UserRegisterInterface.class);
        this.startActivity(UserRegister);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                doLogin();
                break;
            case R.id.register_btn:
                doRegister();
                break;
        }
    }

    public void doLogin(){
        loginThread login=new loginThread(AccountEditText.getText().toString(),
                PasswordEditText.getText().toString(),messageType.LOGIN,mHandler);
        login.start();
        dialog.show();
    }

    public void doRegister(){//启动注册对话框活动
        UserRegisterActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
