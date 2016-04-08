package com.example.administrator.jaja.RecvClass;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/31.
 */
public class RoomData implements Serializable {
    public int roomID;
    public int roomType;
    public int bedNumber;
    public int window;
    public int price;
    public int ownerAccount;
    public RoomData(int roomID,int roomType,int bedNumber,int window,int price,int ownerAccount){
        this.roomID=roomID;
        this.roomType=roomType;
        this.bedNumber=bedNumber;
        this.window=window;
        this.price=price;
        this.ownerAccount=ownerAccount;
    }
}
