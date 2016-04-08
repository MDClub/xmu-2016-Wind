package com.example.administrator.jaja.RecvThread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.jaja.messageType;
import com.example.administrator.jaja.net.NetDataTypeTransform;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ChangePasswordThread extends Thread {
    private Handler mHandler;
    private DataInputStream netInputStream;
    private DataOutputStream netOutputStream;
    private Socket clientSocket;
    private int msgType;
    private int userAccount;
    private String originPassword;
    private String newPassword;
    public NetDataTypeTransform netDataTypeTransform;
    public ChangePasswordThread(messageType type,String originPassword,String newPassword,int userAccount,Handler handler) {
        msgType = type.ordinal();
        netDataTypeTransform = new NetDataTypeTransform();
        this.mHandler = handler;
        this.userAccount=userAccount;
        this.originPassword=originPassword;
        this.newPassword=newPassword;
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket("103.37.167.225", 8887);
            netInputStream = new DataInputStream(clientSocket.getInputStream());
            netOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("messageType", msgType);
            jsonObject.put("userAccount",this.userAccount);
            jsonObject.put("originPassword",this.originPassword);
            jsonObject.put("newPassword",this.newPassword);
            netOutputStream.write((jsonObject.toString()).getBytes());
            netOutputStream.flush();
            byte[] receive = new byte[1024];
            String rec;
            netInputStream.read(receive);
            rec = netDataTypeTransform.ByteArraytoString(receive, receive.length);
            Log.d("accept:", rec);
            JSONObject jsonObject1;
            jsonObject1= new JSONObject(rec);
            Message message = new Message();
            message.what = msgType;
            message.arg1=jsonObject1.getInt("ack");
            this.mHandler.sendMessage(message);
            netOutputStream.close();
            netInputStream.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
