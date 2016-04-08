package com.example.administrator.jaja.RecvClass;

/**
 * Created by Administrator on 2016/3/29.
 */
import java.io.Serializable;

public class Register implements Serializable {
    public int UserAccount;
    public String password;
    public int balance;
    public Register(int userAccount,String password,int balance){
        this.UserAccount=userAccount;
        this.balance=balance;
        this.password=password;
    }
}
