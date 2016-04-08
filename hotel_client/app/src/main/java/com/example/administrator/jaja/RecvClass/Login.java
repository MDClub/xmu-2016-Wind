package com.example.administrator.jaja.RecvClass;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/29.
 */
public class Login implements Serializable{
    public int UserAccount;
    public String password;
    public int balance;
    public Login(int userAccount,String password,int balance){
        this.UserAccount=userAccount;
        this.balance=balance;
        this.password=password;
    }
}
