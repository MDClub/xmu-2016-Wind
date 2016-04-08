package com.example.administrator.jaja.RecvThread;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.jaja.RecvClass.RoomData;
import com.example.administrator.jaja.messageType;
import com.example.administrator.jaja.net.NetDataTypeTransform;
import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
/**
 * Created by Administrator on 2016/3/30.
 */
public class GetRoomDataThread extends Thread {
    private Handler mHandler;
    private DataInputStream netInputStream;
    private DataOutputStream netOutputStream;
    private Socket clientSocket;
    private int msgType;
    private int roomID;
    private int userAccount;
    public RoomData roomData;
    public NetDataTypeTransform netDataTypeTransform;

    public GetRoomDataThread(messageType type,int roomID,int userAccount,Handler handler) {
        msgType = type.ordinal();
        netDataTypeTransform = new NetDataTypeTransform();
        this.mHandler = handler;
        this.roomID=roomID;
        this.userAccount=userAccount;
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket("103.37.167.225", 8887);
            netInputStream = new DataInputStream(clientSocket.getInputStream());
            netOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("messageType", msgType);
            jsonObject.put("roomID",roomID);
            jsonObject.put("userAccount",this.userAccount);
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
//    @Override
//    public void run() {
//        try {
//            clientSocket = new Socket("103.37.167.225", 8887);
//            netInputStream = new DataInputStream(clientSocket.getInputStream());
//            netOutputStream = new DataOutputStream(clientSocket.getOutputStream());
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("messageType", msgType);
//            netOutputStream.write((jsonObject.toString()).getBytes());
//            netOutputStream.flush();
//
//            byte[] receive = new byte[1024];
//            String rec;
//            JSONObject jsonObject1;
//            for(int i=0;i<16;i++)
//            {
//                netInputStream.read(receive);
//                rec = netDataTypeTransform.ByteArraytoString(receive, receive.length);
//                Log.d("ds",rec);
//                jsonObject1= new JSONObject(rec);
////              if(jsonObject1.getInt("ack") == messageType.ROOM_DATA_LOAD_COMPLETED.ordinal())
////                   break;
//                roomData = new RoomData(jsonObject1.getInt("room_ID"),jsonObject1.getInt("room_type"),
//                        jsonObject1.getInt("bed_number"),jsonObject1.getInt("window"),jsonObject1.getInt("price"),
//                        jsonObject1.getInt("owner_account"));
//                Message message = new Message();
//                message.what = msgType;
//                message.arg1 = jsonObject1.getInt("ack");
//                Log.d("ack", Integer.toString(jsonObject1.getInt("ack")));
//                message.obj = roomData;
//                this.mHandler.sendMessage(message);
//            }
//            Message message = new Message();
//            message.what = msgType;
//            message.arg1 = messageType.ROOM_DATA_LOAD_COMPLETED.ordinal();
//            this.mHandler.sendMessage(message);
//            netOutputStream.close();
//            netInputStream.close();
//            clientSocket.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }