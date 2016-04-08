package com.example.administrator.jaja.RecvThread;
import android.os.Handler;
import android.os.Message;
import com.example.administrator.jaja.RecvClass.Login;
import com.example.administrator.jaja.messageType;
import com.example.administrator.jaja.net.NetDataTypeTransform;
import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
/**
 * Created by Administrator on 2016/3/17.
 */
public class loginThread extends Thread {
    private Handler mHandler;
    private DataInputStream netInputStream;
    private DataOutputStream netOutputStream;
    private Socket clientSocket;
    private String userAccount;
    private String Password;
    private int msgType;
    public NetDataTypeTransform netDataTypeTransform;

    public loginThread(String userAccount, String Password,messageType type,Handler handler) {
        this.userAccount = userAccount;
        this.Password = Password;
        msgType=type.ordinal();
        netDataTypeTransform=new NetDataTypeTransform();
        this.mHandler=handler;
    }
    @Override
    public void run() {
        try
        {
            clientSocket = new Socket("103.37.167.225", 8887);
            netInputStream = new DataInputStream(clientSocket.getInputStream());
            netOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("messageType", msgType);
            jsonObject.put("account", userAccount);
            jsonObject.put("password", Password);
            netOutputStream.write((jsonObject.toString()).getBytes());
            netOutputStream.flush();
            byte[] receive=new byte[1024];
            netInputStream.read(receive);
            String rec=netDataTypeTransform.ByteArraytoString(receive,receive.length);
            JSONObject jsonObject1=new JSONObject(rec);
            Login login=new Login(jsonObject1.getInt("usr_account"),
                    jsonObject1.getString("passwd"),jsonObject1.getInt("balance"));
            Message message=new Message();
            message.what=messageType.LOGIN.ordinal();//一层协议
            message.arg1=jsonObject1.getInt("ack");//二层
            message.obj=login;
            this.mHandler.sendMessage(message);
            netOutputStream.close();
            netInputStream.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
