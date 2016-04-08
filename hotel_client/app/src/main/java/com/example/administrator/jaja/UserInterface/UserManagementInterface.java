package com.example.administrator.jaja.UserInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.example.administrator.jaja.R;
import com.example.administrator.jaja.RecvClass.Login;
/**
 * Created by Administrator on 2016/3/15.
 */
public class UserManagementInterface extends Activity implements View.OnClickListener{
    private Button cancelReservationButton;//续住
    private Button ReservationButton;
    private Button changePasswordButton;
    private TextView accountText;
    private Login loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_management_layout);

        //setContentView(R.layout.activity_main1);
        findViewByIdAndSetListener();
        loadLoginData();
    }

    public void findViewByIdAndSetListener(){

        accountText=(TextView)findViewById(R.id.account_text);
        cancelReservationButton=(Button)findViewById(R.id.cancel_reservation_button);
        cancelReservationButton.setOnClickListener(this);

        ReservationButton=(Button)findViewById(R.id.reservation_button);
        ReservationButton.setOnClickListener(this);

        changePasswordButton=(Button)findViewById(R.id.change_password_button);
        changePasswordButton.setOnClickListener(this);
    }

    public void loadLoginData(){
        loginInfo=getLoginInfo();

        accountText.setText("账号:" + Integer.toString(loginInfo.UserAccount));
    }
    public Login getLoginInfo(){
        return (Login) getIntent().getSerializableExtra("user_info");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reservation_button://启动入住活动
                StartReservationIntent();
                break;
            case R.id.cancel_reservation_button:
                StartCancelReservationIntent();
                break;
            case R.id.book_button:
                break;
            case R.id.view_consumption_button:
                break;
            case R.id.change_password_button:
                StartChangePasswordIntent();

                break;
        }
    }

    public void StartCancelReservationIntent(){
        Intent StartCancelReservation=new Intent(this, cancelRoomReservationInterface.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("UserAccount",loginInfo.UserAccount);
        StartCancelReservation.putExtras(mBundle);
        this.startActivity(StartCancelReservation);
    }

    public void StartReservationIntent(){
        Intent Reservation=new Intent(this, roomReservationInterface.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("UserAccount",loginInfo.UserAccount);
        Reservation.putExtras(mBundle);
        this.startActivity(Reservation);
    }

    public void StartChangePasswordIntent(){
        Intent changePasswordIntent=new Intent(this, ChangePasswordInterface.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("UserAccount",loginInfo.UserAccount);
        changePasswordIntent.putExtras(mBundle);
        this.startActivity(changePasswordIntent);
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


