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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.jaja.R;
import com.example.administrator.jaja.RecvClass.Login;
import com.example.administrator.jaja.RecvClass.RoomData;
import com.example.administrator.jaja.RecvThread.GetRoomDataThread;
import com.example.administrator.jaja.UI.CustomProgressDialog;
import com.example.administrator.jaja.messageType;
/**
 * Created by Administrator on 2016/3/29.
 * 入住活动
 */
public class roomReservationInterface extends Activity implements View.OnClickListener {
    private Handler mHandler;
    private Button doReservationButton;
    private EditText roomIdEditText;
    private TextView accountTextView;

    private CustomProgressDialog dialog;

    private int account;//用户账户

    //private ListView availableRoomListView;
    //String[] roomDataString = {"100", "101", "102", "103", "104", "200", "201", "202", "203", "204", "300", "301", "302", "303", "304"};
    //ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.room_reservation_layout);
        dialog =new CustomProgressDialog(this, "正在订房中",R.anim.frame);
        account= (int)getIntent().getSerializableExtra("UserAccount");
        findViewByIdAndSetListener();
        initHandler();
       // loadDataToListView();
        //loadRoomFromServer();//从服务器获取可用的房间数据，然后显示到列表上
    }

    public void findViewByIdAndSetListener() {
//        availableRoomListView = (ListView) findViewById(R.id.list_view);
//        availableRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
        roomIdEditText=(EditText)findViewById(R.id.room_id_edittext);

        doReservationButton=(Button)findViewById(R.id.do_reservation_button);
        doReservationButton.setOnClickListener(this);

        accountTextView=(TextView)findViewById(R.id.account_text);
        accountTextView.setText(Integer.toString(account));
    }

//    public void loadRoomFromServer()
//    {
//        GetRoomDataThread getRoomDataThread = new GetRoomDataThread(messageType.GET_ROOM_DATA, mHandler);
//        getRoomDataThread.start();
//    }

//    public void initHandler() {
//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                int count = 0;
//                RoomData roomData = (RoomData) msg.obj;
//                if (msg.what == messageType.GET_ROOM_DATA.ordinal()) {
//                    if (msg.arg1 == messageType.ROOM_DATA_LOADING.ordinal()) {
//                        //   Log.i("123", String.valueOf(roomDataString.length));
//                        roomDataString[count] = "房号:" + roomData.roomID + " 价格:" + roomData.price;
//                        if (1 == roomData.roomType)//标准单间
//                            roomDataString[count] += " 标准";
//                        if (2 == roomData.roomType)//豪华
//                            roomDataString[count] += " 豪华";
//                        if (1 == roomData.window)//是否有窗户
//                            roomDataString[count] += " 有窗";
//                        if (0 == roomData.window)//是否有窗户
//                            roomDataString[count] += " 无窗";
//                        count++;
//
//                    } else if (msg.arg1 == messageType.ROOM_DATA_LOAD_COMPLETED.ordinal()) {//读取结束载入
//                        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
//                        loadDataToListView();
//
//                    }
//                }
//            }
//        };
//    }

    public void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == messageType.ROOM_RESERVATION_SUCCESS.ordinal()) {
                    Toast.makeText(getApplicationContext(), "入住成功", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                if (msg.arg1 == messageType.ROOM_RESERVATION_FAILURE.ordinal()) {
                    Toast.makeText(getApplicationContext(), "入住失败", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        };
    }

//    public void loadDataToListView() {
//        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.hhssb, roomDataString);
//        availableRoomListView.setAdapter(adapter);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.do_reservation_button:
                GetRoomDataThread getRoomDataThread = new GetRoomDataThread(messageType.DO_RESERVATION,
                        Integer.parseInt(roomIdEditText.getText().toString()),account,mHandler);
                getRoomDataThread.start();
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
